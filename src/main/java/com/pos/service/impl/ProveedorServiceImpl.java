package com.pos.service.impl;

import com.pos.model.Proveedor;
import com.pos.repository.ProveedorRepository;
import com.pos.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {
    
    @Autowired
    private ProveedorRepository proveedorRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Proveedor> findById(Long id) {
        return proveedorRepository.findById(id);
    }
    
    @Override
    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
    
    @Override
    public void deleteById(Long id) {
        proveedorRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Proveedor> findByNit(String nit) {
        return proveedorRepository.findByNit(nit);
    }
}
