package bo.gob.an.sau.mgs.infrastructure.resources;

import bo.gob.an.sau.mgs.domain.model.User;
import bo.gob.an.sau.mgs.domain.repositories.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/users")
@Tag(name = "users")
public class UserResource {
    Logger logger;
    private  final UserRepository userRepository;
    public UserResource(Logger logger, UserRepository userRepository){
        this.logger = logger;
        this.userRepository = userRepository;
    }
    @Operation(summary = "Retorna todos los usuarios habilitados")
    @GET
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = User.class, required = true)))
    public Uni<List<User>> getTestUsers() {
        return userRepository.findAll();
    }
}
