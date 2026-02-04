package com.wink.gongongu.auth.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static void deleteLoginTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("LOGIN_TOKEN", "")
            .path("/")
            .maxAge(0)
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

}
