package ru.aora.erp.presentation.controller.dashboard;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aora.erp.domain.service.UserparamService;
import ru.aora.erp.domain.service.user.UserAuthorityCacheService;
import ru.aora.erp.domain.service.user.UserService;
import ru.aora.erp.model.entity.business.Contract;
import ru.aora.erp.model.entity.business.Ks;
import ru.aora.erp.model.entity.business.User;
import ru.aora.erp.model.entity.business.Userparam;
import ru.aora.erp.presentation.controller.exception.DtoValidationException;
import ru.aora.erp.presentation.controller.ks.KsController;
import ru.aora.erp.presentation.entity.dto.settings.UserparamDto;
import ru.aora.erp.presentation.entity.dto.settings.UserparamDtoMapper;
import ru.aora.erp.presentation.entity.dto.settings.UserparamListDto;
import ru.aora.erp.presentation.entity.dto.user.UserIdModuleAuthorityDto;
import ru.aora.erp.presentation.entity.dto.user.UsersDto;
import ru.aora.erp.presentation.presenter.AllSidebarPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;




import static java.util.Objects.requireNonNull;
import static ru.aora.erp.utils.mvc.MvcUtils.redirectTo;

import static ru.aora.erp.presentation.entity.dto.settings.UserparamDtoMapper.toUserparam;

@Controller
public final class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private static final String DASHBOARD_TEMPLATE = "dashboard";
    private static final String LOGIN_TEMPLATE = "login";
    private static final String DTO_MODEL = "userparamDto";

    private static String CURRENT_USER;

    private static String CURRENT_ID;
    private static final String UI_CHANE_NODE_MODEL = "uiChaneNodeModel";

    private static final String USERS_DTO_MODEL = "usersDto";
    private static final String MODULE_AUTHORITY_DTO_LIST_MODEL = "moduleAuthorityDtoList";

    private final UserService userService;
    private final UserAuthorityCacheService authorityCache;
    private final UserparamService userparamService;
    private final AllSidebarPresenter sidebarPresenter;

    public DashboardController(AllSidebarPresenter sidebarPresenter, UserparamService userparamService, UserService userService, UserAuthorityCacheService authorityCache) {
        this.sidebarPresenter = sidebarPresenter;
        this.userparamService = userparamService;
        this.userService=userService;
        this.authorityCache = authorityCache;
    }

    @RequestMapping(DashboardUrl.MAPPING)
    public String dashboard(
            Map<String, Object> model,
            HttpServletRequest request,
            @SessionAttribute(name = "username", required = false) @ModelAttribute("username") String currentUsername,
            @SessionAttribute(name = "userId", required = false) @ModelAttribute("userId") String currentUserId){ // Add this line
//        HttpSession session = request.getSession(false);
//
//        Object usernameAttribute = session.getAttribute("username");
//        Object userIdAttribute = session.getAttribute("userId");
//
//        if (usernameAttribute != null && userIdAttribute != null) {
//            CURRENT_USER = usernameAttribute.toString();
//            CURRENT_ID = userIdAttribute.toString();
//
//            // Continue with your logic here using username and userId variables
//        } else {
//            // Handle the case when the attributes are not set or are null
//        }

        // use the retrieved username and userId in your logic
        //if (principal == null || StringUtils.isBlank(principal.getName())) {
      //      logger.info("User not authenticated or username is empty");
       //     return LOGIN_TEMPLATE;
     //   }
     //   logger.info("Authenticated user: {}", principal.getName());
    //    try {

        final UserparamListDto userparamDto = UserparamListDto.of(
                UserparamDtoMapper.toUserparamDtoList(userparamService.loadAll())
        );

        model.put(
                UI_CHANE_NODE_MODEL,
                sidebarPresenter.allRootNodes()
        );
        //model.put(DTO_MODEL, userparamDto);
//        String currentUsername = (String) session.getAttribute("username");
//        String currentUserId = (String) session.getAttribute("userId");
        model.put(DTO_MODEL, userparamResult(userparamService.loadAll(), userService.loadAll(), currentUsername, currentUserId));

        logger.info("Exiting the dashboard method"); // Add this line
        return DASHBOARD_TEMPLATE;
        //} catch (UserNotFoundException ex) {
        //    logger.error("User not found: {}", principal.getName(), ex);
            // Redirect the user to the login page
      //      return LOGIN_TEMPLATE;
      //  }
    }

