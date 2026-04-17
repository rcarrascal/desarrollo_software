package com.pos.service.impl;

import com.pos.model.DetalleVenta;
import com.pos.model.Producto;
import com.pos.model.Venta;
import com.pos.repository.ProductoRepository;
import com.pos.repository.VentaRepository;
import com.pos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaServiceImpl implements VentaService {
    
    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Venta> findAll() {
        return ventaRepository.findAllWithDetails();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }
    
    @Override
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }
    
    @Override
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }
    
    @Override
    public Venta realizarVenta(Venta venta) {
        for (DetalleVenta detalle : venta.getDetalles()) {
            detalle.setVenta(venta);
            detalle.calcularSubtotal();
            
            Producto producto = detalle.getProducto();
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
        }
        
        venta.calcularTotal();
        return ventaRepository.save(venta);
    }
}
