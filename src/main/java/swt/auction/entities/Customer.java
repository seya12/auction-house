package swt.auction.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Customer {

  @Id
  @GeneratedValue
  private Long Id;

  private String firstName;

  private String lastName;

  @Embedded
  private Address shippingAddress;

  @Embedded
  private Address paymentAddress;

  @OneToMany(mappedBy = "buyer")
  private List<Article> boughtArticles;

  @OneToMany(mappedBy = "seller")
  private List<Article> soldArticles;

  @Enumerated
  private ArticleStatus status;


}
