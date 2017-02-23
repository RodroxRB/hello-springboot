package travel.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BARCO on 16-Feb-17.
 */
@Configuration
@ConfigurationProperties(prefix = "maps")
public class GeneralConfiguration {

  private String airports;
  private String apiIp;
  private String accessToken;

  public String getAirports() {
    return airports;
  }

  public void setAirports(String airports) {
    this.airports = airports;
  }

  public String getApiIp() {
    return apiIp;
  }

  public void setApiIp(String apiIp) {
    this.apiIp = apiIp;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
