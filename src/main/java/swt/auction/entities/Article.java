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
public class Article {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private String description;

  private Double reservePrice;

  private Double hammerPrice;

  private LocalDateTime auctionStartDate;

  private LocalDateTime auctionEndDate;

  @ManyToOne
  private Customer buyer;

  @ManyToOne
  private Customer seller;

  @OneToMany(mappedBy = "article")
  private List<Bid> bids;


}
