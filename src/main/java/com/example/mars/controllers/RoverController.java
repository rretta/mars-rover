package com.example.mars.controllers;

import com.example.mars.responses.RoverResponse;
import com.example.mars.services.MapService;
import com.example.mars.services.RoverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoverController {
    private RoverService roverService;
    private RoverResponse roverResponse;
    private final MapController mapController;

    public RoverController(MapController mapController) {
        this.mapController = mapController;
    }
    @GetMapping("/api/rover")
    public ResponseEntity<RoverResponse> roverStatus(){
        roverResponse = new RoverResponse();
        if (roverService !=null){
            roverResponse.entity = roverService;
            return new ResponseEntity<>(roverResponse, HttpStatus.OK);
        }else{
            roverResponse.exception = "No existe ningun rover";
            return new ResponseEntity<>(roverResponse ,HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/api/rover/default")
    public ResponseEntity<RoverResponse> createDefaultRover(){
        MapService mapService = mapController.getMap();
        RoverResponse roverResponse = new RoverResponse();
        if (roverService == null && mapService !=null){
            try {
                roverService = new RoverService(mapService);
                roverResponse.entity = roverService;
                return new ResponseEntity<>(roverResponse, HttpStatus.CREATED);
            }catch(Exception e){
                roverResponse.exception = e.getMessage();
                return new ResponseEntity<>(roverResponse ,HttpStatus.BAD_REQUEST);
            }
        }else{
            roverResponse.exception = "No se a podido crear un rover. " +
                    "Ya existe un rover o no se a creado un mapa.";
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/api/rover/custom/{xParam}/{yParam}")
    public ResponseEntity<RoverResponse> createCustomRover(@PathVariable int x, @PathVariable int y, @PathVariable int facing){
        MapService mapService = mapController.getMap();
        RoverResponse roverResponse = new RoverResponse();
        if (roverService == null && mapService !=null){
            try{
                roverService = new RoverService(mapService,x,y,facing);
                roverResponse.entity = roverService;
                return new ResponseEntity<>(roverResponse, HttpStatus.CREATED);
            }catch(Exception e){
                roverResponse.exception = e.getMessage();
                return new ResponseEntity<>(roverResponse, HttpStatus.BAD_REQUEST);
            }
        }else{
            roverResponse.exception = "No se a podido crear un rover. " +
                    "Ya existe un rover o no se a creado un mapa.";
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/api/rover/move/{input}")

    public ResponseEntity<RoverResponse> sendInputToRover(@PathVariable String input){

        roverResponse = new RoverResponse();

        if (roverService !=null){

            roverService.input(input);
            roverResponse.entity = roverService;
            return new ResponseEntity<>(roverResponse, HttpStatus.OK);
        }else{
            roverResponse.exception = "No existe ningun rover.";
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
