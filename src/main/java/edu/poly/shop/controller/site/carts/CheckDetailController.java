package edu.poly.shop.controller.site.carts;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.model.Account;
import edu.poly.shop.model.Customer;
import edu.poly.shop.model.Order;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.utils.SessionUtils;
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
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/site/accounts/login";
        } else {
            // Lấy thông tin khách hàng đã đăng ký nếu có
            Customer customer = customerService.findByUsername(loggedInUser.getUsername());
            if (customer == null) {
                customer = new Customer();
            }
            model.addAttribute("customer", customer);
        }
        return "site/carts/checkdetail";
    }

    @PostMapping("saveCustomerInfo")
    public String saveCustomerInfo(@ModelAttribute Customer customer, HttpServletRequest request) {
        // Lấy thông tin người dùng đã đăng nhập
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        if (loggedInUser != null) {
            customer.setUsername(loggedInUser.getUsername());
            // Lưu thông tin khách hàng
            customerService.save(customer);
        }
        return "redirect:/site/carts/checkdetail";
    }

    @PostMapping("placeorder")
    public String placeOrder(@RequestParam("customerId") Integer customerId) {
        Order pendingOrder = orderService.findPendingOrderByCustomerId(customerId);
        if (pendingOrder != null) {
            pendingOrder.setStatus(1); // Cập nhật trạng thái đơn hàng thành 1
            orderService.save(pendingOrder);
        }
        return "redirect:/site/carts/checkdetail";
    }
}
