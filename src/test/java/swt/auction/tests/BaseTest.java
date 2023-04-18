package swt.auction.tests;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import swt.auction.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

  protected EntityManager entityManager;

  @BeforeAll
  void setup() {
    JpaUtil.getEntityManagerFactory();
  }

  @AfterAll
  void tearDown() {
    JpaUtil.closeEntityManagerFactory();
  }

  @BeforeEach
  void init() {
    entityManager = JpaUtil.getTransactionalEntityManager();
    initRepository();
    deleteEntities();
  }

  protected abstract void initRepository();

  public void deleteEntities() {
    entityManager.createQuery("delete from Bid ").executeUpdate();
    entityManager.createQuery("delete from Article ").executeUpdate();
    entityManager.createQuery("delete from Customer").executeUpdate();
    JpaUtil.commitAndBegin(entityManager);
  }

}
