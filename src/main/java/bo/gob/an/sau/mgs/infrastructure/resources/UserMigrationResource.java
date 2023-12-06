package bo.gob.an.sau.mgs.infrastructure.resources;

import bo.gob.an.sau.mgs.aplication.services.UserMigrationService;
import bo.gob.an.sau.mgs.domain.model.User;
import bo.gob.an.sau.mgs.domain.model.UserMigrationDetails;
import bo.gob.an.sau.mgs.domain.model.UserValidationDetails;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.GET;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Optional;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


@Path("/api/user-migration-support")
@Tag(name = "User migration")
public class UserMigrationResource {
    Logger logger;
    private final UserMigrationService migrationService;
    public UserMigrationResource(UserMigrationService migrationService, Logger logger){
        this.migrationService = migrationService;
        this.logger = logger;
    }
    @Operation(summary = "Returns a hero for a given identifier")
    @GET
    @Path("/{usernameOrEmail}")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = UserMigrationDetails.class)))
    @APIResponse(responseCode = "204", description = "The hero is not found for a given identifier")
    public Uni<RestResponse<Optional<UserMigrationDetails>>> getMigrationDetails(@RestPath String usernameOrEmail) {
        return migrationService.getMigrationDetails(usernameOrEmail)
                .map(user -> {
                    if (user != null) {
                        return RestResponse.ok(user);
                    }
                    logger.debugf("No se encuentra el usuario con  usernameOrEmail %s",usernameOrEmail);
                    return RestResponse.noContent();
                });
    }
    @Operation(summary = "Verifica la contraseña del usuario")
    @POST
    @Path("/{usernameOrEmail}")
    @APIResponse(responseCode = "200", description = "Validacion del password", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = boolean.class)))
    public Uni<RestResponse<?>> verifyPassword(@RestPath String usernameOrEmail, @Valid UserValidationDetails userValidationDetails) {
        return migrationService.passwordIsCorrect(usernameOrEmail,userValidationDetails.getPassword()).map(resp->{
            if (resp){
                return RestResponse.ok();
            }else {
                return RestResponse.noContent();
            }
        });
    }
}
