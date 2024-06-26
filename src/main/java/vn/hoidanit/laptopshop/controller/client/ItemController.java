package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

  private final ProductService productService;

  public ItemController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/product/{id}")
  public String getProductPage(Model model, @PathVariable long id) {
    System.out.println(">>> check path id = " + id);
    Product product = this.productService.getProductById(id).get();
    model.addAttribute("product", product);
    model.addAttribute("id", id);
    return "client/product/detail";
  }

}
