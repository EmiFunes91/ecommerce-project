package com.ecommerce.service;

import com.ecommerce.model.Carrito;
import com.ecommerce.model.Producto;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    private final Carrito carrito = new Carrito();

    public Carrito agregarProducto(Producto producto) {
        carrito.getProductos().add(producto);
        return carrito;
    }

    public Carrito actualizarProducto(Producto producto) {
        carrito.getProductos().stream()
                .filter(p -> p.getId().equals(producto.getId()))
                .forEach(p -> {
                    p.setCantidad(producto.getCantidad());
                    p.setPrecio(producto.getPrecio());
                });
        return carrito;
    }

    public Carrito eliminarProducto(Long id) {
        carrito.getProductos().removeIf(producto -> producto.getId().equals(id));
        return carrito;
    }

    public Carrito obtenerCarrito() {
        return carrito;
    }
}
