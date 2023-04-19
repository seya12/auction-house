package swt.auction.tests;

import org.junit.jupiter.api.*;
import swt.auction.backend.*;
import swt.auction.entities.*;
import swt.auction.entities.enums.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class ArticleTests extends BaseTest {

  ArticleRepository articleRepository;

  @Override
  protected void initRepository() {
    articleRepository = new ArticleRepository(entityManager);
  }

  @Test
  void findArticleWhenExistsIsFound() {
    Article article = getDefaultArticle();

    Article fetchedArticle = articleRepository.find(article.getId());

    assertThat(article).usingRecursiveComparison().isEqualTo(fetchedArticle);
  }

  @Test
  void findArticleWhenNotExistsIsNotFound() {
    Article fetchedArticle = articleRepository.find(5L);

    assertThat(fetchedArticle).isNull();
  }

  @Test
  void findAllArticlesWhenNoExistSizeIsZero() {
    assertThat(articleRepository.findAll().size()).isEqualTo(0);
  }

  @Test
  void findAllArticlesWhenTwoExistSizeIsTwo() {
    getDefaultArticle();
    getDefaultArticle();

    assertThat(articleRepository.findAll().size()).isEqualTo(2);
  }

  @Test
  void saveArticlesWhenNewAreFound() {
    Article articleOne = getDefaultArticle();
    Article articleTwo = getDefaultArticle();

    Article fetchedArticleOne = articleRepository.find(articleOne.getId());
    Article fetchedArticleTwo = articleRepository.find(articleTwo.getId());

    assertThat(articleOne).usingRecursiveComparison().isEqualTo(fetchedArticleOne);
    assertThat(articleTwo).usingRecursiveComparison().isEqualTo(fetchedArticleTwo);
  }

  @Test
  void updateArticleWhenUpdatedIsFound() {
    Article article = getDefaultArticle();
    article.setName("New Name");
    article.setDescription("New Description");
    article.setReservePrice(200D);
    article.setHammerPrice(300D);
    article.setAuctionStartDate(LocalDateTime.of(2021, 1, 1, 0, 0));
    article.setAuctionEndDate(LocalDateTime.of(2024, 1, 1, 0, 0));

    var updatedArticle = articleRepository.update(article);
    Article fetchedArticle = articleRepository.find(article.getId());

    assertThat(updatedArticle).usingRecursiveComparison().isEqualTo(fetchedArticle);
  }

  @Test
  void deleteArticleWhenDeletedIsNotFound() {
    Article article = getDefaultArticle();

    articleRepository.delete(article);
    Article fetchedArticle = articleRepository.find(article.getId());

    assertThat(fetchedArticle).isNull();
  }

  @Test
  void deleteWhenNoArticleFoundThenNoArticleDeleted() {
    getDefaultArticle();

    int sizeBefore = articleRepository.findAll().size();
    articleRepository.delete(new Article());
    int sizeAfter = articleRepository.findAll().size();

    assertThat(sizeBefore).isEqualTo(sizeAfter);
  }

  @Test
  void findArticlesByDescriptionWhenNotFoundEmptyListIsReturned() {
    var articles = articleRepository.findArticlesByDescription("", 0D, null);
    assertThat(articles).isEmpty();
  }

  @Test
  void findArticlesByDescriptionWhenFoundListIsReturned() {
    getDefaultArticle();
    getDefaultArticle();

    var articles = articleRepository.findArticlesByDescription("Test", 500D, null);
    assertThat(articles.size()).isEqualTo(2);
  }

  @Test
  void findArticlesByDescriptionWhenFoundWithDifferentDescriptionListIsReturned() {
    var article = getDefaultArticle();
    getDefaultArticle("abc");

    var articles = articleRepository.findArticlesByDescription("Test", 500D, null);

    assertThat(articles.size()).isEqualTo(1);
    assertThat(articles.get(0)).usingRecursiveComparison().isEqualTo(article);
  }

  @Test
  void findArticlesByDescriptionWhenFoundWithDifferentPriceListIsReturned() {
    getDefaultArticle();

    var articles = articleRepository.findArticlesByDescription("Test", 1D, null);

    assertThat(articles.size()).isEqualTo(0);
  }

  @Test
  void findArticlesByDescriptionWhenFoundWithOrderThenOrderedListIsReturned() {
    var articleOne = getDefaultArticle(100D);
    var articleTwo = getDefaultArticle(50D);
    var articleThree = getDefaultArticle(75D);

    var articles = articleRepository.findArticlesByDescription("", 500D, ArticleOrder.RESERVE_PRICE);

    assertThat(articles.size()).isEqualTo(3);
    assertThat(articles.get(0)).usingRecursiveComparison().isEqualTo(articleTwo);
    assertThat(articles.get(1)).usingRecursiveComparison().isEqualTo(articleThree);
    assertThat(articles.get(2)).usingRecursiveComparison().isEqualTo(articleOne);
  }

  @Test
  void getTopArticlesWhenNoArticlesThenEmptyListIsReturned() {
    var articles = articleRepository.getTopArticles(10);
    assertThat(articles).isEmpty();
  }

  @Test
  void getTopArticlesWhenArticlesThenListIsReturned() {
    var articleOne = getDefaultArticle(10D, 50D);
    var articleTwo = getDefaultArticle(10D, 20D);
    var articleThree = getDefaultArticle(10D, 100D);

    var articles = articleRepository.getTopArticles(10);

    assertThat(articles.size()).isEqualTo(3);
    assertThat(articles.get(0)).usingRecursiveComparison().isEqualTo(articleThree);
    assertThat(articles.get(1)).usingRecursiveComparison().isEqualTo(articleOne);
    assertThat(articles.get(2)).usingRecursiveComparison().isEqualTo(articleTwo);
  }

  private Article getDefaultArticle(String description, Double reservePrice, Double hammerPrice) {
    Article article = Article.builder()
      .name("Test Article")
      .description(description)
      .reservePrice(reservePrice)
      .hammerPrice(hammerPrice)
      .auctionStartDate(LocalDateTime.of(2020, 1, 1, 0, 0))
      .auctionStartDate(LocalDateTime.of(2023, 1, 1, 0, 0))
      .status(ArticleStatus.SOLD)
      .bids(new ArrayList<>())
      .build();
    articleRepository.save(article);
    JpaUtil.commitAndBegin(entityManager);
    return article;
  }

  private Article getDefaultArticle(String description) {
    return getDefaultArticle(description, 100D, 200D);
  }

  private Article getDefaultArticle(Double reservePrice) {
    return getDefaultArticle("Test Description", reservePrice, 200D);
  }

  private Article getDefaultArticle(Double reservePrice, Double hammerPrice) {
    return getDefaultArticle("Test Description", reservePrice, hammerPrice);
  }

  private Article getDefaultArticle() {
    return getDefaultArticle("Test Description", 100D, 200D);
  }

}
