package com.ubio.stockdemo.api.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class EncryptionUtil {

    @Value("${encryption.key}")
    private String secretKey;

    private SecretKeySpec secretKeySpec;

    @PostConstruct
    public void init() {
        byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);
        secretKeySpec = new SecretKeySpec(key, "AES");
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedBytes);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}