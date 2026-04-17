package com.pos.service;

import com.pos.model.Compra;

import java.util.List;
import java.util.Optional;

public interface CompraService {
    List<Compra> findAll();
    Optional<Compra> findById(Long id);
    Compra save(Compra compra);
    void deleteById(Long id);
    Compra realizarCompra(Compra compra);
}
