package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository("shipRepository")
public interface ShipRepository extends JpaRepository<Ship, Long>, JpaSpecificationExecutor<Ship>, PagingAndSortingRepository<Ship, Long> {

    Ship findById(long id);
    List<Ship> findAllByName(String name);

}
