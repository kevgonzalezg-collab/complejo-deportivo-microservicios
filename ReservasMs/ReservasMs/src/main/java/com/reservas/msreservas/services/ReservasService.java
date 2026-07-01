package com.reservas.msreservas.services;

import com.reservas.msreservas.dto.*;
import com.reservas.msreservas.entity.Reservas;
import com.reservas.msreservas.exception.ReservaException;
import com.reservas.msreservas.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservasService {

    private final ReservaRepository repository;
    private final WebClient seguridadWebClient;
    private final WebClient listaNegraWebClient;
    private final WebClient usuariosWebClient;
    private final WebClient canchasWebClient;
    private final WebClient notificacionesWebClient;
    private final WebClient estadisticasWebClient;

    // Constructor
    public ReservasService(ReservaRepository repository,
                           WebClient seguridadWebClient,
                           WebClient listaNegraWebClient,
                           WebClient usuariosWebClient,
                           WebClient canchasWebClient,
                           WebClient notificacionesWebClient,
                           WebClient estadisticasWebClient) {
        this.repository = repository;
        this.seguridadWebClient = seguridadWebClient;
        this.listaNegraWebClient = listaNegraWebClient;
        this.usuariosWebClient = usuariosWebClient;
        this.canchasWebClient = canchasWebClient;
        this.notificacionesWebClient = notificacionesWebClient;
        this.estadisticasWebClient = estadisticasWebClient;
    }

    public ReservaResponseDTO crearReserva(ReservaRequestDTO request, String token) {

        // 1. SEGURIDAD
        try {
            SeguridadEspejoDTO auth = seguridadWebClient.post()
                    .uri("/validar")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(SeguridadEspejoDTO.class)
                    .block();

            if (auth == null || !auth.isValido()) {
                throw new ReservaException("Acceso denegado: El token no es válido.");
            }
        } catch (ReservaException e) {
            throw e;
        } catch (WebClientResponseException e) {
            throw new ReservaException("Error en SeguridadMS: Servidor respondió con código " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new ReservaException("Error de comunicación: El microservicio de Seguridad/Auth está APAGADO o inaccesible.");
        }

        // =====================================================================
        // 2. USUARIO
        // =====================================================================
        UsuarioEspejoDTO usuario = null;
        try {
            usuario = usuariosWebClient.get()
                    .uri("/{id}", request.getIdUsuario())
                    .retrieve()
                    .bodyToMono(UsuarioEspejoDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new ReservaException("Error en UsuariosMS: El usuario con ID " + request.getIdUsuario() + " no se pudo validar. Código HTTP: " + e.getStatusCode());
        } catch (Exception e) {
            throw new ReservaException("Error de comunicación: El microservicio de Usuarios está APAGADO.");
        }

        // =====================================================================
// =====================================================================
        // 3. LISTA NEGRA
        // =====================================================================
        try {
            if (usuario == null || usuario.getRut() == null) {
                throw new ReservaException("No se pudo obtener el RUT del usuario para validar la lista negra.");
            }

            String rutAValidar = usuario.getRut();
            System.out.println("[RESERVAS] Consultando Lista Negra para el RUT: " + rutAValidar);

            ListaNegraEspejoDTO sancionado = listaNegraWebClient.get()
                    .uri("/{rut}", rutAValidar)
                    .retrieve()
                    .bodyToMono(ListaNegraEspejoDTO.class)
                    .onErrorReturn(new ListaNegraEspejoDTO()) // Si da error 404, retorna un DTO vacío en vez de explotar
                    .block();

            System.out.println("[RESERVAS] Respuesta lista negra: " + sancionado);

            // Validamos estrictamente que tenga un motivo real antes de bloquear
            if (sancionado != null && sancionado.getMotivo() != null && !sancionado.getMotivo().isEmpty()) {
                System.out.println("[RESERVAS] Usuario en lista negra detectado");

                throw new ReservaException(
                        "Reserva rechazada: El usuario " +
                                (sancionado.getNombre() != null ? sancionado.getNombre() : "Desconocido") +
                                " está en LISTA NEGRA. Motivo: " +
                                sancionado.getMotivo()
                );
            } else {
                System.out.println("[RESERVAS] Usuario limpio (NO está en lista negra o respuesta vacía)");
            }

        } catch (ReservaException e) {
            throw e; // Relanzamos la excepción de negocio si es que fue bloqueado
        } catch (Exception e) {
            System.out.println("[RESERVAS] Advertencia: No se pudo verificar la lista negra, permitiendo reserva por defecto. Error: " + e.getMessage());
        }
        // 4. CANCHA
        try {
            canchasWebClient.get()
                    .uri("/{id}", request.getIdCancha())
                    .retrieve()
                    .bodyToMono(CanchaEspejoDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new ReservaException("Error en CanchasMS: La cancha con ID " + request.getIdCancha() + " no se pudo validar. Código HTTP: " + e.getStatusCode());
        } catch (Exception e) {
            throw new ReservaException("Error de comunicación: El microservicio de Canchas está APAGADO.");
        }

        // Validación de fecha básica
        if (request.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new ReservaException("No puedes agendar en una fecha pasada.");
        }

        // Guardar la Reserva
        Reservas reserva = new Reservas();
        reserva.setIdUsuario(request.getIdUsuario());
        reserva.setIdCancha(request.getIdCancha());
        reserva.setFechaHora(request.getFechaHora());
        reserva.setDuracionMinutos(request.getDuracionMinutos());
        reserva.setEstado("PENDIENTE");

        Reservas guardada = repository.save(reserva);

        // 5. ENVIAR NOTIFICACIÓN
        try {
            NotificacionesEspejoDTO aviso = new NotificacionesEspejoDTO();
            aviso.setCorreoDestino("kevis.test@gmail.com");
            aviso.setAsunto("¡Cancha Reservada!");
            aviso.setMensaje("Tu reserva #" + guardada.getId() + " fue creada.");
            aviso.setFechaEnvio(LocalDateTime.now().toString());
            aviso.setEstado("ENVIADO");

            notificacionesWebClient.post().bodyValue(aviso).retrieve().bodyToMono(Object.class).block();
        } catch (Exception e) {
            System.out.println(" [RESERVAS] No se envió la notificación.");
        }

        // 6. ESTADÍSTICAS
        try {
            MetricaEspejoDTO estadisticaData = new MetricaEspejoDTO(java.time.LocalDate.now(), 1, 0, 0, 0.0);
            estadisticasWebClient.post().bodyValue(estadisticaData).retrieve().bodyToMono(Object.class).block();
        } catch (Exception e) {
            System.out.println(" [RESERVAS] No se actualizaron las estadísticas.");
        }

        return deEntidadADto(guardada);
    }

    public List<ReservaResponseDTO> listarTodas() {
        return repository.findAll().stream().map(this::deEntidadADto).collect(Collectors.toList());
    }

    public ReservaResponseDTO obtenerPorId(Long id) {
        Reservas reserva = repository.findById(id)
                .orElseThrow(() -> new ReservaException("No se encontró la reserva con ID: " + id));
        return deEntidadADto(reserva);
    }

    public void actualizarEstadoReserva(Long id, String nuevoEstado) {
        Reservas reserva = repository.findById(id)
                .orElseThrow(() -> new ReservaException("No se encontró la reserva con ID: " + id));
        reserva.setEstado(nuevoEstado);
        repository.save(reserva);
    }

    private ReservaResponseDTO deEntidadADto(Reservas reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getIdUsuario(),
                reserva.getIdCancha(),
                reserva.getFechaHora(),
                reserva.getEstado()
        );
    }
}