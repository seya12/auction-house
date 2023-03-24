package swt.auction.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Bid {

  @Id
  @GeneratedValue
  private Long id;

  private LocalDateTime date;

  @OneToOne
  @JoinColumn(name = "customerId")
  private Customer bidder;

  @ManyToOne
  private Article article;

}
