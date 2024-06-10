package edu.poly.shop.controller.site;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.service.CartService;
import edu.poly.shop.service.OrderDetailService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("site/carts/")
public class CartDetailController {

    @Autowired
    CartService cartService;

    @Autowired
    OrderDetailService orderDetailService;
    
     @RequestMapping("cartdetail")
    public String home(Model model, HttpServletRequest request) {
        cartService.populateCartDetails(model, request);
        return "site/carts/cartdetail";
    }

    @GetMapping("increase/{id}")
    public String increaseQuantity(@PathVariable("id") Integer id) {
        orderDetailService.increaseQuantity(id);
        return "redirect:/site/carts/cartdetail";
    }

    @GetMapping("decrease/{id}")
    public String decreaseQuantity(@PathVariable("id") Integer id) {
        orderDetailService.decreaseQuantity(id);
        return "redirect:/site/carts/cartdetail";
    }

    @GetMapping("remove/{id}")
    public String removeProduct(@PathVariable("id") Integer id) {
        orderDetailService.removeProduct(id);
        return "redirect:/site/carts/cartdetail";
    }
}

