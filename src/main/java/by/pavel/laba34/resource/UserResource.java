package by.pavel.laba34.resource;

import by.pavel.laba34.dto.UserDTO;
import by.pavel.laba34.entity.User;
import by.pavel.laba34.mapper.UserMapper;
import by.pavel.laba34.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Users", description = "REST API для пользователей")
public class UserResource {

    @Inject
    private UserService service;

    @GET
    @Operation(summary = "Получить всех пользователей")
    public List<UserDTO> getUsers() {
        return service.getAllUsers().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public UserDTO getUser(@PathParam("id") Long id) {
        return UserMapper.toDTO(service.getUser(id));
    }

    @POST
    @Operation(summary = "Создать пользователя")
    public Response createUser(@Valid UserDTO dto, @Context UriInfo uriInfo) {
        User user = UserMapper.toEntity(dto);
        User created = service.createUser(user);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(created.getId()))
                .build();

        return Response.created(location)
                .entity(UserMapper.toDTO(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Обновить пользователя")
    public UserDTO updateUser(@PathParam("id") Long id, @Valid UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        return UserMapper.toDTO(service.updateUser(id, user));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Удалить пользователя")
    public Response deleteUser(@PathParam("id") Long id) {
        service.deleteUser(id);
        return Response.noContent().build();
    }
    @POST
    @Path("/generate")
    @Operation(summary = "Сгенерировать тестовых пользователей")
    public Response generateUsers(@QueryParam("count") @DefaultValue("50") int count) {
        int generatedCount = service.generateUsers(count);
        return Response.ok("Успешно сгенерировано пользователей: " + generatedCount).build();
    }
}