package com.pf.im.service.impl;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.pf.common.core.utils.StringUtils;
import com.pf.common.redis.service.RedisService;
import com.pf.im.service.ConversationKeyService;
import com.pf.im.service.EncryptionService;

/**
 * 基于 Redis 的会话密钥管理实现：
 * - 为每个 roomId 生成随机会话密钥 conversationKey（Base64 字符串）；
 * - 使用 EncryptionService（masterKey）对 conversationKey 进行包裹加密并持久化；
 * - 在需要时解包还原 conversationKey，供前端做端到端加解密。
 */
@Service
public class ConversationKeyServiceImpl implements ConversationKeyService {

    private static final String ROOM_KEY_PREFIX = "im:room:conversation:key:";

    private final RedisService redisService;

    private final EncryptionService encryptionService;

    private final SecureRandom secureRandom = new SecureRandom();

    public ConversationKeyServiceImpl(RedisService redisService, EncryptionService encryptionService) {
        this.redisService = redisService;
        this.encryptionService = encryptionService;
    }

    @Override
    public String getOrCreateConversationKey(String roomId) {
        if (StringUtils.isEmpty(roomId)) {
            return null;
        }
        String cacheKey = ROOM_KEY_PREFIX + roomId;
        String wrappedKey = redisService.getCacheObject(cacheKey);
        if (StringUtils.isNotEmpty(wrappedKey)) {
            // 已存在会话密钥：使用 masterKey 解包
            return encryptionService.decrypt(wrappedKey);
        }
        // 生成新的会话密钥：32 字节随机数，通过 Base64 表示
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);
        String conversationKey = Base64.getEncoder().encodeToString(keyBytes);
        // 使用 EncryptionService 进行包裹加密，只在 Redis 中保存包裹后的密文
        String newWrappedKey = encryptionService.encrypt(conversationKey);
        redisService.setCacheObject(cacheKey, newWrappedKey);
        return conversationKey;
    }
}
