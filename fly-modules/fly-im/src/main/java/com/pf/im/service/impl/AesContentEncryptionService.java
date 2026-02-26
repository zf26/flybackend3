package com.pf.im.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import jakarta.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pf.im.service.EncryptionService;

/**
 * 基于 AES-GCM 的内容加解密实现：
 * 使用配置中的 im.content-key 派生出对称密钥，对敏感内容进行加密，
 * 输出格式：Base64( iv || cipherBytes )。
 */
@Service
public class AesContentEncryptionService implements EncryptionService {

    // 配置中的原始密钥字符串，通过哈希派生为 256 bit 对称密钥
    @Value("${im.content-key}")
    private String rawKey;

    private SecretKey secretKey;

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";

    private static final int GCM_TAG_LENGTH = 128; // bits

    private static final int IV_LENGTH = 12; // bytes

    private final SecureRandom secureRandom = new SecureRandom();

    @PostConstruct
    public void init() throws Exception {
        if (rawKey == null || rawKey.isEmpty()) {
            throw new IllegalStateException("im.content-key 配置不能为空");
        }
        // 使用 SHA-256 对配置字符串进行哈希，得到 256bit 密钥
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(rawKey.getBytes(StandardCharsets.UTF_8));
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
    }

    @Override
    public String encrypt(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) {
            return plaintext;
        }
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            byte[] cipherBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            byte[] all = new byte[iv.length + cipherBytes.length];
            System.arraycopy(iv, 0, all, 0, iv.length);
            System.arraycopy(cipherBytes, 0, all, iv.length, cipherBytes.length);

            return Base64.getEncoder().encodeToString(all);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("加密内容失败", e);
        }
    }

    @Override
    public String decrypt(String ciphertext) {
        if (ciphertext == null || ciphertext.isEmpty()) {
            return ciphertext;
        }
        try {
            byte[] all = Base64.getDecoder().decode(ciphertext);
            if (all.length <= IV_LENGTH) {
                return ciphertext;
            }
            byte[] iv = Arrays.copyOfRange(all, 0, IV_LENGTH);
            byte[] cipherBytes = Arrays.copyOfRange(all, IV_LENGTH, all.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            byte[] plainBytes = cipher.doFinal(cipherBytes);
            return new String(plainBytes, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            // 如果解密失败，为避免影响业务流程，返回原始密文
            return ciphertext;
        }
    }
}
