package bo.gob.an.sau.mgs.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
