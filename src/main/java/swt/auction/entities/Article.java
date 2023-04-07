package swt.auction.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article extends BaseEntity {

  private String name;

  private String description;

  private Double reservePrice;

  private Double hammerPrice;

  private LocalDateTime auctionStartDate;

  private LocalDateTime auctionEndDate;

  @ManyToOne
  @ToString.Exclude
  private Customer seller;

  @ManyToOne
  @ToString.Exclude
  private Customer buyer;

  @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, orphanRemoval = true)
  @ToString.Exclude
  private List<Bid> bids;

  @Enumerated(EnumType.STRING)
  private ArticleStatus status;

  public void addBuyer(Customer buyer) {
    if (this.buyer != null) {
      this.buyer.getBoughtArticles().remove(this);
    }
    this.buyer = buyer;
    buyer.getBoughtArticles().add(this);
  }

  public void removeBuyer() {
    if (buyer != null && buyer.getBoughtArticles() != null) {
      buyer.getBoughtArticles().remove(this);
    }
    buyer = null;
  }

  public void addSeller(Customer seller) {
    if (this.seller != null) {
      this.seller.getSoldArticles().remove(this);
    }
    this.seller = seller;
    seller.getSoldArticles().add(this);
  }

  public void removeSeller() {
    if (seller != null && seller.getSoldArticles() != null) {
      seller.getSoldArticles().remove(this);
    }
    seller = null;
  }
}
