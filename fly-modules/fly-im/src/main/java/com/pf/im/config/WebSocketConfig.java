package com.pf.im.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.pf.im.websocket.JwtHandshakeInterceptor;
import com.pf.im.websocket.ImHandshakeHandler;

/**
 * WebSocket/STOMP 配置：
 * 1. 暴露 /ws 端点供前端建立 WebSocket 连接；
 * 2. 配置应用前缀 /app 和用户前缀 /user；
 * 3. 通过 STOMP relay 将消息转发到 RabbitMQ，实现多实例共享消息。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    private final ImHandshakeHandler imHandshakeHandler;

    // RabbitMQ STOMP relay 主机地址
    @Value("${spring.rabbitmq.host}")
    private String relayHost;

    // RabbitMQ STOMP 端口（由外部配置指定）
    @Value("${im.stomp-port:32963}")
    private int relayPort;

    // STOMP 客户端登录用户名
    @Value("${spring.rabbitmq.username}")
    private String relayLogin;

    // STOMP 客户端登录密码
    @Value("${spring.rabbitmq.password}")
    private String relayPasscode;

    // STOMP 虚拟主机
    @Value("${spring.rabbitmq.virtual-host:/}")
    private String relayVirtualHost;

    // 构造函数注入握手拦截器和自定义握手处理器
    public WebSocketConfig(JwtHandshakeInterceptor jwtHandshakeInterceptor, ImHandshakeHandler imHandshakeHandler) {
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
        this.imHandshakeHandler = imHandshakeHandler;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 STOMP 端点：/ws，对应前端 WebSocket 连接入口
        registry.addEndpoint("/ws")
//                .setHandshakeHandler(imHandshakeHandler)
//                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 前端发送到后端的应用前缀，例如 /app/chat.send
        registry.setApplicationDestinationPrefixes("/app");
        // 开启 STOMP broker relay，将 /topic、/queue、/user 前缀的消息交给 RabbitMQ 处理
        registry.enableStompBrokerRelay("/topic", "/queue", "/user")
                .setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setClientLogin(relayLogin)
                .setClientPasscode(relayPasscode)
                .setSystemLogin(relayLogin)
                .setSystemPasscode(relayPasscode)
                .setVirtualHost(relayVirtualHost);
        // 点对点用户目的地前缀，例如 /user/queue/chat
        registry.setUserDestinationPrefix("/user");
    }
}
