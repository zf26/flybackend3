package com.pf.im.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pf.im.domain.ImConversation;

/**
 * IM 会话/房间 Mapper。
 */
public interface ImConversationMapper {

    /**
     * 按用户查询其参与的会话列表。
     *
     * @param userId 用户ID
     * @return 会话列表
     */
    List<ImConversation> selectByUserId(@Param("userId") Long userId);
}
