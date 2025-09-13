package org.purwa.crud_process.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.purwa.crud_process.model.Customer;
import org.purwa.crud_process.repository.CustomerRepository;

import java.net.URI;
import java.util.List;

@Path("/api/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
  @Inject
  CustomerRepository customerRepository;

  @POST
  public Response createCustomer(Customer customer) {
    customerRepository.createCustomer(customer);

    return Response.created(URI.create("/api/customer" + customer.getId())).entity(customer).build();
  }

  @GET
  @Path("/{id}")
  public Response getCustomerById(@PathParam("id") Long id) {
    Customer customer = customerRepository.findCustomerById(id);

    return customer != null ? Response.ok(customer).build() : Response.status(Response.Status.NOT_FOUND).build();
  }

  @GET
  @Path("/gets")
  public List<Customer> getAllCustomers() {
    return customerRepository.findAllCustomers();
  }

  @PUT
  @Path("/{id}")
  public Response updateCustomer(@PathParam("id") Long id, Customer customer) {
    if (customerRepository.findCustomerById(id) == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    customer.setId(id);
    Customer updated = customerRepository.updateCustomer(customer);

    return Response.ok(updated).build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteCustomer(@PathParam("id") Long id) {
    boolean deleted = customerRepository.deleteCustomer(id);

    return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
  }
}
