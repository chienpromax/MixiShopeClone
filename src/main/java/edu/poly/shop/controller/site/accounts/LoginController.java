package edu.poly.shop.controller.site.accounts;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import edu.poly.shop.utils.CustomUserDetails;
import edu.poly.shop.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("site/accounts")
public class LoginController {

    @Autowired
    AccountService accountService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<Account> accountOptional = accountService.findById(username);
        if (accountOptional.isPresent()
                && bCryptPasswordEncoder.matches(password, accountOptional.get().getPassword())) {
            Account account = accountOptional.get();
            CookieUtils.addCookie(response, "loggedInUser", username, 24 * 60 * 60);
            if (account.isRole()) {
                return "redirect:/admin/products/searchpaginated";
            }
            List<OrderDetailDto> orderDetails = orderDetailService.findAllOrderDetailsWithProductsByUsername(username);
            model.addAttribute("orderdetails", orderDetails);

            return "redirect:/site/page/home";
        } else {
            model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu");
            model.addAttribute("user", new AccountDto());
            return "site/accounts/login";
        }
    }

    @RequestMapping("login")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;

                AccountDto accountDto = new AccountDto();
                accountDto.setUsername(userDetails.getUsername());
                model.addAttribute("user", accountDto);
            } else if (principal instanceof DefaultOAuth2User) {
                DefaultOAuth2User oAuth2User = (DefaultOAuth2User) principal;

                String username = oAuth2User.getAttribute("name");
                AccountDto accountDto = new AccountDto();
                accountDto.setUsername(username);
                model.addAttribute("user", accountDto);
            } else {
                model.addAttribute("user", new AccountDto());
            }
        } else {
            model.addAttribute("user", new AccountDto());
        }

        return "site/accounts/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/site/accounts/login?logout";
    }
}
