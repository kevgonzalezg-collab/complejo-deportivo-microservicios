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

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository repository;

    @Value("${servicio.canchas.url}")
    private String canchasUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public HorarioResponseDTO crearHorario(HorarioRequestDTO request) {

        // 1. VALIDACIÓN INTER-MICROSERVICIO: ¿Existe la cancha?
        try {
            String urlCompleta = canchasUrl + "/" + request.getCanchaId();
            restTemplate.getForObject(urlCompleta, CanchaEspejoDTO.class);
        } catch (Exception e) {
            throw new HorarioException("No se puede crear el horario: La cancha con ID "
                    + request.getCanchaId() + " no existe en el sistema.");
        }

        // 2. GUARDAR EN TU BASE DE DATOS DE XAMPP (db_horarios)
        Horario horario = new Horario();
        horario.setCanchaId(request.getCanchaId());

        // CORREGIDO: Usamos el método string que sí existe ahora
        horario.setDiaSemana(request.getDiaSemana());

        horario.setHoraInicio(request.getHoraInicio());
        horario.setHoraFin(request.getHoraFin());
        horario.setEstaDisponible(true); // Usando el nombre exacto de tu entidad

        Horario guardado = repository.save(horario);

        // 3. RETORNAR RESPUESTA A POSTMAN
        return new HorarioResponseDTO(
                guardado.getId(),
                guardado.getCanchaId(),
                guardado.getHoraInicio(),
                guardado.getHoraFin(),
                guardado.isEstaDisponible()
        );
    }
}