package service;
import com.usuarios.msusuarios.dto.UsuarioRequestDTO;
import com.usuarios.msusuarios.dto.UsuarioResponseDTO;
import com.usuarios.msusuarios.entity.Usuario;
import com.usuarios.msusuarios.repository.UsuarioRepository;
import com.usuarios.msusuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock private UsuarioRepository repository;

    @InjectMocks private UsuarioService service;

    @Test
    void testCrearUsuarioExitoso() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setRut("11111111-1");
        dto.setNombre("Kevis");
        dto.setEmail("kevis@example.com");

        Usuario usuario = new Usuario();
        usuario.setId(11L);
        usuario.setRut("11111111-1");
        usuario.setNombre("Kevis");
        usuario.setEmail("kevis@example.com");

        // Cambiado a tu método real .save() igual que en lista negra
        when(repository.save(any())).thenReturn(usuario);

        UsuarioResponseDTO response = service.crearUsuario(dto);
        assertNotNull(response);
    }

    @Test
    void testObtenerPorIdExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(11L);
        usuario.setNombre("Kevis");

        // Mismo formato que testObtenerPorRutExiste de lista negra
        when(repository.findById(11L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO response = service.obtenerPorId(11L);
        assertNotNull(response);
        assertEquals("Kevis", response.getNombre());
    }

    @Test
    void testObtenerPorIdNoExiste() {
        // Mismo formato que testObtenerPorRutNoExiste de lista negra
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
    }
}