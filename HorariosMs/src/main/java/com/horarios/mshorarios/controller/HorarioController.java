package com.horarios.mshorarios.service;

import com.horarios.mshorarios.dto.HorarioRequestDTO;
import com.horarios.mshorarios.dto.HorarioResponseDTO;
import com.horarios.mshorarios.dto.CanchaEspejoDTO;
import com.horarios.mshorarios.entity.Horario;
import com.horarios.mshorarios.exception.HorarioException;
import com.horarios.mshorarios.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioController {

    @Autowired
    private HorarioRepository repository;

    @Value("${servicio.canchas.url}")
    private String canchasUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // 1. Crear un nuevo horario (El que ya teníamos impecable)
    public HorarioResponseDTO crearHorario(HorarioRequestDTO request) {
        try {
            String urlCompleta = canchasUrl + "/" + request.getCanchaId();
            restTemplate.getForObject(urlCompleta, CanchaEspejoDTO.class);
        } catch (Exception e) {
            throw new HorarioException("No se puede crear el horario: La cancha con ID "
                    + request.getCanchaId() + " no existe en el sistema.");
        }

        Horario horario = new Horario();
        horario.setCanchaId(request.getCanchaId());
        horario.setDiaSemana(request.getDiaSemana());
        horario.setHoraInicio(request.getHoraInicio());
        horario.setHoraFin(request.getHoraFin());
        horario.setEstaDisponible(true);

        Horario guardado = repository.save(horario);

        return new HorarioResponseDTO(
                guardado.getId(),
                guardado.getCanchaId(),
                guardado.getHoraInicio(),
                guardado.getHoraFin(),
                guardado.isEstaDisponible()
        );
    }

    // 2. NUEVO MÉTODO: Listar horarios por cancha (Lo pide tu HorarioController)
    public List<HorarioResponseDTO> listarPorCancha(Long canchaId) {
        return repository.findByCanchaId(canchaId).stream()
                .map(horario -> new HorarioResponseDTO(
                        horario.getId(),
                        horario.getCanchaId(),
                        horario.getHoraInicio(),
                        horario.getHoraFin(),
                        horario.isEstaDisponible()
                ))
                .collect(Collectors.toList());
    }
}