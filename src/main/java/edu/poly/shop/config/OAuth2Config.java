// package edu.poly.shop.config;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.oauth2.client.registration.ClientRegistration;
// import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
// import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
// import org.springframework.security.oauth2.core.AuthorizationGrantType;

// @Configuration
// public class OAuth2Config {

//     @Value("${spring.security.oauth2.client.registration.google.client-id}")
//     private String clientId;

//     @Value("${spring.security.oauth2.client.registration.google.client-secret}")
//     private String clientSecret;

//     @Bean
//     public ClientRegistrationRepository clientRegistrationRepository() {
//         ClientRegistration registration = ClientRegistration.withRegistrationId("google")
//                 .clientId(clientId)
//                 .clientSecret(clientSecret)
//                 .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                 .tokenUri("https://oauth2.googleapis.com/token")
//                 .authorizationUri("https://accounts.google.com/o/oauth2/auth")
//                 .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
//                 .scope("profile", "email") // Thay đổi để phù hợp với cấu hình
//                 .build();

//         return new InMemoryClientRegistrationRepository(registration);
//     }
// }
