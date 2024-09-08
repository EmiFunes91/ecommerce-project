package com.ecommerce.controller;

import com.mercadopago.resources.preference.Preference;
import com.ecommerce.model.Producto;
import com.ecommerce.service.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/mercadopago")
public class MercadoPagoController {

    private static final Logger logger = LoggerFactory.getLogger(MercadoPagoController.class);

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping("/pago")
    public String crearPago(@RequestBody List<Producto> productos) {
        try {
            // Crear la preferencia usando el servicio
            Preference preference = mercadoPagoService.crearPreferencia(productos);
            String initPoint = preference.getSandboxInitPoint();  // Usar sandbox para pruebas

            return "redirect:" + initPoint;  // Redirige al link de pago de Mercado Pago

        } catch (Exception e) {
            logger.error("Error al crear el pago con Mercado Pago", e);  // Usar logging para errores
            return "Error al crear el pago";
        }
    }

    @GetMapping("/success")
    public String pagoExitoso() {
        return "Pago exitoso con Mercado Pago";
    }

    @GetMapping("/failure")
    public String pagoFallido() {
        return "El pago ha fallado";
    }

    @GetMapping("/pending")
    public String pagoPendiente() {
        return "El pago está pendiente";
    }
}


