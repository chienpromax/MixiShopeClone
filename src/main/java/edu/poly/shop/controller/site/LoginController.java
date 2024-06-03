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
import edu.poly.shop.utils.CookieUtils;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("site/accounts")
public class LoginController {

    @Autowired
    AccountService accountService;

    @RequestMapping("login")
    public String home(Model model, HttpServletRequest request) {
        String loggerdInUser = (String) SessionUtils.getAttribute(request, "loggedInUser");
        if (loggerdInUser != null) {
            AccountDto accountdto = new AccountDto();
            accountdto.setUsername(loggerdInUser);
            accountdto.setPassword(loggerdInUser);
            model.addAttribute("user", accountdto);
        }else{
            model.addAttribute("user", new AccountDto());
        }
        return "site/accounts/login";
    }

    @PostMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<Account> accountdto = accountService.findById(username);
        if (accountdto.isPresent() && accountdto.get().getPassword().equals(password)) {
            // Save to session
            SessionUtils.setAttribute(request, "loggedInUser", username);
            // Save to cookie
            CookieUtils.addCookie(response, "loggedInUser", username, 24 * 60 * 60);

            return "redirect:/site/home";
        } else {
            model.addAttribute("message", "username or password failed");
            model.addAttribute("user", new AccountDto());
            return "site/accounts/login";
        }
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate session
        SessionUtils.invalidateSession(request);
        // Remove cookies if needed
        CookieUtils.deleteCookie(request, response, "loggedInUser");
        return "redirect:/site/accounts/login";
    }
}
