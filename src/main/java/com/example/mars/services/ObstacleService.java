package com.example.mars.services;

import com.example.mars.dto.ObstacleDto;
import com.example.mars.models.Obstacle;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ObstacleService {
    public List<Obstacle> findAll();
    public void guardarObstaculo(int X, int Y);
}
