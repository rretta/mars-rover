package com.example.mars.services;


import org.springframework.stereotype.Service;

@Service
public class RoverService {
    private final MapService mapService;
    private int x;
    private int y;
    private int direction;



    public RoverService() {
        this.mapService = null;
        this.x = 0;
        this.y = 0;
        this.direction = 0;
    }

    public RoverService(MapService mapService){
        if (mapService.isHasRover()){
            throw new IllegalCallerException("Solo puede haber un rover");
        }
        int[] coords = mapService.findEmptyCoordinate();
        this.mapService = mapService;
        this.mapService.setHasRover(true);
        x = coords[0];
        y = coords[1];
        direction = 0;
    };



    public RoverService(MapService mapService, int x, int y, int direction){
        this.roverValidations(mapService, x, y);

        this.mapService = mapService;
        this.mapService.setHasRover(true);
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    private void roverValidations(MapService mapService, int x, int y){
        if (x > mapService.getMapSizeX() || x < 0 || y < 0 || y > mapService.getMapSizeY()){
            throw new IllegalArgumentException("Las coordenadas deben estar dentro del mapa");
        }
        if (mapService.isHasRover()){
            throw new IllegalCallerException("Solo puede haber un rover");
        }
        if(!mapService.checkEmptyObstaclePosition(new int[] {x,y})){
            throw new IllegalArgumentException("Existe un obstaculo en esa coordenada");
        }
    }
    public int[] position() {
        int[] coords = new int[2];
        coords[0] = x;
        coords[1] = y;
        return coords;
    }
    public void input(String command){
        command = command.toLowerCase();

        for (char c : command.toCharArray()) {
            if (c != 'l' && c != 'f' && c != 'b' && c != 'r') {
                throw new IllegalArgumentException("Caracter no permitido: " + c);
            }
            this.executeCommand(c);
        }
    }
    private void executeCommand(Character c){
        switch (c) {
            case 'l' -> this.direction = (this.direction + 3) % 4;
            case 'r' -> this.direction = (this.direction + 1) % 4;
            case 'b' -> this.movement('b');
            case 'f' -> this.movement('f');
        }
    }


    private void movement(Character command){
        int north = 0;
        int east = 1;
        int south = 2;
        int west = 3;
        if ((command == 'f' && this.direction == north) || (command == 'b' && this.direction == south) ){
            int[] new_position = new int[2];
            new_position[0] = x;
            new_position[1] = (y + 1) % mapService.getMapSizeY();
            if (this.mapService.checkEmptyObstaclePosition(new_position)){

                y = new_position[1];
                return;
            }
        }else if ((command == 'f' && this.direction == south) || (command == 'b' && this.direction == north) ){
            int[] new_position = new int[2];
            new_position[0] = x;
            new_position[1] = (y + mapService.getMapSizeY() - 1) % mapService.getMapSizeY();
            if (this.mapService.checkEmptyObstaclePosition(new_position)){
                y = new_position[1];
                return;
            }
        }else if ((command == 'f' && this.direction == east) || (command == 'b' && this.direction == west) ){
            int[] new_position = new int[2];
            new_position[0] = (x + 1) % mapService.getMapSizeX();
            new_position[1] = y;
            if (this.mapService.checkEmptyObstaclePosition(new_position)){
                x = new_position[0];
                return;
            }
        }else if ((command == 'f' && this.direction == west) || (command == 'b' && this.direction == east) ){
            int[] new_position = new int[2];
            new_position[0] = (x + mapService.getMapSizeX() - 1) % mapService.getMapSizeX();
            new_position[1] = y;
            if (this.mapService.checkEmptyObstaclePosition(new_position)){
                x = new_position[0];
                return;
            }
        }
        throw new RuntimeException("Existe un obstaculo en el camino, no se puedo completar el comando");
    }
    public int getDirection(){
        return direction;
    }
    public MapService getMap() {
        return mapService;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}