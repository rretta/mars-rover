package com.example.mars.repository;

import com.example.mars.models.Obstacle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IObstacleRepository extends JpaRepository<Obstacle, Long> {
}
