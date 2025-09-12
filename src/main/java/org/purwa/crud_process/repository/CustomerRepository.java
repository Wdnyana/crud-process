package org.purwa.crud_process.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.purwa.crud_process.model.Customer;

import java.util.List;

@ApplicationScoped
public class CustomerRepository {
  @Inject
  EntityManager em;

  public Customer findCustomerById(Long id) {
    return  em.find(Customer.class, id);
  }

  public List<Customer> findAllCustomers() {
    return em.createQuery("SELECT c FROM Customer c ORDER BY c.name ASC", Customer.class).getResultList();
  }

  @Transactional
  public void createCustomer(Customer customer) {
    em.persist(customer);
  }

  @Transactional
  public Customer updateCustomer(Customer customer) {
    return em.merge(customer);
  }

  @Transactional
  public boolean deleteCustomer(Long id) {
    Customer customerId = findCustomerById(id);

    if (customerId != null) {
      em.remove(customerId);
      return true;
    }

    return false;
  }
}
