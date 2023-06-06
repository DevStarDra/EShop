package sk.stuba.fei.uim.oop.assignment3.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmountById {
    private Long amount;

    public AmountById(Product product) {
        amount = product.getAmount();
    }
}