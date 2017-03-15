package travel.controller;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import travel.configuration.GeneralConfiguration;
import travel.entities.PlaceStaying;
import travel.entities.Trip;
import travel.helper.Helper;
import travel.repositories.TripRepository;

import static org.apache.tomcat.jni.Time.sleep;

/**
 * Created by BARCO on 24-Feb-17.
 */
@Controller
@RequestMapping("/place")
public class PlaceController {

  private TripRepository tripRepository;
  private GeneralConfiguration generalConfiguration;

  private final int TOTAL_PER_PLACE=3;
  private final int MAX_SEARCHES=3;

  @Inject
  public PlaceController(TripRepository tripRepository, GeneralConfiguration generalConfiguration) {
    this.tripRepository = tripRepository;
    this.generalConfiguration = generalConfiguration;
  }


  @RequestMapping(value = "/flightsAjax.json")
  public
  @ResponseBody
  String getFlightsInfo() {
    List<Trip> list = tripRepository.findByUser_id((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    JSONArray big_array = new JSONArray();

    for (Trip trip : list) {
      List<String> city_names= new ArrayList<>();
      JSONArray res_array = new JSONArray();
      List<List<String>> airports = new ArrayList<>();
      for (PlaceStaying p : trip.getPlaceStayings()) {
        airports.add(findAirports(p));
        city_names.add(p.getPlace().getName());
      }
      for (int i = 0; i < airports.size() - 1; i++) {
        res_array.put(findFlights(airports.get(i), airports.get(i + 1)).put("startCity",city_names.get(i)).put("endCity",city_names.get(i+1)));
      }
      big_array.put(new JSONObject().put("trip_id", trip.getId()).put("flights", res_array));
    }

    return new JSONObject().put("result", big_array).toString();
  }


  private List<String> findAirports(PlaceStaying placeStaying) {
    try {
      HttpClient httpClient = HttpClientBuilder.create().build();
      String text = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
          + URLEncoder.encode(String.valueOf(placeStaying.getPlace().getLat()), "UTF-8") + ","
          + URLEncoder.encode(String.valueOf(placeStaying.getPlace().getLon()), "UTF-8")
          + "&radius=50000&types=" + URLEncoder.encode("airport", "UTF-8")
          + "&key=" + URLEncoder.encode(generalConfiguration.getApikey(), "UTF-8");

      HttpGet getRequest = new HttpGet(text);

      HttpResponse response = httpClient.execute(getRequest);
      String resp = Helper.convertStreamToString(response.getEntity().getContent());
      JSONObject obj = new JSONObject(resp);
      JSONArray arr = obj.getJSONArray("results");
      int i = 0;
      List<String> airport_codes = new ArrayList<>();
      while (i < arr.length()) {

        JSONObject location = arr.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
        String code = findAirportCode(location.getDouble("lat"), location.getDouble("lng"));
        if (code != null) {
          airport_codes.add(code);
        }

        i++;
      }
      return airport_codes;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  public String findAirportCode(double lat, double lng) {
    double delta = 0.01;
    ClassLoader classLoader = getClass().getClassLoader();
    String csvFile = "./src/resources/airports.csv";
    String line = "";
    String cvsSplitBy = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(classLoader.getResource("airports.csv").getFile()))) {

      while ((line = br.readLine()) != null) {

        // use comma as separator
        String[] airport = line.split(cvsSplitBy);
        if (Math.abs(Double.valueOf(airport[6]) - lat) < delta && Math.abs(Double.valueOf(airport[7]) - lng) < delta) {

          return airport[4];
        }

      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private JSONObject findFlights(List<String> origin, List<String> dest) {
    Set<String> hs = new HashSet<>();
    hs.addAll(origin);
    origin.clear();
    origin.addAll(hs);
    hs.clear();
    hs.addAll(dest);
    dest.clear();
    dest.addAll(hs);
    try {

      boolean found = false;
      int i = 0;
      int j = 0;
      int total_count=0;
      while (!found && i < origin.size() && j<dest.size()){
        if (origin.get(i).equals("\\N")) {
          i++;
          j = 0;
        } else if (dest.get(j).equals("\\N")) {
          j++;
          if (j == dest.size()) {
            j = 0;
            i++;
          }
        } else {
          HttpClient httpClient = HttpClientBuilder.create().build();
          String text = "https://api.test.sabre.com/v2/shop/flights/fares"
              + "?origin="
              + origin.get(i)
              + "&destination="
              + dest.get(j)
              + "&lengthofstay=0";
          HttpGet getRequest = new HttpGet(text);
          getRequest.addHeader("Authorization",
              "Bearer T1RLAQLXVr3g13p1Uqd+tT9DzE61LSP6FhB/EWy+LpDjg8dwfDmEIJsYAADA8GT8VTTyLO5bUqzufQgT9RUtNERksrYTh2kY5MX+iTIKzwH8+uXxPimn/1buIGvw7B9SJ4mpROwyCTcGV0QzDzIKV+uNvHHeqFJ5xPcinJt0RvPMrKPEZvpb2r2brKIQSLcYO4NXVmcYN6/iXl8kmcW3KcQ58XNqRvClhV7Vr7CiV2S/Id9pJ/GDeplAKKX2RFhIAcuJyBzI3lXW6B/CMMXLggg2L2L2mKkir3rFn1GxcaiuXecFmtbLa/QGmVG7");
          System.out.println(text);
          HttpResponse response = httpClient.execute(getRequest);
          System.out.println(response.getStatusLine());
          if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            found = true;
            String resp = Helper.convertStreamToString(response.getEntity().getContent());
            JSONObject obj = new JSONObject(resp);
            JSONArray fares = obj.getJSONArray("FareInfo");
            JSONObject fare = fares.getJSONObject(fares.length() - 1);
            JSONObject last = new JSONObject()
                .put("Fare", fare.getJSONObject("LowestFare").getDouble("Fare"))
                .put("Company", fare.getJSONObject("LowestFare").getJSONArray("AirlineCodes").get(0))
                .put("origin", origin.get(i))
                .put("destination", dest.get(j))
                .put("Result","OK");
            return last;

          }
          total_count++;
          j++;
          if (j == dest.size()) {
            j = 0;
            i++;
          }
        }
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new JSONObject().put("Result", "Flights not found").put("origin_airports", origin).put("destination_airports", dest);
  }




  //Finds information about places of interest in the cities used
  @RequestMapping(value = "/googleAjax.json")
  public
  @ResponseBody
  String getGoogleInfo() {


    List<Trip> list = tripRepository.findByUser_id((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    JSONArray res_array = new JSONArray();
    for (Trip trip : list) {
      JSONArray arr_one_trip = new JSONArray();
      JSONObject one_trip = new JSONObject();

      for (PlaceStaying p : trip.getPlaceStayings()) {
        JSONObject mid = new JSONObject()
            .put("name", p.getPlace().getId())
            .put("info", getInfo(p));
        arr_one_trip.put(mid);
      }

      one_trip.put("trip_id", trip.getId());
      one_trip.put("results", arr_one_trip);
      res_array.put(one_trip);
    }

    return res_array.toString();
  }

  private JSONObject getInfo(PlaceStaying placeStaying) {
    String pagetoken=null;
    JSONArray arrRestaurants = null;
    JSONArray arrHotels = null;
    HttpClient httpClient = null;
    JSONObject obj=null;
    JSONArray restaurants=null;
    JSONArray hotels=null;
    try {
      httpClient = HttpClientBuilder.create().build();
      String text = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
          + URLEncoder.encode(String.valueOf(placeStaying.getPlace().getLat()), "UTF-8") + ","
          + URLEncoder.encode(String.valueOf(placeStaying.getPlace().getLon()), "UTF-8")
          + "&radius=5000&types=" + URLEncoder.encode("restaurant", "UTF-8")
          + "&key=" + URLEncoder.encode(generalConfiguration.getApikey(), "UTF-8");
      System.out.println(text);
      HttpGet getRequest = new HttpGet(text);

      HttpResponse response = httpClient.execute(getRequest);
      String resp = Helper.convertStreamToString(response.getEntity().getContent());
      obj = new JSONObject(resp);
      arrRestaurants = obj.getJSONArray("results");
      restaurants=analizeRestaurants(arrRestaurants, httpClient);



      httpClient = HttpClientBuilder.create().build();
      text = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
          + URLEncoder.encode(String.valueOf(placeStaying.getPlace().getLat()), "UTF-8") + ","
          + URLEncoder.encode(String.valueOf(placeStaying.getPlace().getLon()), "UTF-8")
          + "&radius=5000&types=" + URLEncoder.encode("lodging", "UTF-8")
          + "&key=" + URLEncoder.encode(generalConfiguration.getApikey(), "UTF-8");
      getRequest = new HttpGet(text);

      response = httpClient.execute(getRequest);
      resp = Helper.convertStreamToString(response.getEntity().getContent());
      obj = new JSONObject(resp);
      arrHotels = obj.getJSONArray("results");
      hotels=analizeHotels(arrHotels, httpClient);

    if (obj.has("next_page_token")) {
      pagetoken = obj.getString("next_page_token");
    }


    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    JSONObject rta=new JSONObject().put("restaurants",restaurants)
        .put("hotels",hotels)
        .put("pagetoken",pagetoken)
        .put("lon",placeStaying.getPlace().getLon())
        .put("lat",placeStaying.getPlace().getLat());

    return rta;

  }


  private JSONArray analizeRestaurants(JSONArray arr, HttpClient httpClient) {


    int i = 0;
    int good_ones = 0;
    JSONObject obj = null;
    JSONArray r = new JSONArray();
    while (good_ones < TOTAL_PER_PLACE && i < arr.length()) {
      obj = arr.getJSONObject(i);
      if (obj.getJSONArray("types").toList().contains("restaurant")  && obj.has("photos")) {
        String name = obj.getString("name");
        JSONArray photos = obj.getJSONArray("photos");
        int width = 0;
        String photo_reference = "";
        if (!photos.isNull(0)) {
          width = ((JSONObject) photos.get(0)).getInt("width");
          photo_reference = ((JSONObject) photos.get(0)).getString("photo_reference");
        }
        JSONObject j = new JSONObject();
        j.put("name", name);
        j.put("photo", "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + width
            + "&photoreference=" + photo_reference
            + "&key=" + generalConfiguration.getApikey());
        j.put("vicinity",obj.getString("vicinity"));
        if (obj.has("rating"))
          j.put("rating",obj.getDouble("rating"));
        r.put(good_ones, j);
        good_ones++;
      }
      i++;
    }
    return r;

  }

  private JSONArray analizeHotels(JSONArray arr, HttpClient httpClient) {

    boolean opened;
    int i = 0;
    int good_ones = 0;
    JSONObject obj = null;
    JSONArray r = new JSONArray();
    while (good_ones < TOTAL_PER_PLACE && i < arr.length()) {
      obj = arr.getJSONObject(i);
      if (obj.getJSONArray("types").toList().contains("lodging")  && obj.has("photos")) {
        String name = obj.getString("name");
        JSONArray photos = obj.getJSONArray("photos");
        int width = 0;
        String photo_reference = "";
        if (!photos.isNull(0)) {
          width = ((JSONObject) photos.get(0)).getInt("width");
          photo_reference = ((JSONObject) photos.get(0)).getString("photo_reference");
        }
        JSONObject j = new JSONObject();
        j.put("name", name);
        j.put("photo", "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + width
            + "&photoreference=" + photo_reference
            + "&key=" + generalConfiguration.getApikey());
        j.put("vicinity",obj.getString("vicinity"));
        if (obj.has("rating"))
          j.put("rating",obj.getDouble("rating"));
        r.put(good_ones, j);
        good_ones++;
      }
      i++;
    }
    return r;

  }


}



