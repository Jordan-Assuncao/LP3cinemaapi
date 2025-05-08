package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
