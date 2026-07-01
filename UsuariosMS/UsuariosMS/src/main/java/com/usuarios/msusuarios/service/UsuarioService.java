package com.usuarios.msusuarios.service;

import com.usuarios.msusuarios.dto.UsuarioRequestDTO;
import com.usuarios.msusuarios.dto.UsuarioResponseDTO;
import com.usuarios.msusuarios.dto.NotificacionesEspejoDTO;
import com.usuarios.msusuarios.dto.MetricaEspejoDTO;
import com.usuarios.msusuarios.entity.Usuario;
import com.usuarios.msusuarios.exception.UsuarioException;
import com.usuarios.msusuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final WebClient notificacionesWebClient;
    private final WebClient estadisticasWebClient;


    public UsuarioService(UsuarioRepository repository,
                          WebClient notificacionesWebClient,
                          WebClient estadisticasWebClient) {
        this.repository = repository;
        this.notificacionesWebClient = notificacionesWebClient;
        this.estadisticasWebClient = estadisticasWebClient;
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioException("No se encontró el usuario con el ID: " + id));
        return convertirADto(usuario);
    }

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new UsuarioException("El email es obligatorio para registrar un usuario.");
        }

        // 🔥 VALIDACIÓN BÁSICA PARA EL RUT
        if (request.getRut() == null || request.getRut().isEmpty()) {
            throw new UsuarioException("El RUT es obligatorio para registrar un usuario.");
        }

        Usuario usuario = new Usuario();
        usuario.setRut(request.getRut()); // DE RUT AGREGADO
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setRol(request.getRol());
        usuario.setTelefono(request.getTelefono());

        Usuario guardado = repository.save(usuario);

        // =====================================================================
        //   1. NOTIFICACIONES (Puerto 8085) CON WEBCLIENT
        // =====================================================================
        try {
            NotificacionesEspejoDTO aviso = new NotificacionesEspejoDTO();
            aviso.setCorreoDestino(guardado.getEmail());
            aviso.setAsunto(" ¡Bienvenido al Sistema!");
            aviso.setMensaje("¡Bienvenido al sistema de canchas, " + guardado.getNombre() + "! Tu cuenta ha sido creada con éxito.");

            notificacionesWebClient.post()
                    .bodyValue(aviso)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            System.out.println("[USUARIOS] ¡Correo de bienvenida enviado con éxito a Notificaciones!");
        } catch (Exception e) {
            System.out.println(" [USUARIOS] No se pudo enviar el correo de bienvenida: " + e.getMessage());
        }


        //   2. ESTADÍSTICAS (Puerto 8095) CON WEBCLIENT
        // =====================================================================
        try {
            MetricaEspejoDTO metrica = new MetricaEspejoDTO();
            metrica.setFecha(java.time.LocalDate.now());
            metrica.setTotalReservasCreadas(0);
            metrica.setTotalReservasPagadas(0);
            metrica.setTotalReservasCanceladas(0);
            metrica.setRecaudacionTotal(0.0);

            estadisticasWebClient.post()
                    .bodyValue(metrica)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            System.out.println(" [USUARIOS] ¡Alerta de tráfico por nuevo usuario \"" + guardado.getNombre() + "\" enviada con éxito!");
        } catch (Exception e) {
            System.out.println(" [USUARIOS] No se pudo actualizar el módulo de estadísticas: " + e.getMessage());
        }

        return convertirADto(guardado);
    }

    private UsuarioResponseDTO convertirADto(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setRut(usuario.getRut()); //
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        dto.setTelefono(usuario.getTelefono());
        return dto;
    }
}