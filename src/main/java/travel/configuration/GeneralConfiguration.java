package travel.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BARCO on 16-Feb-17.
 */
@Configuration
@ConfigurationProperties(prefix = "google")
public class GeneralConfiguration {

  private String apikey;

  public String getApikey() {
    return apikey;
  }

  public void setApikey(String apikey) {
    this.apikey = apikey;
  }
}
