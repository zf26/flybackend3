package com.pf.im.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.pf.im.model.SignalMessage;
import com.pf.im.service.MessagePushService;

@Controller
public class WebRtcSignalController {

    private final MessagePushService messagePushService;

    public WebRtcSignalController(MessagePushService messagePushService) {
        this.messagePushService = messagePushService;
    }

    @MessageMapping("/webrtc.signal")
    public void handleSignal(@Payload SignalMessage signalMessage,
                             Principal principal,
                             SimpMessageHeaderAccessor headerAccessor) {
        if (principal == null || signalMessage == null) {
            return;
        }
        Long fromUserId;
        try {
            fromUserId = Long.valueOf(principal.getName());
        } catch (NumberFormatException ex) {
            return;
        }
        signalMessage.setFromUserId(fromUserId);
        if (signalMessage.getTimestamp() == null) {
            signalMessage.setTimestamp(System.currentTimeMillis());
        }
        if (signalMessage.getToUserId() != null) {
            messagePushService.sendPrivateSignal(signalMessage);
        }
        if (signalMessage.getRoomId() != null && !signalMessage.getRoomId().isEmpty()) {
            messagePushService.sendRoomSignal(signalMessage);
        }
    }
}
