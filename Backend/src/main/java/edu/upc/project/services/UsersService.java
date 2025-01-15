package edu.upc.project.services;

import edu.upc.project.dao.GameManager;
import edu.upc.project.dao.GameManagerImpl;
import edu.upc.project.models.Inventory;
import edu.upc.project.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UsersService {

    private GameManager gm;

    public UsersService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "Login a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User and password combination not found"),
            @ApiResponse(code = 500, message = "Validation error")

    })

    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(User user) throws SQLException {
        if (user.getUsername() == null || user.getPassword() == null)
            return Response.status(500).build();
        User dbUser = this.gm.getUser(user.getUsername());
        if (dbUser == null || !dbUser.getPassword().equals(user.getPassword()))
            return Response.status(404).build();
        return Response.status(201).entity(dbUser).build();
    }

    @GET
    @ApiOperation(value = "Get a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) throws SQLException {
        User user = this.gm.getUser(username);
            if (user == null) return Response.status(404).build();
            else  return Response.status(201).entity(user).build();
    }

    @POST
    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) throws SQLException {
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null)
            return Response.status(500).entity(user).build();
        User result = this.gm.addUser(user);
        if (result != null)
        {
            return Response.status(201).entity(user).build();
        }
        return Response.status(500).entity(user).build();
    }

    @POST
    @ApiOperation(value = "Delete an existing user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted successfully"),
            @ApiResponse(code = 400, message = "Validation error"),
            @ApiResponse(code = 401, message = "Incorrect user & password combination")
    })
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("username") String username, User user) throws SQLException {
        if (user.getUsername() == null || user.getPassword() == null)
            return Response.status(400).entity(user).build();
        User StoredUser = this.gm.getUser(user.getUsername());
        if (Objects.equals(StoredUser.getUsername(), user.getUsername())
                && Objects.equals(StoredUser.getPassword(), user.getPassword()))
        {
            this.gm.deleteUser(StoredUser);
            return Response.status(200).entity(user).build();
        }
        else
            return Response.status(401).entity(user).build();
    }

    @GET
    @ApiOperation(value = "get the inventory of an user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Inventory.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{username}/inventory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventoryUser(@PathParam("username") String username) throws SQLException {
        User user = this.gm.getUser(username);
        if (user == null) return Response.status(404).build();
        else
        {
            GenericEntity<List<Inventory>> entity = new GenericEntity<List<Inventory>>(this.gm.getInventory(username)) {};
            return Response.status(201).entity(entity).build();
        }
    }

    @PUT
    @ApiOperation(value = "Add an item to the inventory of an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 401, message = "Purchase not allowed"),
            @ApiResponse(code = 404, message = "User/item not found")

    })
    @Path("/{username}/inventory/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItemInventory(@PathParam("username") String username, @QueryParam("item") int itemID) throws SQLException {
        Integer result = this.gm.addItemInventory(username, itemID);
        switch (result)
        {
            case -1:
            {
                return Response.status(404).build();
            }
            case -2:
            {
                return Response.status(401).build();
            }
            default:
            {
                return Response.status(200).build();
            }
        }
    }
}