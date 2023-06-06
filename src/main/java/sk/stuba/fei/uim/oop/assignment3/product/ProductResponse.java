package sk.stuba.fei.uim.oop.assignment3.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sk.stuba.fei.uim.oop.assignment3.payment.Payment;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private long amount;
    private String unit;
    private double price;
    //private List<Payment> shoppingList;

    public ProductResponse (Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.amount = product.getAmount();
        this.unit = product.getUnit();
        this.price = product.getPrice();
        //this.shoppingList = product.getShoppingList();
    }

}