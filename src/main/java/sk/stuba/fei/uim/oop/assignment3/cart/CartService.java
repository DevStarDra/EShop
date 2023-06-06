package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.payment.Payment;
import sk.stuba.fei.uim.oop.assignment3.payment.PaymentRepository;
import sk.stuba.fei.uim.oop.assignment3.product.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {
    private final CartRepository repository;

    @Autowired
    private IProductService productService;

    @Autowired
    private  PaymentRepository paymentRepository;


    @Autowired
    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cart> getAll() {
        return this.repository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public Cart getById(Long id) {
        return this.repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Cart createCart() {
        return this.repository.save(new Cart());
    }

    public Cart changeCart(CartRequest request, Cart cart) {
        cart.setPayed(request.isPayed());
        return this.repository.save(cart);
    }

    @Override
    public ResponseEntity addProductToCart(long productId, long cartId, long amount) {
        Optional<Cart> cartOpt = this.repository.findById(cartId);
        Cart cart = cartOpt.orElseThrow(NotFoundException::new);

        Product product = this.productService.getById(productId);

        if(cart.isPayed() || amount > product.getAmount())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else
        {
            product.setAmount(product.getAmount() - amount);
            this.productService.save(product);}

        if(isProductInCart(productId, cart)) {
            for(Payment p:cart.getShoppingList()){
                if(productId == p.getProduct().getId()) {
                    p.getProduct().setAmount(p.getProduct().getAmount() + amount);
                    this.paymentRepository.save(p);
                }
            }
        } else {
            Payment p = new Payment(product, amount);
            p = this.paymentRepository.save(p);
            cart.getShoppingList().add(p);
        }

        this.productService.save(product);
        Cart savedCart = this.repository.save(cart);

        List<CartEntryDto> collect = savedCart.getShoppingList().stream().map(p -> {
            CartEntryDto dto = new CartEntryDto();
            dto.setProductId(p.getProduct().getId());
            dto.setAmount(p.getAmount());
            return dto;
        }).collect(Collectors.toList());

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", savedCart.getId());
        map.put("payed", savedCart.isPayed());
        map.put("shoppingList", collect);
        return ResponseEntity.ok(map);
    }

    @Override
    public HttpStatus payForCart(Long id){
        Cart cart = this.repository.findById(id).orElseThrow(NotFoundException::new);
        if(!cart.isPayed()) {
            cart.setPayed(true);
            this.repository.save(cart);
            return HttpStatus.OK;
        }
        else
            return HttpStatus.BAD_REQUEST;
    }

    @Override
    public Long getPriceOfCart(Long id){
        Cart cart = this.repository.findById(id).orElseThrow(NotFoundException::new);
        Long price = 0L;
        for(Payment p : cart.getShoppingList()){
            price += p.getAmount()*this.productService.getById(p.getProduct().getId()).getPrice();
        }
        return price;
    }

    public boolean isProductInCart(long productId, Cart cart){
        for(Payment p: cart.getShoppingList())
            if(p.getProduct().getId() == productId)
                return true;
        return false;
    }
}


