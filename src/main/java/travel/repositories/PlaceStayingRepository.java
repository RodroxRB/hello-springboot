package travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import travel.entities.PlaceStaying;
import travel.entities.PlaceStayingId;
import travel.entities.Trip;

/**
 * Created by BARCO on 14-Mar-17.
 */
public interface PlaceStayingRepository  extends JpaRepository<PlaceStaying, PlaceStayingId> {


}
