package org.purwa.crud_process.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.purwa.crud_process.data.ShipmentDataRequest;
import org.purwa.crud_process.enums.ShipmentStatus;
import org.purwa.crud_process.model.Customer;
import org.purwa.crud_process.model.Product;
import org.purwa.crud_process.model.Shipment;
import org.purwa.crud_process.model.ShipmentItem;
import org.purwa.crud_process.repository.CustomerRepository;
import org.purwa.crud_process.repository.ProductRespository;
import org.purwa.crud_process.repository.ShipmentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ShipmentService {
  @Inject
  CustomerRepository customerRepository;

  @Inject
  ProductRespository  productRespository;

  @Inject
  EntityManager em;
  @Inject
  ShipmentRepository shipmentRepository;

  @Transactional
  public Shipment createShipment(ShipmentDataRequest request) {
    Customer findCustomer = customerRepository.findCustomerById(request.customerId());
    if (findCustomer == null) {
      throw new NotFoundException("ID Customer " + request.customerId() + " not found");
    }

    Shipment objectShipment = new Shipment();
    objectShipment.setCustomer(findCustomer);
    objectShipment.setStatus(ShipmentStatus.PROCESSING);

    List<ShipmentItem> listOfShipmentItems = new ArrayList<>();
    for (var dataItem : request.items()) {
      Product findProduct = productRespository.findProductById(dataItem.productId());

      if (findProduct == null) {
        throw new NotFoundException("ID Product " + dataItem.productId() + "  not found");
      }

      if(findProduct.getQuantity() < dataItem.quantity()) {
        throw  new IllegalStateException("Insufficient stock for " + findProduct.getName() + " products.");
      }

      findProduct.setQuantity(findProduct.getQuantity() - dataItem.quantity());
      em.merge(findProduct);

      ShipmentItem objectItem = new ShipmentItem();
      objectItem.setProduct(findProduct);
      objectItem.setQuantity(dataItem.quantity());
      objectItem.setShipment(objectShipment);

      listOfShipmentItems.add(objectItem);
    }

    objectShipment.setItems(listOfShipmentItems);
    em.persist(objectShipment);

    return objectShipment;
  }

  @Transactional
  public Shipment updateStatus(Long id, String status) {

    Optional<Shipment> shipment = shipmentRepository.findCustomerWithTheirId(id);
    if (shipment.isEmpty()) {
      throw new NotFoundException("Shipping with id " + id + " Not Found");
    }

    Shipment getShipment = shipment.get();

    ShipmentStatus newStatus;
    try {
      newStatus = ShipmentStatus.valueOf(status.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Status '" + status + "' is not valid!. The available Status are: " + Arrays.toString(ShipmentStatus.values()));
    }

    getShipment.setStatus(newStatus);
    
    return getShipment;
  }

}
