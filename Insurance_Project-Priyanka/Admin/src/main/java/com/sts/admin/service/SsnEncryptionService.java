package com.sts.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Pattern;

@Service
public class SsnEncryptionService {

    private static final Logger log = LoggerFactory.getLogger(SsnEncryptionService.class);

    private static final String AES = "AES";
    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // bits
    private static final int IV_LENGTH = 12; // bytes



    // allowed base64 chars (standard). We'll allow url-safe variant optionally.
    private static final Pattern BASE64_STD_PATTERN = Pattern.compile("^[A-Za-z0-9+/]+={0,2}$");
    private static final Pattern BASE64_URL_PATTERN = Pattern.compile("^[A-Za-z0-9\\-_]+={0,2}$");

    private final SecretKey secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * @param base64Key        Base64-encoded AES key (standard or PEM). Required.
     * @param allowUrlSafeBase64 if true, accept URL-safe Base64 (with '-' and '_').
     */
    public SsnEncryptionService(
            @Value("${ssn.aes.base64.key:}") String base64Key,
            @Value("${ssn.aes.allowUrlSafeBase64:false}") boolean allowUrlSafeBase64) {

        if (base64Key == null || base64Key.isBlank()) {
            throw new IllegalStateException("Configuration property 'ssn.aes.base64.key' must be provided and non-empty");
        }

        // Clean: remove PEM headers/footers and whitespace
        String cleaned = base64Key
                .replaceAll("-----BEGIN [^-]+-----", "")
                .replaceAll("-----END [^-]+-----", "")
                .replaceAll("\\s+", "");

        // Quick debug log (don't log full key in prod)
        log.debug("ssn.aes.base64.key provided, cleaned length = {}", cleaned.length());

        // Validate allowed characters
        boolean validStd = BASE64_STD_PATTERN.matcher(cleaned).matches();
        boolean validUrl = allowUrlSafeBase64 && BASE64_URL_PATTERN.matcher(cleaned).matches();

        if (!validStd && !validUrl) {
            // give a helpful message that includes a small sample (no secrets)
            String sample = cleaned.length() > 32 ? cleaned.substring(0, 32) + "..." : cleaned;
            log.error("ssn.aes.base64.key contains invalid characters for Base64. Sample: {}", sample);
            throw new IllegalArgumentException(
                    "ssn.aes.base64.key is not valid Base64. Ensure you provided a proper Base64 string (no file paths or '.' chars).");
        }

        // Decode using appropriate decoder
        byte[] keyBytes;
        try {
            Base64.Decoder decoder = (validStd) ? Base64.getDecoder() : Base64.getUrlDecoder();
            keyBytes = decoder.decode(cleaned);
        } catch (IllegalArgumentException ex) {
            log.error("Failed to Base64-decode ssn.aes.base64.key", ex);
            throw new IllegalArgumentException("Failed to Base64-decode ssn.aes.base64.key: " + ex.getMessage(), ex);
        }

        // Validate AES key length: 16, 24, or 32 bytes
        int len = keyBytes.length;
        if (len != 16 && len != 24 && len != 32) {
            log.error("Invalid AES key size: {} bytes (expected 16/24/32)", len);
            throw new IllegalArgumentException("Decoded AES key must be 16, 24, or 32 bytes but was " + len);
        }

        this.secretKey = new SecretKeySpec(keyBytes, AES);
        log.info("SsnEncryptionService initialized successfully (key-length={} bytes)", len);
    }

    public String encrypt(String plain) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

            Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            byte[] cipherText = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));

            // store IV + ciphertext as base64
            byte[] ivAndCt = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, ivAndCt, 0, iv.length);
            System.arraycopy(cipherText, 0, ivAndCt, iv.length, cipherText.length);
            return Base64.getEncoder().encodeToString(ivAndCt);
        } catch (Exception e) {
            throw new RuntimeException("SSN encryption failed", e);
        }
    }

    public String decrypt(String base64IvAndCiphertext) {
        try {
            byte[] ivAndCt = Base64.getDecoder().decode(base64IvAndCiphertext);
            if (ivAndCt.length < IV_LENGTH) {
                throw new IllegalArgumentException("Input too short to contain IV + ciphertext");
            }
            byte[] iv = new byte[IV_LENGTH];
            byte[] ct = new byte[ivAndCt.length - IV_LENGTH];
            System.arraycopy(ivAndCt, 0, iv, 0, IV_LENGTH);
            System.arraycopy(ivAndCt, IV_LENGTH, ct, 0, ct.length);

            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            byte[] plain = cipher.doFinal(ct);
            return new String(plain, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("SSN decryption failed", e);
        }
    }
}
