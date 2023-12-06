package bo.gob.an.sau.mgs.domain.repositories;

import bo.gob.an.sau.mgs.domain.model.User;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    Uni<List<User>> findAll();

    Optional<User> findByEmail(String email);
}
