package com.example.mars;

import com.example.mars.services.MapService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MapTest {
    @Test
    public void shouldCreateDefaultMap(){
        MapService map = new MapService();
        Assertions.assertNotNull(map.getMapSizeX());
        Assertions.assertNotNull(map.getMapSizeY());
    }


    @Test
    public void shouldCreateCustomMap(){
        MapService map = new MapService(15, 25);
        Assertions.assertEquals(15, map.getMapSizeX());
        Assertions.assertEquals(25, map.getMapSizeY());
    }

    @Test
    public void shouldCreateRandomObstacle() {
        MapService map = new MapService(10, 10);


        Assertions.assertTrue(map.getObstacles().isEmpty());


        map.createRandomObstacle();

        // Verificar que haya un obstáculo después de la creación
        Assertions.assertFalse(map.getObstacles().isEmpty());
    }

    @Test
    public void shouldCreateUniqueObstacles() {
        MapService map = new MapService(10, 10);

        // Crear varios obstáculos
        for (int i = 0; i < 10; i++) {
            map.createRandomObstacle();
        }

        // Verificar que todos los obstáculos sean únicos
        ArrayList<int[]> obstacles = map.getObstacles();
        for (int i = 0; i < obstacles.size(); i++) {
            for (int j = i + 1; j < obstacles.size(); j++) {
                Assertions.assertFalse(Arrays.equals(obstacles.get(i), obstacles.get(j)));
            }
        }
    }

    @Test
    public void shouldCreateObstacleInsideMapLimits() {
        MapService map = new MapService(10, 10);

        for (int i = 0; i < 100; i++) {
            map.createRandomObstacle();
            ArrayList<int[]> obstacles = map.getObstacles();
            for (int[] obstacle : obstacles) {
                Assertions.assertTrue(obstacle[0] >= 0 && obstacle[0] <= 10);
                Assertions.assertTrue(obstacle[1] >= 0 && obstacle[1] <= 10);
            }
        }
    }

}
