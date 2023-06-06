package sk.stuba.fei.uim.oop.assignment3.product;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.payment.Payment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    protected Long id;
    private String name;
    private String description;
    protected long amount;
    private String unit;
    private Long price;

    /*@Setter
    @OneToMany
    private List<Payment> shoppingList = new ArrayList<>();*/
}