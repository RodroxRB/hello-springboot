package travel.social;

/**
 * Created by BARCO on 08-Feb-17.
 */
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

import javax.sql.DataSource;

class DatabaseSocialConfigurer extends SocialConfigurerAdapter {
  private final DataSource dataSource;

  public DatabaseSocialConfigurer(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
    TextEncryptor textEncryptor = Encryptors.noOpText();
    return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor);
  }
}
