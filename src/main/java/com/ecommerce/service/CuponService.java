package com.ecommerce.service;

import com.ecommerce.model.Cupon;
import com.ecommerce.repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    // Aplicar cupón
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

    // Obtener todos los cupones
    public List<Cupon> obtenerTodosLosCupones() {
        return cuponRepository.findAll();
    }

    // Crear un nuevo cupón
    public Cupon crearCupon(Cupon cupon) {
        return cuponRepository.save(cupon);
    }

    // Eliminar un cupón por ID
    public void eliminarCupon(Long id) {
        cuponRepository.deleteById(id);
    }
}


