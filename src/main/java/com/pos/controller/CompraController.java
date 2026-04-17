package com.pos.controller;

import com.pos.model.Compra;
import com.pos.model.DetalleCompra;
import com.pos.service.CompraService;
import com.pos.service.ProductoService;
import com.pos.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/compras")
public class CompraController {
    
    @Autowired
    private CompraService compraService;
    
    @Autowired
    private ProveedorService proveedorService;
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("compras", compraService.findAll());
        return "compras/lista";
    }
    
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        Compra compra = new Compra();
        compra.setDetalles(new ArrayList<>());
        
        model.addAttribute("compra", compra);
        model.addAttribute("proveedores", proveedorService.findAll());
        model.addAttribute("productos", productoService.findAll());
        return "compras/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Compra compra, 
                         @RequestParam("productoIds") Long[] productoIds,
                         @RequestParam("cantidades") Integer[] cantidades,
                         @RequestParam("precios") String[] precios,
                         RedirectAttributes redirectAttributes) {
        try {
            compra.setDetalles(new ArrayList<>());
            
            for (int i = 0; i < productoIds.length; i++) {
                if (productoIds[i] != null && cantidades[i] != null && cantidades[i] > 0) {
                    DetalleCompra detalle = new DetalleCompra();
                    detalle.setProducto(productoService.findById(productoIds[i]).orElse(null));
                    detalle.setCantidad(cantidades[i]);
                    detalle.setPrecioUnitario(new java.math.BigDecimal(precios[i]));
                    compra.getDetalles().add(detalle);
                }
            }
            
            if (compra.getDetalles().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Debe agregar al menos un producto");
                return "redirect:/compras/nueva";
            }
            
            compraService.realizarCompra(compra);
            redirectAttributes.addFlashAttribute("success", "Compra realizada exitosamente");
            return "redirect:/compras";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al realizar la compra: " + e.getMessage());
            return "redirect:/compras/nueva";
        }
    }
    
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Compra compra = compraService.findById(id).orElse(null);
        if (compra == null) {
            redirectAttributes.addFlashAttribute("error", "Compra no encontrada");
            return "redirect:/compras";
        }
        model.addAttribute("compra", compra);
        return "compras/detalle";
    }
}
