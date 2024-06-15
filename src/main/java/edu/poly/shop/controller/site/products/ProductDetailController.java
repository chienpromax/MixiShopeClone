package edu.poly.shop.controller.site.products;

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
import edu.poly.shop.model.Customer;
import edu.poly.shop.model.Product;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("site/products")
public class ProductDetailController {

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;;

    @RequestMapping("productdetail")
    public String requestMethodName() {
        return "site/products/productdetail";
    }
    
    @PostMapping("addtocart/{productId}")
    public String addToCart(@PathVariable("productId") Long productId, HttpServletRequest request, Model model) {
        // Kiểm tra đăng nhập
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/site/accounts/login";
        }
        // Lấy thông tin khách hàng
        String username = loggedInUser.getUsername();
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            return "redirect:/site/carts/checkdetail";
        }
        // Thêm sản phẩm vào giỏ hàng
        orderService.addProductToCart(customer.getCustomerId(), productId);

        return "redirect:/site/carts/cartdetail"; // Chuyển hướng tới trang giỏ hàng
        // return "site/products/productdetail";
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
}