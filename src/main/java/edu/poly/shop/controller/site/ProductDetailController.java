package edu.poly.shop.controller.site;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.model.Account;
import edu.poly.shop.model.Product;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("site/products")
public class ProductDetailController {

    @Autowired
    ProductService productService;

    @RequestMapping("productdetail")
    public String requestMethodName() {
        return "site/products/productdetail";
    }

    public List<Product> findRandomSimilarProducts(List<Product> similarProducts, int count) {
        // Sao chép danh sách sản phẩm để có thể trộn
        List<Product> copyOfSimilarProducts = new ArrayList<>(similarProducts);
        // Trộn danh sách sản phẩm
        Collections.shuffle(copyOfSimilarProducts);
        // Lấy ra số lượng sản phẩm ngẫu nhiên
        return copyOfSimilarProducts.subList(0, Math.min(copyOfSimilarProducts.size(), count));
    }

    @GetMapping("/productdetail/{productid}")
    public String viewProductDetail(@PathVariable("productid") Long productId,
            Model model) {
        Optional<Product> optionalProduct = productService.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            List<Product> similarProducts = productService
                    .findByCategoryid(product.getCategory().getCategoryid(), PageRequest.of(0,
                            20))
                    .getContent();

            List<Product> randomSimilarProducts = findRandomSimilarProducts(similarProducts, 4);

            model.addAttribute("product", product);
            model.addAttribute("similarProducts", randomSimilarProducts);
        } else {
            return "site/products/productdetail";
        }
        return "site/products/productdetail";
    }

    @GetMapping("/addtocart/{productid}")
    public String addToCart(@PathVariable("productid") Long productId, HttpServletRequest request) {
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/site/accounts/login";
        }
        return "redirect:/site/products/productdetail/" + productId; // Redirect back to product detail page
    }
    
}