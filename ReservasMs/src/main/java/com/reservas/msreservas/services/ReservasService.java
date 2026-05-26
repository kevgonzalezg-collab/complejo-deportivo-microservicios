package com.reservas.msreservas.service;

import com.reservas.msreservas.dto.CanchaEspejoDTO;
import com.reservas.msreservas.dto.UsuarioEspejoDTO; // IMPORTADO NUEVO
import com.reservas.msreservas.dto.ReservaRequestDTO;
import com.reservas.msreservas.dto.ReservaResponseDTO;
import com.reservas.msreservas.entity.Reservas;
import com.reservas.msreservas.exception.ReservaException;
import com.reservas.msreservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservasService {

    @Autowired
    private ReservaRepository repository;

    @Value("${servicio.canchas.url}")
    private String canchasUrl;

    @Value("${servicio.usuarios.url}") // INYECTADO NUEVO
    private String usuariosUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // 1. Método para Crear Reserva con doble validación
    public ReservaResponseDTO crearReserva(ReservaRequestDTO request) {

        // VALIDACIÓN A: Microservicio de Canchas (Puerto 8082)
        try {
            String urlCanchasCompleta = canchasUrl + "/" + request.getIdCancha();
            restTemplate.getForObject(urlCanchasCompleta, CanchaEspejoDTO.class);
        } catch (Exception e) {
            throw new ReservaException("No se pudo registrar la reserva: La cancha con ID "
                    + request.getIdCancha() + " no existe en el sistema.");
        }

        // VALIDACIÓN B: Microservicio de Usuarios (Puerto 8081) - ¡NUEVO ENLACE!
        try {
            String urlUsuariosCompleta = usuariosUrl + "/" + request.getIdUsuario();
            restTemplate.getForObject(urlUsuariosCompleta, UsuarioEspejoDTO.class);
        } catch (Exception e) {
            throw new ReservaException("No se pudo registrar la reserva: El usuario con ID "
                    + request.getIdUsuario() + " no existe en el sistema.");
        }

        // VALIDACIÓN DE FECHA LOCAL
        if (request.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new ReservaException("No puedes agendar en una fecha pasada.");
        }

        // GUARDAR EN TU BASE DE DATOS DE XAMPP (db_reservas)
        Reservas reserva = new Reservas();
        reserva.setIdUsuario(request.getIdUsuario());
        reserva.setIdCancha(request.getIdCancha());
        reserva.setFechaHora(request.getFechaHora());
        reserva.setDuracionMinutos(request.getDuracionMinutos());
        reserva.setEstado("PENDIENTE");

        Reservas guardada = repository.save(reserva);

        return new ReservaResponseDTO(
                guardada.getId(),
                guardada.getIdUsuario(),
                guardada.getIdCancha(),
                guardada.getFechaHora(),
                guardada.getEstado()
        );
    }

    // 2. Método para Listar Todas
    public List<ReservaResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(reserva -> new ReservaResponseDTO(
                        reserva.getId(),
                        reserva.getIdUsuario(),
                        reserva.getIdCancha(),
                        reserva.getFechaHora(),
                        reserva.getEstado()
                ))
                .collect(Collectors.toList());
    }
}