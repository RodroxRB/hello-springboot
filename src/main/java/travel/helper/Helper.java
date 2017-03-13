package travel.helper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by BARCO on 13-Mar-17.
 */
public class Helper {

  public static JSONArray concatArray(JSONArray... arrs)
      throws JSONException {
    JSONArray result = new JSONArray();
    for (JSONArray arr : arrs) {
      for (int i = 0; i < arr.length(); i++) {
        result.put(arr.get(i));
      }
    }
    return result;
  }

  public static String convertStreamToString(InputStream is) {

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
