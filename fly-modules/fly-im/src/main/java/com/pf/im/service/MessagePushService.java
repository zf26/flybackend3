package com.pf.im.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.pf.im.model.ChatMessage;
import com.pf.im.model.SignalMessage;

/**
 * 消息推送服务：
 * 封装基于 STOMP 的点对点（/user/**）和广播（/topic/**）推送能力，
 * 供 IM 控制器及其他业务模块调用。
 */
@Service
public class MessagePushService {

    private final SimpMessagingTemplate messagingTemplate;

    // 通过构造函数注入 Spring 的 STOMP 消息模板
    public MessagePushService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 按用户 ID 发送点对点消息，destination 形如 "/queue/chat"
    public void sendToUser(Long userId, String destination, Object payload) {
        if (userId == null) {
            return;
        }
        String user = String.valueOf(userId);
        messagingTemplate.convertAndSendToUser(user, destination, payload);
    }

    // 发送广播/主题消息，topic 将拼接为 /topic/{topic}
    public void sendToTopic(String topic, Object payload) {
        if (topic == null || topic.isEmpty()) {
            return;
        }
        String dest = "/topic/" + topic;
        messagingTemplate.convertAndSend(dest, payload);
    }

    // 发送私聊消息，固定推送到 /user/{toUserId}/queue/chat
    public void sendPrivateChat(ChatMessage message) {
        if (message == null || message.getToUserId() == null) {
            return;
        }
        sendToUser(message.getToUserId(), "/queue/chat", message);
    }

    // 发送房间消息，固定推送到 /topic/room.{roomId}
    public void sendRoomChat(ChatMessage message) {
        if (message == null || message.getRoomId() == null || message.getRoomId().isEmpty()) {
            return;
        }
        String topic = "room." + message.getRoomId();
        sendToTopic(topic, message);
    }

    public void sendPrivateSignal(SignalMessage message) {
        if (message == null || message.getToUserId() == null) {
            return;
        }
        sendToUser(message.getToUserId(), "/queue/webrtc", message);
    }

    public void sendRoomSignal(SignalMessage message) {
        if (message == null || message.getRoomId() == null || message.getRoomId().isEmpty()) {
            return;
        }
        String topic = "webrtc.room." + message.getRoomId();
        sendToTopic(topic, message);
    }
}
