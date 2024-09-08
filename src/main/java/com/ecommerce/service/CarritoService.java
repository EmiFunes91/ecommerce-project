package com.ecommerce.service;

import com.ecommerce.model.Carrito;
import com.ecommerce.model.Producto;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    private final Carrito carrito = new Carrito();

    // Agregar producto al carrito
    public Carrito agregarProducto(Producto producto) {
        // Verificar si el producto ya está en el carrito
        boolean productoExistente = carrito.getProductos().stream()
                .anyMatch(p -> p.getId().equals(producto.getId()));

        if (productoExistente) {
            // Si ya existe, simplemente actualizamos la cantidad
            carrito.getProductos().stream()
                    .filter(p -> p.getId().equals(producto.getId()))
                    .forEach(p -> p.setCantidad(p.getCantidad() + producto.getCantidad()));
        } else {
            // Si no existe, lo agregamos al carrito
            carrito.getProductos().add(producto);
        }

        // Recalcular el total del carrito
        recalcularTotal();

        return carrito;
    }

    // Actualizar producto en el carrito
    public Carrito actualizarProducto(Producto producto) {
        carrito.getProductos().stream()
                .filter(p -> p.getId().equals(producto.getId()))
                .forEach(p -> {
                    p.setCantidad(producto.getCantidad());
                    p.setPrecio(producto.getPrecio());
                });

        // Recalcular el total del carrito
        recalcularTotal();

        return carrito;
    }

    // Eliminar un producto del carrito
    public Carrito eliminarProducto(Long id) {
        carrito.getProductos().removeIf(producto -> producto.getId().equals(id));

        // Recalcular el total del carrito
        recalcularTotal();

        return carrito;
    }

    // Obtener el carrito actual
    public Carrito obtenerCarrito() {
        return carrito;
    }

    // Método privado para recalcular el total del carrito
    private void recalcularTotal() {
        double total = carrito.getProductos().stream()
                .mapToDouble(producto -> producto.getCantidad() * producto.getPrecio())
                .sum();
        carrito.setTotal(total);
    }
}

