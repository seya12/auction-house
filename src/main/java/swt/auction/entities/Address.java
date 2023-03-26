package swt.auction.entities;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {

  private String zipCode;
  private String city;
  private String street;

}
