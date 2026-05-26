package com.canchas.inventarioms.service;

import com.canchas.inventarioms.dto.ImplementoRequestDTO;
import com.canchas.inventarioms.dto.ImplementoResponseDTO;
import com.canchas.inventarioms.dto.MetricaEspejoDTO; // 👈 Importamos tu nuevo DTO espejo
import com.canchas.inventarioms.model.Implemento;
import com.canchas.inventarioms.repository.ImplementoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // 👈 Importamos RestTemplate

import java.util.List;

@Service
public class ImplementoService {

    private final ImplementoRepository repository;
    // Instanciamos RestTemplate para las llamadas HTTP
    private final RestTemplate restTemplate = new RestTemplate();

    public ImplementoService(ImplementoRepository repository) {
        this.repository = repository;
    }

    public ImplementoResponseDTO crear(ImplementoRequestDTO dto) {
        // Transformamos el DTO de entrada en un Modelo para guardarlo en la BD
        Implemento implemento = new Implemento(null, dto.getNombre(), dto.getCantidad(), dto.getEstado());
        Implemento guardado = repository.save(implemento);


        // ESTADÍSTICAS (Puerto 8095)
        // =====================================================================
        try {
            MetricaEspejoDTO metrica = new MetricaEspejoDTO();
            metrica.setFecha(java.time.LocalDate.now());
            metrica.setTotalReservasCreadas(0);      // 0 porque es inventario
            metrica.setTotalReservasPagadas(0);
            metrica.setTotalReservasCanceladas(0);
            metrica.setRecaudacionTotal(0.0);        // 0.0 porque no entra dinero de reserva

            String urlEstadisticas = "http://localhost:8095/api/estadisticas";
            restTemplate.postForObject(urlEstadisticas, metrica, Object.class);

            System.out.println(" [INVENTARIO] ¡Alerta de nuevo implemento \"" + guardado.getNombre() + "\" enviada con éxito al puerto 8095!");
        } catch (Exception e) {
            // Un try-catch controlado para que si Estadísticas cae, el inventario se registre igual
            System.out.println(" [INVENTARIO] No se pudo notificar al módulo de estadísticas: " + e.getMessage());
        }
        // =====================================================================

        // Transformamos el Modelo guardado en un DTO de salida para devolverlo
        return new ImplementoResponseDTO(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getCantidad(),
                guardado.getEstado()
        );
    }

    public List<ImplementoResponseDTO> listar() {
        // Buscamos todos los registros y los transformamos a DTOs de salida
        return repository.findAll().stream()
                .map(i -> new ImplementoResponseDTO(i.getId(), i.getNombre(), i.getCantidad(), i.getEstado()))
                .toList();
    }
}