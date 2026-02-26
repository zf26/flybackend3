package com.pf.im.service;

/**
 * 应用层加解密服务接口，主要用于对聊天消息内容等敏感数据进行对称加密保护。
 */
public interface EncryptionService {

    /**
     * 加密明文字符串，返回密文表示（例如 Base64 编码）。
     * 如果入参为空，直接返回原值。
     */
    String encrypt(String plaintext);

    /**
     * 解密密文字符串，返回明文。
     * 如果入参为空，直接返回原值。
     */
    String decrypt(String ciphertext);
}
