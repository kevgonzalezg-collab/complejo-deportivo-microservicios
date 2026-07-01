package com.canchas.inventarioms.service;

import com.canchas.inventarioms.dto.ImplementoRequestDTO;
import com.canchas.inventarioms.dto.ImplementoResponseDTO;
import com.canchas.inventarioms.dto.MetricaEspejoDTO;
import com.canchas.inventarioms.model.Implemento;
import com.canchas.inventarioms.repository.ImplementoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDate;
import java.util.List;

@Service
public class ImplementoService {

    private final ImplementoRepository repository;
    private final WebClient estadisticasWebClient;

    // Inyección limpia por constructor estricto
    public ImplementoService(ImplementoRepository repository, WebClient estadisticasWebClient) {
        this.repository = repository;
        this.estadisticasWebClient = estadisticasWebClient;
    }

    public ImplementoResponseDTO crear(ImplementoRequestDTO dto) {
        // Transformamos el DTO de entrada en un Modelo para guardarlo en la BD
        Implemento implemento = new Implemento(null, dto.getNombre(), dto.getCantidad(), dto.getEstado());
        Implemento guardado = repository.save(implemento);

        // ESTADÍSTICAS (Puerto 8095) - Migrado a WebClient con bloque síncrono controlado
        // =====================================================================
        try {
            MetricaEspejoDTO metrica = new MetricaEspejoDTO();
            metrica.setFecha(LocalDate.now());
            metrica.setTotalReservasCreadas(0);      // 0 porque es inventario
            metrica.setTotalReservasPagadas(0);
            metrica.setTotalReservasCanceladas(0);
            metrica.setRecaudacionTotal(0.0);        // 0.0 porque no entra dinero de reserva

            estadisticasWebClient.post()
                    .bodyValue(metrica)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block(); // Bloqueo controlado síncrono

            System.out.println(" [INVENTARIO] ¡Alerta de nuevo implemento \"" + guardado.getNombre() + "\" enviada con éxito al puerto 8095!");
        } catch (Exception e) {
            // Try-catch controlado por si Estadísticas está abajo, el inventario se registra igual
            System.out.println(" [INVENTARIO] No se pudo notificar al módulo de estadísticas: " + e.getMessage());
        }
        // =====================================================================

        return new ImplementoResponseDTO(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getCantidad(),
                guardado.getEstado()
        );
    }

    public List<ImplementoResponseDTO> listar() {
        return repository.findAll().stream()
                .map(i -> new ImplementoResponseDTO(i.getId(), i.getNombre(), i.getCantidad(), i.getEstado()))
                .toList();
    }
}