package com.pagos.mspagos.repository;

import com.pagos.mspagos.entity.Pago; // Asegúrate de que esté en MAYÚSCULAS
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Aquí tenía que ser PAGO, no pago
}