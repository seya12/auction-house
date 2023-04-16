package swt.auction.tests;


import org.junit.jupiter.api.*;
import swt.auction.entities.*;
import swt.auction.entities.embeddables.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


public class CustomerTests extends BaseTest {

  CustomerRepository customerRepository;

  @Override
  protected void initRepository() {
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


  @Test
  void getTopSellersWhenNoCustomerExistsThenEmptyList() {
    List<Customer> topSellers = customerRepository.getTopSellers(5);

    assertThat(topSellers).isEmpty();
  }

  @Test
  void getTopSellersWhenCustomersExistReturnTopSellers() {
    Customer customerOne = getDefaultCustomer();
    Customer customerTwo = getDefaultCustomer();
    Customer customerThree = getDefaultCustomer();

    Article articleOne = new Article();
    articleOne.setHammerPrice(10D);
    articleOne.setBids(new ArrayList<>());
    Article articleTwo = new Article();
    articleTwo.setHammerPrice(50D);
    articleTwo.setBids(new ArrayList<>());
    Article articleThree = new Article();
    articleThree.setHammerPrice(25D);
    articleThree.setBids(new ArrayList<>());

    articleOne.addSeller(customerOne);
    articleTwo.addSeller(customerTwo);
    articleThree.addSeller(customerThree);

    JpaUtil.execute((em) -> {
      em.persist(articleOne);
      em.persist(articleTwo);
      em.persist(articleThree);
    });

    List<Customer> topSellers = customerRepository.getTopSellers(3);

    assertAll(
      () -> assertThat(topSellers.size()).isEqualTo(3),
      () -> assertThat(topSellers.get(0).getId()).isEqualTo(customerTwo.getId()),
      () -> assertThat(topSellers.get(1).getId()).isEqualTo(customerThree.getId()),
      () -> assertThat(topSellers.get(2).getId()).isEqualTo(customerOne.getId())
             );
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
      .bids(new ArrayList<>())
      .paymentOptions(new ArrayList<>())
      .build();

    customerRepository.save(customer);
    JpaUtil.commitAndBegin(entityManager);
    entityManager.detach(customer);

    return customer;
  }
}
