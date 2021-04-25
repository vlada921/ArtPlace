package birintsev.artplace.services;

import birintsev.artplace.model.db.Publication;
import birintsev.artplace.model.db.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * This is a <strong>business logic</strong> providing interface.
 * */
@Service
public interface PublicationService {

    default Slice<Publication> findForUserFirstPage(User subscriber) {
        return findForUser(subscriber, defaultFirstPublicationPage());
    }

    private Pageable defaultFirstPublicationPage() {
        final int defaultPageSize = 10;
        return PageRequest.of(
            0,
            defaultPageSize,
            Sort.Direction.DESC,
            "publicationDate"
        );
    }

    Slice<Publication> findForUser(User user, Pageable pageable);
}
