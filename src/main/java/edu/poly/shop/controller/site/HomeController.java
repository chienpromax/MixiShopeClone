package edu.poly.shop.controller.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.model.Product;
import edu.poly.shop.service.ProductService;

@Controller
@RequestMapping("site")
public class HomeController {
    
    @Autowired
    private ProductService productService; 

    @RequestMapping("home")
    public String home(Model model) {
        List<Product> product = productService.findAll();
        if (product.size() > 12) {
            product = product.subList(0, 12);
        }
        model.addAttribute("products", product);
        return "site/home";
    }
    
}
