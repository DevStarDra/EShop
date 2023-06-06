package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import sk.stuba.fei.uim.oop.assignment3.payment.Payment;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartResponse {

    private long id;

    private boolean payed;

    private List<Payment> shoppingList;

    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.payed = cart.isPayed();
        this.shoppingList = cart.getShoppingList();
    }
}