package edu.upc.project.services;

import edu.upc.project.dao.GameManager;
import edu.upc.project.dao.GameManagerImpl;
import edu.upc.project.models.ItemType;
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
import java.sql.SQLException;
import java.util.List;

@Api(value = "/store", description = "Endpoint to Store Service")
@Path("/store")
public class StoreService {

    private GameManager gm;

    public StoreService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "Get all items")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() throws SQLException {

        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(this.gm.getItems())
        {

        };
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Get a specific item")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class),
            @ApiResponse(code = 201, message = "Item not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") Integer itemID) throws SQLException {
        Item item = this.gm.getItem(itemID);
        if (item.getId() == null) return Response.status(404).build();
        else  return Response.status(201).entity(item).build();
    }

//    @GET
//    @ApiOperation(value = "Get all items of a specific type")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer="List"),
//            @ApiResponse(code = 404, message = "Item not found")
//    })
//    @Path("/{type}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getItemsbyType(@PathParam("type") ItemType type) {
//
//        List<Item> list = this.gm.listItembyType(type);
//        if (list == null)
//            return Response.status(404).build();
//        else
//        {
//            GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(list) {};
//            return Response.status(201).entity(entity).build();
//        }
//    }

//    @POST
//    @ApiOperation(value = "Create a new item")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful", response = Item.class),
//            @ApiResponse(code = 500, message = "Validation Error")
//
//    })
//    @Path("/")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response newItem(Item item) {
//        if (item.getType() == null || item.getValue() == null)
//            return Response.status(500).entity(item).build();
//        else {
//            this.gm.addItem(item);
//            return Response.status(201).entity(item).build();
//        }
//    }
}