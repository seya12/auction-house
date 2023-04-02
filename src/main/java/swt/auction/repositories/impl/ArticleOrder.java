package swt.auction.repositories.impl;

import swt.auction.entities.*;

public enum ArticleOrder {
  NAME(Article_.NAME), RESERVE_PRICE("reservePrice"), HAMMER_PRICE("hammerPrice"), AUCTION_START_DATE(
    "auctionStartDate");
  public final String label;

  private ArticleOrder(String label) {
    this.label = label;
  }
}
