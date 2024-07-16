package edu.poly.shop.controller.site.accounts;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import edu.poly.shop.model.Account;
import edu.poly.shop.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class LoginWithFBGGController extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private AccountService accountService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());
        String username = oAuth2User.getAttribute("name"); // Lấy tên làm username
        String email = oAuth2User.getAttribute("email");

        // Kiểm tra xem username đã tồn tại trong cơ sở dữ liệu hay chưa
        if (!accountService.existsByUsername(username)) {
            // Tạo mới tài khoản
            Account newAccount = new Account();
            newAccount.setUsername(username);
            newAccount.setEmail(email);
            newAccount.setPassword(bCryptPasswordEncoder.encode("12345xyz@54321"));
            newAccount.setRole(false); // ROLE_USER
            accountService.save(newAccount);
            // System.out.println("New account created: " + newAccount.getUsername());
        } else {
            System.out.println("Account already exists:");
        }

        response.sendRedirect("/site/page/home");
    }

}
