package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {

  private final ProductService productService;
  private final UploadService uploadService;

  public ProductController(ProductService productService, UploadService uploadService) {
    this.productService = productService;
    this.uploadService = uploadService;
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
    Product product = this.productService.getProductById(id).get();
    model.addAttribute("product", product);
    model.addAttribute("id", id);
    return "admin/product/detail";
  }

  @GetMapping("/admin/product/create") // GET
  public String getCreateProductPage(Model model) {
    model.addAttribute("newProduct", new Product());
    return "admin/product/create";
  }

  @PostMapping("/admin/product/create")
  public String createProductPage(Model model, @ModelAttribute("newProduct") @Valid Product product,
      BindingResult newProductBindingResult,
      @RequestParam("hoidanitFile") MultipartFile file) {

    // validate
    List<FieldError> errors = newProductBindingResult.getFieldErrors();
    for (FieldError error : errors) {
      System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
    }

    // Nếu có lỗi thì trả về trang Create
    if (newProductBindingResult.hasErrors()) {
      return "/admin/product/create";
    }

    String image = this.uploadService.handleSaveUploadFile(file, "product");
    product.setImage(image);
    System.out.println("run here " + product);
    this.productService.handSaveProduct(product);
    return "redirect:/admin/product";
  }

  @RequestMapping("/admin/product/update/{id}") // GET
  public String getUpdateProductPage(Model model, @PathVariable long id) {
    Optional<Product> currentProduct = this.productService.getProductById(id);
    model.addAttribute("newProduct", currentProduct.get());
    return "admin/product/update";
  }

  @PostMapping("/admin/product/update")
  public String postUpdateUser(Model model, @ModelAttribute("newProduct") @Valid Product product,
      BindingResult newProductBindingResult,
      @RequestParam("hoidanitFile") MultipartFile file) {

    // validate
    if (newProductBindingResult.hasErrors()) {
      return "admin/product/update";
    }

    Product currentProduct = this.productService.getProductById(product.getId()).get();
    if (currentProduct != null) {
      // update new image
      if (!file.isEmpty()) {
        String img = this.uploadService.handleSaveUploadFile(file, "product");
        currentProduct.setImage(img);
      }

      System.out.println("run here 1");
      currentProduct.setName(product.getName());
      currentProduct.setPrice(product.getPrice());
      currentProduct.setDetailDesc(product.getDetailDesc());
      currentProduct.setShortDesc(product.getShortDesc());
      currentProduct.setFactory(product.getFactory());
      currentProduct.setTarget(product.getTarget());

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
