package edu.poly.shop.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.domain.ReportProduct;
import edu.poly.shop.repository.ReportProductDAO;

@Controller
@RequestMapping("/admin/report")
public class RevenueProductController {

    @Autowired
    private ReportProductDAO reportProductDAO;

    @GetMapping("/bestSellingProducts")
    public String bestSellingProducts(Model model) {
        List<ReportProduct> items = reportProductDAO.getBestSellingProducts();
        model.addAttribute("items", items);
        return "admin/report/bestSellingProducts";
    }
}
