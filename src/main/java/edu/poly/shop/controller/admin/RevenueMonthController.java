package edu.poly.shop.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.domain.ReportMonth;
import edu.poly.shop.repository.ReportMonthDAO;

@Controller
@RequestMapping("/admin/report")
public class RevenueMonthController {

    @Autowired
    private ReportMonthDAO reportMonthDAO;

    @GetMapping("/revenueMonth")  
    public String revenueByMonth(Model model) {
        List<ReportMonth> items = reportMonthDAO.getRevenueByMonth();
        model.addAttribute("items", items);
        return "admin/report/revenueMonth";
    }
}
