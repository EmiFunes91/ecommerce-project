package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Cupon;
import com.ecommerce.service.ProductoService;
import com.ecommerce.service.UsuarioService;
import com.ecommerce.service.PedidoService;
import com.ecommerce.service.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CuponService cuponService;

    // Gestión de productos
    @GetMapping("/productos")
    public List<Producto> obtenerTodosLosProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    @PostMapping("/productos")
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @PutMapping("/productos/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @DeleteMapping("/productos/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    // Gestión de usuarios
    @GetMapping("/usuarios")
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    // Gestión de pedidos
    @GetMapping("/pedidos")
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoService.obtenerPedidos();
    }

    @PutMapping("/pedidos/{id}/estado")
    public void actualizarEstadoPedido(@PathVariable Long id, @RequestParam String estado) throws Exception {
        pedidoService.actualizarEstadoPedido(id, estado);
    }

    // Gestión de cupones
    @GetMapping("/cupones")
    public List<Cupon> obtenerTodosLosCupones() {
        return cuponService.obtenerTodosLosCupones();
    }

    @PostMapping("/cupones")
    public Cupon crearCupon(@RequestBody Cupon cupon) {
        return cuponService.crearCupon(cupon);
    }

    @DeleteMapping("/cupones/{id}")
    public void eliminarCupon(@PathVariable Long id) {
        cuponService.eliminarCupon(id);
    }
}

