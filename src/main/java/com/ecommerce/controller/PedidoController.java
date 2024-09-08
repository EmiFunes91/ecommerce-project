package com.ecommerce.controller;

import com.ecommerce.model.Pedido;
import com.ecommerce.model.DetallePedido;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.PedidoService;
import com.ecommerce.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public Pedido crearPedido(@RequestBody List<DetallePedido> detalles, @RequestParam Double total, @RequestParam Usuario usuario, @RequestParam(required = false) String codigoCupon) throws Exception {
        Pedido nuevoPedido = pedidoService.crearPedido(usuario, detalles, total, codigoCupon);

        // Enviar confirmación por correo después de la creación del pedido
        emailService.enviarCorreo(usuario.getEmail(), "Confirmación de Pedido", "Tu pedido ha sido recibido y está en proceso.");

        return nuevoPedido;
    }

    @GetMapping
    public List<Pedido> obtenerPedidos() {
        return pedidoService.obtenerPedidos();
    }

    @GetMapping("/{id}")
    public Pedido obtenerPedidoPorId(@PathVariable Long id) {
        return pedidoService.obtenerPedidoPorId(id);
    }

    // Actualizar el estado de un pedido
    @PutMapping("/{idPedido}/estado")
    public ResponseEntity<String> actualizarEstadoPedido(
            @PathVariable Long idPedido,
            @RequestParam String estado) {
        try {
            // Definir los estados válidos
            List<String> estadosValidos = List.of("Pagado", "Pendiente", "Cancelado", "Enviado", "Entregado");

            // Verificar si el estado es válido
            if (!estadosValidos.contains(estado)) {
                return ResponseEntity.status(400).body("Estado inválido");
            }

            pedidoService.actualizarEstadoPedido(idPedido, estado);
            return ResponseEntity.ok("Estado del pedido actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Cancelar un pedido
    @PutMapping("/{idPedido}/cancelar")
    public ResponseEntity<String> cancelarPedido(@PathVariable Long idPedido) {
        try {
            pedidoService.cancelarPedido(idPedido);
            return ResponseEntity.ok("Pedido cancelado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}




