package edu.poly.shop.controller.site.carts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.model.Account;
import edu.poly.shop.model.Order;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("site/carts/ordercompleta")
public class OrderCompletaController {

    @Autowired
    private OrderService orderService;

    @RequestMapping
    public String showOrders(Model model, HttpServletRequest request) {
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        if (loggedInUser != null) {
            List<Order> orders = orderService.findByUsername(loggedInUser.getUsername());
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
