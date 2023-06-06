package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDto {
    private long id;
    private boolean payed;

    List<CartPayment> shoppingList;
}
