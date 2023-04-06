package swt.auction.main;

import swt.auction.entities.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.time.*;
import java.util.*;

public class Insights {

  public void evaluation() {
    findArticlesByDescription();
    getArticlePrice(5);
    getTopSellers();
    getTopArticles();
  }


  private void findArticlesByDescription() {
    JpaUtil.executeWithResult(
      (em) -> new ArticleRepository(em).findArticlesByDescription("test", 0D, ArticleOrder.AUCTION_START_DATE));

  }

  private Double getArticlePrice(long id) {
    var article = JpaUtil.executeWithResult(em -> new ArticleRepository(em).find(id));

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

  private void getTopSellers() {

  }

  private void getTopArticles() {
  }

}
