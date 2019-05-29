package com.space.controller;

import com.space.exception.NotFoundException;
import com.space.exception.NotValidIdException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipRequest;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest")
public class ShipController {

    @Autowired
    private ShipService shipService;

    @GetMapping("/ships")
    public ResponseEntity<List<Ship>> getAllShips(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "planet", required = false) String planet,
                                                  @RequestParam(value = "shipType", required = false) ShipType shipType, @RequestParam(value = "after", required = false) Long after,
                                                  @RequestParam(value = "before", required = false) Long before, @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                                  @RequestParam(value = "minSpeed", required = false) Double minSpeed, @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                                  @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize, @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                                  @RequestParam(value = "minRating", required = false) Double minRating, @RequestParam(value = "maxRating", required = false) Double maxRating,
                                                  @RequestParam(value = "order", required = false) ShipOrder shipOrder, @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                  @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize){
        return new ResponseEntity<>(shipService.getAllShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, shipOrder, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/ships/count")
    public ResponseEntity<Integer> getCount(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "planet", required = false) String planet,
                                         @RequestParam(value = "shipType", required = false) ShipType shipType, @RequestParam(value = "after", required = false) Long after,
                                         @RequestParam(value = "before", required = false) Long before, @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                         @RequestParam(value = "minSpeed", required = false) Double minSpeed, @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                         @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize, @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                         @RequestParam(value = "minRating", required = false) Double minRating, @RequestParam(value = "maxRating", required = false) Double maxRating) {
        return new ResponseEntity<>(shipService.getCountShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating), HttpStatus.OK);
    }

    @PostMapping("/ships")
    public ResponseEntity<Ship> createShip(@RequestBody ShipRequest shipRequest) throws NotValidIdException {
        Ship ship = shipService.createShip(shipRequest);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @PostMapping("/ships/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable("id") String id, @RequestBody ShipRequest shipRequest)  throws NotFoundException, NotValidIdException {
        Ship ship = shipService.updateShip(id, shipRequest);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @GetMapping("/ships/{id}")
    public ResponseEntity<Ship> getShipById(@PathVariable("id") String id) throws NotValidIdException, NotFoundException {  // проверить на валидность id
        Ship ship = shipService.getShip(id);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @DeleteMapping("/ships/{id}")
    public ResponseEntity deleteShipById(@PathVariable("id") String id) throws NotValidIdException, NotFoundException { // проверить на валидность id
        shipService.deleteShip(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
