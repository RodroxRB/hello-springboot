package travel.entities;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by BARCO on 14-Mar-17.
 */
@Entity
@Table(name = "place")
public class Place implements Serializable {


  @Id
  @NotEmpty
  private String id;

  @Column(name = "name")
  @NotEmpty
  private String name;

  @Column(name = "lat")
  @NotNull
  private float lat;

  @Column(name = "lon")
  @NotNull
  private float lon;

  @OneToMany(mappedBy = "place",fetch = FetchType.EAGER)
  @Valid
  private List<PlaceStaying> placeStayings;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public List<PlaceStaying> getPlaceStayings() {
    return placeStayings;
  }

  public void setPlaceStayings(List<PlaceStaying> placeStayings) {
    this.placeStayings = placeStayings;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Place{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", lat=" + lat +
        ", lon=" + lon +
        '}';
  }
}
