package edu.upc.project.services;

import edu.upc.project.dao.GameManager;
import edu.upc.project.dao.GameManagerImpl;
import edu.upc.project.models.FAQ;
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

@Api(value = "/faqs", description = "Endpoint to FAQS Service")
@Path("/faqs")
public class FAQService {

    private GameManager gm;

    public FAQService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "Get all FAQs")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = FAQ.class, responseContainer = "List")
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFAQS() throws SQLException {

        GenericEntity<List<FAQ>> entity = new GenericEntity<List<FAQ>>(this.gm.getFAQS())
        {

        };
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "Create a new FAQ")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = FAQ.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newFAQ(FAQ faq) throws SQLException {
        if (faq.getSender() == null || faq.getDate() == null || faq.getQuestion() == null
                || faq.getAnswer() == null)
            return Response.status(500).entity(faq).build();
        FAQ result = this.gm.addFAQ(faq);
        if (result != null)
            return Response.status(201).entity(faq).build();
        return Response.status(500).entity(faq).build();
    }

}