//    @RequestMapping("/dashboard/info")
//    public String dashboard(HttpServletRequest request) {
//
//    return CURRENT_ID;
//    }
    @SuppressWarnings("WeakerAccess")
    static class UserparamResultDto {
        public BigDecimal userparamZoom = BigDecimal.ZERO;
        public Integer userparamColor = 0;
        public Integer userparamPattern = 0;
        public String userparamID="";
        public String userID="";
        @Override
        public String toString() {
            return new StringJoiner(", ", UserparamResultDto.class.getSimpleName() + "[", "]")
                    .add("userID=" + userID)
                    .add("userparamID=" + userparamID)
                    .add("userparamColor=" + userparamColor)
                    .add("userparamPattern=" + userparamPattern)
                    .add("userparamZoom=" + userparamZoom)
                    .toString();
        }
    }

    private UserparamResultDto userparamResult(Collection<Userparam> userparamList, Collection<User> userList,String currentUsername, String currentUserId) {
        final UserparamResultDto userparamResult = new UserparamResultDto();
        logger.info("userparamResult called with currentUsername: {} and currentUserId: {}", currentUsername, currentUserId); // Add this line
        if (currentUsername == null || currentUserId == null) {
            // Throw the custom exception when the current username or user ID is null
            throw new UserNotFoundException("User not found");
        }
        requireNonNull(userList);
        requireNonNull(userparamList);

        logger.info("Iterating through userList to find matching user"); // Add this line
        for (User user : userList) {
            if (user != null && user.getActiveStatus() == 0) {
                if (user.getUsername().equals(currentUsername)) {
                    currentUserId = user.getId();
                }
            }
        }
        logger.info("Finished iterating through userList"); // Add this line

        logger.info("Iterating through userparamList to find matching userparam and currentUserId: {}",currentUserId); // Add this line
        boolean userparamFound = false;
        for (Userparam userparam : userparamList) {
            if (userparam != null && userparam.getActiveStatus() == 0) {
                if (userparam.getUserId().equals(currentUserId)) {
                    userparamResult.userparamID = userparam.getId();
                    userparamResult.userID = userparam.getUserId();
                    userparamResult.userparamColor = userparam.getUserThemeColor();
                    userparamResult.userparamPattern = userparam.getUserThemePattern();
                    userparamResult.userparamZoom = userparam.getUserThemeZoom();
                    userparamFound = true;
                    break; // Exit the loop as the matching userparam is found
                }
            }
        }
        logger.info("Finished iterating through userparamList"); // Add this line
        if (!userparamFound) {
            Userparam newUserparam = new Userparam();
            newUserparam.setUserId(currentUserId);
            newUserparam.setUserThemeColor(1);
            newUserparam.setUserThemePattern(1);
            newUserparam.setUserThemeZoom(BigDecimal.valueOf(100));
            userparamService.create(newUserparam);
        }

        return userparamResult;
    }
    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    @PutMapping
    public @ResponseBody String putUserparams(@Valid @RequestBody UserparamDto userparamDto, BindingResult bindingResult) {
        DtoValidationException.throwIfHasErrors(bindingResult);
        return userparamService.update(toUserparam(userparamDto)).getMsg();
    }

    @RequestMapping(DashboardUrl.ROOT_MAPPING)
    public String redirectToRoot() {
        return redirectTo(DASHBOARD_TEMPLATE);
    }

//    @RequestMapping(DashboardUrl.SUCCESS_MAPPING)
//    public String successLogin(HttpServletRequest request) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
//            request.getSession().setAttribute("username", auth.getName());
//            request.getSession().setAttribute("userId", userService.loadUserByUsername(auth.getName()).getId());
//            return "redirect:" + DashboardUrl.MAPPING;
//        } else {
//            return "redirect:" + DashboardUrl.LOGIN_MAPPING;
//        }
//    }

    @RequestMapping(DashboardUrl.LOGIN_MAPPING)
    public String login(Map<String, Object> model, HttpServletRequest request) {
        logger.info("Entering the login method");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            // The user is already authenticated, set session attributes and redirect to the appropriate page
            //request.getSession().setAttribute("username", auth.getName());
            //request.getSession().setAttribute("userId", userService.loadUserByUsername(auth.getName()).getId());
            //return "redirect:" + DashboardUrl.MAPPING;
            return redirectTo(DASHBOARD_TEMPLATE);
        } else {
            List<User> users = userService.loadAll();
            UsersDto usersDto = UsersDto.of(users);
            Collection<UserIdModuleAuthorityDto> userModuleAuthorityDtoList = UserIdModuleAuthorityDto.of(
                    usersDto.getUsers(),
                    authorityCache.allAuthorities()
            );
            model.put(USERS_DTO_MODEL, usersDto);
            model.put(MODULE_AUTHORITY_DTO_LIST_MODEL, userModuleAuthorityDtoList);
            logger.info("Exiting the login method");
            return LOGIN_TEMPLATE;
        }
    }


}
