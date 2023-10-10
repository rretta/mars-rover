package com.example.mars.repository;

import com.example.mars.models.Rover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoverRepository extends JpaRepository<Rover, Long> {
}