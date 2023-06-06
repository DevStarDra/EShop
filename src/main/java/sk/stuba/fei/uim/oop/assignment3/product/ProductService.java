package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.Cart;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }

    @Override
    public void deleteAll(Long id) {
        this.repository.deleteAllById(id);
    }

    @Override
    public Product save(Product p) {
        return this.repository.save(p);
    }

    @Override
    public Product AmountById(Product product) {
        Product newProduct = new Product();
        newProduct.setAmount(product.getAmount());
        return newProduct;
    }

    @Override
    public Product updateAmount(Product product, ProductRequest request) {
        product.setAmount(product.getAmount() + request.getAmount());
        return this.repository.save(product);
    }


    @Override
    public Product getById(Long id) {
        return this.repository.findById(id).orElseThrow(NotFoundException::new);
    }


    @Override
    public Product createProduct(ProductRequest request) {
        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setDescription(request.getDescription());
        newProduct.setAmount(request.getAmount());
        newProduct.setUnit(request.getUnit());
        newProduct.setPrice(request.getPrice());
        return this.repository.save(newProduct);
    }

    public Product changeProduct(ProductRequest request, Product product) {
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        return this.repository.save(product);
    }
}


