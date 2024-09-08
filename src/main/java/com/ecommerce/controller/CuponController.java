package com.ecommerce.controller;

import com.ecommerce.model.Cupon;
import com.ecommerce.service.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @PostMapping("/aplicar")
    public Cupon aplicarCupon(@RequestParam String codigo) {
        try {
            return cuponService.aplicarCupon(codigo);
        } catch (Exception e) {
            throw new RuntimeException("Error al aplicar el cupón: " + e.getMessage());
        }
    }
}

