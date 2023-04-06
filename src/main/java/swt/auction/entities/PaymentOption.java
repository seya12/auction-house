package swt.auction.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaymentOption extends BaseEntity {
  @ManyToOne
  private Customer owner;
}
