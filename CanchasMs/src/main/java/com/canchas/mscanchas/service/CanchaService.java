package com.canchas.mscanchas.service;

import com.canchas.mscanchas.dto.CanchaRequestDTO;
import com.canchas.mscanchas.dto.CanchaResponseDTO;
import com.canchas.mscanchas.dto.MetricaEspejoDTO;
import com.canchas.mscanchas.entity.Canchas;
import com.canchas.mscanchas.exception.CanchaException;
import com.canchas.mscanchas.repository.CanchaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // 👈 Importamos RestTemplate

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CanchaService {

    @Autowired
    private CanchaRepository repository;

    // Instanciamos RestTemplate para poder hacer la llamada HTTP
    private final RestTemplate restTemplate = new RestTemplate();

    public CanchaResponseDTO crearCancha(CanchaRequestDTO request) {

        if (request.getPrecioHora() <= 0) {
            throw new CanchaException("El precio por hora debe ser un valor positivo.");
        }

        Canchas cancha = new Canchas();
        cancha.setNombre(request.getNombre());
        cancha.setDeporte(request.getDeporte());
        cancha.setPrecioHora(request.getPrecioHora());
        cancha.setTipoPasto(request.getTipoPasto());

        Canchas guardada = repository.save(cancha);


        //  ESTADÍSTICAS (Puerto 8095)
        // =====================================================================
        try {
            MetricaEspejoDTO metrica = new MetricaEspejoDTO();
            metrica.setFecha(java.time.LocalDate.now());
            metrica.setTotalReservasCreadas(0);
            metrica.setTotalReservasPagadas(0);
            metrica.setTotalReservasCanceladas(0);
            metrica.setRecaudacionTotal(0.0);

            String urlEstadisticas = "http://localhost:8095/api/estadisticas";
            restTemplate.postForObject(urlEstadisticas, metrica, Object.class);

            System.out.println(" [CANCHAS] ¡Alerta de nueva cancha \"" + guardada.getNombre() + "\" enviada con éxito al puerto 8095!");
        } catch (Exception e) {
            // Usamos un try-catch controlado para que si Estadísticas está apagado, la cancha se cree igual
            System.out.println(" [CANCHAS] No se pudo notificar al módulo de estadísticas: " + e.getMessage());
        }
        // =====================================================================

        return convertirADto(guardada);
    }

    public List<CanchaResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public CanchaResponseDTO obtenerPorId(Long id) {
        Canchas cancha = repository.findById(id)
                .orElseThrow(() -> new CanchaException("No se encontró la cancha con el ID: " + id));
        return convertirADto(cancha);
    }

    private CanchaResponseDTO convertirADto(Canchas cancha) {
        return new CanchaResponseDTO(
                cancha.getId(),
                cancha.getNombre(),
                cancha.getDeporte(),
                cancha.getPrecioHora(),
                cancha.getTipoPasto()
        );
    }
}