package edu.poly.shop.controller.site.carts;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.model.Customer;
import edu.poly.shop.model.Order;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.utils.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("site/carts/")
public class CheckDetailController {

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @GetMapping("checkdetail")
    public String showCheckDetail(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        String username = null;

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails loggedInUser = (CustomUserDetails) principal;
            username = loggedInUser.getUsername();
        } else if (principal instanceof DefaultOAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            username = oAuth2User.getAttribute("name");
        }

        if (username == null) {
            return "redirect:/site/accounts/login";
        } else {
            // Lấy thông tin khách hàng đã đăng ký nếu có
            Customer customer = customerService.findByUsername(username);
            if (customer == null) {
                customer = new Customer();
                customer.setUsername(username);
                customerService.save(customer);  // Lưu thông tin khách hàng nếu chưa có trong hệ thống
            }
            model.addAttribute("customer", customer);
        }
        return "site/carts/checkdetail";
    }

    @PostMapping("saveCustomerInfo")
    public String saveCustomerInfo(@ModelAttribute Customer customer) {
        // Lấy thông tin người dùng đã đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        String username = null;

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails loggedInUser = (CustomUserDetails) principal;
            username = loggedInUser.getUsername();
        } else if (principal instanceof DefaultOAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            username = oAuth2User.getAttribute("name");
        }

        if (username == null) {
            return "redirect:/site/accounts/login";
        }

        customer.setUsername(username);
        // Lưu thông tin khách hàng
        customerService.save(customer);

        return "redirect:/site/carts/checkdetail";
    }

    @PostMapping("placeorder")
    public String placeOrder(@RequestParam("customerId") Integer customerId) {
        Order pendingOrder = orderService.findPendingOrderByCustomerId(customerId);
        if (pendingOrder != null) {
            pendingOrder.setStatus(1); // Cập nhật trạng thái đơn hàng thành 1
            orderService.save(pendingOrder);
            return "redirect:/site/carts/ordercompleta";
        }
        return "redirect:/site/carts/checkdetail";
    }
}



// package edu.poly.shop.controller.site.carts;

// import java.io.IOException;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import edu.poly.shop.model.Account;
// import edu.poly.shop.model.Customer;
// import edu.poly.shop.model.Order;
// import edu.poly.shop.service.CustomerService;
// import edu.poly.shop.service.OrderService;
// import edu.poly.shop.utils.CustomUserDetails;
// import edu.poly.shop.utils.SessionUtils;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Controller
// @RequestMapping("site/carts/")
// public class CheckDetailController {

//     @Autowired
//     CustomerService customerService;

//     @Autowired
//     OrderService orderService;

//     @GetMapping("checkdetail")
//     public String showCheckDetail(Model model, HttpServletRequest request, HttpServletResponse response)
//             throws IOException {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         CustomUserDetails loggedInUser = (CustomUserDetails) authentication.getPrincipal();

//         if (loggedInUser == null) {
//             return "redirect:/site/accounts/login";
//         } else {
//             // Lấy thông tin khách hàng đã đăng ký nếu có
//             Customer customer = customerService.findByUsername(loggedInUser.getUsername());
//             if (customer == null) {
//                 customer = new Customer();
//             }
//             model.addAttribute("customer", customer);
//         }
//         return "site/carts/checkdetail";
//     }

//     @PostMapping("saveCustomerInfo")
//     public String saveCustomerInfo(@ModelAttribute Customer customer) {
//         // Lấy thông tin người dùng đã đăng nhập
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         CustomUserDetails loggedInUser = (CustomUserDetails) authentication.getPrincipal();
//         if (loggedInUser == null) {
//             return "redirect:/site/accounts/login";
//         }

//         customer.setUsername(loggedInUser.getUsername());
//         // Lưu thông tin khách hàng
//         customerService.save(customer);

//         return "redirect:/site/carts/checkdetail";
//     }

//     @PostMapping("placeorder")
//     public String placeOrder(@RequestParam("customerId") Integer customerId) {
//         Order pendingOrder = orderService.findPendingOrderByCustomerId(customerId);
//         if (pendingOrder != null) {
//             pendingOrder.setStatus(1); // Cập nhật trạng thái đơn hàng thành 1
//             orderService.save(pendingOrder);
//             return "redirect:/site/carts/ordercompleta";
//         }
//         return "redirect:/site/carts/checkdetail";
//     }
// }
