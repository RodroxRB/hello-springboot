package travel.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
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

  @Column(name = "date_arrival")
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date date_arrival;


  @Column(name = "date_departure")
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date date_departure;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_id",referencedColumnName = "id")
  @Valid
  private Trip trip;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id",referencedColumnName = "id")
  @Valid
  private Place place;


  public Date getDate_arrival() {
    return date_arrival;
  }

  public void setDate_arrival(Date date_arrival) {
    this.date_arrival = date_arrival;
  }

  public Date getDate_departure() {
    return date_departure;
  }

  public void setDate_departure(Date date_departure) {
    this.date_departure = date_departure;
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
        ", place=" + place +
        '}';
  }
}
