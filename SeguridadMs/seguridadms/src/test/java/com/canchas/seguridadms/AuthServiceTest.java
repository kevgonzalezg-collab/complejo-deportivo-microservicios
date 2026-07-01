package com.canchas.seguridadms;

import com.canchas.seguridadms.dto.AuthResponseDTO;
import com.canchas.seguridadms.dto.RegistroRequestDTO;
import com.canchas.seguridadms.model.Credencial;
import com.canchas.seguridadms.repository.CredencialRepository;
import com.canchas.seguridadms.service.AuthService;
import com.canchas.seguridadms.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private CredencialRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarAdminPorDominio() {
        // Arrange: Preparamos datos
        RegistroRequestDTO dto = new RegistroRequestDTO();
        dto.setEmail("admin@canchas.cl");
        dto.setPassword("123456");

        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("hashedPass");

        // Act: Ejecutamos el servicio
        AuthResponseDTO response = authService.registrar(dto);

        // Assert: Verificamos el resultado
        assertTrue(response.getMensaje().contains("ADMIN"));
        verify(repository, times(1)).save(any(Credencial.class));
    }
}