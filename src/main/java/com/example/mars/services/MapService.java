package com.example.mars.services;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Service
public class MapService {
    private final int mapSizeX;
    private final int mapSizeY;
    private  boolean hasRover = false;

    private ArrayList<int[]> obstacles = new ArrayList<>();


    public MapService(int mapSizeX, int mapSizeY) {
        if (mapSizeX <= 0 || mapSizeY <= 0) {
            throw new IllegalArgumentException("Los valores de X e Y deben ser mayores que cero.");
        }
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
    }
    public MapService(){
        this.mapSizeX = 10;
        this.mapSizeY = 10;
    }

    public void createRandomObstacle(){
        if (hasRover){
            throw new IllegalCallerException("Debe crear los obstaculos antes de crear el Rover");
        }
        int [] coords = new int[2];
        Random random = new Random();
        coords[0] = random.nextInt(this.getMapSizeX()+1);
        coords[1] = random.nextInt(this.getMapSizeY()+1);
        if (obstacles.isEmpty()){
            obstacles.add(coords);
        }else if(checkEmptyObstaclePosition(coords)){
            obstacles.add(coords);
        }else {
            obstacles.add(findEmptyCoordinate());
        }
    }
    public int[] findEmptyCoordinate() {
        for (int i = 0; i <= mapSizeX; i++) {
            for (int j = 0; j <= mapSizeY; j++) {
                int[] coords = {i, j};
                if (this.checkEmptyObstaclePosition(coords)) {
                    return coords;
                }
            }
        }
        throw new RuntimeException("El mapa esta lleno de obstaculos");
    }


    public boolean checkEmptyObstaclePosition(int[] coords){
        boolean check = true;
        for (int[] obstacle : obstacles) {
            if (Arrays.equals(coords, obstacle)) {
                check = false;
                break;
            }
        }
        return check;
    }



    public ArrayList<int[]> getObstacles() {
        return obstacles;
    }

    public int getMapSizeY() {
        return mapSizeY;
    }
    public int getMapSizeX() {
        return mapSizeX;
    }
    public boolean isHasRover() {
        return hasRover;
    }

    public void setHasRover(boolean hasRover) {
        this.hasRover = hasRover;
    }


}
