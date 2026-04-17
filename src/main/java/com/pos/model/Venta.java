package com.pos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String numeroVenta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal total;
    
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        fecha = LocalDateTime.now();
        if (numeroVenta == null) {
            numeroVenta = "V-" + System.currentTimeMillis();
        }
    }
    
    public void calcularTotal() {
        total = detalles.stream()
                .map(detalle -> {
                    if (detalle.getSubtotal() == null) {
                        detalle.calcularSubtotal();
                    }
                    return detalle.getSubtotal() != null ? detalle.getSubtotal() : BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
