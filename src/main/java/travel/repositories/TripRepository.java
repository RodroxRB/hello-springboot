package travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import travel.entities.Trip;

/**
 * Created by BARCO on 23-Feb-17.
 */
public interface TripRepository extends JpaRepository<Trip, Integer> {


  Optional<Trip> findById(Integer id);
}
