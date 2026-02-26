package com.pf.im.websocket;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.pf.system.api.model.LoginUser;

/**
 * 自定义握手处理器：
 * 从握手属性中读取 LoginUser，将用户 ID 封装为 Principal，
 * 供 STOMP 点对点发送时识别当前用户身份。
 */
@Component
public class ImHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        Object loginUserObj = attributes.get("loginUser");
        if (loginUserObj instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) loginUserObj;
            String name = String.valueOf(loginUser.getUserid());
            return new StompPrincipal(name);
        }
        return null;
    }

    private static class StompPrincipal implements Principal {

        private final String name;

        private StompPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
