package edu.poly.shop.service;

import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Gán quyền ROLE_USER
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        oAuth2User = new DefaultOAuth2User(Collections.singleton(authority), oAuth2User.getAttributes(), "name");

        return oAuth2User;
    }
}
