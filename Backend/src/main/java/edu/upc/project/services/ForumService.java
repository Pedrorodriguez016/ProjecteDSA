package edu.upc.project.services;

import edu.upc.project.dao.GameManager;
import edu.upc.project.dao.GameManagerImpl;
import edu.upc.project.models.*;
import edu.upc.project.models.Thread;
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

@Api(value = "/forums", description = "Endpoint to forum service")
@Path("/forums")
public class ForumService {

    private GameManager gm;

    public ForumService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "Get all threads")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Thread.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getThreads() throws SQLException {
        GenericEntity<List<Thread>> entity = new GenericEntity<List<Thread>>(this.gm.getThreads())
        {

        };
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "Create a new thread alongside its first message")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Thread.class),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newThread(ThreadMessageWrapper threadmessagewrapper) throws SQLException {

        Thread thread = threadmessagewrapper.getThread();
        Message message = threadmessagewrapper.getMessage();

        if (thread.getTitle() == null || thread.getCreator() == null || thread.getDate() == null
        || message.getMessage() == null || message.getDate() == null || message.getSender() == null)
            return Response.status(400).entity(thread).build();

        Thread resultThread = this.gm.addThread(thread);

        if (resultThread == null)
            return Response.status(500).entity(thread).build();

        //We add the ID of the thread to the message that we have created
        message.setThread(resultThread.getId());
        Message resultMessage = this.gm.addMessage(message);

        if (resultMessage == null)
            return Response.status(500).entity(thread).build();

        return Response.status(201).entity(resultThread).build();
    }

    @GET
    @ApiOperation(value = "Get a thread's messages")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Message.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Thread not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getThread(@PathParam("id") Integer threadID) throws SQLException {
        Thread thread = this.gm.getThread(threadID);
        if (thread == null) return Response.status(404).build();
        else
        {
            GenericEntity<List<Message>> entity = new GenericEntity<List<Message>>(this.gm.getMessages(threadID)) {};
            return Response.status(201).entity(entity).build();
        }
    }
}