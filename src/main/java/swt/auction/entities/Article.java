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

  @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
  @ToString.Exclude
  private List<Bid> bids;

  @Enumerated
  private ArticleStatus status;

}
