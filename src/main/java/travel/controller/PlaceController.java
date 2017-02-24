package travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

import travel.repositories.TripRepository;

/**
 * Created by BARCO on 24-Feb-17.
 */
@Controller
@RequestMapping("/place")
public class PlaceController {

  private TripRepository tripRepository;

  @Inject
  public PlaceController (TripRepository tripRepository)
  {
    this.tripRepository=tripRepository;
  }
}
