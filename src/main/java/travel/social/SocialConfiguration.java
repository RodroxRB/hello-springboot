package travel.social;

/**
 * Created by BARCO on 08-Feb-17.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ReconnectFilter;
import org.springframework.social.connect.web.SignInAdapter;

import javax.sql.DataSource;

import travel.security.AuthUtil;

@Configuration
public class SocialConfiguration {

  @Bean
  public SocialConfigurer socialConfigurerAdapter(DataSource dataSource) {
    // https://github.com/spring-projects/spring-social/blob/master/spring-social-config/src/main/java/org/springframework/social/config/annotation/SocialConfiguration.java#L87
    return new DatabaseSocialConfigurer(dataSource);
  }

  @Bean
  public SignInAdapter authSignInAdapter() {
    return (userId, connection, request) -> {
      AuthUtil.authenticate(connection);
      return null;
    };
  }

  @Bean
  public ReconnectFilter apiExceptionHandler(UsersConnectionRepository usersConnectionRepository, UserIdSource dataSource) {
    return new ReconnectFilter( usersConnectionRepository, dataSource) ;
  }
}
