package vn.hoidanit.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Product save(Product hoidanit);

  List<Product> findByName(String name);

  List<Product> findAll();

  Optional<Product> findById(long id);

  void deleteById(long id);
}
