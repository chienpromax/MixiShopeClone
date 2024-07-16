package edu.poly.shop.controller.site.carts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.model.Order;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.utils.CustomUserDetails;

@Controller
@RequestMapping("site/carts/ordercompleta")
public class OrderCompletaController {

    @Autowired
    private OrderService orderService;

    @RequestMapping
    public String showOrders(Model model) {
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

        if (username != null) {
            List<Order> orders = orderService.findByUsername(username);
            model.addAttribute("orders", orders);
        } else {
            return "redirect:/site/accounts/login";
        }
        return "site/carts/ordercompleta";
    }

    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam Integer orderId, @RequestParam int status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/site/carts/ordercompleta";
    }
}
