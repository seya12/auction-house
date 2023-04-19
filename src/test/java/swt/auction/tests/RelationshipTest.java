package swt.auction.tests;

import org.junit.jupiter.api.*;
import swt.auction.entities.*;
import swt.auction.entities.embeddables.*;
import swt.auction.entities.enums.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class RelationshipTest extends BaseTest {
  BidRepository bidRepository;
  CustomerRepository customerRepository;
  ArticleRepository articleRepository;

  @Override
  protected void initRepository() {
    bidRepository = new BidRepository(entityManager);
    customerRepository = new CustomerRepository(entityManager);
    articleRepository = new ArticleRepository(entityManager);
  }

  @Test
  void deleteCustomerThenBuyerArticleExistsBidDeleted() {
    var buyer = getDefaultCustomer();
    var seller = getDefaultCustomer();
    var article = getDefaultArticle(seller, buyer);
    var bid = getDefaultBid(buyer, article);

    customerRepository.delete(buyer);

    assertThat(bidRepository.find(bid.getId())).isNull();
    assertThat(articleRepository.find(article.getId())).isNotNull();
    assertThat(articleRepository.find(article.getId()).getBuyer()).isNull();
    assertThat(customerRepository.find(buyer.getId())).isNull();
  }

  @Test
  void deleteCustomerThenSellerArticleExistsBidNotDeleted() {
    var buyer = getDefaultCustomer();
    var seller = getDefaultCustomer();
    var article = getDefaultArticle(seller, buyer);
    var bid = getDefaultBid(buyer, article);

    customerRepository.delete(seller);

    assertThat(bidRepository.find(bid.getId())).usingRecursiveComparison().isEqualTo(bid);
    assertThat(articleRepository.find(article.getId())).isNotNull();
    assertThat(articleRepository.find(article.getId()).getSeller()).isNull();
    assertThat(customerRepository.find(seller.getId())).isNull();
  }

  @Test
  void deleteArticleThenCustomerExistsBidDeleted() {
    var buyer = getDefaultCustomer();
    var seller = getDefaultCustomer();
    var article = getDefaultArticle(seller, buyer);
    var bid = getDefaultBid(buyer, article);

    articleRepository.delete(article);

    assertThat(bidRepository.find(bid.getId())).isNull();
    assertThat(articleRepository.find(article.getId())).isNull();
    assertThat(customerRepository.find(seller.getId())).isNotNull();
    assertThat(customerRepository.find(buyer.getId())).isNotNull();
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

    return customer;
  }

  private Article getDefaultArticle(Customer seller, Customer buyer) {
    Article article = Article.builder()
      .name("Test Article")
      .description("Test Description")
      .reservePrice(10D)
      .hammerPrice(50D)
      .auctionStartDate(LocalDateTime.of(2020, 1, 1, 0, 0))
      .auctionStartDate(LocalDateTime.of(2023, 1, 1, 0, 0))
      .status(ArticleStatus.SOLD)
      .bids(new ArrayList<>())
      .build();

    article.addSeller(seller);
    article.addBuyer(buyer);
    articleRepository.save(article);
    JpaUtil.commitAndBegin(entityManager);

    return article;
  }


  private Bid getDefaultBid(Customer customer, Article article) {
    Bid bid = Bid.builder()
      .bid(1.0)
      .build();
    bid.addCustomer(customer);
    bid.addArticle(article);
    bidRepository.save(bid);
    JpaUtil.commitAndBegin(entityManager);
    
    return bid;
  }
}
