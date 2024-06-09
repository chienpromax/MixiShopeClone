package edu.poly.shop.controller.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("add")
    public String addToCart(@RequestParam("orderDetailId") Long orderDetailId) {
        // Thực hiện logic để tăng số lượng sản phẩm trong giỏ hàng dựa trên orderDetailId
        orderDetailService.increaseQuantity(orderDetailId);
        return "redirect:/site/carts/cartdetail";
    }

    @PostMapping("remove")
    public String removeFromCart(@RequestParam("orderDetailId") Long orderDetailId) {
        // Thực hiện logic để giảm số lượng sản phẩm trong giỏ hàng hoặc xóa sản phẩm nếu số lượng là 1
        orderDetailService.decreaseOrRemove(orderDetailId);
        return "redirect:/site/carts/cartdetail";
    }

    @RequestMapping("cartdetail")
    public String home(Model model, HttpServletRequest request) {
        cartService.populateCartDetails(model, request);
        return "site/carts/cartdetail";
    }
}


// // In thông tin ra console
// Account loggedInUser = (Account) SessionUtils.getAttribute(request,
// "loggedInUser");
// if (loggedInUser != null) {
// String username = loggedInUser.getUsername();
// System.out.println("Logged in user: " + username);

// List<OrderDetailDto> orderDetails = (List<OrderDetailDto>)
// model.getAttribute("orderdetails");
// if (orderDetails != null) {
// for (OrderDetailDto orderDetail : orderDetails) {
// System.out.println("Product: " + orderDetail.getProductName());
// System.out.println("Quantity: " + orderDetail.getQuantity());
// System.out.println("Unit Price: " + orderDetail.getUnitPrice());
// System.out.println("Total Price: " + orderDetail.getQuantity() *
// orderDetail.getUnitPrice());
// }
// double totalRevenue = (double) model.getAttribute("totalRevenue");
// System.out.println("Total Revenue: " + totalRevenue);
// } else {
// System.out.println("No order details found.");
// }
// } else {
// System.out.println("No user logged in.");
// }
