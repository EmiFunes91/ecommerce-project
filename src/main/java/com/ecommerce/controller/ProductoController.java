package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Listar todos los productos
    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public Optional<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    // Crear un nuevo producto
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del producto no puede estar vacío");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new RuntimeException("El precio debe ser un valor positivo");
        }
        return productoService.guardarProducto(producto);
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> productoExistente = productoService.obtenerProductoPorId(id);
        if (productoExistente.isPresent()) {
            Producto prodActualizado = productoExistente.get();
            if (producto.getNombre() != null && !producto.getNombre().isEmpty()) {
                prodActualizado.setNombre(producto.getNombre());
            }
            if (producto.getPrecio() != null && producto.getPrecio() > 0) {
                prodActualizado.setPrecio(producto.getPrecio());
            }
            prodActualizado.setCantidad(producto.getCantidad());
            return productoService.guardarProducto(prodActualizado);
        } else {
            throw new RuntimeException("Producto no encontrado con el ID: " + id);
        }
    }

    // Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }
}



