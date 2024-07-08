package edu.poly.shop.controller.site.VNPay;

import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.config.VNPayService;
import edu.poly.shop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@org.springframework.stereotype.Controller
public class VNPayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/site/VNPays/vnPayMain")
    public String showOrderInfo(@RequestParam("orderId") String orderId,
            @RequestParam("amount") double orderTotal,
            @RequestParam("orderInfo") String orderInfo,
            @RequestParam("orderDate") String orderDate,
            @RequestParam("status") int status,
            Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderTotal", orderTotal);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("orderDate", orderDate);
        model.addAttribute("status", status);
        return "site/VNPays/vnPayMain";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("orderId") String orderId,
            @RequestParam("amount") double orderTotal,
            @RequestParam("orderInfo") String orderInfo,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }


    @GetMapping("/vnpay-payment")
    public String getMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        // Định dạng thời gian thanh toán
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedPaymentTime = "";
        try {
            Date date = inputFormat.parse(paymentTime);
            formattedPaymentTime = outputFormat.format(date);
        } catch (ParseException e) {
            
        }

        // Chuyển đổi tổng tiền thành đơn vị VNĐ
        double totalPriceVND = Double.parseDouble(totalPrice) / 100;

        // Cập nhật trạng thái đơn hàng lên 4 nếu thanh toán thành công
        if (paymentStatus == 1) {
            String orderIdString = orderInfo.replaceAll("[^\\d]", ""); // Loại bỏ tất cả ký tự không phải số
            try {
                Integer orderId = Integer.parseInt(orderIdString);
                orderService.updateOrderStatus(orderId, 4);
            } catch (NumberFormatException e) {

            }
        }
        

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPriceVND);
        model.addAttribute("paymentTime", formattedPaymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "site/VNPays/ordersuccess" : "site/VNPays/orderfail";
    }

}
