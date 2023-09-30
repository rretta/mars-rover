package com.example.mars.controllers;


import com.example.mars.dto.ObstacleDto;
import com.example.mars.models.Obstacle;
import com.example.mars.responses.MapResponse;
import com.example.mars.services.MapService;
import com.example.mars.services.ObstacleService;
import com.example.mars.services.ObstacleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class MapController {
    private MapService mapService;
    private MapResponse mapResponse;


    @Autowired
    private ObstacleServiceImp obstacleService;


    @GetMapping("/api/map")
    public ResponseEntity<MapResponse> getMapping() {

        if (mapService != null) {

            try {
                mapResponse = new MapResponse();
                mapResponse.entity = mapService;
                return new ResponseEntity<>(mapResponse, HttpStatus.OK);
            } catch (Exception e) {
                mapResponse.exception = e.getMessage();
                return new ResponseEntity<>(mapResponse, HttpStatus.BAD_REQUEST);
            }


        }

        mapResponse = new MapResponse();
        mapResponse.exception = "No hay mapa";
        return new ResponseEntity<>(mapResponse, HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/api/map/obstacles")
    public List<Obstacle> getAllObstacles() {

       return obstacleService.findAll();

    }

    ;

    @PostMapping("/api/map/default")
    public ResponseEntity<MapResponse> mapGeneratorDefault() {
        if (mapService == null) {
            try {
                mapService = new MapService();
                mapResponse = new MapResponse(); // Inicializa mapResponse
                mapResponse.entity = mapService;
                return new ResponseEntity<>(mapResponse, HttpStatus.CREATED);
            } catch (Exception e) {
                mapResponse = new MapResponse(); // Inicializa mapResponse
                mapResponse.exception = e.getMessage();
                return new ResponseEntity<>(mapResponse, HttpStatus.BAD_REQUEST);
            }
        }
        //mapResponse = new MapResponse();
        mapResponse.exception = "Ya creaste un mapa";
        return new ResponseEntity<>(mapResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/map/custom/{xParam}/{yParam}")
    public ResponseEntity<MapResponse> customMapGenerator(@PathVariable int xParam, @PathVariable int yParam) {
        if (mapService == null) {
            try {
                mapService = new MapService(xParam,yParam);
                mapResponse = new MapResponse();
                mapResponse.entity = mapService;
                return new ResponseEntity<>(mapResponse, HttpStatus.CREATED);
            } catch (Exception e) {
                mapResponse.exception = e.getMessage();
                return new ResponseEntity<>(mapResponse, HttpStatus.BAD_REQUEST);
            }
        }
        mapResponse = new MapResponse();
        mapResponse.exception = "Ya hay un mapa creado.";
        return new ResponseEntity<>(mapResponse, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/api/map/obstacle/random")
    public ResponseEntity<MapResponse> obstacleGenerator() {

        mapResponse = new MapResponse();
        if (mapService == null) {
            mapResponse.exception = "No existe un mapa";
            return new ResponseEntity<>(mapResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            int[] coords = mapService.createRandomObstacle();

            obstacleService.guardarObstaculo(coords[0], coords[1]);
            mapResponse.entity = mapService;
            return new ResponseEntity<>(mapResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            mapResponse.exception = e.getMessage();
            return new ResponseEntity<>(mapResponse, HttpStatus.BAD_REQUEST);
        }

    }


    public MapService getMap() {
        return mapService;
    }
}
