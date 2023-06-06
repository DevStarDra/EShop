package sk.stuba.fei.uim.oop.assignment3.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.cart.Cart;
import sk.stuba.fei.uim.oop.assignment3.product.AmountById;
import sk.stuba.fei.uim.oop.assignment3.product.Product;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long amount;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Cart cart;

    public Payment(Product product, long amount) {
        this.product = product;
        this.amount = amount;
    }
}