package swt.auction.repositories.impl;

import jakarta.persistence.*;
import swt.auction.entities.*;
import swt.auction.repositories.*;

import java.util.*;

public class CustomerRepository extends Repository<Customer> {

  public CustomerRepository(EntityManager entityManager) {
    super(Customer.class, entityManager);
  }

  public List<Customer> getTopSellers(int count) {
    var criteriaBuilder = entityManager.getCriteriaBuilder();
    var query = criteriaBuilder.createQuery(Customer.class);
    var customer = query.from(Customer.class);
    var soldArticles = customer.join(Customer_.SOLD_ARTICLES);

    query.multiselect(customer, criteriaBuilder.sum(soldArticles.get(Article_.HAMMER_PRICE)));
    query.groupBy(customer.get(Customer_.ID));
    query.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(soldArticles.get(Article_.HAMMER_PRICE))));

    return entityManager.createQuery(query).setMaxResults(count).getResultList();
  }

  @Override
  public boolean deleteById(Long primaryKey) {
    var entity = find(primaryKey);

    if (entity == null) {
      return false;
    }

    for (var article : entity.getBoughtArticles()) {
      article.setBuyer(null);
    }
    for (var article : entity.getSoldArticles()) {
      article.setSeller(null);
    }
    entityManager.remove(entity);
    return true;
  }
}
