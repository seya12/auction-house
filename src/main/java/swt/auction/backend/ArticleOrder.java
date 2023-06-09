package swt.auction.backend;

import swt.auction.entities.*;

public enum ArticleOrder {
  NAME(Article_.NAME), RESERVE_PRICE(Article_.RESERVE_PRICE), HAMMER_PRICE(Article_.HAMMER_PRICE), AUCTION_START_DATE(
    Article_.AUCTION_START_DATE);
  public final String label;

  ArticleOrder(String label) {
    this.label = label;
  }
}
