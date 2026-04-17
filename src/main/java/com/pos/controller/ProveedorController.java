package com.pos.controller;

import com.pos.model.Proveedor;
import com.pos.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
    
    @Autowired
    private ProveedorService proveedorService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorService.findAll());
        return "proveedores/lista";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/form";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Proveedor proveedor = proveedorService.findById(id).orElse(null);
        if (proveedor == null) {
            redirectAttributes.addFlashAttribute("error", "Proveedor no encontrado");
            return "redirect:/proveedores";
        }
        model.addAttribute("proveedor", proveedor);
        return "proveedores/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Proveedor proveedor, BindingResult result, 
                         RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "proveedores/form";
        }
        
        try {
            proveedorService.save(proveedor);
            redirectAttributes.addFlashAttribute("success", "Proveedor guardado exitosamente");
            return "redirect:/proveedores";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el proveedor: " + e.getMessage());
            return "proveedores/form";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            proveedorService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Proveedor eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el proveedor: " + e.getMessage());
        }
        return "redirect:/proveedores";
    }
}
