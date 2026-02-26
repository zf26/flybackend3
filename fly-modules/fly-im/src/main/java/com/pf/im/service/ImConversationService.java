package com.pf.im.service;

import java.util.List;

import com.pf.im.domain.ImConversation;

/**
 * 会话列表服务：按用户查询其参与的会话列表。
 */
public interface ImConversationService {

    /**
     * 查询指定用户参与的会话列表。
     *
     * @param userId 用户ID
     * @return 会话列表
     */
    List<ImConversation> listByUserId(Long userId);
}
