package com.ecommerce.controller;

import com.ecommerce.model.Cupon;
import com.ecommerce.repository.CuponRepository;
import com.ecommerce.service.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @Autowired
    private CuponRepository cuponRepository;

    // Aplicar un cupón a un pedido
    @PostMapping("/aplicar")
    public Cupon aplicarCupon(@RequestParam String codigo) {
        try {
            return cuponService.aplicarCupon(codigo);
        } catch (Exception e) {
            throw new RuntimeException("Error al aplicar el cupón: " + e.getMessage());
        }
    }

    // Listar todos los cupones
    @GetMapping
    public List<Cupon> obtenerTodosLosCupones() {
        return cuponRepository.findAll();
    }

    // Crear un nuevo cupón
    @PostMapping
    public Cupon crearCupon(@RequestBody Cupon cupon) {
        return cuponRepository.save(cupon);
    }

    // Obtener un cupón por ID
    @GetMapping("/{id}")
    public Optional<Cupon> obtenerCuponPorId(@PathVariable Long id) {
        return cuponRepository.findById(id);
    }

    // Actualizar un cupón existente
    @PutMapping("/{id}")
    public Cupon actualizarCupon(@PathVariable Long id, @RequestBody Cupon cuponActualizado) {
        Optional<Cupon> cuponExistente = cuponRepository.findById(id);
        if (cuponExistente.isPresent()) {
            Cupon cupon = cuponExistente.get();
            cupon.setCodigo(cuponActualizado.getCodigo());
            cupon.setDescuento(cuponActualizado.getDescuento());
            cupon.setFechaExpiracion(cuponActualizado.getFechaExpiracion());
            cupon.setActivo(cuponActualizado.isActivo());
            return cuponRepository.save(cupon);
        } else {
            throw new RuntimeException("Cupón no encontrado con el ID: " + id);
        }
    }

    // Eliminar un cupón por ID
    @DeleteMapping("/{id}")
    public void eliminarCupon(@PathVariable Long id) {
        cuponRepository.deleteById(id);
    }
}


