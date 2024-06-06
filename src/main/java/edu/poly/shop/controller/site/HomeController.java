package edu.poly.shop.controller.site;

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
@RequestMapping("site")
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
        
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        return "site/home";
    }
}


// @Controller
// @RequestMapping("site")
// public class HomeController {

//     @Autowired
//     private ProductService productService;

//     @Autowired
//     CategoryService categoryService;

//     @RequestMapping("home")
//     public String home(Model model, HttpServletRequest request) {
//         List<Product> product = productService.findAll();
//         if (product.size() > 12) {
//             product = product.subList(0, 12);
//         }
//         model.addAttribute("products", product);
//         String loggedInUser = (String) SessionUtils.getAttribute(request, "loggedInUser");
//         model.addAttribute("loggedInUser", loggedInUser);

//         return "site/home";
//     }
// }
