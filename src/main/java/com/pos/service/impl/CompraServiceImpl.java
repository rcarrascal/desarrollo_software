package com.pos.service.impl;

import com.pos.model.Compra;
import com.pos.model.DetalleCompra;
import com.pos.model.Producto;
import com.pos.repository.CompraRepository;
import com.pos.repository.ProductoRepository;
import com.pos.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompraServiceImpl implements CompraService {
    
    @Autowired
    private CompraRepository compraRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Compra> findAll() {
        return compraRepository.findAllWithDetails();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Compra> findById(Long id) {
        return compraRepository.findById(id);
    }
    
    @Override
    public Compra save(Compra compra) {
        return compraRepository.save(compra);
    }
    
    @Override
    public void deleteById(Long id) {
        compraRepository.deleteById(id);
    }
    
    @Override
    public Compra realizarCompra(Compra compra) {
        for (DetalleCompra detalle : compra.getDetalles()) {
            detalle.setCompra(compra);
            
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }
        
        compra.calcularTotal();
        return compraRepository.save(compra);
    }
}
