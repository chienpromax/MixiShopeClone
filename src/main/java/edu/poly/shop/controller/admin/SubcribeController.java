package edu.poly.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.service.EmailService;

@Controller
@RequestMapping("site/accounts")
public class SubcribeController {

    @Autowired
    private EmailService emailService;

    @PostMapping("subscribe")
    public String subscribe(@RequestParam("email") String email, Model model) {
        String to = "xuanchient@gmail.com"; 
        String subject = "Người theo dõi";
        String text = "Có người mới theo giõi bạn với email: " + email;
        emailService.sendEmail(to, subject, text);
        return "redirect:/site/home";
    }
}
