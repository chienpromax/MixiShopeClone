package edu.poly.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import edu.poly.shop.domain.OrderDetailDto;
import edu.poly.shop.model.Account;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CartService {

    @Autowired
    OrderDetailService orderDetailService;

    public void populateCartDetails(Model model, HttpServletRequest request) {
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        if (loggedInUser != null) {
            String username = loggedInUser.getUsername();
            List<OrderDetailDto> orderDetails = orderDetailService.findAllOrderDetailsWithProductsByUsername(username);
            model.addAttribute("orderdetails", orderDetails);
            double totalRevenue = orderDetails.stream()
                    .mapToDouble(orderDetail -> orderDetail.getQuantity() * orderDetail.getProductPrice())
                    .sum();
            model.addAttribute("totalRevenue", totalRevenue);
        }
    }
}
