package birintsev.artplace.model.db.repo;

import birintsev.artplace.model.db.SubscriptionTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;
import java.util.UUID;

public interface TariffRepo extends JpaRepository<SubscriptionTariff, UUID> {

    Set<SubscriptionTariff> findByNameIn(Set<String> tariffNames);
}
