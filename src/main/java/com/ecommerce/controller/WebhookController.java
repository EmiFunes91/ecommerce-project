package com.ecommerce.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {

    @PostMapping("/mercadopago")
    public String recibirNotificacionMercadoPago(@RequestBody String payload) {
        // Aquí puedes procesar el payload y actualizar el estado del pedido
        System.out.println("Notificación recibida de Mercado Pago: " + payload);
        return "OK";
    }

    @PostMapping("/paypal")
    public String recibirNotificacionPayPal(@RequestBody String payload) {
        // Aquí puedes procesar el payload y actualizar el estado del pedido
        System.out.println("Notificación recibida de PayPal: " + payload);
        return "OK";
    }
}

