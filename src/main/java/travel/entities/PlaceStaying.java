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
@Table(name = "place")
public class PlaceStaying implements Serializable {


  public PlaceStaying() {

  }
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "place_id")
  @NotEmpty
  private String place_id;

  @Column(name = "name")
  @NotEmpty
  private String name;

  @Column(name = "lat")
  @NotNull
  private float lat;

  @Column(name = "lon")
  @NotNull
  private float lon;

  @Column(name = "date")
  @DateTimeFormat(pattern="yyyy-MM-dd")
  @NotNull
  private Date date;

  @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
  @JoinColumn(name = "trip_id")
  private Trip trip;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Trip getTrip() {
    return trip;
  }

  public void setTrip(Trip trip) {
    this.trip = trip;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getPlace_id() {
    return place_id;
  }

  public void setPlace_id(String place_id) {
    this.place_id = place_id;
  }

  @Override
  public String toString() {
    return "PlaceStaying{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", date=" + date +

            '}';
  }

  public float getLat() {
    return lat;
  }

  public void setLat(float lat) {
    this.lat = lat;
  }

  public float getLon() {
    return lon;
  }

  public void setLon(float lon) {
    this.lon = lon;
  }
}
