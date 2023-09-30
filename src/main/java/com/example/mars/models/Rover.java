package com.example.mars.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rover")
public class Rover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "posx")
    private Integer x;

    @Column(name = "posy")
    private Integer y;

    @Column(name = "direction")
    private String direction;


}
