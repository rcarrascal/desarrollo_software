package com.pos.service.impl;

import com.pos.model.Producto;
import com.pos.repository.ProductoRepository;
import com.pos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }
    
    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }
    
    @Override
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findByCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
    }
}
