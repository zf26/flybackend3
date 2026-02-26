package com.pf.im.service;

import java.util.List;

import com.pf.im.domain.ImChatMessage;
import com.pf.im.model.ChatMessage;

/**
 * 聊天历史消息服务：
 * 将当前实时 ChatMessage 转换并持久化到 im_chat_message 表，
 * 并提供按房间查询历史消息的能力。
 */
public interface ImChatMessageService {

    /**
     * 根据实时聊天消息保存一条历史记录。
     *
     * @param chatMessage 当前实时消息
     */
    void saveFromChatMessage(ChatMessage chatMessage);

    /**
     * 按房间查询最近的若干条聊天消息（密文）。
     *
     * @param roomId 房间ID
     * @param limit  返回条数上限
     * @return 历史消息列表
     */
    List<ImChatMessage> listByRoomId(String roomId, int limit);

    /**
     * 查询两个用户之间的私聊历史消息（不区分发送方/接收方），可选按 roomId 过滤。
     *
     * @param userId1 当前用户ID
     * @param userId2 目标用户ID
     * @param roomId  房间ID（可选）
     * @param limit   返回条数上限
     * @return 历史消息列表
     */
    List<ImChatMessage> listPrivateHistory(Long userId1, Long userId2, String roomId, int limit);
}
