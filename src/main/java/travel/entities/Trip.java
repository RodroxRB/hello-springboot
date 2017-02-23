package travel.entities;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by BARCO on 22-Feb-17.
 */
@Entity
@Table(name = "trip")
public class Trip implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Column(name = "user_id")
  private String user_id;

  @OneToOne(cascade = CascadeType.ALL)
  @Valid
  private PlaceStaying startPoint;

  @OneToOne(cascade = CascadeType.ALL)
  @Valid
  private PlaceStaying endPoint;

  @OneToMany(mappedBy = "trip",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  @Valid
  private List<PlaceStaying> placeStayings;

  protected  Trip(){}

  public Trip(PlaceStaying startPoint, PlaceStaying endPoint, List<PlaceStaying> placeStayings) {
    this.startPoint = startPoint;
    startPoint.setTrip(this);
    this.endPoint = endPoint;
    endPoint.setTrip(this);
    this.placeStayings = placeStayings;
    for (PlaceStaying op : placeStayings) {
      op.setTrip(this);
    }
  }

  public PlaceStaying getStartPoint() {
    return startPoint;
  }

  public void setStartPoint(PlaceStaying startPoint) {
    this.startPoint = startPoint;
  }

  public PlaceStaying getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(PlaceStaying endPoint) {
    this.endPoint = endPoint;
  }

  public List<PlaceStaying> getPlaceStayings() {
    return placeStayings;
  }

  public void setPlaceStayings(List<PlaceStaying> placeStayings) {
    this.placeStayings = placeStayings;
  }

  @Override
  public String toString() {
    return "Trip{" +
            "id=" + id +
            ", startPoint=" + startPoint +
            ", endPoint=" + endPoint +
            ", placeStayings=" + placeStayings +
            '}';
  }
}
