package com.canchas.mscanchas.repository;

import com.canchas.mscanchas.entity.Canchas; // Asegúrate que termine en 's'
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanchaRepository extends JpaRepository<Canchas, Long> {
    // Aquí el diamante debe decir <Canchas, Long>
}