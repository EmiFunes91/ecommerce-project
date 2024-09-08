package com.ecommerce.service;

import com.ecommerce.model.Cupon;
import com.ecommerce.repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public Cupon aplicarCupon(String codigo) throws Exception {
        Optional<Cupon> cuponOpt = cuponRepository.findByCodigo(codigo);

        if (cuponOpt.isPresent()) {
            Cupon cupon = cuponOpt.get();
            if (!cupon.isActivo()) {
                throw new Exception("El cupón no está activo");
            }
            if (cupon.getFechaExpiracion().before(new Date())) {
                throw new Exception("El cupón ha expirado");
            }
            return cupon;
        } else {
            throw new Exception("Cupón no válido");
        }
    }
}

