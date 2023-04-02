package swt.auction.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard extends PaymentOption {
  private String cardNumber;
  private String cardValidTo;
  private String cardVerificationValue;
}
