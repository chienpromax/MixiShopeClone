package edu.poly.shop.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.domain.Report;
import edu.poly.shop.repository.ReportDAO;

@Controller
@RequestMapping("/admin/report")
public class RevenueCategoryController {

    @Autowired
    private ReportDAO reportDAO;

    @GetMapping("/revenueCategory")
    public String revenueCategory(Model model) {
        List<Report> items = reportDAO.getRevenueCategory();
        model.addAttribute("items", items);
        return "admin/report/revenueCategory";
    }
}
