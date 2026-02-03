package com.wink.gongongu.auth.handler;

import com.wink.gongongu.auth.dto.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Value("${app.front-base-url}")
    private String frontBaseUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();


    }
}
