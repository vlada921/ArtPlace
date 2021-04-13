package birintsev.artplace.controllers;

import birintsev.artplace.dto.UserDTO;
import birintsev.artplace.model.db.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class UserAccountController {

    private final ModelMapper modelMapper;

    @RequestMapping(
        method = RequestMethod.GET,
        path = "/account"
    )
    protected ModelAndView account(@AuthenticationPrincipal User user) {
        ModelAndView mav = new ModelAndView("account");
        mav.addObject(
            "userDto",
            modelMapper.map(user, UserDTO.class)
        );
        mav.addObject("readOnly", Boolean.TRUE);
        return mav;
    }
}
