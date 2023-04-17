package swt.auction.tests;

import org.junit.jupiter.api.*;
import swt.auction.entities.*;
import swt.auction.entities.enums.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.time.*;

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


  private Article getDefaultArticle() {
    Article article = Article.builder()
      .name("Test Article")
      .description("Test Description")
      .reservePrice(100D)
      .hammerPrice(200D)
      .auctionStartDate(LocalDateTime.of(2020, 1, 1, 0, 0))
      .auctionStartDate(LocalDateTime.of(2023, 1, 1, 0, 0))
      .status(ArticleStatus.SOLD)
      .build();
    articleRepository.save(article);
    JpaUtil.commitAndBegin(entityManager);
    return article;
  }
}
