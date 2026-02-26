package com.pf.im.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pf.im.domain.ImChatMessage;

/**
 * IM 聊天历史消息 Mapper。
 */
public interface ImChatMessageMapper {

    /**
     * 新增一条聊天消息记录。
     *
     * @param message 消息实体
     * @return 影响行数
     */
    int insertImChatMessage(ImChatMessage message);

    /**
     * 按房间查询最近的若干条聊天消息。
     *
     * @param roomId 房间ID
     * @param limit  返回条数上限
     * @return 消息列表
     */
    List<ImChatMessage> selectByRoomId(@Param("roomId") String roomId, @Param("limit") int limit);

    /**
     * 按双方用户查询私聊历史消息（不区分发送方/接收方），可选按 roomId 过滤。
     *
     * @param userId1 当前用户ID
     * @param userId2 目标用户ID
     * @param roomId  房间ID（可选）
     * @param limit   返回条数上限
     * @return 消息列表
     */
    List<ImChatMessage> selectPrivateHistory(@Param("userId1") Long userId1,
                                             @Param("userId2") Long userId2,
                                             @Param("roomId") String roomId,
                                             @Param("limit") int limit);
}
