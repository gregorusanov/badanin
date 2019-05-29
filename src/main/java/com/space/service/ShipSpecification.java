package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ShipSpecification {
    public static Specification<Ship> filter(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                                             Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {

        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                final Collection<Predicate> predicates = new ArrayList<>();

                if (name != null) {
                    final Predicate namePredicate = cb.like(root.get("name"), "%"+name+"%");
                    predicates.add(namePredicate);
                }

                if (planet != null) {
                    final Predicate planetPredicate = cb.like(root.get("planet"), "%"+planet+"%" );
                    predicates.add(planetPredicate);
                }

                if (shipType != null) {
                    final Predicate shipTypePredicate = cb.equal(root.get("shipType"), shipType );
                    predicates.add(shipTypePredicate);
                }

                if (after != null) {
                    Date start = new Date(after);
                    final Predicate afterPredicate = cb.greaterThanOrEqualTo(root.get("prodDate"), start);
                    predicates.add(afterPredicate);
                }

                if (before != null) {
                    Date finish = new Date(before);
                    final Predicate beforePredicate = cb.lessThanOrEqualTo(root.get("prodDate"), finish);
                    predicates.add(beforePredicate);
                }

                if (isUsed != null) {
                    final Predicate isUsedPredicate = cb.equal(root.get("isUsed"), isUsed);
                    predicates.add(isUsedPredicate);
                }

                if (minSpeed != null) {
                    final Predicate minSpeedPredicate = cb.greaterThanOrEqualTo(root.get("speed"), minSpeed);
                    predicates.add(minSpeedPredicate);
                }

                if (maxSpeed != null) {
                    final Predicate maxSpeedPredicate = cb.lessThanOrEqualTo(root.get("speed"), maxSpeed);
                    predicates.add(maxSpeedPredicate);
                }

                if (minCrewSize != null) {
                    final Predicate minCrewSizePredicate = cb.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize);
                    predicates.add(minCrewSizePredicate);
                }

                if (maxCrewSize != null) {
                    final Predicate maxCrewSizePredicate = cb.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize);
                    predicates.add(maxCrewSizePredicate);
                }

                if (minRating != null) {
                    final Predicate minRatingPredicate = cb.greaterThanOrEqualTo(root.get("rating"), minRating);
                    predicates.add(minRatingPredicate);
                }

                if (maxRating != null) {
                    final Predicate maxRatingPredicate = cb.lessThanOrEqualTo(root.get("rating"), maxRating);
                    predicates.add(maxRatingPredicate);
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
