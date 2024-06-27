package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User save(User hoidanit);

  List<User> findByEmail(String email);

  List<User> findAll();

  User findById(long id);

  void deleteById(long id);

  boolean existsByEmail(String email);

  // Lấy phần tử đầu tiên
  // User findFirstByEmail(String email);
  // User findTop1ByEmail(String email);

  // List<User> findByEmailAndAddress(String email, String address);

}
