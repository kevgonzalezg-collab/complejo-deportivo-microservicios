package com.canchas.listanegrams.service;

import com.canchas.listanegrams.dto.ClienteBloqueadoRequestDTO;
import com.canchas.listanegrams.dto.ClienteBloqueadoResponseDTO;
import com.canchas.listanegrams.model.ClienteBloqueado;
import com.canchas.listanegrams.repository.ClienteBloqueadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteBloqueadoService {

    private final ClienteBloqueadoRepository repository;

    public ClienteBloqueadoService(ClienteBloqueadoRepository repository) {
        this.repository = repository;
    }

    public ClienteBloqueadoResponseDTO bloquear(ClienteBloqueadoRequestDTO dto) {
        // Verificamos si el RUT ya está en la lista negra
        if (repository.findByRut(dto.getRut()).isPresent()) {
            throw new RuntimeException("Este RUT ya se encuentra en la lista negra");
        }

        // Creamos el registro y le asignamos la fecha de hoy automáticamente
        ClienteBloqueado cliente = new ClienteBloqueado();
        cliente.setRut(dto.getRut());
        cliente.setNombre(dto.getNombre());
        cliente.setMotivo(dto.getMotivo());
        cliente.setFechaBloqueo(LocalDate.now());

        ClienteBloqueado guardado = repository.save(cliente);

        return new ClienteBloqueadoResponseDTO(
                guardado.getId(), guardado.getRut(), guardado.getNombre(),
                guardado.getMotivo(), guardado.getFechaBloqueo()
        );
    }

    public List<ClienteBloqueadoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(c -> new ClienteBloqueadoResponseDTO(c.getId(), c.getRut(), c.getNombre(), c.getMotivo(), c.getFechaBloqueo()))
                .toList();
    }


    public Optional<ClienteBloqueadoResponseDTO> obtenerPorRut(String rut) {
        return repository.findByRut(rut)
                .map(c -> new ClienteBloqueadoResponseDTO(
                        c.getId(),
                        c.getRut(),
                        c.getNombre(),
                        c.getMotivo(),
                        c.getFechaBloqueo()
                ));
    }
}