package edu.poly.shop.controller.site.accounts;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.domain.AccountDto;
import edu.poly.shop.model.Account;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.EmailService;

@Controller
@RequestMapping("site/accounts")
public class ForgotPasswordController {

    @Autowired
    AccountService accountService;

    @Autowired
    EmailService emailService;

    @GetMapping("forgotpassword")
    public String home(Model model) {
        model.addAttribute("account", new AccountDto());
        return "site/accounts/forgotpassword";
    }

    @PostMapping("forgotpassword")
    public String quenMatKhau(@ModelAttribute("account") AccountDto dto, Model model) {
        Optional<Account> account = accountService.findById(dto.getUsername());
        if (account.isPresent() && account.get().getEmail().equals(dto.getEmail())) {
            String pass = account.get().getPassword();
            emailService.sendEmail(dto.getEmail(), "Your Password", "Your password is: " + pass);
            model.addAttribute("message", "mật khẩu được gửi về email.");
        } else {
            model.addAttribute("message", "sai username or email.");
        }
        model.addAttribute("account", new AccountDto());
        return "site/accounts/forgotpassword";
    }
}
