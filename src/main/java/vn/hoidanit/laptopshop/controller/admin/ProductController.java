package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  // @GetMapping("/admin/product")
  // public String getDashboard() {
  // return "admin/product/show";
  // }

  @RequestMapping("/admin/product")
  public String getProductPage(Model model) {
    List<Product> products = this.productService.getAllProducts();
    System.out.println(">>> check products: " + products);
    model.addAttribute("products1", products);
    return "admin/product/show";
  }

  @RequestMapping("/admin/product/{id}")
  public String getProductDetailPage(Model model, @PathVariable long id) {
    System.out.println(">>> check path id = " + id);
    Product product = this.productService.getProductById(id);
    model.addAttribute("product", product);
    model.addAttribute("id", id);
    return "admin/product/detail";
  }

  @GetMapping("/admin/product/create") // GET
  public String getCreateProductPage(Model model) {
    model.addAttribute("newProduct", new Product());
    return "admin/product/create";
  }

  @RequestMapping(value = "/admin/product/create", method = RequestMethod.POST)
  public String createProductPage(Model model, @ModelAttribute("newProduct") Product hoidanit) {
    System.out.println("run here " + hoidanit);
    this.productService.handSaveProduct(hoidanit);
    return "redirect:/admin/product";
  }

  @RequestMapping("/admin/product/update/{id}") // GET
  public String getUpdateProductPage(Model model, @PathVariable long id) {
    Product currentProduct = this.productService.getProductById(id);
    model.addAttribute("newProduct", currentProduct);
    return "admin/product/update";
  }

  @PostMapping("/admin/product/update")
  public String postUpdateUser(Model model, @ModelAttribute("newProduct") Product hoidanit) {
    Product currentProduct = this.productService.getProductById(hoidanit.getId());
    if (currentProduct != null) {
      System.out.println("run here 1");
      currentProduct.setName(hoidanit.getName());
      currentProduct.setPrice(hoidanit.getPrice());
      currentProduct.setDetailDesc(hoidanit.getDetailDesc());
      currentProduct.setShortDesc(hoidanit.getShortDesc());
      this.productService.handSaveProduct(currentProduct);
    }
    return "redirect:/admin/product";
  }

  @GetMapping("/admin/product/delete/{id}")
  public String getDeleteUserPage(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    // User user = new User();
    // user.setId(id);
    model.addAttribute("newProduct", new Product());
    return "/admin/product/delete";
  }

  @PostMapping("/admin/product/delete")
  public String postDeleteUserPage(Model model, @ModelAttribute("newProduct") Product eric) {
    System.out.println(" run here ");
    this.productService.deleteProduct(eric.getId());
    return "redirect:/admin/product";
  }
}
