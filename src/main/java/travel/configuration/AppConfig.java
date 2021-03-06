package travel.configuration;

/**
 * Created by BARCO on 09-Feb-17.
 */
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.util.Locale;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "travel")
public class AppConfig extends WebMvcConfigurerAdapter{

  /**
   * Configure TilesConfigurer.
   */
  @Bean
  public TilesConfigurer tilesConfigurer(){
    TilesConfigurer tilesConfigurer = new TilesConfigurer();
    tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/views/**/tiles.xml"});
    tilesConfigurer.setCheckRefresh(true);
    return tilesConfigurer;
  }

  /**
   * Configure ViewResolvers to deliver preferred views.
   */
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    TilesViewResolver viewResolver = new TilesViewResolver();
    registry.viewResolver(viewResolver);
  }

  /**
   * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
   */

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("/static/");
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("/i18/usermsg");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
  @Bean
  public LocaleResolver localeResolver(){
    CookieLocaleResolver resolver = new CookieLocaleResolver();
    resolver.setDefaultLocale(new Locale("es"));
    resolver.setCookieName("myLocaleCookie");
    resolver.setCookieMaxAge(4800);
    return resolver;
  }
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("mylocale");
    registry.addInterceptor(interceptor);
  }

}
