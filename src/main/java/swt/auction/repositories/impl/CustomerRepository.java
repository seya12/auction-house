package swt.auction.repositories.impl;

import jakarta.persistence.*;
import swt.auction.entities.*;
import swt.auction.repositories.*;

public class CustomerRepository extends Repository<Customer, Long> {

  public CustomerRepository(EntityManager entityManager) {
    super(Customer.class, entityManager);
  }
}
