package sk.stuba.fei.uim.oop.assignment3.product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();
    Product getById(Long id);
    Product createProduct(ProductRequest request);
    Product changeProduct(ProductRequest request,Product product);
    void deleteAll(Long id);
    Product save(Product p);
    Product AmountById(Product product);
    Product updateAmount(Product product,ProductRequest request);
}