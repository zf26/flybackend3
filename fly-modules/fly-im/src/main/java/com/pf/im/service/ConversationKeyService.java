package com.pf.im.service;

/**
 * 会话密钥管理服务：
 * 为每个房间/会话生成并管理对话密钥（conversationKey），
 * 并以包裹形式（wrappedConversationKey）持久化，
 * 供前端在进入会话时获取用于端到端加解密。
 */
public interface ConversationKeyService {

    /**
     * 获取指定房间的会话密钥。如果不存在，则生成新的会话密钥并保存包裹后的密文。
     *
     * @param roomId 房间/会话 ID
     * @return 明文会话密钥，用于前端做 AES-GCM 加解密
     */
    String getOrCreateConversationKey(String roomId);
}
