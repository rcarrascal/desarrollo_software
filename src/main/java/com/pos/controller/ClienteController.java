package com.pos.controller;

import com.pos.model.Cliente;
import com.pos.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        return "clientes/lista";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Cliente cliente = clienteService.findById(id).orElse(null);
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "clientes/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result, 
                         RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "clientes/form";
        }
        
        try {
            clienteService.save(cliente);
            redirectAttributes.addFlashAttribute("success", "Cliente guardado exitosamente");
            return "redirect:/clientes";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el cliente: " + e.getMessage());
            return "clientes/form";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Cliente eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
}
