package com.canchas.seguridadms.repository;

import com.canchas.seguridadms.model.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long> {


    Optional<Credencial> findByEmail(String email);

}