package com.pagos.mspagos.service;

import com.pagos.mspagos.dto.PagoRequestDTO;
import com.pagos.mspagos.dto.PagoResponseDTO;
import com.pagos.mspagos.dto.ReservaEspejoDTO;
import com.pagos.mspagos.dto.NotificacionesEspejoDTO;
import com.pagos.mspagos.dto.MetricaEspejoDTO;
import com.pagos.mspagos.entity.Pago;
import com.pagos.mspagos.exception.PagoException;
import com.pagos.mspagos.repository.PagoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    @Value("${servicio.reservas.url}")
    private String reservasUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public PagoResponseDTO procesarPago(PagoRequestDTO request) {

        Long usuarioIdDeLaReserva = null;
        ReservaEspejoDTO reserva = null;

        try {

            String urlCompleta = reservasUrl + "/" + request.getReservaId();

            System.out.println("URL CONSULTADA: " + urlCompleta);

            reserva = restTemplate.getForObject(urlCompleta, ReservaEspejoDTO.class);

            System.out.println("RESERVA ENCONTRADA: " + reserva);

            if (reserva != null) {
                usuarioIdDeLaReserva = reserva.getUsuarioId();
            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new PagoException(
                    "ERROR REAL: " + e.getMessage()
            );
        }

        Pago pago = new Pago();

        pago.setReservaId(request.getReservaId());
        pago.setMonto(request.getMonto());
        pago.setMetodoPago(request.getMetodoPago());

        pago.setEstadoPago("APROBADO");

        Pago guardado = repository.save(pago);


        //  NOTIFICACIONES
        // =====================================================================
        try {
            NotificacionesEspejoDTO aviso = new NotificacionesEspejoDTO();


            aviso.setCorreoDestino("kevis.test@gmail.com");
            aviso.setAsunto(" ¡Tu Pago ha sido Aprobado!");
            aviso.setMensaje("¡Pago aprobado con éxito! Tu reserva #" + guardado.getReservaId() + " por un monto de $" + guardado.getMonto() + " ya está confirmada. ¡A jugar!");

            String urlNotificaciones = "http://localhost:8085/api/notificaciones";

            restTemplate.postForObject(urlNotificaciones, aviso, Object.class);
            System.out.println(" [PAGOS] ¡Comprobante de pago enviado con éxito a Notificaciones!");
        } catch (Exception e) {
            System.out.println("[PAGOS] No se pudo notificar el pago: " + e.getMessage());
        }
        // =====================================================================
        //  2. ACTUALIZAR EL ESTADO EN EL MS-RESERVAS
        // =====================================================================
        try {
            if (reserva != null) {
                String urlActualizarReserva = reservasUrl + "/" + guardado.getReservaId() + "/estado?nuevoEstado=PAGADO";
                restTemplate.put(urlActualizarReserva, null);
                System.out.println(" [PAGOS] ¡Estado de la reserva #" + guardado.getReservaId() + " actualizado a PAGADO en ms-reservas!");
            }
        } catch (Exception e) {
            System.out.println(" [PAGOS] No se pudo actualizar el estado en Reservas: " + e.getMessage());
        }


        //  ESTADÍSTICAS (Puerto 8095)
        // =====================================================================
        try {
            MetricaEspejoDTO metrica = new MetricaEspejoDTO();
            metrica.setFecha(java.time.LocalDate.now());
            metrica.setTotalReservasCreadas(0);
            metrica.setTotalReservasPagadas(1);
            metrica.setTotalReservasCanceladas(0);
            metrica.setRecaudacionTotal(guardado.getMonto());

            String urlEstadisticas = "http://localhost:8095/api/estadisticas";
            restTemplate.postForObject(urlEstadisticas, metrica, Object.class);

            System.out.println(" [PAGOS] ¡Métrica de recaudación enviada con éxito al puerto 8095 por $" + guardado.getMonto() + "!");
        } catch (Exception e) {
            System.out.println(" [PAGOS] No se pudo actualizar el módulo de estadísticas: " + e.getMessage());
        }
        // =====================================================================

        return new PagoResponseDTO(
                guardado.getId(),
                guardado.getReservaId(),
                guardado.getMonto(),
                guardado.getEstadoPago()
        );
    }
}