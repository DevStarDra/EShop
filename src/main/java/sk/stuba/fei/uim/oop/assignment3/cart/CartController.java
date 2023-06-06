package sk.stuba.fei.uim.oop.assignment3.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.payment.Payment;
import sk.stuba.fei.uim.oop.assignment3.payment.PaymentRequest;
import sk.stuba.fei.uim.oop.assignment3.product.IProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService service;

    @Autowired
    private IProductService productService;

    @GetMapping()
    public List<CartResponse> getAllCarts() {
        return this.service.getAll().stream().map(CartResponse::new).collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<CartResponse> addCart() {
        return new ResponseEntity<>(new CartResponse(this.service.createCart()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Cart changeCart (@PathVariable("id") Long id, @RequestBody CartRequest request) {
        Cart cart = new Cart();
        CartResponseDto cartResponseDto = getCartById(id);
        if(cartResponseDto == null) throw new NotFoundException();
        cart.setId(id);
        cart.setPayed(request.isPayed());
           cart.setShoppingList(request.getShoppingList().stream().map(cartPayment -> {
                Payment payment = new Payment();
                payment.setAmount(cartPayment.getAmount());
                payment.setProduct(this.productService.getById(cartPayment.getId()));
                payment.setCart(cart);
                return payment;
            }).collect(Collectors.toList()));
        new CartResponse(this.service.changeCart(request, cart));
        return cart;
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable("id") Long id){
        CartResponseDto cart = getCartById(id);
        if(cart == null)
            throw new NotFoundException();
        this.service.delete(id);
    }

    @GetMapping("/{id}")
    public CartResponseDto getCartById(@PathVariable("id") Long id) {
        CartResponseDto cart = new CartResponseDto();
        if (this.service.getById(id) == null) throw new NotFoundException();
        Cart byId = this.service.getById(id);
        cart.setId(byId.getId());
        cart.setPayed(byId.isPayed());
        List<CartPayment> collectCart = byId.getShoppingList().stream().map(product -> {
            CartPayment cartPayment = new CartPayment();
            cartPayment.setProductId(product.getProduct().getId());
            cartPayment.setAmount(product.getProduct().getAmount());
            return cartPayment;
        }).collect(Collectors.toList());
        cart.setShoppingList(collectCart);
        return cart;
    }


    @PostMapping("/{id}/add")
    public ResponseEntity addProductToCart(@PathVariable("id") long cartId, @RequestBody PaymentRequest request) {
        return this.service.addProductToCart(request.getProductId(), cartId, request.getAmount());
    }

    @GetMapping("/{id}/pay")
    public ResponseEntity<String> payForCart(@PathVariable("id") Long id) throws JsonProcessingException {
        HttpStatus status = this.service.payForCart(id);
        String price = this.service.getPriceOfCart(id).toString();
        if(this.getCartById(id).isPayed())
            return new ResponseEntity<String>(price, status);
        else
            return new ResponseEntity<String>(price, status);
    }
}