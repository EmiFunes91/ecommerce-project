package com.ecommerce.controller;

import com.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/mercadopago")
    public String recibirNotificacionMercadoPago(@RequestBody String payload) {
        // Aquí puedes procesar el payload y actualizar el estado del pedido
        System.out.println("Notificación recibida de Mercado Pago: " + payload);
        // Dependiendo del payload, actualiza el estado del pedido
        // Ejemplo: Si el pago fue aprobado, marcar el pedido como "Pagado"
        // pedidoService.actualizarEstadoPedido(idPedido, "Pagado");

        return "OK";
    }

    @PostMapping("/paypal")
    public String recibirNotificacionPayPal(@RequestBody String payload) {
        // Aquí puedes procesar el payload y actualizar el estado del pedido
        System.out.println("Notificación recibida de PayPal: " + payload);
        // Dependiendo del payload, actualiza el estado del pedido
        // Ejemplo: Si el pago fue aprobado, marcar el pedido como "Pagado"
        // pedidoService.actualizarEstadoPedido(idPedido, "Pagado");

        return "OK";
    }
}


