package edu.poly.shop.controller.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.poly.shop.model.Account;
import edu.poly.shop.model.Category;
import edu.poly.shop.model.OrderDetail;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class NavController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderDetailService orderDetailService;

    @ModelAttribute
    public void addLoggedInUserToModel(Model model, HttpServletRequest request) {
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
    }

    @ModelAttribute
    public String cart(Model model) {
        List<OrderDetail> list = orderDetailService.findAll();
        model.addAttribute("orderdetails", list);
        return "site/carts/cartshopping";
    }

    @ModelAttribute
    public String home(Model model, HttpServletRequest request) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "site/home";
    }

}
