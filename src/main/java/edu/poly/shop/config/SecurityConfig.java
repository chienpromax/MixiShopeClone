package edu.poly.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import edu.poly.shop.controller.site.accounts.LoginWithFBGGController;
import edu.poly.shop.service.CustomOAuth2UserService;
import edu.poly.shop.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    LoginWithFBGGController loginWithFBGGController;

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
                        .requestMatchers("/site/video/**").permitAll()
                        .requestMatchers("/site/accounts/*", "/oauth2/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/site/carts/**", "/site/VNPays/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/site/accounts/login")
                        .loginProcessingUrl("/site/accounts/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
                            if (isAdmin) {
                                response.sendRedirect("/admin/products/searchpaginated");
                            } else {
                                response.sendRedirect("/site/page/home");
                            }
                        }))
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/site/accounts/login")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/site/page/home");
                        })
                        .failureUrl("/site/accounts/login")
                        .successHandler(loginWithFBGGController)
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and())
                .logout(logout -> logout
                        .logoutUrl("/site/accounts/logout")
                        .logoutSuccessUrl("/site/accounts/login")
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "loggedInUser")
                        .invalidateHttpSession(true));

        return http.build();
    }

}
