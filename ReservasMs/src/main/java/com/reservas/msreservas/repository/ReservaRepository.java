package com.reservas.msreservas.repository;

import com.reservas.msreservas.entity.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reservas, Long> {
}