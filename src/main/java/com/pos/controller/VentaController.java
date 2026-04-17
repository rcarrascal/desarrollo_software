package com.pos.controller;

import com.pos.model.DetalleVenta;
import com.pos.model.Venta;
import com.pos.service.ClienteService;
import com.pos.service.ProductoService;
import com.pos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/ventas")
public class VentaController {
    
    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.findAll());
        return "ventas/lista";
    }
    
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        Venta venta = new Venta();
        venta.setDetalles(new ArrayList<>());
        
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("productos", productoService.findAll());
        return "ventas/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@RequestParam("cliente.id") Long clienteId,
                         @RequestParam(value = "productoIds", required = false) Long[] productoIds,
                         @RequestParam(value = "cantidades", required = false) Integer[] cantidades,
                         @RequestParam(value = "precios", required = false) String[] precios,
                         RedirectAttributes redirectAttributes) {
        try {
            if (clienteId == null) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar un cliente");
                return "redirect:/ventas/nueva";
            }
            
            if (productoIds == null || productoIds.length == 0) {
                redirectAttributes.addFlashAttribute("error", "Debe agregar al menos un producto");
                return "redirect:/ventas/nueva";
            }
            
            Venta venta = new Venta();
            venta.setCliente(clienteService.findById(clienteId).orElseThrow(() -> 
                new RuntimeException("Cliente no encontrado")));
            venta.setDetalles(new ArrayList<>());
            
            for (int i = 0; i < productoIds.length; i++) {
                if (productoIds[i] != null && !productoIds[i].toString().isEmpty() && 
                    cantidades[i] != null && cantidades[i] > 0 &&
                    precios[i] != null && !precios[i].isEmpty()) {
                    
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setProducto(productoService.findById(productoIds[i]).orElseThrow(() -> 
                        new RuntimeException("Producto no encontrado")));
                    detalle.setCantidad(cantidades[i]);
                    detalle.setPrecioUnitario(new java.math.BigDecimal(precios[i]));
                    venta.getDetalles().add(detalle);
                }
            }
            
            if (venta.getDetalles().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Debe agregar al menos un producto válido");
                return "redirect:/ventas/nueva";
            }
            
            ventaService.realizarVenta(venta);
            redirectAttributes.addFlashAttribute("success", "Venta realizada exitosamente");
            return "redirect:/ventas";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al realizar la venta: " + e.getMessage());
            return "redirect:/ventas/nueva";
        }
    }
    
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Venta venta = ventaService.findById(id).orElse(null);
        if (venta == null) {
            redirectAttributes.addFlashAttribute("error", "Venta no encontrada");
            return "redirect:/ventas";
        }
        model.addAttribute("venta", venta);
        return "ventas/detalle";
    }
}
