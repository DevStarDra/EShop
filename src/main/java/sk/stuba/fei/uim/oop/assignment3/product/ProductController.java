package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping()
    public List<ProductResponse> getAllProducts() {
        return this.service.getAll().stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request) {
        return new ResponseEntity<>(new ProductResponse(this.service.createProduct(request)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Product changeProduct (@PathVariable("id") Long id, @RequestBody ProductRequest request) {
        Product product = getProductById(id);
        new ProductResponse(this.service.changeProduct(request, product));
        return product;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        Product product = getProductById(id);
        if(product == null)
            throw new NotFoundException();
        service.deleteAll(id);
    }

    @GetMapping("/{id}/amount")
    public AmountById getAmount(@PathVariable("id") Long id){
        Product product = getProductById(id);
        return new AmountById(product);
    }

    @PostMapping("/{id}/amount")
    public AmountById postAmount(@PathVariable("id") Long id, @RequestBody ProductRequest request){
        Product product = getProductById(id);
        this.service.updateAmount(product, request);
        return getAmount(id);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        if (this.service.getById(id) == null)
            throw new NotFoundException();
        return this.service.getById(id);
    }
}