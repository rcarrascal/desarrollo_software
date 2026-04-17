package com.pos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El código es obligatorio")
    @Column(nullable = false, unique = true)
    private String codigo;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;
    
    @Column(length = 500)
    private String descripcion;
    
    @NotNull(message = "El precio de compra es obligatorio")
    @PositiveOrZero(message = "El precio debe ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra;
    
    @NotNull(message = "El precio de venta es obligatorio")
    @PositiveOrZero(message = "El precio debe ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;
    
    @PositiveOrZero(message = "El stock debe ser positivo")
    @Column(nullable = false)
    private Integer stock = 0;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<DetalleCompra> detallesCompra = new ArrayList<>();
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<DetalleVenta> detallesVenta = new ArrayList<>();
}
