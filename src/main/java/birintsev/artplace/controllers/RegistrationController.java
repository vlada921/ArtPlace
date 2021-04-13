package birintsev.artplace.controllers;

import birintsev.artplace.dto.RegistrationRequest;
import birintsev.artplace.model.db.User;
import birintsev.artplace.services.UserService;
import birintsev.artplace.services.exceptions.UserExistException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Locale;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Controller
@RequestMapping(RegistrationController.REG_PAGE_PATH)
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        RegistrationController.class
    );

    private static final String REG_REQUEST_MODEL_ATTR_NAME =
        "registrationRequest";

    private static final String ERROR_MODEL_ATTR_NAME =
        "error";

    private static final String ERRORS_MODEL_ATTR_NAME =
        "errors";

    private static final String MESSAGE_MODEL_ATTR_NAME =
        "message";

    public static final String REG_PAGE_PATH =
        "/registration";

    public static final String REG_FORM_HANDLER_PATH =
        "/form-submit";

    public static final String REG_CONFIRM_HANDLER_PATH =
        "/confirm";

    private static final String REG_VIEW_NAME =
        "registration";

    private static final String ACCOUNT_VIEW_NAME =
        "account";

    private static final String ERROR_VIEW_NAME =
        "error";

    private final UserService userService;

    private final MessageSource messageSource;

    private final Locale localeDefault;

    @RequestMapping(
        method = RequestMethod.GET
    )
    private ModelAndView registrationPage() {
        ModelAndView modelAndView = new ModelAndView(REG_VIEW_NAME);
        modelAndView.getModelMap().addAttribute(
            REG_REQUEST_MODEL_ATTR_NAME,
            new RegistrationRequest()
        );
        return modelAndView;
    }

    @RequestMapping(
        method = RequestMethod.POST,
        path = REG_FORM_HANDLER_PATH
    )
    private ModelAndView registrationFormSubmit(
        @ModelAttribute(REG_REQUEST_MODEL_ATTR_NAME)
        @Valid
        RegistrationRequest request,
        BindingResult errors
    ) {
        ModelAndView modelAndView;

        if (errors.hasErrors()) {
            modelAndView = new ModelAndView(
                REG_VIEW_NAME,
                REG_REQUEST_MODEL_ATTR_NAME,
                request
            );
            modelAndView.addObject(ERRORS_MODEL_ATTR_NAME, errors);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);

            return modelAndView;
        }
        try {
            User user = userService.register(request);
            modelAndView = new ModelAndView(ACCOUNT_VIEW_NAME);

            modelAndView.addObject(
                MESSAGE_MODEL_ATTR_NAME,
                messageSource.getMessage(
                    "registration.success.message",
                    new Object[] {user.getName()},
                    localeDefault
                )
            );
            modelAndView.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            modelAndView = new ModelAndView(REG_VIEW_NAME);
            modelAndView.addObject(ERROR_MODEL_ATTR_NAME, e);
            modelAndView.addObject(REG_REQUEST_MODEL_ATTR_NAME, request);
            modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return modelAndView;
    }

    @RequestMapping(
        path = REG_CONFIRM_HANDLER_PATH,
        method = RequestMethod.GET
    )
    private ModelAndView confirm(@RequestParam(value = "token") String token) {
        ModelAndView mav;
        try {
            userService.confirm(token);
            mav = new ModelAndView("redirect:/login");
        } catch (UserExistException e) {
            LOGGER.error(e.getMessage(), e);
            mav = new ModelAndView("redirect:" + ACCOUNT_VIEW_NAME);
        } catch (NoSuchElementException e) {
            mav = new ModelAndView(
                ERROR_VIEW_NAME,
                ERROR_MODEL_ATTR_NAME,
                messageSource.getMessage(
                    "registration.token.expired.message",
                    new Object[]{},
                    localeDefault
                )
            );
        }
        return mav;
    }
}
