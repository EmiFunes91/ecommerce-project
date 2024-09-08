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

    // Agregar un producto al carrito
    @PostMapping("/agregar")
    public Carrito agregarProducto(@RequestBody Producto producto) {
        return carritoService.agregarProducto(producto);
    }

    // Actualizar un producto existente en el carrito
    @PutMapping("/actualizar")
    public Carrito actualizarProducto(@RequestBody Producto producto) {
        return carritoService.actualizarProducto(producto);
    }

    // Eliminar un producto del carrito
    @DeleteMapping("/eliminar/{id}")
    public Carrito eliminarProducto(@PathVariable Long id) {
        return carritoService.eliminarProducto(id);
    }

    // Obtener el carrito actual
    @GetMapping
    public Carrito obtenerCarrito() {
        return carritoService.obtenerCarrito();
    }
}

