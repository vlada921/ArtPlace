package birintsev.artplace.services;

import birintsev.artplace.model.db.Public;
import birintsev.artplace.model.db.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * This is a <strong>business logic</strong> providing interface.
 * */
@Service
public interface PublicService {

    /**
     * @return all the {@link Public publics} subscribed by passed user
     *         (not depending on
     *         {@link birintsev.artplace.model.db.SubscriptionTariff tariff})
     * */
    Page<Public> userSubscriptions(User subscriber);
}
