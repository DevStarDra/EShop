package sk.stuba.fei.uim.oop.assignment3.cart;


import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.product.Product;

import java.util.List;

@Setter
@Getter
public class CartRequest {
    private boolean payed;
    private List<Product> shoppingList;
}