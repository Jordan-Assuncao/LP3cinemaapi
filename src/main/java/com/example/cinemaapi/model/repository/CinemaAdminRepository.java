package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.CinemaAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CinemaAdminRepository extends JpaRepository<CinemaAdmin, Long> {

    Optional<CinemaAdmin> findByEmail(String email);
}
