package com.pos.repository;

import com.pos.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findByNumeroVenta(String numeroVenta);
    
    @Query("SELECT v FROM Venta v JOIN FETCH v.cliente LEFT JOIN FETCH v.detalles ORDER BY v.fecha DESC")
    List<Venta> findAllWithDetails();
}
