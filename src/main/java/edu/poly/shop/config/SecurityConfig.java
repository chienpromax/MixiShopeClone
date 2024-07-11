package edu.poly.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import edu.poly.shop.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/*").permitAll()
                        .requestMatchers("/static/**", "/css/**", "/image/**", "/js/**", "/uploads/**").permitAll()
                        .requestMatchers("/site/page/**").permitAll()
                        .requestMatchers("/site/products/**").permitAll()
                        .requestMatchers("/site/accounts/*").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/site/carts/**", "/site/VNPays/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/site/accounts/login")
                        .loginProcessingUrl("/site/accounts/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            // Chuyển hướng sau khi đăng nhập thành công
                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
                            if (isAdmin) {
                                response.sendRedirect("/admin/products/searchpaginated");
                            } else {
                                response.sendRedirect("/site/page/home");
                            }
                        }))
                .logout(logout -> logout
                        .logoutUrl("/site/accounts/logout")
                        .logoutSuccessUrl("/site/accounts/login")
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "loggedInUser")
                        .invalidateHttpSession(true)
                        );

        return http.build();
    }

}
