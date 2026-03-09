package com.wink.gongongu.domain.chat.websocket;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.auth.jwt.TokenStatus;
import com.wink.gongongu.auth.jwt.service.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatStompAuthChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = resolveBearerToken(accessor);
            TokenStatus tokenStatus = jwtTokenProvider.validateToken(token);

            if (tokenStatus != TokenStatus.VALID) {
                throw new AccessDeniedException("Invalid websocket access token");
            }

            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(new UserPrincipal(userId), null, List.of());

            accessor.setUser(authentication);
        }

        return message;
    }

    private String resolveBearerToken(StompHeaderAccessor accessor) {
        String authorization = accessor.getFirstNativeHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring(7);
    }
}
