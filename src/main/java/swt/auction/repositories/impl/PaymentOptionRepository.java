package swt.auction.repositories.impl;

import jakarta.persistence.*;
import swt.auction.entities.*;
import swt.auction.repositories.*;

public class PaymentOptionRepository extends Repository<PaymentOption> {

  public PaymentOptionRepository(EntityManager entityManager) {
    super(PaymentOption.class, entityManager);
  }
}
