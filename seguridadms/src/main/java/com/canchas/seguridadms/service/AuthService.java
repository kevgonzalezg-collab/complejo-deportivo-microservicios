package com.canchas.seguridadms.service;

import com.canchas.seguridadms.dto.AuthResponseDTO;
import com.canchas.seguridadms.dto.LoginRequestDTO;
import com.canchas.seguridadms.dto.RegistroRequestDTO;
import com.canchas.seguridadms.model.Credencial;
import com.canchas.seguridadms.repository.CredencialRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final CredencialRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CredencialRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTRAR
    public AuthResponseDTO registrar(RegistroRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Credencial nuevaCredencial = new Credencial();
        nuevaCredencial.setEmail(dto.getEmail());
        nuevaCredencial.setPassword(passwordEncoder.encode(dto.getPassword())); // Encriptación BCrypt
        nuevaCredencial.setRol(dto.getRol().toUpperCase());

        repository.save(nuevaCredencial);

        return new AuthResponseDTO("Usuario registrado exitosamente", null);
    }

    // LOGIN
    public AuthResponseDTO login(LoginRequestDTO dto) {
        Optional<Credencial> credencialOpt = repository.findByEmail(dto.getEmail());

        if (credencialOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Credencial credencial = credencialOpt.get();

        if (!passwordEncoder.matches(dto.getPassword(), credencial.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return new AuthResponseDTO("Login exitoso. Bienvenido " + credencial.getRol(), "token-simulado-por-ahora");
    }

    //  LISTAR
    public List<Credencial> listarTodasCredenciales() {
        return repository.findAll();
    }
}