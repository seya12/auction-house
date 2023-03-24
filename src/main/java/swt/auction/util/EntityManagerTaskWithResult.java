package swt.auction.util;

import jakarta.persistence.*;

@FunctionalInterface
public interface EntityManagerTaskWithResult<T> {
  T execute(EntityManager entityManager);
}
