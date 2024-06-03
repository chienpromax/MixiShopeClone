package edu.poly.shop.controller.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.model.Category;
import edu.poly.shop.model.Product;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("site")
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("home")
    public String home(Model model, HttpServletRequest request) {
        List<Product> product = productService.findAll();
        if (product.size() > 12) {
            product = product.subList(0, 12);
        }
        model.addAttribute("products", product);

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        String loggedInUser = (String) SessionUtils.getAttribute(request, "loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        return "site/home";
    }

    @RequestMapping("category/{categoryid}")
    public String viewCategoryProducts(@PathVariable("categoryid") Long categoryid, Model model) {
        List<Product> products = productService.findByCategoryId(categoryid);
        model.addAttribute("products", products);

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return"site/doanhmuc";
    }
    
}
