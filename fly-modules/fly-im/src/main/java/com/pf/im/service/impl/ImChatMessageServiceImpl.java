package com.pf.im.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pf.im.domain.ImChatMessage;
import com.pf.im.mapper.ImChatMessageMapper;
import com.pf.im.model.ChatMessage;
import com.pf.im.service.ImChatMessageService;

/**
 * 聊天历史消息服务实现。
 */
@Service
public class ImChatMessageServiceImpl implements ImChatMessageService {

    private final ImChatMessageMapper chatMessageMapper;

    public ImChatMessageServiceImpl(ImChatMessageMapper chatMessageMapper) {
        this.chatMessageMapper = chatMessageMapper;
    }

    @Override
    public void saveFromChatMessage(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return;
        }
        ImChatMessage entity = new ImChatMessage();
        entity.setRoomId(chatMessage.getRoomId());
        entity.setFromUserId(chatMessage.getFromUserId());
        entity.setToUserId(chatMessage.getToUserId());
        entity.setMsgType(chatMessage.getType());
        // 此处 content 已经是加密后的密文（或未来前端 E2E 加密后的密文）
        entity.setContentCipher(chatMessage.getContent());
        entity.setExtra(chatMessage.getExtra());
        Long ts = chatMessage.getTimestamp();
        if (ts == null) {
            ts = System.currentTimeMillis();
        }
        entity.setMsgTime(ts);
        entity.setReadFlag(0);
        entity.setDeletedFlag(0);
        chatMessageMapper.insertImChatMessage(entity);
    }

    @Override
    public List<ImChatMessage> listByRoomId(String roomId, int limit) {
        return chatMessageMapper.selectByRoomId(roomId, limit);
    }

    @Override
    public List<ImChatMessage> listPrivateHistory(Long userId1, Long userId2, String roomId, int limit) {
        return chatMessageMapper.selectPrivateHistory(userId1, userId2, roomId, limit);
    }
}
