package travel.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.inject.Inject;

import travel.configuration.GeneralConfiguration;
import travel.entities.PlaceStaying;
import travel.entities.Trip;
import travel.repositories.TripRepository;

/**
 * Created by BARCO on 24-Feb-17.
 */
@Controller
@RequestMapping("/place")
public class PlaceController {

  private TripRepository tripRepository;
  private GeneralConfiguration generalConfiguration;

  @Inject
  public PlaceController(TripRepository tripRepository, GeneralConfiguration generalConfiguration) {
    this.tripRepository = tripRepository;
    this.generalConfiguration = generalConfiguration;
  }

  @RequestMapping(value = "/googleAjax.json")
  public
  @ResponseBody
  String getGoogleInfo() {

    List<Trip> list = tripRepository.findByUser_id((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    JSONArray res_array= new JSONArray();
    for (Trip trip : list) {
      JSONArray arr_one_trip= new JSONArray();
      JSONObject one_trip= new JSONObject();
      JSONObject stp = new JSONObject()
          .put("name", trip.getStartPoint().getId())
          .put("info", getInfo(trip.getStartPoint()));
      arr_one_trip.put(stp);

      for (PlaceStaying p : trip.getPlaceStayings()) {
        JSONObject mid = new JSONObject()
            .put("name", p.getId())
            .put("info", getInfo(p));
        arr_one_trip.put(mid);
      }

      JSONObject endp = new JSONObject()
          .put("name", trip.getEndPoint().getId())
          .put("info", getInfo(trip.getEndPoint()));
      arr_one_trip.put(endp);

      one_trip.put("trip_id",trip.getId());
      one_trip.put("results",arr_one_trip);
      res_array.put(one_trip);
    }

    return res_array.toString();
  }

  private JSONObject getInfo(PlaceStaying placeStaying) {
    JSONArray arr=null;
    HttpClient httpClient=null;
    try {
      httpClient = HttpClientBuilder.create().build();
      String text = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
          + URLEncoder.encode(String.valueOf(placeStaying.getLat()), "UTF-8") + ","
          + URLEncoder.encode(String.valueOf(placeStaying.getLon()), "UTF-8")
          + "&radius=2000&types=" + URLEncoder.encode("food|cafe|restaurant", "UTF-8")
          + "&key=" + URLEncoder.encode(generalConfiguration.getApikey(), "UTF-8");
      System.out.println(text);
      HttpGet getRequest = new HttpGet(text);

      HttpResponse response = httpClient.execute(getRequest);
      String resp = convertStreamToString(response.getEntity().getContent());
      JSONObject obj = new JSONObject(resp);
      arr = obj.getJSONArray("results");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return analizeRestaurants(arr,httpClient);
  }

  private JSONObject analizeRestaurants(JSONArray arr,HttpClient httpClient) {

      boolean opened;
      int i = 0;
      int good_ones = 0;
      JSONObject obj = null;
      JSONArray r=new JSONArray();
      System.out.println("So far so good");
      while (good_ones < 3 && i < arr.length()) {
        obj = arr.getJSONObject(i);
        String name = obj.getString("name");
        if (obj.has("opening_hours") && obj.has("photos")) {
          opened = obj.getJSONObject("opening_hours").getBoolean("open_now");
          JSONArray photos = obj.getJSONArray("photos");
          int width = 0;
          String photo_reference = "";
          if (!photos.isNull(0)) {
            width = ((JSONObject) photos.get(0)).getInt("width");
            photo_reference = ((JSONObject) photos.get(0)).getString("photo_reference");
          }
          String text =
              "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + width
                  + "&photoreference=" + photo_reference
                  + "&key=" +  generalConfiguration.getApikey();

          //HttpGet getRequest = new HttpGet(text);
          //HttpResponse response = httpClient.execute(getRequest);
          JSONObject j= new JSONObject();
          j.put("opened",opened);
          j.put("name",name);
          j.put("photo","https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + width
              + "&photoreference=" + photo_reference
              + "&key=" +  generalConfiguration.getApikey());
          r.put(good_ones,j);
          good_ones++;
        }
        i++;
      }
      return new JSONObject().put("restaurants",r);

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
