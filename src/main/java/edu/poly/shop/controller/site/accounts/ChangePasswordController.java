package edu.poly.shop.controller.site.accounts;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.domain.AccountDto;
import edu.poly.shop.model.Account;
import edu.poly.shop.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("site/accounts")
public class ChangePasswordController {

    @Autowired
    AccountService accountService;

    @RequestMapping("changepassword")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("account", new AccountDto());
        return "site/accounts/changepassword";
    }

    @PostMapping("changepassword")
    public String changePassword(HttpServletRequest request, Model model,
                                 @RequestParam("username") String username, 
                                 @RequestParam("password") String currentPassword,
                                 @RequestParam("newpassword") String newPassword,
                                 @RequestParam("enternewpassword") String confirmNewPassword) {
        Optional<Account> accountOptional = accountService.findById(username);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            // Kiểm tra mật khẩu hiện tại
            if (account.getPassword().equals(currentPassword)) {
                // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới có khớp không
                if (newPassword.equals(confirmNewPassword)) {
                    // Cập nhật mật khẩu mới
                    account.setPassword(newPassword);
                    accountService.save(account);
                    model.addAttribute("message", "Đổi mật khẩu thành công");
                } else {
                    model.addAttribute("message", "Mật khẩu mới và xác nhận mật khẩu không khớp");
                }
            } else {
                model.addAttribute("message", "Mật khẩu hiện tại không đúng");
            }
        } else {
            model.addAttribute("message", "Tài khoản không tồn tại");
        }

        model.addAttribute("account", new AccountDto());
        return "site/accounts/changepassword";
    }
}

