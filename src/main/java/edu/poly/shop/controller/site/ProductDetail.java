package edu.poly.shop.controller.site;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.model.Product;
import edu.poly.shop.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("site")
public class ProductDetail {

    @Autowired
    ProductService productService;
    
    @RequestMapping("productdetail")
    public String requestMethodName() {
        return "site/productdetail";
    }

    @GetMapping("productdetail/{productid}")
    public String getproductdetail(Model model, @PathVariable("productid") Long productid) {
        Optional<Product> product = productService.findById(productid);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "site/productdetail";
        }
        return "site/productdetail";
    }
    
}
