package swt.auction.util;

import jakarta.persistence.*;

@FunctionalInterface
public interface EntityManagerTask {
  void execute(EntityManager entityManager);
}
