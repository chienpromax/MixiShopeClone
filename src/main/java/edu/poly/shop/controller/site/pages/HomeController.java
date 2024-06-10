package edu.poly.shop.controller.site.pages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.model.Account;
import edu.poly.shop.model.Product;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("site/page")
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("home")
    public String home(Model model, HttpServletRequest request) {
        List<Product> products = productService.findAll();
        if (products.size() > 12) {
            products = products.subList(0, 12);
        }
        model.addAttribute("products", products);
        
        // Kiểm tra xem loggedInUser có phải là một đối tượng Account hay không
        Object loggedInUserObj = SessionUtils.getAttribute(request, "loggedInUser");
        if (loggedInUserObj instanceof Account) {
            Account loggedInUser = (Account) loggedInUserObj;
            model.addAttribute("loggedInUser", loggedInUser);
        }

        return "site/page/home";
    }
}