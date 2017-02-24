package travel.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;

import travel.entities.Trip;
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

  @RequestMapping(value = "/googleAjax.json")
  public @ResponseBody String getGoogleInfo()
  {

    List<Trip> list=tripRepository.findByUser_id((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    HttpClient httpClient = HttpClientBuilder.create().build();
    for (Trip trip:list)
    {
      try {
        String text ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + trip.getStartPoint().getLat()+","+trip.getStartPoint().getLon()+"&radius=2000"
            + "&key=AIzaSyAdUOM7XKmfD56v-ROOGe-GmoTLT6LDrOY";
        HttpGet getRequest = new HttpGet(text);


        HttpResponse response = httpClient.execute(getRequest);
        String resp = convertStreamToString(response.getEntity().getContent());
        System.out.println(text);
        System.out.println(resp);

      } catch (ClientProtocolException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return "";
  }



  private String convertStreamToString(InputStream is) {

    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }
}
