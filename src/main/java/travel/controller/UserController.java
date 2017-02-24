package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import travel.entities.PlaceStaying;
import travel.entities.Trip;
import travel.messages.ErrorMessage;
import travel.messages.ValidationResponse;
import travel.repositories.TripRepository;

/**
 * Created by BARCO on 10-Feb-17.
 */
@Controller
@RequestMapping("/user")
public class UserController {

  private UsersConnectionRepository usersConnectionRepository;
  private TripRepository tripRepository;

  @Autowired
  public UserController(UsersConnectionRepository usersConnectionRepository,TripRepository tripRepository)
  {
    this.usersConnectionRepository=usersConnectionRepository;
    this.tripRepository=tripRepository;
  }

  @RequestMapping("/home")
  public ModelAndView home()
  {
    ModelAndView m= new ModelAndView("user/home");
    String s=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(s);
    Connection<?> connection=connectionRepository.findPrimaryConnection(Twitter.class);
    if (connection==null)
    {
      connection=connectionRepository.findPrimaryConnection(Facebook.class);
    }
    List<Trip> trips= tripRepository.findByUser_id((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    m.addObject("trips",trips);
    m.addObject("connection",connection);
    return m;
  }

  @RequestMapping("/newtrip")
  public ModelAndView newTrip()
  {
    ModelAndView m= new ModelAndView("user/newtrip");
    String s=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(s);
    Connection<?> connection=connectionRepository.findPrimaryConnection(Twitter.class);
    if (connection==null)
    {
      connection=connectionRepository.findPrimaryConnection(Facebook.class);
    }
    m.addObject("connection",connection);
    m.addObject("trip",new Trip(new PlaceStaying(),new PlaceStaying(),new ArrayList<PlaceStaying>()));
    return m;
  }


  @RequestMapping(value = "/tripAjax.json",method= RequestMethod.POST)
  public @ResponseBody
  ValidationResponse checkTrip(@ModelAttribute("trip") @Valid Trip trip, BindingResult result)
  {
    ValidationResponse res = new ValidationResponse();
    if(result.hasErrors()){
      res.setStatus("FAIL");
      List<FieldError> allErrors = result.getFieldErrors();
      List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
      for (FieldError objectError : allErrors) {
        errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
      }
      res.setErrorMessageList(errorMesages);

    }else{
      res.setStatus("SUCCESS");
    }

    return res;
  }

  @RequestMapping(value = "/add_trip",method= RequestMethod.POST)
  public String addTrip(@ModelAttribute("trip") @Valid Trip trip, BindingResult result,final RedirectAttributes redirectAttributes)
  {


      if (trip.getPlaceStayings()!=null) {
        for (PlaceStaying p : trip.getPlaceStayings()) {
          p.setTrip(trip);
        }
      }
      trip.setUser_id((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
      tripRepository.save(trip);
      return "redirect:/user/home";
  }

    @RequestMapping("/logout")
    private String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/";
    }




}
