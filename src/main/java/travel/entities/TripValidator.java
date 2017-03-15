package travel.entities;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

/**
 * Created by BARCO on 23-Feb-17.
 */
@Component
public class TripValidator implements Validator {
  @Override
  public boolean supports(Class<?> aClass) {
    return Trip.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    Trip trip = (Trip) o;
    Date last = null;
    for (PlaceStaying p : trip.getPlaceStayings()) {
      System.out.println(p.getPlace());
      if (p.getPlace().getLat() != 0) {
        System.out.println(p.getDate_arrival());
        System.out.println(p.getDate_departure());
        if (p.getDate_arrival() == null && last==null && p.getDate_departure()!=null) {
          last=p.getDate_departure();
        }
        else if (last!=null && p.getDate_departure()!=null && p.getDate_arrival()!=null && (p.getDate_arrival().before(p.getDate_departure()) || p.getDate_arrival().equals(p.getDate_departure()))  && (p.getDate_arrival().after(last) || p.getDate_arrival().equals(last)))
        {
          last=p.getDate_departure();
        }
        else if (!(last!=null && p.getDate_departure()==null && p.getDate_arrival()!=null && (p.getDate_arrival().after(last) || p.getDate_arrival().equals(last))))
        {
          errors.reject("Dates are wrong");
          return;
        }
        else if (!(p.getDate_departure()==null && p.getDate_arrival()!=null))
        {
          errors.reject("Dates are missing");
          return;
        }
      }
    }

  }
}
