package com.areeba.cms.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AESEncryptionService {

    private static final String ALGORITHM = "AES";

    private final String secret;

    public AESEncryptionService(@Value("${SECRET_KEY}") String secret) {
        this.secret = secret;
    }

    // Method to encrypt card number
    public String encrypt(String plainText) throws Exception {
        SecretKey secretKey = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Method to decrypt card number
    public String decrypt(String encryptedText) throws Exception {
        SecretKey secretKey = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // Generate AES key using the same secret key
    private SecretKey generateKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, ALGORITHM);
    }
}
