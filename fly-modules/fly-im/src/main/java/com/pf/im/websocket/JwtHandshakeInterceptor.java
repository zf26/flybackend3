package com.pf.im.websocket;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.pf.common.core.utils.StringUtils;
import com.pf.common.security.auth.AuthUtil;
import com.pf.common.security.utils.SecurityUtils;
import com.pf.system.api.model.LoginUser;

/**
 * WebSocket 握手拦截器：
 * 在握手阶段从请求中解析 JWT，校验登录状态，并将 LoginUser 放入 attributes，
 * 供后续握手处理器和 STOMP 会话使用。
 */
@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        if (!(request instanceof ServletServerHttpRequest)) {
            return false;
        }
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        // 复用 SecurityUtils，从请求头中获取 Authorization 并裁剪前缀
        String token = SecurityUtils.getToken(servletRequest);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        // 通过 AuthUtil 和 Redis 校验 token，并获取当前登录用户信息
        LoginUser loginUser = AuthUtil.getLoginUser(token);
        if (loginUser == null || loginUser.getUserid() == null) {
            return false;
        }
        // 将用户信息放入握手属性，后续在 ImHandshakeHandler 和 Controller 中使用
        attributes.put("loginUser", loginUser);
        attributes.put("userId", loginUser.getUserid());
        attributes.put("userName", loginUser.getUsername());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
    }
}
