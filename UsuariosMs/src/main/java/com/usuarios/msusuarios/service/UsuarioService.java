package com.usuarios.msusuarios.service;

import com.usuarios.msusuarios.dto.UsuarioRequestDTO;
import com.usuarios.msusuarios.dto.UsuarioResponseDTO;
import com.usuarios.msusuarios.entity.Usuario;
import com.usuarios.msusuarios.exception.UsuarioException;
import com.usuarios.msusuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

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

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido()); // 📢 Seteado
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword()); // 📢 Seteado obligatorio
        usuario.setRol(request.getRol());           // 📢 Seteado obligatorio
        usuario.setTelefono(request.getTelefono());

        Usuario guardado = repository.save(usuario);
        return convertirADto(guardado);
    }

    private UsuarioResponseDTO convertirADto(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());               // 📢 Agregado a la respuesta
        dto.setTelefono(usuario.getTelefono());
        return dto;
    }
}