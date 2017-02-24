package travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import travel.entities.Trip;

/**
 * Created by BARCO on 23-Feb-17.
 */
public interface TripRepository extends JpaRepository<Trip, Long> {


  Optional<Trip> findById(Integer id);

  @Query("Select t from Trip t where t.user_id = :user_id")
  List<Trip> findByUser_id(@Param("user_id") String user_id);
}
