package swt.auction.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Bid extends BaseEntity {

  private LocalDateTime date;

  @OneToOne
  @JoinColumn(name = "customerId")
  private Customer bidder;

  @ManyToOne
  private Article article;

}
