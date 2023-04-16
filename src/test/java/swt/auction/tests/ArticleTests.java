package swt.auction.tests;

import org.junit.jupiter.api.*;
import swt.auction.entities.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

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

  private Article getDefaultArticle() {
    Article article = Article.builder()
      .name("Test Article")
      .description("Test Description")
      .build();
    articleRepository.save(article);
    JpaUtil.commitAndBegin(entityManager);
    return article;
  }
}
