
<center>

# THE MARS ROVER KATA
</center>
<p align="center">
    <img src="https://github.com/rretta/mars-rover/assets/87555292/ff1dabfa-944c-40cb-8b85-519cc0af8049" alt="pngegg (1)" width="200" height="200">

  
</p>

API Rest created for the [ATL Academy](https://getgophish.com) in which the [Mars Rover Kata](https://getgophish.com) is performed using Java as language and applying TDD with Junit.

This readme describes how to use the API and the endpoints.



## ENDPOINTS

### MAP


| Method   | Route                      | Description                                      |
| -------- | -------------------------- | ------------------------------------------------ |
| GET      | /api/map               | All info about map and obstacles                               |
| POST     | /api/map/default               | Create a default map (10x10)                          |
| POST      | /api/map/custom/{xParam}/{yParam}         | Create custom map sending the params X and Y                   |
| POST      | /api/map/obstacle/random          |  Create random obstacle             |


### ROVER


| Method   | Route                      | Description                                      |
| -------- | -------------------------- | ------------------------------------------------ |
| GET      | /api/rover               | All info about the rover                               |
| POST     | /api/rover/default               | Create rover at default position                          |
| POST      | /api/rover/custom/{xParam}/{yParam}         | Create custom rover sending the params X and Y                   |
| POST      | /api/rover/move/{input}          |  Move the rover             |


## RESPONSES

API return the JSON for example after you create a default map the successful response will be
```javascript
{
    "entity": {
        "mapSizeX": 10,
        "mapSizeY": 10,
        "hasRover": false,
        "obstacles": []
    },
    "exception": null
}
```

The `entity` attribute contains all information about the map.

The `obstacles` attribute will bring an array of integers with X-axis and Y-axis with information of each node where an obstacle is found.



The rover in case of collision with an obstacle will give a `RuntimeException` indicating all the command setup that was performed before the collision.


## STATUS CODES

*The Mars Rover Kata* returns the following status codes in its API:

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 201 | `CREATED` |
| 400 | `BAD REQUEST` |
| 404 | `NOT FOUND` |
| 500 | `INTERNAL SERVER ERROR` |
