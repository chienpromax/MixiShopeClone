
package edu.poly.shop.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.service.ProductService;
import edu.poly.shop.model.Product;

@Controller
@RequestMapping("site")
public class AllProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/allproducts")
    public String viewAllProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "minPrice", required = false) Double minPrice,
                                  @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                  @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder,
                                  @RequestParam(value = "searchQuery", required = false) String searchQuery,
                                  Model model) {
        int pageSize = 6; // Số sản phẩm hiển thị trên mỗi trang
        Sort sort = getSortOrder(sortOrder);
        Page<Product> productPage;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            productPage = productService.findByNameContaining(searchQuery, PageRequest.of(page, pageSize, sort));
        } else if (minPrice != null && maxPrice != null) {
            productPage = productService.findByPriceBetween(minPrice, maxPrice, PageRequest.of(page, pageSize, sort));
        } else {
            productPage = productService.findAll(PageRequest.of(page, pageSize, sort));
        }

        long totalProducts = productPage.getTotalElements(); // Lấy tổng số sản phẩm
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("searchQuery", searchQuery); // Thêm searchQuery vào model
        model.addAttribute("minPrice", minPrice); // Thêm minPrice vào model
        model.addAttribute("maxPrice", maxPrice); // Thêm maxPrice vào model
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalProducts", totalProducts); // Thêm totalProducts vào model

        return "site/allproducts"; // Trả về trang allproducts.html
    }

    private Sort getSortOrder(String sortOrder) {
        if ("lowToHigh".equals(sortOrder)) {
            return Sort.by("price").ascending();
        } else if ("highToLow".equals(sortOrder)) {
            return Sort.by("price").descending();
        }
        return Sort.unsorted();
    }
}



// @Controller
// @RequestMapping("site")
// public class AllProductsController {

//     @Autowired
//     private ProductService productService;

//     @GetMapping("/allproducts")
//     public String viewAllProducts(@RequestParam(value = "page", defaultValue = "0") int page,
//                                   @RequestParam(value = "minPrice", required = false) Double minPrice,
//                                   @RequestParam(value = "maxPrice", required = false) Double maxPrice,
//                                   @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder,
//                                   Model model) {
//         int pageSize = 6; // Số sản phẩm hiển thị trên mỗi trang
//         Sort sort = getSortOrder(sortOrder);
//         Page<Product> productPage;

//         if (minPrice != null && maxPrice != null) {
//             productPage = productService.findByPriceBetween(minPrice, maxPrice, PageRequest.of(page, pageSize, sort));
//         } else {
//             productPage = productService.findAll(PageRequest.of(page, pageSize, sort));
//         }

//         long totalProducts = productPage.getTotalElements(); // Lấy tổng số sản phẩm
//         model.addAttribute("sortOrder", sortOrder);

//         model.addAttribute("products", productPage.getContent());
//         model.addAttribute("currentPage", page);
//         model.addAttribute("totalPages", productPage.getTotalPages());
//         model.addAttribute("totalProducts", totalProducts); // Thêm totalProducts vào model

//         return "site/allproducts"; // Trả về trang allproducts.html
//     }

//     private Sort getSortOrder(String sortOrder) {
//         if ("lowToHigh".equals(sortOrder)) {
//             return Sort.by("price").ascending();
//         } else if ("highToLow".equals(sortOrder)) {
//             return Sort.by("price").descending();
//         }
//         return Sort.unsorted();
//     }
// }
