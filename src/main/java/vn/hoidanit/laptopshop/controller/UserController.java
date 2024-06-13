package vn.hoidanit.laptopshop.controller;

import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.laptopshop.service.UserService;
import vn.hoidanit.laptopshop.domain.User;

import java.util.List;

import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

  // Không nên tiêm phụ thuộc @Autwired
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;

  }

  @RequestMapping("/")
  public String getHomePage(Model model) {
    List<User> arrUsers = this.userService.getAllUsersByEmail("12taothichmi2009@gmail.com");
    System.out.println("arrUsers" + arrUsers);
    String test = this.userService.handleHello();
    model.addAttribute("eric", "test");
    model.addAttribute("hoidanit", "from controller with model");
    return "hello";
  }

  // @RequestMapping("/admin/user") // GET
  // public String getUserPage(Model model) {
  // String test = this.userService.handleHello();
  // model.addAttribute("newUser", new User());
  // return "admin/user/create";
  // }

  @RequestMapping("/admin/user")
  public String getUserPage(Model model) {
    List<User> users = this.userService.getAllUsers();
    System.out.println(">>> check users: " + users);
    model.addAttribute("users1", users);
    return "admin/user/table-user";
  }

  @RequestMapping("/admin/user/{id}")
  public String getUserDetailPage(Model model, @PathVariable long id) {
    System.out.println(">>> check path id = " + id);
    User user = this.userService.getUserById(id);
    model.addAttribute("user", user);
    model.addAttribute("id", id);
    return "admin/user/show";
  }

  @RequestMapping("/admin/user/create") // GET
  public String getCeateUserPage(Model model) {
    model.addAttribute("newUser", new User());
    return "admin/user/create";
  }

  @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
  public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
    System.out.println("run here " + hoidanit);
    this.userService.handSaveUser(hoidanit);
    return "redirect:/admin/user";
  }

  @RequestMapping("/admin/user/update/{id}") // GET
  public String getUpdateUserPage(Model model, @PathVariable long id) {
    User currentUser = this.userService.getUserById(id);
    model.addAttribute("newUser", currentUser);
    return "admin/user/update";
  }

  @PostMapping("/admin/user/update")
  public String postUpdateUser(Model model, @ModelAttribute("newUser") User hoidanit) {
    User currentUser = this.userService.getUserById(hoidanit.getId());
    if (currentUser != null) {
      System.out.println("run here 1");
      currentUser.setAddress(hoidanit.getAddress());
      currentUser.setFullName(hoidanit.getFullName());
      currentUser.setPhone(hoidanit.getPhone());

      this.userService.handSaveUser(currentUser);
    }
    return "redirect:/admin/user";
  }

  @GetMapping("/admin/user/delete/{id}")
  public String getDeleteUserPage(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    User user = new User();
    user.setId(id);
    model.addAttribute("newUser", user);
    return "/admin/user/delete";
  }

}
