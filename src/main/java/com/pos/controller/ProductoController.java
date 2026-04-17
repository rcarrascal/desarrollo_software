package com.pos.controller;

import com.pos.model.Producto;
import com.pos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "productos/lista";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/form";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.findById(id).orElse(null);
        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        return "productos/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Producto producto, BindingResult result, 
                         RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "productos/form";
        }
        
        try {
            productoService.save(producto);
            redirectAttributes.addFlashAttribute("success", "Producto guardado exitosamente");
            return "redirect:/productos";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el producto: " + e.getMessage());
            return "productos/form";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productoService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Producto eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }
}
