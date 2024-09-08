package com.ecommerce.service;

import com.ecommerce.model.Producto;
import com.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Umbral de stock bajo (puedes ajustarlo)
    private static final int UMBRAL_STOCK_BAJO = 5;

    // Obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    // Obtener productos con stock bajo
    public List<Producto> obtenerProductosConStockBajo() {
        return productoRepository.findAll().stream()
                .filter(producto -> producto.getCantidad() < UMBRAL_STOCK_BAJO)
                .collect(Collectors.toList());
    }

    // Obtener un producto por ID
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Guardar un nuevo producto o actualizar uno existente
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar un producto por ID
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}






