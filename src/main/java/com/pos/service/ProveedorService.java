package com.pos.service;

import com.pos.model.Proveedor;

import java.util.List;
import java.util.Optional;

public interface ProveedorService {
    List<Proveedor> findAll();
    Optional<Proveedor> findById(Long id);
    Proveedor save(Proveedor proveedor);
    void deleteById(Long id);
    Optional<Proveedor> findByNit(String nit);
}
