package travel.entities;

import java.io.Serializable;

/**
 * Created by BARCO on 14-Mar-17.
 */
public class PlaceStayingId implements Serializable{

  private String place;
  private long trip;


  public long getTrip() {
    return trip;
  }

  public void setTrip(int trip) {
    this.trip = trip;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PlaceStayingId that = (PlaceStayingId) o;

    if (place != null ? !place.equals(that.place) : that.place != null) return false;
    if (trip != -1 ? !(trip==(that.trip)) : that.trip != -1)
      return false;

    return true;
  }

  public int hashCode() {
    int result;
    result = (int)((place != null ? place.hashCode() : 0)*trip);
    return result;
  }
}
