package com.space.service;


import com.space.controller.ShipOrder;
import com.space.exception.NotFoundException;
import com.space.exception.NotValidIdException;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.List;

public interface ShipService {

    List<Ship> getAllShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                           Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder shipOrder, Integer pageNumber, Integer pageSize);

    Integer getCountShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                       Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating);

    Ship createShip(ShipRequest ship) throws NotValidIdException;

    Ship getShip(String id) throws NotFoundException, NotValidIdException;

    Ship updateShip(String id, ShipRequest ship) throws NotFoundException, NotValidIdException;

    void deleteShip(String id) throws NotValidIdException, NotFoundException;
}
