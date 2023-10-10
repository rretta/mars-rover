package com.example.mars.services;


import com.example.mars.models.Rover;
import com.example.mars.repository.IRoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoverServiceImp implements RoverService {



    private final MapService mapService;
    private int x;
    private int y;
    private int direction;

    private String fullcommand;


    @Autowired
    IRoverRepository repository;


    public RoverServiceImp() {
        this.mapService = null;
        this.x = 0;
        this.y = 0;
        this.direction = 0;
        this.fullcommand = "";
    }

    public RoverServiceImp(MapService mapService){
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



    public RoverServiceImp(MapService mapService, int x, int y, int direction){
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

        this.fullcommand = fullcommand + command;

        for (char c : command.toCharArray()) {
            if (c != 'l' && c != 'f' && c != 'b' && c != 'r') {
                throw new IllegalArgumentException("Caracter no permitido: " + c);
            }
            this.executeCommand(c);
        }


/*
        String posToString = "";

        switch (this.getDirection()){
            case 0:
                posToString = "NORTH";
            case 1:
                posToString = "EAST";
            case 2:
                posToString = "SOUTH";
            case 3:
                posToString = "WEST";
        }

        Rover rover = new Rover();
        rover.setX(this.getX());
        rover.setY(this.getY());
        rover.setDirection(posToString);



        repository.save(rover);

*/





    }
    private void executeCommand(Character c){
        switch (c) {
            case 'b' -> this.movement('b');
            case 'f' -> this.movement('f');
            case 'l' -> this.direction = (this.direction + 3) % 4;
            case 'r' -> this.direction = (this.direction + 1) % 4;

        }
    }


    private void movement(Character command){
       final int NORTH = 0;
       final int EAST = 1;
       final int SOUTH = 2;
       final int WEST = 3;
        if ((command == 'f' && this.direction == NORTH) || (command == 'b' && this.direction == SOUTH) ){
            int[] new_position = new int[2];
            new_position[0] = x;
            new_position[1] = (y + 1) % mapService.getMapSizeY();
            if (this.mapService.checkEmptyObstaclePosition(new_position)){

                y = new_position[1];
                return;
            }
        }else if ((command == 'f' && this.direction == SOUTH) || (command == 'b' && this.direction == NORTH) ){
            int[] new_position = new int[2];
            new_position[0] = x;
            new_position[1] = (y + mapService.getMapSizeY() - 1) % mapService.getMapSizeY();
            if (this.mapService.checkEmptyObstaclePosition(new_position)){
                y = new_position[1];
                return;
            }
        }else if ((command == 'f' && this.direction == EAST) || (command == 'b' && this.direction == WEST) ){
            int[] new_position = new int[2];
            new_position[0] = (x + 1) % mapService.getMapSizeX();
            new_position[1] = y;
            if (this.mapService.checkEmptyObstaclePosition(new_position)){
                x = new_position[0];
                return;
            }
        }else if ((command == 'f' && this.direction == WEST) || (command == 'b' && this.direction == EAST) ){
            int[] new_position = new int[2];
            new_position[0] = (x + mapService.getMapSizeX() - 1) % mapService.getMapSizeX();
            new_position[1] = y;
            if (this.mapService.checkEmptyObstaclePosition(new_position)){
                x = new_position[0];
                return;
            }
        }

        String parsedCommand = fullcommand.substring(4);

        throw new RuntimeException("Existe un obstaculo en el camino, no se puedo completar el comando " + parsedCommand.toUpperCase());
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



    @Override
    public void guardarRover(int posX, int posY, int DIR) {




        Rover rover = new Rover();
        rover.setX(posX);
        rover.setY(posY);
        rover.setDirection(convertDirection(DIR));

        repository.save(rover);




    }



        private static String convertDirection(int direction) {
            String result = "";
            switch (direction) {
                case 0:
                    result = "NORTH";
                    break;
                case 1:
                    result = "EAST";
                    break;
                case 2:
                    result = "SOUTH";
                    break;
                case 3:
                    result = "WEST";
                    break;
                default:
                    result = "Invalid direction";
                    break;
            }
            return result;
        }




}