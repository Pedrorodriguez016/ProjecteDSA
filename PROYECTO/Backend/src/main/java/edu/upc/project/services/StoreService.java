package edu.upc.project.services;

import edu.upc.project.GameManager;
import edu.upc.project.GameManagerImpl;
import edu.upc.project.models.ElementType;
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

@Api(value = "/store", description = "Endpoint to Store Service")
@Path("/store")
public class StoreService {

    private GameManager gm;

    public StoreService() {
        this.gm = GameManagerImpl.getInstance();
        if (gm.sizeItemsStore()==0) {
            this.gm.createItem(0, ElementType.KNIFE, 154);
            this.gm.createItem(1, ElementType.ARMOR, 175);
            this.gm.createItem(2, ElementType.SWORD, 4896);
            this.gm.createItem(3, ElementType.POTION, 34);
        }
    }

    @GET
    @ApiOperation(value = "Get all items")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "No items found")
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {

        if (this.gm.sizeItemsStore() == 0)
            return Response.status(404).build();
        else
        {
            GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(this.gm.getItems()) {};
            return Response.status(201).entity(entity).build();
        }
    }

    @GET
    @ApiOperation(value = "Get all items of a specific type")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Item not found")
    })
    @Path("/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsbyType(@PathParam("type") ElementType type) {

        List<Item> list = this.gm.listItembyType(type);
        if (list == null)
            return Response.status(404).build();
        else
        {
            GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(list) {};
            return Response.status(201).entity(entity).build();
        }
    }

    @POST
    @ApiOperation(value = "Create a new item")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newItem(Item item) {
        if (item.getType() == null || item.getValue() == null)
            return Response.status(500).entity(item).build();
        else {
            this.gm.addItem(item);
            return Response.status(201).entity(item).build();
        }
    }
}