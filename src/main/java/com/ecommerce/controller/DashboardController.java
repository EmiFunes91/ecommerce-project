package com.ecommerce.controller;

import com.ecommerce.model.Pedido;
import com.ecommerce.model.Producto;
import com.ecommerce.service.PedidoService;
import com.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    // Obtener estadísticas del Dashboard
    @GetMapping("/estadísticas")
    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();

        // Total de productos en la tienda
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        estadisticas.put("totalProductos", productos.size());

        // Total de pedidos realizados
        List<Pedido> pedidos = pedidoService.obtenerPedidos();
        estadisticas.put("totalPedidos", pedidos.size());

        // Productos con stock bajo
        List<Producto> productosStockBajo = productoService.obtenerProductosConStockBajo();
        estadisticas.put("productosStockBajo", productosStockBajo.size());

        // Ingresos totales
        double ingresosTotales = pedidos.stream().mapToDouble(Pedido::getTotal).sum();
        estadisticas.put("ingresosTotales", ingresosTotales);

        // Productos más vendidos
        Map<String, Integer> productosVendidos = new HashMap<>();
        for (Pedido pedido : pedidos) {
            pedido.getDetalles().forEach(detalle -> {
                String nombreProducto = detalle.getProducto().getNombre();
                productosVendidos.put(nombreProducto, productosVendidos.getOrDefault(nombreProducto, 0) + detalle.getCantidad());
            });
        }

        // Obtener los 5 productos más vendidos
        estadisticas.put("productosMasVendidos", productosVendidos.entrySet()
                .stream()
                .sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue())) // Ordenar de mayor a menor cantidad vendida
                .limit(5) // Limitar a los 5 más vendidos
                .toArray());

        return estadisticas;
    }
}

