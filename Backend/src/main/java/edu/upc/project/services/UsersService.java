package edu.upc.project.services;

import edu.upc.project.GameManager;
import edu.upc.project.GameManagerImpl;
import edu.upc.project.config.CORS;
import edu.upc.project.models.Item;
import edu.upc.project.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UsersService {

    private GameManager gm;

    public UsersService() {
        this.gm = GameManagerImpl.getInstance();
        if (gm.sizeUsers()==0) {
            this.gm.createUser(0, "Jonathan", "iitifjdoe", "g@g.com", 653);
            this.gm.createUser(1, "Mary", "rfotk98693", "e@e.com", 863);
        }
    }

    @POST
    @CORS
    @ApiOperation(value = "login a user to the game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User and password combination not found"),
            @ApiResponse(code = 500, message = "Validation error")

    })

    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(User user) {
        if (user.getUsername() == null || user.getPassword() == null)
            return Response.status(500).build();
        for (User userlist : this.gm.listUsers())
        {
            if (user.getUsername().equals(userlist.getUsername()) && user.getPassword().equals(userlist.getPassword()))
            {
                return Response.status(201).entity(userlist).build();
            }
        }
        return Response.status(404).build();
    }

    @OPTIONS
    @Path("/login")
    public Response optionsForLogin() {
        return Response.ok()
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization")
                .build();
    }

    @GET
    @ApiOperation(value = "Get all users in alphabetical order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = this.gm.listUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get a User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Integer id) {
        User u = this.gm.getUser(id);
        if (u == null) return Response.status(404).build();
        else  return Response.status(201).entity(u).build();
    }

    @POST
    @CORS
    @ApiOperation(value = "create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null)
            return Response.status(500).entity(user).build();
        if (user.getId() == null)
        {
            user.setId(this.gm.sizeUsers());
        }
        this.gm.addUser(user);
        return Response.status(201).entity(user).build();
    }

    @OPTIONS
    @Path("/")
    public Response optionsForRegister() {
        return Response.ok()
                .header("Access-Control-Allow-Methods", "POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization")
                .build();
    }

    @GET
    @ApiOperation(value = "get the inventory of an user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}/inventory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventoryUser(@PathParam("id") Integer id) {
        User user = this.gm.getUser(id);
        if (user == null) return Response.status(404).build();
        else
        {
            GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(user.getInventory()) {};
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
    public Response addItemInventory(@PathParam("id") Integer userID, @QueryParam("item") int itemID)
    {
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

    @OPTIONS
    @Path("/{id}/inventory/")
    public Response optionsForAddItemInventory() {
        return Response.ok()
                .header("Access-Control-Allow-Methods", "POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization")
                .build();
    }
}