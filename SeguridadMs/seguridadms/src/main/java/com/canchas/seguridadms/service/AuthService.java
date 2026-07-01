package com.canchas.seguridadms.service;

import com.canchas.seguridadms.dto.AuthResponseDTO;
import com.canchas.seguridadms.dto.LoginRequestDTO;
import com.canchas.seguridadms.dto.RegistroRequestDTO;
import com.canchas.seguridadms.model.Credencial;
import com.canchas.seguridadms.repository.CredencialRepository;
import com.canchas.seguridadms.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final CredencialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(CredencialRepository repository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // REGISTRAR
    public AuthResponseDTO registrar(RegistroRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Credencial nuevaCredencial = new Credencial();
        nuevaCredencial.setEmail(dto.getEmail());
        nuevaCredencial.setPassword(passwordEncoder.encode(dto.getPassword()));

        // NORMALIZACIÓN DE ROL POR DOMINIO DE CORREO
        if (dto.getEmail().toLowerCase().endsWith("@canchas.cl")) {
            nuevaCredencial.setRol("ADMIN");
        } else {
            nuevaCredencial.setRol("CLIENTE");
        }

        repository.save(nuevaCredencial);

        return new AuthResponseDTO("Usuario registrado exitosamente con rol: " + nuevaCredencial.getRol(), null);
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

        String tokenReal = jwtUtil.generarToken(credencial.getEmail(), credencial.getRol());

        return new AuthResponseDTO("Login exitoso. Bienvenido " + credencial.getRol(), tokenReal);
    }

    // LISTAR
    public List<Credencial> listarTodasCredenciales() {
        return repository.findAll();
    }
}