package com.luxoft.resources;

import com.luxoft.RequestController;
import com.luxoft.clients.Client;

import com.luxoft.clients.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "REST", description = "APIs for working with clients")
@Path("customers")
public class ClientResource {

    private Logger logger = LoggerFactory.getLogger(ClientResource.class);
    private RequestController requestController = new RequestController();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Find client by id")
    public Client getClient(@PathParam("id") int id) {
        return requestController.getClientById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Find all clients")
    public List getAllClients() {
        return requestController.getAllClients();
    }

    @POST
    @ApiOperation(value = "Create new client")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(Client client) {
        try {
            requestController.addClient(client);
        } catch (ClientException e) {
            logger.error(e.getErrorCode().getErrorString());
            return Response.status(400).entity(e.getErrorCode().getErrorString()).build();
        }
        logger.info("Client with ID: " + client.getId() + " Successfully created");
        return Response.status(201).entity(client.toString() + "\n" + "Successfully created").build();
    }

    @PUT
    @ApiOperation(value = "Update client by id")
    public Response updateClient(Client client) {
        try {
            requestController.updateClient(client);
        } catch (ClientException e) {
            logger.error(e.getErrorCode().getErrorString());
            return Response.status(400).entity(e.getErrorCode().getErrorString()).build();
        }
        logger.info("Client with ID: " + client.getId() + " Successfully updated");
        return Response.status(201).entity(client.toString() + "\n" + "Successfully updated").build();
    }

    @DELETE
    @ApiOperation(value = "Delete client by id")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteClient (@PathParam("id") int id) {
        requestController.deleteClient(id);
        logger.info("Client with ID: " + id + " Successfully deleted");
        return Response.status(202).entity("Client deleted successfully").build();
    }
}
