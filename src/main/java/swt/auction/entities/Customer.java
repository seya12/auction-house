package swt.auction.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

  private String firstName;

  private String lastName;

  private String email;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "zipCode", column = @Column(name = "shippingCode")),
    @AttributeOverride(name = "city", column = @Column(name = "shippingCity")),
    @AttributeOverride(name = "street", column = @Column(name = "shippingStreet"))
  })
  private Address shippingAddress;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "zipCode", column = @Column(name = "paymentCode")),
    @AttributeOverride(name = "city", column = @Column(name = "paymentCity")),
    @AttributeOverride(name = "street", column = @Column(name = "paymentStreet"))
  })
  private Address paymentAddress;

  @OneToMany(mappedBy = "owner", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  @ToString.Exclude
  private List<PaymentOption> paymentOptions;


  @OneToMany(mappedBy = "buyer", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  @ToString.Exclude
  private List<Article> boughtArticles;

  @OneToMany(mappedBy = "seller", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  @ToString.Exclude
  private List<Article> soldArticles;

  public void addBoughtArticle(Article article) {
    if (article.getBuyer() != null) {
      article.getBuyer().boughtArticles.remove(article);
    }
    article.setBuyer(this);
    boughtArticles.add(article);
  }

  public void addSoldArticle(Article article) {
    if (article.getSeller() != null) {
      article.getSeller().soldArticles.remove(article);
    }
    article.setSeller(this);
    soldArticles.add(article);
  }

  public void addPaymentOption() {

  }
}
