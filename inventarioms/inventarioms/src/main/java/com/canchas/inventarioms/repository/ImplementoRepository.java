package com.canchas.inventarioms.repository;

import com.canchas.inventarioms.model.Implemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImplementoRepository extends JpaRepository<Implemento, Long> {
}