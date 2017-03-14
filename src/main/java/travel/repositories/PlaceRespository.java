package travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import travel.entities.Place;
import travel.entities.Trip;

/**
 * Created by BARCO on 14-Mar-17.
 */
public interface PlaceRespository extends JpaRepository<Place, String> {
}
