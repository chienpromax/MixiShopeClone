package edu.poly.shop.controller.site.accounts;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.domain.AccountDto;
import edu.poly.shop.domain.OrderDetailDto;
import edu.poly.shop.model.Account;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.OrderDetailService;
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

    @Autowired
    OrderDetailService orderDetailService;

    @PostMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model,
                        @RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<Account> accountOptional = accountService.findById(username);
        if (accountOptional.isPresent() && accountOptional.get().getPassword().equals(password)) {
            Account account = accountOptional.get();
            // Clear previous session attribute if exists
            SessionUtils.removeAttribute(request, "loggedInUser");
            // Save to session
            SessionUtils.setAttribute(request, "loggedInUser", account);
            // Save to cookie
            CookieUtils.addCookie(response, "loggedInUser", username, 24 * 60 * 60);
            if (account.isRole()) {
                return "redirect:/admin/products/searchpaginated";
            }
            // Lấy thông tin giỏ hàng
            List<OrderDetailDto> orderDetails = orderDetailService.findAllOrderDetailsWithProductsByUsername(username);
            model.addAttribute("orderdetails", orderDetails);

            return "redirect:/site/page/home";
        } else {
            model.addAttribute("message", "Sai tên đăng nhập hoạc mật khẩu");
            model.addAttribute("user", new AccountDto());
            return "site/accounts/login";
        }
    }

    @RequestMapping("login")
    public String home(Model model, HttpServletRequest request) {
        Account loggedInUser = (Account) SessionUtils.getAttribute(request, "loggedInUser");
        if (loggedInUser != null) {
            AccountDto accountDto = new AccountDto();
            accountDto.setUsername(loggedInUser.getUsername());
            accountDto.setPassword(loggedInUser.getPassword());
            model.addAttribute("user", accountDto);
        } else {
            model.addAttribute("user", new AccountDto());
        }
        return "site/accounts/login";
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