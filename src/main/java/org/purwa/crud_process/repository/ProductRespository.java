package org.purwa.crud_process.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.purwa.crud_process.model.Product;

import java.util.List;

@ApplicationScoped
public class ProductRespository {
  @Inject
  EntityManager em;

  public Product findProductById(Long id) {
    return em.find(Product.class, id);
  }

  public List<Product> findAllProducts() {
    return em.createQuery("SELECT p FROM Product p ORDER BY p.name", Product.class).getResultList();
  }

  @Transactional
  public void createProduct(Product product) {
    em.persist(product);
  }

  @Transactional
  public Product updateProduct(Product product) {
    return em.merge(product);
  }

  @Transactional
  public boolean deleteProduct(Long id) {
    Product productId = findProductById(id);

    if (productId != null) {
      em.remove(productId);
      return true;
    }

    return false;
  }
}
