package com.pos.service.impl;

import com.pos.model.Cliente;
import com.pos.repository.ClienteRepository;
import com.pos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    @Override
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findByDocumento(String documento) {
        return clienteRepository.findByDocumento(documento);
    }
}
