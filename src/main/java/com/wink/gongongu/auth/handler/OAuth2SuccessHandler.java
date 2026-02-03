package com.wink.gongongu.auth.handler;

import com.wink.gongongu.auth.jwt.service.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.front-base-url}")
    private String frontBaseUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        boolean isTmpUser = hasAuthority(oauth2User, "ROLE_TMP");
        String loginToken = jwtTokenProvider.createLoginToken(Long.valueOf(oauth2User.getName()));
        addLoginTokenCookie(response,loginToken);

        if(isTmpUser){ //임시 유저일 경우 회원가입 프론트 URL로 리다이렉트
            String targetUrl = UriComponentsBuilder.fromUriString(frontBaseUrl)
                .path("/signup")
//                .queryParam("oauthId", oauth2User.getName())
                .build()
                .toUriString();

            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            String targetUrl = UriComponentsBuilder.fromUriString(frontBaseUrl)
//                .queryParam("userId", oauth2User.getName())
                .build()
                .toUriString();

            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

    }

    private boolean hasAuthority(OAuth2User oauth2User, String authority){
        for (GrantedAuthority ga : oauth2User.getAuthorities()) {
            if(authority.equals(ga.getAuthority())){
                return true;
            }
        }
        return false;
    }

    private void addLoginTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("LOGIN_TOKEN", token)
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .path("/")
            .maxAge(86400) // 1일
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
