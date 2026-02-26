package com.pf.im.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pf.common.core.utils.StringUtils;
import com.pf.common.core.web.controller.BaseController;
import com.pf.common.core.web.domain.AjaxResult;
import com.pf.im.domain.ImChatMessage;
import com.pf.im.model.ChatMessage;
import com.pf.im.service.ConversationKeyService;
import com.pf.im.service.EncryptionService;
import com.pf.im.service.ImChatMessageService;

/**
 * IM 审计接口：
 * 按房间查询历史消息，并在服务端解密后返回明文，
 * 仅供内部后台/审计系统使用。
 */
@RestController
@RequestMapping("/audit")
public class ImAuditController extends BaseController {

    private final ImChatMessageService chatMessageService;

    private final EncryptionService encryptionService;

    private final ConversationKeyService conversationKeyService;

    public ImAuditController(ImChatMessageService chatMessageService,
                             EncryptionService encryptionService,
                             ConversationKeyService conversationKeyService) {
        this.chatMessageService = chatMessageService;
        this.encryptionService = encryptionService;
        this.conversationKeyService = conversationKeyService;
    }

    /**
     * 按房间查询最近的若干条历史消息，并在服务端解密为明文返回。
     *
     * @param roomId 房间ID
     * @param limit  返回条数上限，默认 50
     * @return ChatMessage 列表，content 为明文
     */
    @GetMapping("/messages")
    public AjaxResult listMessages(@RequestParam("roomId") String roomId,
                                   @RequestParam(value = "limit", required = false, defaultValue = "50") int limit) {
        if (StringUtils.isEmpty(roomId)) {
            return error("roomId 不能为空");
        }
        if (limit <= 0) {
            limit = 50;
        }
        // 获取会话密钥（如存在），用于解密前端 E2E 加密的消息内容
        String conversationKey = null;
        try {
            conversationKey = conversationKeyService.getOrCreateConversationKey(roomId);
        } catch (Exception ex) {
            // 忽略异常，后续仅尝试旧的服务端密钥解密
            conversationKey = null;
        }
        List<ImChatMessage> historyList = chatMessageService.listByRoomId(roomId, limit);
        List<ChatMessage> result = new ArrayList<>();
        for (ImChatMessage item : historyList) {
            ChatMessage msg = new ChatMessage();
            msg.setRoomId(item.getRoomId());
            msg.setFromUserId(item.getFromUserId());
            msg.setToUserId(item.getToUserId());
            msg.setType(item.getMsgType());
            msg.setTimestamp(item.getMsgTime());
            msg.setExtra(item.getExtra());
            // 解密密文内容，优先尝试旧的服务端密钥，再尝试会话密钥，若都失败则保持原密文
            String cipher = item.getContentCipher();
            if (cipher != null) {
                String plain = null;
                // 1. 先尝试使用 im.content-key 的旧方案解密（兼容历史数据）
                try {
                    plain = encryptionService.decrypt(cipher);
                } catch (Exception ex) {
                    plain = null;
                }
                // 2. 若旧方案失败且存在会话密钥，则使用会话密钥按 E2E 方案解密
                if (plain == null && StringUtils.isNotEmpty(conversationKey)) {
                    try {
                        plain = decryptWithConversationKey(conversationKey, cipher);
                    } catch (Exception ex) {
                        plain = null;
                    }
                }
                if (plain == null) {
                    // 解密失败则返回原密文，避免数据丢失
                    plain = cipher;
                }
                msg.setContent(plain);
            }
            result.add(msg);
        }
        return success(result);
    }

    // 使用会话密钥按 AES-GCM 方式解密前端 E2E 加密的消息内容
    private String decryptWithConversationKey(String conversationKey, String cipherText) throws Exception {
        final String TRANSFORMATION = "AES/GCM/NoPadding";
        final int IV_LENGTH = 12;
        final int GCM_TAG_LENGTH = 128;

        byte[] allBytes = Base64.getDecoder().decode(cipherText);
        if (allBytes.length <= IV_LENGTH) {
            throw new IllegalArgumentException("cipher text too short");
        }
        byte[] iv = new byte[IV_LENGTH];
        byte[] cipherBytes = new byte[allBytes.length - IV_LENGTH];
        System.arraycopy(allBytes, 0, iv, 0, IV_LENGTH);
        System.arraycopy(allBytes, IV_LENGTH, cipherBytes, 0, cipherBytes.length);

        byte[] keyBytes = Base64.getDecoder().decode(conversationKey);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
        byte[] plainBytes = cipher.doFinal(cipherBytes);
        return new String(plainBytes, StandardCharsets.UTF_8);
    }
}
