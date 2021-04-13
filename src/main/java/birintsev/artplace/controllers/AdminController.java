package birintsev.artplace.controllers;

import birintsev.artplace.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(AdminController.RESOURCE_ADMIN)
@AllArgsConstructor
public class AdminController {

    public static final String RESOURCE_ADMIN = "/admin";

    public static final String RESOURCE_USERS = "/users";

    private static final String VIEW_NAME_USERS = "users";

    private static final Logger LOGGER = LoggerFactory.getLogger(
        AdminController.class
    );

    private final UserService userService;

    @RequestMapping(RESOURCE_USERS)
    protected ModelAndView users() {
        String usersModelAttrName = "users";
        return new ModelAndView(
            VIEW_NAME_USERS,
            usersModelAttrName,
            userService.allUsers()
        );
    }
}
