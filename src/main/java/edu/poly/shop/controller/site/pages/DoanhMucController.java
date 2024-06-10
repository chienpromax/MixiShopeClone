package edu.poly.shop.controller.site.pages;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

@Controller
@RequestMapping("site/page")
public class DoanhMucController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/doanhmuc/{categoryid}")
    public String viewCategoryProducts(@PathVariable("categoryid") Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder,
            @RequestParam(value = "searchQuery", required = false) String searchQuery,
            Model model) {
        int pageSize = 6;

        Optional<Category> categoryOptional = categoryService.findById(categoryId);
        String categoryName = categoryOptional.map(Category::getName).orElse(null);

        Sort sort = Sort.by("price").ascending();
        if ("lowToHigh".equals(sortOrder)) {
            sort = Sort.by("price").ascending();
        } else if ("highToLow".equals(sortOrder)) {
            sort = Sort.by("price").descending();
        }
        model.addAttribute("sortOrder", sortOrder);

        return viewProducts(categoryId, page, minPrice, maxPrice, searchQuery, sort, model, categoryName);
    }

    private String viewProducts(Long categoryId, int page, Double minPrice, Double maxPrice, String searchQuery,
            Sort sort, Model model, String categoryName) {
        int pageSize = 6;
        Page<Product> productPage;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            productPage = productService.findByCategoryidAndNameContaining(categoryId, searchQuery,
                    PageRequest.of(page, pageSize, sort));
        } else {
            if (minPrice != null && maxPrice != null) {
                productPage = productService.findByCategoryidAndPriceBetween(categoryId, minPrice, maxPrice,
                        PageRequest.of(page, pageSize, sort));
            } else {
                productPage = productService.findByCategoryid(categoryId, PageRequest.of(page, pageSize, sort));
            }
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categoryid", categoryId);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("totalProducts", productPage.getTotalElements());

        return "site/page/doanhmuc";
    }
}