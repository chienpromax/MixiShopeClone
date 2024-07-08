// package edu.poly.shop.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final UserDetailsService userDetailsService;

//     public SecurityConfig(UserDetailsService userDetailsService) {
//         this.userDetailsService = userDetailsService;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public DaoAuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//         authProvider.setUserDetailsService(userDetailsService);
//         authProvider.setPasswordEncoder(passwordEncoder());
//         return authProvider;
//     }

//     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth.authenticationProvider(authenticationProvider());
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http.csrf().disable()
//                 .authorizeHttpRequests(authorize -> authorize
//                         .requestMatchers("/home/index", "/auth/login/**").permitAll()
//                         .requestMatchers("/home/admins").hasRole("ADMIN")
//                         .requestMatchers("/home/user").hasAnyRole("ADMIN", "USER")
//                         .anyRequest().permitAll())
//                 .formLogin(form -> form
//                         .loginPage("/auth/login/form")
//                         .loginProcessingUrl("/auth/login")
//                         .defaultSuccessUrl("/auth/login/success", false)
//                         .failureUrl("/auth/login/error")
//                         .usernameParameter("username")
//                         .passwordParameter("password"))

//                 .logout(logout -> logout
//                         .logoutUrl("/auth/logoff")
//                         .logoutSuccessUrl("/auth/logoff/success"))

//                 .exceptionHandling(exception -> exception
//                         .accessDeniedPage("/auth/access/denied"))

//                 .oauth2Login(oauth2 -> oauth2
//                         .loginPage("/auth/login/form") // Custom login page for OAuth2
//                         .defaultSuccessUrl("/oauth2/login/success", true) // URL on successful OAuth2 login
//                         .failureUrl("/auth/login/error") // URL on OAuth2 login failure
//                         .authorizationEndpoint(auth -> auth
//                                 .baseUri("/oauth2/authorization")));

//         return http.build();
//     }
// }
