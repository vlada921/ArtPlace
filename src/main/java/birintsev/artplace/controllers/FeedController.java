package birintsev.artplace.controllers;

import birintsev.artplace.dto.PublicationDTO;
import birintsev.artplace.dto.UserDTO;
import birintsev.artplace.model.db.Publication;
import birintsev.artplace.model.db.User;
import birintsev.artplace.services.PublicService;
import birintsev.artplace.services.PublicationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/feed")
public class FeedController {

    private static final String FEED_VIEW_NAME = "feed";

    private final ModelMapper modelMapper;

    private final PublicService publicService;

    private final PublicationService publicationService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    protected ModelAndView feed(
        @AuthenticationPrincipal User user
    ) {
        ModelAndView mav = new ModelAndView(FEED_VIEW_NAME);
        mav.addObject(
            "publics",
            publicService.userSubscriptions(user)
        );
        mav.addObject(
            "user",
            modelMapper.map(user, UserDTO.class)
        );
        mav.addObject(
            "publications",
            mapPublications(
                publicationService.findForUserFirstPage(user)
                    .stream()
                    .collect(Collectors.toList())
            )
        );
        return mav;
    }

    @RequestMapping("/page")
    protected ResponseEntity<List<PublicationDTO>> feedPage(
        @AuthenticationPrincipal User user,
        Pageable pageable
    ) {
        return new ResponseEntity<>(
            new ArrayList<>(
                mapPublications(
                    publicationService.findForUser(user, pageable)
                        .stream()
                        .collect(Collectors.toList())
                )
            ),
            HttpStatus.OK
        );
    }

    private Collection<PublicationDTO> mapPublications(
        Collection<Publication> publications
    ) {
        return modelMapper.map(
            publications,
            new TypeToken<List<PublicationDTO>>() {}.getType()
        );
    }
}
