package com.ecommerce.controller;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.ecommerce.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/paypal")
public class PayPalController {

    private static final Logger logger = LoggerFactory.getLogger(PayPalController.class);

    @Autowired
    private PayPalService payPalService;

    @PostMapping("/pago")
    public String crearPago(@RequestParam("total") Double total) {
        try {
            String approvalUrl = payPalService.crearPago(total, "USD", "paypal", "sale", "Pago e-commerce", "http://localhost:8080/cancel", "http://localhost:8080/success");
            if (approvalUrl != null) {
                return "redirect:" + approvalUrl;
            } else {
                return "Error al crear el pago";
            }
        } catch (PayPalRESTException e) {
            logger.error("Error en el proceso de pago con PayPal", e);  // Reemplaza printStackTrace() con logging
            return "Error en el pago";
        }
    }

    @GetMapping("/success")
    public String success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.ejecutarPago(paymentId, payerId);
            return "Pago exitoso: " + payment.toJSON();
        } catch (PayPalRESTException e) {
            logger.error("Error al ejecutar el pago con PayPal", e);  // Reemplaza printStackTrace() con logging
            return "Error en el pago";
        }
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "Pago cancelado";
    }
}


