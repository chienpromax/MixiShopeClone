package edu.poly.shop.controller.site.accounts;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import edu.poly.shop.domain.AccountDto;
import edu.poly.shop.model.Account;
import edu.poly.shop.service.AccountService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("site/accounts")
public class RegisterController {

    @Autowired
    AccountService accountService;

    @RequestMapping("register")
    public String home(Model model) {
        model.addAttribute("account", new AccountDto());
        return "site/accounts/register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute("account") AccountDto dto, @RequestParam("username") String username, Model model) {
        if (accountService.existsByUsername(username)) {
            model.addAttribute("message", "Username đã tồn tại");
            return "site/accounts/register";
        }
        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        accountService.save(entity);
        model.addAttribute("message", "Đăng ký thành công");
        // Reset form
        model.addAttribute("account", new AccountDto());
        return "site/accounts/register";
    }
}
