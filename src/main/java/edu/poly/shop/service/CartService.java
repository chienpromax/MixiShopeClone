package edu.poly.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import edu.poly.shop.domain.OrderDetailDto;
import edu.poly.shop.model.Account;
import edu.poly.shop.utils.CustomUserDetails;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;


@Service
public class CartService {

    @Autowired
    OrderDetailService orderDetailService;

    public void populateCartDetails(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails loggedInUser = (CustomUserDetails) principal;
            String username = loggedInUser.getUsername();

            // Lấy chi tiết giỏ hàng theo username
            List<OrderDetailDto> orderDetails = orderDetailService.findAllOrderDetailsWithProductsByUsername(username);
            model.addAttribute("orderdetails", orderDetails);

            // Tính tổng tiền
            double totalRevenue = orderDetails.stream()
                    .mapToDouble(orderDetail -> orderDetail.getQuantity() * orderDetail.getProductPrice())
                    .sum();
            model.addAttribute("totalRevenue", totalRevenue);
        } else if (principal instanceof DefaultOAuth2User) {
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) principal;
            String username = oAuth2User.getAttribute("name"); // Sử dụng thuộc tính 'name'

            // Lấy chi tiết giỏ hàng theo username
            List<OrderDetailDto> orderDetails = orderDetailService.findAllOrderDetailsWithProductsByUsername(username);
            model.addAttribute("orderdetails", orderDetails);

            // Tính tổng tiền
            double totalRevenue = orderDetails.stream()
                    .mapToDouble(orderDetail -> orderDetail.getQuantity() * orderDetail.getProductPrice())
                    .sum();
            model.addAttribute("totalRevenue", totalRevenue);
        } else {
            // System.err.println("Principal không phải là CustomUserDetails hoặc DefaultOAuth2User: " + principal.getClass().getName());
        }
    }
}




// @Service
// public class CartService {

//     @Autowired
//     OrderDetailService orderDetailService;

//     public void populateCartDetails(Model model, HttpServletRequest request) {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         Object principal = authentication.getPrincipal();

//         String username;
//         if (principal instanceof CustomUserDetails) {
//             CustomUserDetails loggedInUser = (CustomUserDetails) principal;
//             username = loggedInUser.getUsername();
//         } else if (principal instanceof String) {
//             username = (String) principal;
//         } else {
//             throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
//         }

//         // Lấy chi tiết giỏ hàng theo username
//         List<OrderDetailDto> orderDetails = orderDetailService.findAllOrderDetailsWithProductsByUsername(username);
//         model.addAttribute("orderdetails", orderDetails);

//         // Tính tổng tiền
//         double totalRevenue = orderDetails.stream()
//                 .mapToDouble(orderDetail -> orderDetail.getQuantity() * orderDetail.getProductPrice())
//                 .sum();
//         model.addAttribute("totalRevenue", totalRevenue);
//     }
// }
