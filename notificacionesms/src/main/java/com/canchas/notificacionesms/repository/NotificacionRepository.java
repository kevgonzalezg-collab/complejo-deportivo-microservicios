package com.canchas.notificacionesms.repository;

import com.canchas.notificacionesms.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Spring Boot armará la consulta para buscar por el campo estado
    List<Notificacion> findByEstado(String estado);

}