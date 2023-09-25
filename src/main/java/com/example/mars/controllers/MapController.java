package com.example.mars.controllers;


import com.example.mars.responses.MapResponse;
import com.example.mars.services.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class MapController {
    private MapService mapService;
    private MapResponse mapResponse;


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
            mapService.createRandomObstacle();
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
