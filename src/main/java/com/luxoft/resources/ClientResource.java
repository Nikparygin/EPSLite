package com.luxoft.resources;

import com.luxoft.RequestController;
import com.luxoft.clients.Client;

import com.luxoft.clients.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

@Api(value = "REST", description = "APIs for working with clients")
@Path("customers")
public class ClientResource {

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
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "Update client by id")
    public Response updateClient(Client client) {
        try {
            requestController.updateClient(client);
        } catch (ClientException e) {
            return Response.status(400).build();
        }
        return Response.status(201).build();
    }

    @DELETE
    @ApiOperation(value = "Delete client by id")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteClient (@PathParam("id") int id) throws URISyntaxException {
        requestController.deleteClient(id);
        return Response.status(202).entity("Client deleted successfully").build();
    }
}
