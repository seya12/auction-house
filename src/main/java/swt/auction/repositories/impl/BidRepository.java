package swt.auction.repositories.impl;

import jakarta.persistence.*;
import swt.auction.entities.*;
import swt.auction.repositories.*;

public class BidRepository extends Repository<Bid> {

  public BidRepository(EntityManager entityManager) {
    super(Bid.class, entityManager);
  }
}
