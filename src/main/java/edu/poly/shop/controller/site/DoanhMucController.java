package edu.poly.shop.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.service.ProductService;
import edu.poly.shop.model.Category;
import edu.poly.shop.model.Product;
import edu.poly.shop.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("site")
public class DoanhMucController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/doanhmuc/{categoryid}")
    public String viewCategoryProducts(@PathVariable("categoryid") Long categoryId, 
                                       @RequestParam(value = "page", defaultValue = "0") int page, 
                                       Model model) {
        int pageSize = 6; // Số sản phẩm hiển thị trên mỗi trang
        Page<Product> productPage = productService.findByCategoryid(categoryId, PageRequest.of(page, pageSize));
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categoryid", categoryId);
        model.addAttribute("isAllProducts", false); // Thêm thuộc tính này để xác định trang hiện tại là của danh mục
        return "site/doanhmuc";
    }

    @GetMapping("/allproducts")
    public String viewAllProducts(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        int pageSize = 6;
        Page<Product> productPage = productService.findAll(PageRequest.of(page, pageSize));
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("isAllProducts", true); // Thêm thuộc tính này để xác định trang hiện tại là tất cả sản phẩm
        return "site/doanhmuc";
    }
}
