package swt.auction.backend;

import swt.auction.entities.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.time.*;
import java.util.*;

public class Insights {

  private Insights() {
  }

  public static List<Article> findArticlesByDescription(String searchPhrase,
                                                        Double maxReservePrice,
                                                        ArticleOrder articleOrder) {
    return JpaUtil.executeWithResult(
      (em) -> new ArticleRepository(em).findArticlesByDescription(searchPhrase, maxReservePrice, articleOrder));

  }

  public static Double getArticlePrice(long id) {
    var article = JpaUtil.executeWithResult(em -> {
      var result = new ArticleRepository(em).find(id);
      if (result != null) {
        result.getBids().size(); //load bids
      }
      return result;
    });

    if (article == null) {
      throw new ArticleNotFoundException();
    }

    if (LocalDateTime.now().isBefore(article.getAuctionStartDate())) {
      return null;
    }

    if (article.getAuctionEndDate() == null) {
      return article.getBids()
        .stream()
        .max(Comparator.comparingDouble(Bid::getBid))
        .orElse(new Bid())
        .getBid();
    }

    return article.getHammerPrice();

  }

  public static List<Customer> getTopSellers(int count) {
    return JpaUtil.executeWithResult(em -> new CustomerRepository(em).getTopSellers(count));
  }

  public static List<Article> getTopArticles(int count) {
    return JpaUtil.executeWithResult(em -> new ArticleRepository(em).getTopArticles(count));
  }

}
