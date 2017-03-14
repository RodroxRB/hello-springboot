package travel.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by BARCO on 22-Feb-17.
 */
@Entity
@Table(name = "place_trip")
@IdClass(PlaceStayingId.class)
public class PlaceStaying implements Serializable {


  public PlaceStaying() {

  }

  @Column(name = "date")
  @DateTimeFormat(pattern="yyyy-MM-dd")
  @NotNull
  private Date date;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_id",referencedColumnName = "id")
  private Trip trip;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id",referencedColumnName = "id")
  private Place place;


  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Trip getTrip() {
    return trip;
  }

  public void setTrip(Trip trip) {
    this.trip = trip;
  }

  public Place getPlace() {
    return place;
  }

  public void setPlace(Place place) {
    this.place = place;
  }

  @Override
  public String toString() {
    return "PlaceStaying{" +
        ", date=" + date +
        ", place=" + place +
        '}';
  }
}
