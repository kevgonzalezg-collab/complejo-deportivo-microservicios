package com.canchas.notificacionesms.service;

import com.canchas.notificacionesms.dto.NotificacionRequestDTO;
import com.canchas.notificacionesms.dto.NotificacionResponseDTO;
import com.canchas.notificacionesms.model.Notificacion;
import com.canchas.notificacionesms.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository repository;

    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    public NotificacionResponseDTO enviarYRegistrar(NotificacionRequestDTO dto) {
        // 1. Simulamos el envío del correo imprimiéndolo en la consola
        System.out.println("==================================================");
        System.out.println("Enviando correo electrónico...");
        System.out.println("Para: " + dto.getCorreoDestino());
        System.out.println("Asunto: " + dto.getAsunto());
        System.out.println("Mensaje: " + dto.getMensaje());
        System.out.println("==================================================");

        // 2. Creamos el registro de auditoría en la base de datos
        Notificacion notificacion = new Notificacion();
        notificacion.setCorreoDestino(dto.getCorreoDestino());
        notificacion.setAsunto(dto.getAsunto());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setEstado("ENVIADO"); // Lo marcamos como exitoso

        Notificacion guardada = repository.save(notificacion);

        return new NotificacionResponseDTO(
                guardada.getId(), guardada.getCorreoDestino(),
                guardada.getAsunto(), guardada.getMensaje(),
                guardada.getFechaEnvio(), guardada.getEstado()
        );
    }

    public List<NotificacionResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(n -> new NotificacionResponseDTO(
                        n.getId(), n.getCorreoDestino(), n.getAsunto(),
                        n.getMensaje(), n.getFechaEnvio(), n.getEstado()))
                .toList();
    }
}