package edu.poly.shop.controller.site.carts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.model.Order;
import edu.poly.shop.service.OrderService;


@Controller
@RequestMapping("site/carts")
public class CartShoppingController {

    @Autowired
    public OrderService orderService;

    @RequestMapping("cartshopping")
    public String cart(Model model) {
        List<Order> order = orderService.findAll();
        model.addAttribute("orders", order);
        return "site/carts/cartshopping";
    }
    
}
    // trong nav controller
