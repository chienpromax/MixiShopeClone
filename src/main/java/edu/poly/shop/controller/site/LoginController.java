package edu.poly.shop.controller.site;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.domain.AccountDto;
import edu.poly.shop.model.Account;
import edu.poly.shop.service.AccountService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("site")
public class LoginController {

    @Autowired
    AccountService accountService;

    @RequestMapping("login")
    public String home(Model model) {
        model.addAttribute("user", new AccountDto());
        return "site/login";
    }

    @PostMapping("login")
    public String login(Model model, @RequestParam("username") String username,
            @RequestParam("password") String password) {
        Optional<Account> accountdto = accountService.findById(username);
        if (accountdto.isPresent() && accountdto.get().getPassword().equals(password)) {
            return "redirect:/site/home";
        } else {
            model.addAttribute("message", "username or password faild");
            model.addAttribute("user", new AccountDto());
            return "site/login";
        }
    }
}
