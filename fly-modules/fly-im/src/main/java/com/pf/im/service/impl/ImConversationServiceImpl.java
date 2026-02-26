package com.pf.im.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pf.im.domain.ImConversation;
import com.pf.im.mapper.ImConversationMapper;
import com.pf.im.service.ImConversationService;

/**
 * 会话列表服务实现。
 */
@Service
public class ImConversationServiceImpl implements ImConversationService {

    private final ImConversationMapper conversationMapper;

    public ImConversationServiceImpl(ImConversationMapper conversationMapper) {
        this.conversationMapper = conversationMapper;
    }

    @Override
    public List<ImConversation> listByUserId(Long userId) {
        return conversationMapper.selectByUserId(userId);
    }
}
