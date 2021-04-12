package birintsev.artplace.model.db.repo;

import birintsev.artplace.model.db.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository("UserRepo")
public interface UserRepo
extends CrudRepository<User, UUID>, UserDetailsService {

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    @Override
    default UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
        return Optional.ofNullable(findByEmail(email))
            .orElseThrow(
                () -> new UsernameNotFoundException(
                    String.format("User with email %s not found", email)
                )
            );
    }
}
