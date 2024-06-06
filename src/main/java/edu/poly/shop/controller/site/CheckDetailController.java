package edu.poly.shop.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.model.Account;
import edu.poly.shop.model.Customer;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("site/carts/")
public class CheckDetailController {

    @Autowired
    CustomerService customerService;

    @GetMapping("checkdetail")
    public String showCheckDetail(Model model, HttpServletRequest request) {
        // Lấy thông tin người dùng đã đăng nhập
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        if (loggedInUser != null) {
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
}
