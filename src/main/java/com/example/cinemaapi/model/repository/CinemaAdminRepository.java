package com.example.cinemaapi.model.repository;

import com.example.cinemaapi.model.entity.CinemaAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaAdminRepository extends JpaRepository<CinemaAdmin, Long> {
}
