package com.ecommerce.controller;

import com.ecommerce.model.Carrito;
import com.ecommerce.model.Producto;
import com.ecommerce.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/agregar")
    public Carrito agregarProducto(@RequestBody Producto producto) {
        return carritoService.agregarProducto(producto);
    }

    @PutMapping("/actualizar")
    public Carrito actualizarProducto(@RequestBody Producto producto) {
        return carritoService.actualizarProducto(producto);
    }

    @DeleteMapping("/eliminar/{id}")
    public Carrito eliminarProducto(@PathVariable Long id) {
        return carritoService.eliminarProducto(id);
    }

    @GetMapping
    public Carrito obtenerCarrito() {
        return carritoService.obtenerCarrito();
    }
}

