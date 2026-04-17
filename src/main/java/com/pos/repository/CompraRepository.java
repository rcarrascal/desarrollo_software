package com.pos.repository;

import com.pos.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    Optional<Compra> findByNumeroCompra(String numeroCompra);
    
    @Query("SELECT c FROM Compra c JOIN FETCH c.proveedor LEFT JOIN FETCH c.detalles ORDER BY c.fecha DESC")
    List<Compra> findAllWithDetails();
}
