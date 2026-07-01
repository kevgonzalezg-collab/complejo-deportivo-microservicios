package com.canchas.listanegrams.repository;

import com.canchas.listanegrams.model.ClienteBloqueado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteBloqueadoRepository extends JpaRepository<ClienteBloqueado, Long> {

    // Con esto Spring buscará automáticamente por el campo RUT
    Optional<ClienteBloqueado> findByRut(String rut);
}