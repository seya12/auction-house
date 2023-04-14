package swt.auction.repositories.impl;

import jakarta.persistence.*;
import swt.auction.entities.*;
import swt.auction.repositories.*;

import java.util.*;

public class ArticleRepository extends Repository<Article> {

  public ArticleRepository(EntityManager entityManager) {
    super(Article.class, entityManager);
  }

  public List<Article> findArticlesByDescription(String searchPhrase, Double maxReservePrice, ArticleOrder order) {
    var criteriaBuilder = entityManager.getCriteriaBuilder();
    var query = criteriaBuilder.createQuery(Article.class);
    var root = query.from(Article.class);
    var descriptionCriteria = criteriaBuilder.like(root.get(Article_.DESCRIPTION), "%" + searchPhrase + "%");
    var reservePriceCriteria = criteriaBuilder.lessThanOrEqualTo(root.get(Article_.RESERVE_PRICE), maxReservePrice);

    var whereCriteria = maxReservePrice == null || maxReservePrice == 0 ? descriptionCriteria
                                                                        : criteriaBuilder.and(descriptionCriteria,
                                                                          reservePriceCriteria);

    query.select(root);
    query.where(whereCriteria);
    if (order != null) {
      query.orderBy(criteriaBuilder.asc(root.get(order.label)));
    }

    return entityManager.createQuery(query).getResultList();
  }

  public List<Article> getTopArticles(int count) {
    return entityManager.createQuery("select article from Article article" +
                                     "    order by article.hammerPrice - article.reservePrice desc nulls last",
        Article.class)
      .setMaxResults(count)
      .getResultList();
  }
}
