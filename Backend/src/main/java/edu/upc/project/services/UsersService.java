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

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UsersService {

    private GameManager gm;

    public UsersService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "login a user to the game")
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
        User dbUser = this.gm.getUserbyName(user.getUsername());
        if (dbUser == null || !dbUser.getPassword().equals(user.getPassword()))
            return Response.status(404).build();
        return Response.status(201).entity(dbUser).build();
    }

    @GET
    @ApiOperation(value = "get a User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Integer username) throws SQLException {
        User user = this.gm.getUser(username);
            if (user == null) return Response.status(404).build();
            else  return Response.status(201).entity(user).build();
    }

    @POST
    @ApiOperation(value = "create a new user")
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

    @GET
    @ApiOperation(value = "get the inventory of an user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Inventory.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}/inventory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventoryUser(@PathParam("id") Integer username) throws SQLException {
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
    @Path("/{id}/inventory/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItemInventory(@PathParam("id") Integer userID, @QueryParam("item") int itemID) throws SQLException {
        Integer result = this.gm.addItemInventory(userID, itemID);
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