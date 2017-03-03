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


  @RequestMapping(value = "/flightsAjax.json")
  public
  @ResponseBody
  String getFlightsInfo() {
    List<Trip> list = tripRepository.findByUser_id((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    JSONArray big_array = new JSONArray();
    for (Trip trip : list) {
      JSONArray res_array = new JSONArray();
      List<List<String>> airports = new ArrayList<>();
      airports.add(findAirports(trip.getStartPoint()));
      for (PlaceStaying p : trip.getPlaceStayings()) {
        airports.add(findAirports(p));
      }
      airports.add(findAirports(trip.getEndPoint()));
      for (int i = 0; i < airports.size() - 1; i++) {
        res_array.put(findFlights(airports.get(i), airports.get(i + 1)));
      }
      big_array.put(new JSONObject().put("trip_id", trip.getId()).put("flights", res_array));
    }

    return new JSONObject().put("result", big_array).toString();
  }


  private List<String> findAirports(PlaceStaying placeStaying) {
    try {
      HttpClient httpClient = HttpClientBuilder.create().build();
      String text = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
          + URLEncoder.encode(String.valueOf(placeStaying.getLat()), "UTF-8") + ","
          + URLEncoder.encode(String.valueOf(placeStaying.getLon()), "UTF-8")
          + "&radius=50000&types=" + URLEncoder.encode("airport", "UTF-8")
          + "&key=" + URLEncoder.encode(generalConfiguration.getApikey(), "UTF-8");

      HttpGet getRequest = new HttpGet(text);

      HttpResponse response = httpClient.execute(getRequest);
      String resp = convertStreamToString(response.getEntity().getContent());
      JSONObject obj = new JSONObject(resp);
      JSONArray arr = obj.getJSONArray("results");
      int i = 0;
      List<String> airport_codes = new ArrayList<>();
      while (i < arr.length()) {
        if (((JSONObject) arr.get(i)).getString("name").contains("Airport")) {
          JSONObject location = arr.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
          String code = findAirportCode(location.getDouble("lat"), location.getDouble("lng"));
          if (code != null) {
            airport_codes.add(code);
          }
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
          System.out.println(airport[6]);
          return airport[4];
        }

      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private JSONObject findFlights(List<String> origin, List<String> dest) {
    try {
      HttpClient httpClient = HttpClientBuilder.create().build();
      boolean found = false;
      int i = 0;
      int j = 0;
      while (!found && i < origin.size() && j < dest.size()) {

        String text = "https://api.test.sabre.com/v2/shop/flights/fares"
            + "?origin="
            + origin.get(i)
            + "&destination="
            + dest.get(j)
            + "&lengthofstay=0";
        System.out.println(text);
        HttpGet getRequest = new HttpGet(text);
        getRequest.addHeader("Authorization",
            "Bearer T1RLAQIdjw4uOTzMiBRdgtTI7zVUrDRRFBAek/Pr9wL26qdW0+01rGcSAADAdLFhjL3rkzPPQfNiRNQQ18hBHvM7CPJAP7piurJT2W/S5nFrnL5Sn9+GYcvNznayK5YZ64Hh3l9DTMaRYi1xMJwjtuPM6u2NyaUEhijobufA4sfAK+ufmGkzp78WZuFQAFsCKd0wO8Dpj/G8E/WpJ9xd5w6yOJOuJqM4lgKIZGHTHy/ZKfKT6KVGb20Yl1TtqYOPkMlC/GZtonQPKlL1z7VC0z9pbkduQSaoXXRoQRXewW2Bc0XOHu5DxT2KmZ2l");
        HttpResponse response = httpClient.execute(getRequest);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
          found = true;
          String resp = convertStreamToString(response.getEntity().getContent());
          JSONObject obj = new JSONObject(resp);
          JSONArray fares = obj.getJSONArray("FareInfo");
          JSONObject last = fares.getJSONObject(fares.length() - 1);
          last.put("Result", "OK");
          return last;

        }
        j++;
        if (j == dest.size()) {
          j = 0;
          i++;
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

      one_trip.put("trip_id", trip.getId());
      one_trip.put("results", arr_one_trip);
      res_array.put(one_trip);
    }

    return res_array.toString();
  }

  private JSONObject getInfo(PlaceStaying placeStaying) {
    JSONArray arr = null;
    HttpClient httpClient = null;
    try {
      httpClient = HttpClientBuilder.create().build();
      String text = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
          + URLEncoder.encode(String.valueOf(placeStaying.getLat()), "UTF-8") + ","
          + URLEncoder.encode(String.valueOf(placeStaying.getLon()), "UTF-8")
          + "&radius=5000&types=" + URLEncoder.encode("restaurant|lodging", "UTF-8")
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
    return new JSONObject()
        .put("restaurants", analizeRestaurants(arr, httpClient))
        .put("hotels", analizeHotels(arr, httpClient));
  }


  private JSONArray analizeRestaurants(JSONArray arr, HttpClient httpClient) {


    int i = 0;
    int good_ones = 0;
    JSONObject obj = null;
    JSONArray r = new JSONArray();
    while (good_ones < 3 && i < arr.length()) {
      obj = arr.getJSONObject(i);
      if (obj.getJSONArray("types").toList().contains("restaurant") && !obj.getJSONArray("types").toList().contains("lodging")  && obj.has("photos")) {
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
    while (good_ones < 3 && i < arr.length()) {
      obj = arr.getJSONObject(i);
      if (obj.getJSONArray("types").toList().contains("lodging") && !obj.getJSONArray("types").toList().contains("restaurant")  && obj.has("photos")) {
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



