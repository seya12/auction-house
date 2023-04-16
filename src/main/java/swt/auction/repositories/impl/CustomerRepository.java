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
    var customers = entityManager.createQuery(
        "select customer.id from Customer customer" +
        " join customer.soldArticles articles" +
        " group by customer.id" +
        " order by sum(articles.hammerPrice) desc nulls last", Long.class)
      .setMaxResults(count)
      .getResultList();

    var customerList = new ArrayList<Customer>();
    for (var customerId : customers) {
      customerList.add(find(customerId));
    }
    return customerList;
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
