package edu.poly.shop.controller.site;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.model.Product;
import edu.poly.shop.service.ProductService;

@Controller
@RequestMapping("site")
public class ProductDetailController {

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

    // @GetMapping("/productdetail/{productid}")
    // public String viewProductDetail(@PathVariable("productid") Long productId, Model model) {
    //     Optional<Product> optionalProduct = productService.findById(productId);

    //     if (optionalProduct.isPresent()) {
    //         Product product = optionalProduct.get();
    //         List<Product> similarProducts = productService.findByCategoryid(product.getCategory().getCategoryid(), PageRequest.of(0, 4)).getContent();
            
    //         // Lấy ra số lượng sản phẩm tương tự ngẫu nhiên
    //         List<Product> randomSimilarProducts = findRandomSimilarProducts(similarProducts, 4);

    //         model.addAttribute("product", product);
    //         model.addAttribute("similarProducts", randomSimilarProducts);
    //     } else {
    //         // Xử lý khi không tìm thấy sản phẩm
    //         return "site/productdetail";
    //     }

    //     return "site/productdetail";
    // }
}
