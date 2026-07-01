package com.usuarios.msusuarios.repository;

import com.usuarios.msusuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Esta interfaz permite que Spring hable con la base de datos db_usuarios
}