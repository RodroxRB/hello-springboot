package travel.entities;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by BARCO on 23-Feb-17.
 */
@Component
public class TripValidator implements Validator{
  @Override
  public boolean supports(Class<?> aClass) {
    return Trip.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    Trip trip= (Trip) o;
    for(PlaceStaying p:trip.getPlaceStayings())
    {

    }

  }
}
