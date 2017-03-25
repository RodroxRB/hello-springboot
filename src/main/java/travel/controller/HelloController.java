package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;


@Controller
public class HelloController {


    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    public HelloController(UsersConnectionRepository usersConnectionRepository)
    {
        this.usersConnectionRepository=usersConnectionRepository;
    }


    @RequestMapping("/")
    public String index() {
        if( SecurityContextHolder.getContext().getAuthentication() != null &&
            SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
            //when Anonymous Authentication is enabled
            !(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken) )
        {
          return "redirect:/user/home/";

        }
        return "index";
    }

    @RequestMapping("/ownerror")
    public String error() {

        return "error";
    }
}
