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
  @AttributeOverrides({
    @AttributeOverride( name = "zipCode", column = @Column(name = "shippingCode")),
    @AttributeOverride( name = "city", column = @Column(name = "shippingCity")),
    @AttributeOverride( name = "street", column = @Column(name = "shippingStreet"))
  })
  private Address shippingAddress;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride( name = "zipCode", column = @Column(name = "paymentCode")),
    @AttributeOverride( name = "city", column = @Column(name = "paymentCity")),
    @AttributeOverride( name = "street", column = @Column(name = "paymentStreet"))
  })  private Address paymentAddress;

  @OneToMany(mappedBy = "buyer")
  private List<Article> boughtArticles;

  @OneToMany(mappedBy = "seller")
  private List<Article> soldArticles;

  @Enumerated
  private ArticleStatus status;


}
