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
  @Column(name = "id")
  @NotEmpty
  private String id;

  @Column(name = "name")
  @NotEmpty
  private String name;

  @Column(name = "date")
  @DateTimeFormat(pattern="yyyy-MM-dd")
  @NotNull
  private Date date;

  @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
  @JoinColumn(name = "trip_id")
  private Trip trip;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  @Override
  public String toString() {
    return "PlaceStaying{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", date=" + date +

            '}';
  }
}
