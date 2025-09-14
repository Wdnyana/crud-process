package org.purwa.crud_process.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.purwa.crud_process.data.ShipmentDataRequest;
import org.purwa.crud_process.data.ShipmentDataStatus;
import org.purwa.crud_process.model.Shipment;
import org.purwa.crud_process.repository.ShipmentRepository;
import org.purwa.crud_process.service.ShipmentService;

import java.net.URI;
import java.util.List;

@Path("/api/shipment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipmentResource {
  @Inject
  ShipmentRepository shipmentRepository;

  @Inject
  ShipmentService shipmentService;

  @POST
  public Response createShipment(ShipmentDataRequest request) {
    try {
      Shipment shipment = shipmentService.createShipment(request);

      return Response.created(URI.create("/api/shipment/" + shipment.getId())).entity(shipment).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    } catch (IllegalStateException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @GET
  @Path("/gets-summary")
  public List<Shipment> getAllSummaryShipments() {
    return shipmentRepository.findAllShipments();
  }

  @GET
  @Path("/{id}")
  public Response getShipmentById(@PathParam("id") Long id) {
    Shipment shipment = shipmentRepository.findShipmentById(id);

    return shipment != null ? Response.ok(shipment).build() : Response.status(Response.Status.NOT_FOUND).build();
  }

  @PUT
  @Path("/{id}/status")
  public Response updateShipment(@PathParam("id") Long id, ShipmentDataStatus status) {
    try {
      Shipment updated = shipmentService.updateStatus(id, status.status());

      return Response.ok(updated).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    } catch (IllegalStateException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = shipmentRepository.deleteShipment(id);
    return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
  }
}
