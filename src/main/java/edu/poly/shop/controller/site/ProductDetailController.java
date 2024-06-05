package edu.poly.shop.controller.site;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    public List<Product> findRandomSimilarProducts(List<Product> similarProducts, int count) {
        // Sao chép danh sách sản phẩm để có thể trộn
        List<Product> copyOfSimilarProducts = new ArrayList<>(similarProducts);
        // Trộn danh sách sản phẩm
        Collections.shuffle(copyOfSimilarProducts);
        // Lấy ra số lượng sản phẩm ngẫu nhiên
        return copyOfSimilarProducts.subList(0, Math.min(copyOfSimilarProducts.size(), count));
    }

    // @GetMapping("/productdetail/{productid}")
    // public String viewProductDetail(
    //         @PathVariable("productid") Long productId,
    //         @RequestParam(value = "minPrice", required = false) Double minPrice,
    //         @RequestParam(value = "maxPrice", required = false) Double maxPrice,
    //         Model model) {
    //     Optional<Product> optionalProduct = productService.findById(productId);
    
    //     if (optionalProduct.isPresent()) {
    //         Product product = optionalProduct.get();
    //         Pageable pageable = PageRequest.of(0, 20);
    
    //         // Lọc sản phẩm theo phạm vi giá nếu minPrice và maxPrice được cung cấp
    //         if (minPrice != null && maxPrice != null) {
    //             pageable = PageRequest.of(0, 20, Sort.by("price"));
    //         }
    
    //         Page<Product> similarProductsPage = productService.findByCategoryid(product.getCategory().getCategoryid(), pageable);
    //         List<Product> similarProducts = similarProductsPage.getContent();
    
    //         List<Product> randomSimilarProducts = findRandomSimilarProducts(similarProducts, 4);
    
    //         model.addAttribute("product", product);
    //         model.addAttribute("similarProducts", randomSimilarProducts);
    //         model.addAttribute("displayedCount", randomSimilarProducts.size());
    //         model.addAttribute("totalCount", similarProductsPage.getTotalElements());
    //     } else {
    //         model.addAttribute("displayedCount", 0);
    //         model.addAttribute("totalCount", 0);
    //     }
    
    //     return "site/productdetail";
    // }
    

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

    List<Product> randomSimilarProducts =
    findRandomSimilarProducts(similarProducts, 4);

    model.addAttribute("product", product);
    model.addAttribute("similarProducts", randomSimilarProducts);
    } else {
        return "site/productdetail";
    }
    return "site/productdetail";
    }

}