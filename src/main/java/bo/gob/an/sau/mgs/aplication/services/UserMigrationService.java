package bo.gob.an.sau.mgs.aplication.services;

import bo.gob.an.sau.mgs.domain.model.UserMigrationDetails;
import bo.gob.an.sau.mgs.domain.repositories.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class UserMigrationService {

    private static final Logger log = Logger.getLogger(UserMigrationService.class);
    private final UserRepository userRepository;

    public UserMigrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Uni<Optional<UserMigrationDetails>> getMigrationDetails(String usernameOrEmail) {
        log.info("Getting migration data for: " + usernameOrEmail);
        return  Uni.createFrom().item(userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .map(UserMigrationDetails::from));
    }

    public Uni<Boolean> passwordIsCorrect(String usernameOrEmail, String password) {
        log.info("Verifying password for: " + usernameOrEmail);

        return Uni.createFrom().item(userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .map(u -> Objects.equals(u.getPassword(), password))
                .orElse(false));
    }
}
