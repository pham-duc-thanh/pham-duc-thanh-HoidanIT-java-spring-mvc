package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class HomePageController {

  private final ProductService productService;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
    this.productService = productService;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/")
  public String getHomePage(Model model) {
    List<Product> products = this.productService.getAllProducts();
    model.addAttribute("products1", products);
    return "client/homepage/show";
  }

  @GetMapping("/register")
  public String getRegisterPage(Model model) {
    model.addAttribute("registerUser", new RegisterDTO());
    return "client/auth/register";
  }

  @PostMapping("/register")
  public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
      BindingResult bindingResult) {

    // validate
    List<FieldError> errors = bindingResult.getFieldErrors();
    for (FieldError error : errors) {
      System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
    }

    User user = this.userService.registerDTOtoUser(registerDTO);

    String hashPassword = this.passwordEncoder.encode(user.getPassword());

    user.setPassword(hashPassword);
    user.setRole(this.userService.getRoleByName("USER"));

    // save
    System.out.println("run here " + user);
    this.userService.handSaveUser(user);
    return "redirect:/login";

  }

  @GetMapping("/login")
  public String getLoginPage(Model model) {

    return "client/auth/login";
  }

}
