package swt.auction.tests;


import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import swt.auction.entities.*;
import swt.auction.repositories.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


public class CustomerTests {

  Repository<Customer> customerRepository;
  EntityManager entityManager;

  @BeforeEach
  void init() {
    entityManager = JpaUtil.getTransactionalEntityManager();
    customerRepository = new CustomerRepository(entityManager);

    entityManager.createQuery("delete from Article ").executeUpdate();
    entityManager.createQuery("delete from Customer").executeUpdate();
    JpaUtil.commitAndBegin(entityManager);
  }

  @Test
  void findCustomerWhenExistsIsFound() {
    Customer customer = getDefaultCustomer();

    Customer fetchedCustomer = customerRepository.find(customer.getId());

    assertThat(customer).usingRecursiveComparison().isEqualTo(fetchedCustomer);
  }

  @Test
  void findCustomerWhenNotExistsIsNotFound() {
    Customer fetchedCustomer = customerRepository.find(5L);

    assertNull(fetchedCustomer);
  }

  @Test
  void findAllCustomersWhenNoExistSizeIsZero() {
    assertThat(customerRepository.findAll().size()).isEqualTo(0);
  }

  @Test
  void findAllCustomersWhenTwoExistSizeIsTwo() {
    getDefaultCustomer();
    getDefaultCustomer();

    assertThat(customerRepository.findAll().size()).isEqualTo(2);
  }

  @Test
  void saveCustomersWhenNewAreFound() {
    Customer customerOne = getDefaultCustomer();
    Customer customerTwo = getDefaultCustomer();

    Customer fetchedCustomerOne = customerRepository.find(customerOne.getId());
    Customer fetchedCustomerTwo = customerRepository.find(customerTwo.getId());

    assertAll(
      () -> assertThat(customerOne).usingRecursiveComparison().isEqualTo(fetchedCustomerOne),
      () -> assertThat(customerTwo).usingRecursiveComparison().isEqualTo(fetchedCustomerTwo));
  }

  @Test
  void updateCustomerFieldsAreUpdated() {
    Customer customer = getDefaultCustomer();

    customer.setLastName("Test");
    Article newBoughtArticle = new Article();
    Article newSoldArticle = new Article();
    newBoughtArticle.addBuyer(customer);
    newSoldArticle.addSeller(customer);

    var updatedEntity = customerRepository.update(customer);


    assertAll(
      () -> assertThat(customer.getLastName()).isEqualTo(updatedEntity.getLastName()),
      () -> assertThat(updatedEntity.getBoughtArticles().size()).isEqualTo(1),
      () -> assertThat(updatedEntity.getBoughtArticles().get(0).getId()).isNotNull(),
      () -> assertThat(updatedEntity.getSoldArticles().size()).isEqualTo(1),
      () -> assertThat(updatedEntity.getSoldArticles().get(0).getId()).isNotNull()
             );
  }

  @Test
  void removeWhenCustomerFoundThenDelete() {
    Customer customer = getDefaultCustomer();

    customerRepository.delete(customer);

    assertThat(customerRepository.find(customer.getId())).isNull();
  }

  @Test
  void removeWhenNoCustomerFoundThenNoCustomerDeleted() {
    getDefaultCustomer();

    int sizeBefore = customerRepository.findAll().size();
    customerRepository.delete(new Customer());
    int sizeAfter = customerRepository.findAll().size();

    assertThat(sizeBefore).isEqualTo(sizeAfter);
  }

  @Test
  void removeByIdWhenCustomerFoundThenDelete() {
    Customer customer = getDefaultCustomer();

    customerRepository.deleteById(customer.getId());

    assertThat(customerRepository.find(customer.getId())).isNull();
  }

  @Test
  void removeByIdWhenNoCustomerFoundThenNoCustomerDeleted() {
    getDefaultCustomer();

    int sizeBefore = customerRepository.findAll().size();
    customerRepository.deleteById(null);
    customerRepository.deleteById(51L);
    int sizeAfter = customerRepository.findAll().size();

    assertThat(sizeBefore).isEqualTo(sizeAfter);
  }

  @AfterEach
  void close() {
    entityManager.close();
  }

  private Customer getDefaultCustomer() {
    var customer = Customer.builder()
      .firstName("Max")
      .lastName("Mustermann")
      .email("max.mustermann@gmail.com")
      .paymentAddress(new Address("4020", "Linz", "Hafenstraße"))
      .shippingAddress(new Address("4040", "Urfahr", "Rieglstraße"))
      .boughtArticles(new ArrayList<>())
      .soldArticles(new ArrayList<>())
      .build();

    customerRepository.save(customer);
    JpaUtil.commitAndBegin(entityManager);
    entityManager.detach(customer);

    return customer;
  }
}
