package ru.aora.erp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.aora.erp.domain.service.user.UserService;
import ru.aora.erp.model.entity.business.User;
import ru.aora.erp.presentation.controller.dashboard.DashboardController;
import ru.aora.erp.presentation.controller.dashboard.DashboardUrl;

import java.io.IOException;

import static org.reflections.Reflections.log;
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    //private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
    @Autowired
    public CustomAuthenticationSuccessHandler(UserService userService){
        this.userService=userService;
    }
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        // Set the session attributes
        request.getSession().setAttribute("username", authentication.getName());
        request.getSession().setAttribute("userId", userService.loadUserByUsername(authentication.getName()).getId());

        // Log session attributes
        logger.info("Username in session: " + request.getSession().getAttribute("username"));
        logger.info("UserId in session: " + request.getSession().getAttribute("userId"));

        // Redirect to the dashboard page
        getRedirectStrategy().sendRedirect(request, response, DashboardUrl.MAPPING);
    }    // Get the authenticated user
        //        User user = (User) authentication.getPrincipal();


//        logger.info("User object retrieved from authentication: {}", user); // Add this line
//        // Set the userId attribute in the session
//        HttpSession session = request.getSession();
//        session.setAttribute("username", user.getUsername());
//        session.setAttribute("userId", user.getId());
//
//        logger.info("Session object: {}", session); // Add this line
//        // Log the username and userId for debugging purposes
//        logger.info("Session ID: {}", user.getId()); // Add this line



        // Redirect to the dashboard or another page after successful login
//        response.sendRedirect("/dashboard");
//    }
}
