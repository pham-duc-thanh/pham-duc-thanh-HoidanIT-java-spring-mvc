package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public String handleHello() {
    return "Hello from service";
  }

  public List<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  public List<User> getAllUsersByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  public User handSaveUser(User user) {
    User eric = this.userRepository.save(user);
    System.out.println(eric);
    return eric;
  }

  public User getUserById(long id) {
    return this.userRepository.findById(id);
  }

  // public User update(Long id, User user) {
  // User fromDB = userRepository.findById(id).orElse(null);
  // if (fromDB == null) {
  // return null;
  // }
  // fromDB.setEmail(user.getEmail());
  // fromDB.setPhone(user.getPhone());
  // fromDB.setFullName(user.getFullName());
  // fromDB.setAddress(user.getAddress());

  // return userRepository.save(fromDB);
  // }
}
