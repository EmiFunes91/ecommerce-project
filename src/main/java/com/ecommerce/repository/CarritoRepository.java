package com.ecommerce.repository;

import com.ecommerce.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}

