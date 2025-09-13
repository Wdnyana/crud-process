package org.purwa.crud_process.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.purwa.crud_process.model.Product;
import org.purwa.crud_process.repository.ProductRespository;

import java.net.URI;
import java.util.List;

@Path("/api/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
  @Inject
  ProductRespository productRespository;

  @POST
  public Response createProduct(Product product) {
    productRespository.createProduct(product);

    return Response.created(URI.create("/api/product/" + product.getId())).entity(product).build();
  }

  @GET
  @Path("/{id}")
  public Response getProductById(@PathParam("id") Long id) {
    Product productId = productRespository.findProductById(id);

    return productId != null ? Response.ok(productId).build() : Response.status(Response.Status.NOT_FOUND).build();
  }

  @GET
  @Path("/gets")
  public List<Product> getAllProducts() {
    return productRespository.findAllProducts();
  }

  @PUT
  @Path("/{id}")
  public Response updateProduct(@PathParam("id") Long id, Product product) {
    if (productRespository.findProductById(id) == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    product.setId(id);
    Product updated = productRespository.updateProduct(product);

    return Response.ok(updated).build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteProduct(@PathParam("id") Long id) {
    boolean deleted = productRespository.deleteProduct(id);

    return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
  }
}
