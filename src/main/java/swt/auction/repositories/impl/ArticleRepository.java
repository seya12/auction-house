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
    var query = criteriaBuilder.createQuery(Address.class);
    var root = query.from(Address.class);
    query.select(root);
    query.where(criteriaBuilder.like(root.get(Article_.DESCRIPTION), "%" + searchPhrase + "%"));

    return null;
  }
}
