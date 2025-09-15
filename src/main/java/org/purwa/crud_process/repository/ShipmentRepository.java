package org.purwa.crud_process.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.purwa.crud_process.model.Shipment;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ShipmentRepository {
  @Inject
  EntityManager em;

  public Shipment findShipmentById(Long id) {
    return em.find(Shipment.class, id);
  }

  public List<Shipment> findAllShipments() {
    return em.createQuery("SELECT s FROM Shipment s ORDER BY s.creationDate DESC", Shipment.class).getResultList();
  }

  @Transactional
  public boolean deleteShipment(Long id) {
    Shipment shipmentId = findShipmentById(id);

    if (shipmentId != null) {
      em.remove(shipmentId);
      return true;
    }

    return false;
  }

  public Optional<Shipment> findCustomerWithTheirId(Long id) {
    return em.createQuery("SELECT s FROM Shipment s JOIN FETCH s.customer WHERE s.id = :id", Shipment.class)
        .setParameter("id", id)
        .getResultStream()
        .findFirst();
  }
}
