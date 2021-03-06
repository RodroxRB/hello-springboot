package travel.security;

/**
 * Created by BARCO on 08-Feb-17.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;

public class AuthUtil {
  protected static final Logger log = LoggerFactory.getLogger(AuthUtil.class);

  public static void authenticate(Connection<?> connection) {
    UserProfile userProfile = connection.fetchUserProfile();
    String username = connection.getDisplayName();
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info("User {} {} connected.", userProfile.getFirstName(), userProfile.getLastName());
  }
}
