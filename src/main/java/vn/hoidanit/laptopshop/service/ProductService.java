package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> getAllProducts() {
    return this.productRepository.findAll();
  }

  public List<Product> getAllProductsByName(String name) {
    return this.productRepository.findByName(name);
  }

  public Product handSaveProduct(Product product) {
    // Product eric = this.productRepository.save(product);
    // System.out.println(eric);
    // return eric;
    return this.productRepository.save(product);
  }

  public Optional<Product> getProductById(long id) {
    return this.productRepository.findById(id);
  }

  public void deleteProduct(long id) {
    this.productRepository.deleteById(id);
  }
}
