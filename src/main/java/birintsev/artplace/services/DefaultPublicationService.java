package birintsev.artplace.services;

import birintsev.artplace.model.db.Publication;
import birintsev.artplace.model.db.User;
import birintsev.artplace.model.db.repo.PublicationRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultPublicationService implements PublicationService {

    private final PublicationRepo publicationRepo;

    @Override
    public Slice<Publication> findForUser(User subscriber, Pageable pageable) {
        return publicationRepo.findAllBySubscriber(
            subscriber,
            pageable
        );
    }
}
