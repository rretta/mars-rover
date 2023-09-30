package com.example.mars.services;

import com.example.mars.dto.ObstacleDto;
import com.example.mars.models.Obstacle;
import com.example.mars.repository.IObstacleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObstacleServiceImp implements ObstacleService {

    @Autowired
    IObstacleRepository repository;


    @Override
    public List<Obstacle> findAll() {
        return repository.findAll();
    }

    @Override
    public void guardarObstaculo(int X, int Y) {
        System.out.println("testing");
        Obstacle obstacle = new Obstacle();
        obstacle.setX(X);
        obstacle.setY(Y);

        repository.save(obstacle);
    }
}


