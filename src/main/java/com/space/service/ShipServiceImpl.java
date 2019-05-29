package com.space.service;

import com.space.controller.ShipOrder;
import com.space.exception.NotFoundException;
import com.space.exception.NotValidIdException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.space.service.ShipSpecification.filter;


@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    @Transactional
    public List<Ship> getAllShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                                  Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder shipOrder, Integer pageNumber, Integer pageSize) {

        String order;
        if (shipOrder != null) {
            order = shipOrder.getFieldName();
        } else {
            order = ShipOrder.ID.getFieldName();
        }

        return shipRepository.findAll(filter(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating), PageRequest.of(pageNumber, pageSize, Sort.by(order))).getContent();//
    }

    @Override
    @Transactional
    public Integer getCountShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                              Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        List<Ship> list = shipRepository.findAll(filter(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating));
        return list.size();

    }

    @Override
    @Transactional
    public Ship createShip(ShipRequest ship) throws NotValidIdException {

        Ship savedShip = new Ship();
        if (ship.getName() != null && ship.getName().length() < 50 && !ship.getName().isEmpty()) {
            savedShip.setName(ship.getName());
        } else
            throw new NotValidIdException();

        if (ship.getPlanet() != null && ship.getPlanet().length() < 50 && !ship.getPlanet().isEmpty()) {
            savedShip.setPlanet(ship.getPlanet());
        } else
            throw new NotValidIdException();

        if (ship.getSpeed() != null) {
            double speed = Math.rint(100 * ship.getSpeed()) / 100;
            if (speed >= 0.01 && speed <= 0.99) {
                savedShip.setSpeed(speed);
            } else
                throw new NotValidIdException();
        } else
            throw new NotValidIdException();


        if (ship.getCrewSize() != null && ship.getCrewSize() >= 1 && ship.getCrewSize() <= 9999) {
            savedShip.setCrewSize(ship.getCrewSize());
        } else
            throw new NotValidIdException();

        long fromDate = new GregorianCalendar(2800, 0,1).getTimeInMillis();
        long beforeDate = new GregorianCalendar(3020, 0,1).getTimeInMillis();
        if (ship.getProdDate() != null && ship.getProdDate() >= fromDate && ship.getProdDate() < beforeDate) {
            savedShip.setProdDate(new Date(ship.getProdDate()));
        } else {
            throw new NotValidIdException();
        }

        if (ship.getShipType() != null) {
            savedShip.setShipType(ship.getShipType());
        } else {
            throw new NotValidIdException();
        }

        if (ship.getUsed() != null) {
            savedShip.setUsed(ship.getUsed());
        } else {
            savedShip.setUsed(false);
        }

        double rating;
        Calendar cal = Calendar.getInstance();
        cal.setTime(savedShip.getProdDate());
        if (savedShip.getUsed()) {
            rating = 80 * savedShip.getSpeed() * 0.5 / (3019 - cal.get(Calendar.YEAR) + 1);
            double roundRating = Math.rint(100 * rating) / 100;
            savedShip.setRating(roundRating);
        } else {
            rating = 80 * savedShip.getSpeed() * 1 / (3019 - cal.get(Calendar.YEAR) + 1);
            double roundRating = Math.rint(100 * rating) / 100;
            savedShip.setRating(roundRating);
        }

        return shipRepository.saveAndFlush(savedShip);
    }

    @Override
    @Transactional
    public Ship getShip(String sId) throws NotFoundException, NotValidIdException {
        long id;
        try {
            id = Long.parseLong(sId);

        } catch (RuntimeException e) {
            throw new NotValidIdException();
        }
        if (id < 1) {
            throw new NotValidIdException();
        }
        if (!shipRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        return shipRepository.findById(id);
    }

    @Override
    @Transactional
    public Ship updateShip(String sId, ShipRequest ship) throws NotFoundException, NotValidIdException {
        long id;
        try {
            id = Long.parseLong(sId);
            //id = convertId(sId);
        } catch (RuntimeException e) {
            throw new NotValidIdException();
        }
        if (id < 1) {
            throw new NotValidIdException();
        }
        if (!shipRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        Ship updatedShip = shipRepository.findById(id);
////
        /*if (ship.getName() != null && ship.getName().length() < 50 && !ship.getName().isEmpty()) {
            updatedShip.setName(ship.getName());
        } else {
            throw new NotValidIdException();
        }*/
        if (ship.getName() != null) {
            if (ship.getName().length() < 50 && !ship.getName().isEmpty()) {
                updatedShip.setName(ship.getName());
            } else {
                throw new NotValidIdException();
            }
        }
        /*if (ship.getPlanet() != null && ship.getPlanet().length() < 50 && !ship.getPlanet().isEmpty()) {
            updatedShip.setPlanet(ship.getPlanet());
        } else {
            throw new NotValidIdException();
        }*/
        if (ship.getPlanet() != null) {
            if (ship.getPlanet().length() < 50 && !ship.getPlanet().isEmpty()) {
                updatedShip.setPlanet(ship.getPlanet());
            } else {
                throw new NotValidIdException();
            }
        }

        if (ship.getShipType() != null) {
            updatedShip.setShipType(ship.getShipType());
        }

        long fromDate = new GregorianCalendar(2800, 0,1).getTimeInMillis();
        long beforeDate = new GregorianCalendar(3020, 0,1).getTimeInMillis();
        /*if (ship.getProdDate() != null && ship.getProdDate() >= fromDate && ship.getProdDate() < beforeDate) {
            updatedShip.setProdDate(new Date(ship.getProdDate()));
        } else {
            throw new NotValidIdException();
        }*/
        if (ship.getProdDate() != null) {
            if (ship.getProdDate() >= fromDate && ship.getProdDate() < beforeDate) {
                updatedShip.setProdDate(new Date(ship.getProdDate()));
            } else {
                throw new NotValidIdException();
            }
        }
        /////////// ??????
        /*if (ship.getUsed() != null) {
            updatedShip.setUsed(ship.getUsed());
        }*/
        if (ship.getUsed() != null)
            updatedShip.setUsed(ship.getUsed());

        if (ship.getSpeed() != null) {
            double updateSpeed = Math.rint(100 * ship.getSpeed()) / 100;
            if (updateSpeed >= 0.01 && updateSpeed <= 0.99) {
                updatedShip.setSpeed(updateSpeed);
            } else {
                throw new NotValidIdException();
            }
        }

       // double roundRating = Math.rint(100 * rating) / 100;
        /*if (ship.getSpeed() != null && ship.getSpeed() >= 0.01 && ship.getSpeed() <= 0.99) {
            updatedShip.setSpeed(ship.getSpeed());
        } else {
            throw new NotValidIdException();
        }*/


        if (ship.getCrewSize() != null) {
            if (ship.getCrewSize() >= 1 && ship.getCrewSize() <= 9999) {
                updatedShip.setCrewSize(ship.getCrewSize());
            } else {
                throw new NotValidIdException();
            }
        }
        /*if (ship.getCrewSize() != null && ship.getCrewSize() >= 1 && ship.getCrewSize() <= 9999) {
            updatedShip.setCrewSize(ship.getCrewSize());
        } else {
            throw new NotValidIdException();
        }*/



        Calendar cal = Calendar.getInstance();
        cal.setTime(updatedShip.getProdDate());
        double rating;
        if (updatedShip.getUsed()) {
            rating = 80 * updatedShip.getSpeed() * 0.5 / (3019 - cal.get(Calendar.YEAR) + 1);
        } else {
            rating = 80 * updatedShip.getSpeed() * 1 / (3019 - cal.get(Calendar.YEAR) + 1);
        }
        double roundRating = Math.rint(100 * rating) / 100;
        updatedShip.setRating(roundRating);

        return shipRepository.saveAndFlush(updatedShip);
    }

    @Override
    @Transactional
    public void deleteShip(String sId) throws NotFoundException, NotValidIdException {
        long id;
        try {
            id = Long.parseLong(sId);
            //id = convertId(sId);
        } catch (RuntimeException e) {
            throw new NotValidIdException();
        }
        if (id < 1) {
            throw new NotValidIdException();
        }
        if (!shipRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        shipRepository.deleteById(id);
    }

}
