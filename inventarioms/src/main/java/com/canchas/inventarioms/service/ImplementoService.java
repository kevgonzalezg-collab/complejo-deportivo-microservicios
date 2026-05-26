package com.canchas.inventarioms.service;

import com.canchas.inventarioms.dto.ImplementoRequestDTO;
import com.canchas.inventarioms.dto.ImplementoResponseDTO;
import com.canchas.inventarioms.model.Implemento;
import com.canchas.inventarioms.repository.ImplementoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplementoService {

    private final ImplementoRepository repository;

    public ImplementoService(ImplementoRepository repository) {
        this.repository = repository;
    }

    public ImplementoResponseDTO crear(ImplementoRequestDTO dto) {
        // Transformamos el DTO de entrada en un Modelo para guardarlo en la BD
        Implemento implemento = new Implemento(null, dto.getNombre(), dto.getCantidad(), dto.getEstado());
        Implemento guardado = repository.save(implemento);

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