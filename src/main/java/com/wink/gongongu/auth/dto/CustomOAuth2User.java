package com.wink.gongongu.auth.dto;

import com.wink.gongongu.domain.user.entity.UserType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record CustomOAuth2User (
    Long userId,
    UserType userType,
    Map<String, Object> attributes
)implements OAuth2User {

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+this.userType));
    }

    @Override
    public String getName() {
        return String.valueOf(userId);
    }
}
