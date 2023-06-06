package sk.stuba.fei.uim.oop.assignment3.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private long productId;
    private int amount;
}
