package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICartService {
    List<Cart> getAll();
    Cart getById(Long id);
    Cart createCart();
    Cart changeCart(CartRequest request, Cart Cart);
    ResponseEntity addProductToCart(long productId, long cartId, long amount);
    HttpStatus payForCart(Long id);
    Long getPriceOfCart(Long id);
    void delete(Long id);
}