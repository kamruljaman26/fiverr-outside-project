package com.ais.recruit.aisr.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    // Replace with a strong encryption algorithm (e.g., bcrypt)
    private static final String HASHING_ALGORITHM = "SHA-256";

    public static String encrypt(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(HASHING_ALGORITHM);
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle exception (should rarely happen)
            throw new RuntimeException("Error while encrypting password.", e);
        }
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String hashedInputPassword = encrypt(plainPassword).trim();
        System.out.println(plainPassword);
        System.out.println(hashedInputPassword);
        System.out.println(hashedPassword);
        return hashedInputPassword.equals(hashedPassword.trim());
    }
}