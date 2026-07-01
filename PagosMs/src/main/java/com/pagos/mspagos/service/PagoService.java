package com.pagos.mspagos.service;

import com.pagos.mspagos.dto.PagoRequestDTO;
import com.pagos.mspagos.dto.PagoResponseDTO;
import com.pagos.mspagos.dto.ReservaEspejoDTO;
import com.pagos.mspagos.dto.NotificacionesEspejoDTO;
import com.pagos.mspagos.dto.MetricaEspejoDTO;
import com.pagos.mspagos.entity.Pago;
import com.pagos.mspagos.exception.PagoException;
import com.pagos.mspagos.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagoRepository repository;
    private final WebClient reservasWebClient;
    private final WebClient notificacionesWebClient;
    private final WebClient estadisticasWebClient;


    public PagoService(PagoRepository repository,
                       WebClient reservasWebClient,
                       WebClient notificacionesWebClient,
                       WebClient estadisticasWebClient) {
        this.repository = repository;
        this.reservasWebClient = reservasWebClient;
        this.notificacionesWebClient = notificacionesWebClient;
        this.estadisticasWebClient = estadisticasWebClient;
    }




    public List<PagoResponseDTO> listarTodosLosPagos() {
        return repository.findAll().stream()
                .map(pago -> new PagoResponseDTO(
                        pago.getId(),
                        pago.getReservaId(),
                        pago.getMonto(),
                        pago.getEstadoPago()
                ))
                .collect(Collectors.toList());
    }


    //   PROCESAR EL PAGO

    public PagoResponseDTO procesarPago(PagoRequestDTO request) {

        ReservaEspejoDTO reserva = null;

        //  VERIFICAR  LA RESERVA
        try {
            System.out.println(" [PAGOS] Consultando existencia de la reserva ID: " + request.getReservaId());

            reserva = reservasWebClient.get()
                    .uri("/{id}", request.getReservaId())
                    .retrieve()
                    .bodyToMono(ReservaEspejoDTO.class)
                    .block();

            System.out.println(" [PAGOS] Reserva encontrada en el orquestador: " + reserva);

        } catch (Exception e) {
            e.printStackTrace();
            throw new PagoException("ERROR REAL AL CONSULTAR RESERVA: " + e.getMessage());
        }

        //  procedemos a persistir el pago en MariaDB
        Pago p = new Pago();
        p.setReservaId(request.getReservaId());
        p.setMonto(request.getMonto());
        p.setMetodoPago(request.getMetodoPago());
        p.setEstadoPago("APROBADO");

        Pago guardado = repository.save(p);

        // COMPROBANTE A NOTIFICACIONES
        try {
            NotificacionesEspejoDTO aviso = new NotificacionesEspejoDTO();
            aviso.setCorreoDestino("kevis.test@gmail.com");
            aviso.setAsunto(" ¡Tu Pago ha sido Aprobado!");
            aviso.setMensaje("¡Pago aprobado con éxito! Tu reserva #" + guardado.getReservaId() + " por un monto de $" + guardado.getMonto() + " ya está confirmada. ¡A jugar!");

            notificacionesWebClient.post()
                    .bodyValue(aviso)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            System.out.println(" [PAGOS] ¡Comprobante de pago enviado con éxito a Notificaciones!");
        } catch (Exception e) {
            System.out.println(" [PAGOS] No se pudo notificar el pago: " + e.getMessage());
        }

        // ACTUALIZAR EL ESTADO EN MS-RESERVAS
        try {
            if (reserva != null) {
                reservasWebClient.put()
                        .uri("/{id}/estado?nuevoEstado=PAGADO", guardado.getReservaId())
                        .retrieve()
                        .bodyToMono(Void.class)
                        .block();

                System.out.println(" [PAGOS] ¡Estado de la reserva #" + guardado.getReservaId() + " actualizado a PAGADO en ms-reservas!");
            }
        } catch (Exception e) {
            System.out.println(" [PAGOS] No se pudo actualizar el estado en Reservas: " + e.getMessage());
        }

        // ACTUALIZAR LAS ESTADÍSTICAS
        try {
            MetricaEspejoDTO metrica = new MetricaEspejoDTO();
            metrica.setFecha(java.time.LocalDate.now());
            metrica.setTotalReservasCreadas(0);
            metrica.setTotalReservasPagadas(1);
            metrica.setTotalReservasCanceladas(0);
            metrica.setRecaudacionTotal(guardado.getMonto());

            estadisticasWebClient.post()
                    .bodyValue(metrica)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            System.out.println(" [PAGOS] ¡Métrica de recaudación enviada con éxito al puerto 8095 por $" + guardado.getMonto() + "!");
        } catch (Exception e) {
            System.out.println(" [PAGOS] No se pudo actualizar el módulo de estadísticas: " + e.getMessage());
        }

        return new PagoResponseDTO(
                guardado.getId(),
                guardado.getReservaId(),
                guardado.getMonto(),
                guardado.getEstadoPago()
        );
    }




    public PagoResponseDTO obtenerPagoPorId(Long id) {
        Pago pago = repository.findById(id)
                .orElseThrow(() -> new PagoException("No se encontró el pago con el ID: " + id));

        return new PagoResponseDTO(
                pago.getId(),
                pago.getReservaId(),
                pago.getMonto(),
                pago.getEstadoPago()
        );
    }

}