package com.pf.im.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.pf.im.model.ChatMessage;
import com.pf.im.service.MessagePushService;
import com.pf.im.service.EncryptionService;
import com.pf.im.service.ImChatMessageService;

/**
 * STOMP 聊天控制器：
 * 处理前端发送到 /app/chat.send 的消息，
 * 根据消息内容分发到私聊或房间的目的地，并将处理结果回 ACK 给当前用户。
 */
@Controller
public class ImChatController {

    private final MessagePushService messagePushService;

    private final EncryptionService encryptionService;

    private final ImChatMessageService chatMessageService;

    // 构造函数注入消息推送服务、加解密服务与历史消息服务
    public ImChatController(MessagePushService messagePushService,
                            EncryptionService encryptionService,
                            ImChatMessageService chatMessageService) {
        this.messagePushService = messagePushService;
        this.encryptionService = encryptionService;
        this.chatMessageService = chatMessageService;
    }

    // 处理前端发送到 /app/chat.send 的消息
    // 返回值通过 /user/queue/ack 回传给当前用户作为确认
    @MessageMapping("/chat.send")
    @SendToUser("/queue/ack")
    public ChatMessage handleChat(@Payload ChatMessage chatMessage,
                                  Principal principal,
                                  SimpMessageHeaderAccessor headerAccessor) {
        if (principal == null) {
            return null;
        }
        Long fromUserId;
        try {
            // Principal.getName() 由 ImHandshakeHandler 设置为当前用户 ID
            fromUserId = Long.valueOf(principal.getName());
        } catch (NumberFormatException ex) {
            return null;
        }
        chatMessage.setFromUserId(fromUserId);
        if (chatMessage.getTimestamp() == null) {
            chatMessage.setTimestamp(System.currentTimeMillis());
        }
        // 对消息内容进行应用层加密，保护存储和传输中的医疗咨询明文
        // E2E 模式下由前端使用会话密钥进行加密，这里只接收和转发密文
        // 持久化聊天历史记录（内容字段为密文）
        chatMessageService.saveFromChatMessage(chatMessage);
        if (chatMessage.getToUserId() != null) {
            messagePushService.sendPrivateChat(chatMessage);
        }
        if (chatMessage.getRoomId() != null && !chatMessage.getRoomId().isEmpty()) {
            messagePushService.sendRoomChat(chatMessage);
        }
        return chatMessage;
    }
}
