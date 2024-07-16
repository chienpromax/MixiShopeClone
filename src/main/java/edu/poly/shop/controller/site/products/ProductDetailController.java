package edu.poly.shop.controller.site.products;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
import edu.poly.shop.utils.CustomUserDetails;
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
    public String addToCart(@PathVariable("productId") Long productId, Model model, Authentication authentication) {
        // Kiểm tra đăng nhập
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/site/accounts/login";
        }
        Object principal = authentication.getPrincipal();
        String username = null;

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails loggedInUser = (CustomUserDetails) principal;
            username = loggedInUser.getUsername();
        } else if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            username = oAuth2User.getAttribute("name");
        }
        if (username == null) {
            return "redirect:/site/accounts/login";
        }
        // Lấy thông tin khách hàng
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            return "redirect:/site/carts/checkdetail";
        }
        // Kiểm tra thông tin khách hàng
        if (customer.getAddress() == null || customer.getPhone() == null) {
            return "redirect:/site/carts/checkdetail";
        }
        // Thêm sản phẩm vào giỏ hàng
        orderService.addProductToCart(customer.getCustomerId(), productId);

        // Chuyển hướng tới trang chi tiết sản phẩm
        return "redirect:/site/products/productdetail/" + productId;
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