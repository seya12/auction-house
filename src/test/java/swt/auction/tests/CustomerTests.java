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
  void updateCustomerFieldsAreUpdated(){
    Customer customer = getDefaultCustomer();
    customer.setLastName("Test");
    Article newArticle = new Article();
    customer.addBoughtArticle(newArticle);

    var updatedEntity = customerRepository.update(customer);

    assertAll(
      () -> assertThat(customer.getLastName()).isEqualTo(updatedEntity.getLastName()),
      () -> assertThat(updatedEntity.getBoughtArticles().size()).isEqualTo(1));
  }

  @Test
  void removeWhenCustomerFoundThenDelete(){
    Customer customer = getDefaultCustomer();

    customerRepository.delete(customer);

    assertThat(customerRepository.find(customer.getId())).isNull();
  }

  @Test
  void removeWhenNoCustomerFoundThenNoCustomerDeleted(){
    Customer customer = getDefaultCustomer();

    customerRepository.delete(new Customer());

    assertThat(customerRepository.find(customer.getId())).isNotNull();
  }

  @AfterEach
  void commit(){
    entityManager.createQuery("delete from Article ").executeUpdate();
    entityManager.createQuery("delete from Customer").executeUpdate();
    JpaUtil.commit(entityManager);
  }

  private Customer getDefaultCustomer() {
    var customer =  Customer.builder()
      .firstName("Max")
      .lastName("Mustermann")
      .paymentAddress(new Address("4020", "Linz", "Hafenstraße"))
      .shippingAddress(new Address("4040", "Urfahr", "Rieglstraße"))
      .boughtArticles(new ArrayList<>())
      .soldArticles(new ArrayList<>())
      .build();

    customerRepository.save(customer);
    JpaUtil.commit(entityManager);
    entityManager.detach(customer);
    entityManager.getTransaction().begin();

    return customer;
  }


}
