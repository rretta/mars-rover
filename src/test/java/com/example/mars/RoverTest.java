package com.example.mars;

import com.example.mars.services.MapService;
import com.example.mars.services.RoverService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoverTest {

    @Test
    public void shouldCreateRoverWithDefaultPosition() {
        MapService map = new MapService(10, 10);
        RoverService rover = new RoverService(map);

        int[] position = rover.position();

        Assertions.assertEquals(0, position[0]);
        Assertions.assertEquals(0, position[1]);
        Assertions.assertEquals(0, rover.getDirection());
    }

    @Test
    public void shouldCreateRoverWithCustomPosition() {
        MapService map = new MapService(10, 10);
        RoverService rover = new RoverService(map, 5, 5, 2);

        int[] position = rover.position();

        Assertions.assertEquals(5, position[0]);
        Assertions.assertEquals(5, position[1]);
        Assertions.assertEquals(2, rover.getDirection());
    }

    @Test
    public void shouldMoveRoverForward() {
        MapService map = new MapService(10, 10);
        RoverService rover = new RoverService(map);

        rover.input("f");
        int[] position = rover.position();

        Assertions.assertEquals(0, position[0]);
        Assertions.assertEquals(1, position[1]);
    }

    @Test
    public void shouldMoveRoverBackward() {
        MapService map = new MapService(10, 10);
        RoverService rover = new RoverService(map);

        rover.input("b");
        int[] position = rover.position();

        Assertions.assertEquals(0, position[0]);
        Assertions.assertEquals(9, position[1]);
    }

    @Test
    public void shouldTurnRoverLeft() {
        MapService map = new MapService(10, 10);
        RoverService rover = new RoverService(map);

        rover.input("l");

        Assertions.assertEquals(3, rover.getDirection());
    }

    @Test
    public void shouldTurnRoverRight() {
        MapService map = new MapService(10, 10);
        RoverService rover = new RoverService(map);

        rover.input("r");

        Assertions.assertEquals(1, rover.getDirection());
    }
}
