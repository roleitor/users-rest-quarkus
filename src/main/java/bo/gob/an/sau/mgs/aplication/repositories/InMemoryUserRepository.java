package bo.gob.an.sau.mgs.aplication.repositories;

import bo.gob.an.sau.mgs.domain.model.User;
import bo.gob.an.sau.mgs.domain.repositories.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ApplicationScoped
public class InMemoryUserRepository implements UserRepository {
    private final List<User> users;

    public InMemoryUserRepository() {
        this.users = List.of(
                generateUser("Lucy", "Brennan"),
                generateUser("Mark", "Brown"),
                generateUser("Kate", "Thomson"),
                generateUser("John", "Doe"));
    }

    private User generateUser(String name, String lastName) {
        String username = name.toLowerCase(Locale.ROOT);

        return User.builder()
                .username(username)
                .email(username + "@example.com")
                .firstName(name)
                .lastName(lastName)
                .password("password")
                .build();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Uni<List<User>> findAll() {
        return Uni.createFrom().item(this.users);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }
}
