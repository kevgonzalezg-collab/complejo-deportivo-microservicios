package com.canchas.seguridadms.service;

import com.canchas.seguridadms.dto.AuthResponseDTO;
import com.canchas.seguridadms.dto.LoginRequestDTO;
import com.canchas.seguridadms.dto.RegistroRequestDTO;
import com.canchas.seguridadms.model.Credencial;
import com.canchas.seguridadms.repository.CredencialRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final CredencialRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CredencialRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO registrar(RegistroRequestDTO dto) {
        // 1. Verificamos si el correo ya existe
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // 2. Creamos la credencial y encriptamos la contraseña
        Credencial nuevaCredencial = new Credencial();
        nuevaCredencial.setEmail(dto.getEmail());
        nuevaCredencial.setPassword(passwordEncoder.encode(dto.getPassword())); // ¡Aquí se encripta!
        nuevaCredencial.setRol(dto.getRol().toUpperCase());

        repository.save(nuevaCredencial);

        return new AuthResponseDTO("Usuario registrado exitosamente", null);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        // 1. Buscamos al usuario por correo
        Optional<Credencial> credencialOpt = repository.findByEmail(dto.getEmail());

        if (credencialOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Credencial credencial = credencialOpt.get();

        // 2. Comparamos la contraseña de Postman con la encriptada en la BD
        if (!passwordEncoder.matches(dto.getPassword(), credencial.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // 3. Respuesta exitosa
        return new AuthResponseDTO("Login exitoso. Bienvenido " + credencial.getRol(), "token-simulado-por-ahora");
    }
}