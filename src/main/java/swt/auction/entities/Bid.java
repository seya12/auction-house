package swt.auction.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bid extends BaseEntity {

  private double bid;

  private LocalDateTime date;

  @OneToOne
  @JoinColumn(name = "customerId")
  private Customer bidder;

  @ManyToOne
  private Article article;

  public void addArticle(Article article) {
    if (article != null && article.getBids() != null) {
      article.getBids().remove(this);
    }
    this.article = article;
    article.getBids().add(this);
  }

  public void removeArticle() {
    if (article != null && article.getBids() != null) {
      article.getBids().remove(this);
    }
    article = null;
  }

}
