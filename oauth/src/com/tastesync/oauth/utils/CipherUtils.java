package com.tastesync.oauth.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class CipherUtils {
    //16 byte or 32 byte - ASecureSecretKey
    private static byte[] key = new byte[] {
            'T', 'a', 's', 't', 'e', 'D', 'e', 'e', 'P', 'J', 'a', 'g', 's', 'S',
            'y', 'n', 'P', 'r', 'd', 'K', 'e', 'y', '1', 'T', 'a', 's', 't', 'e',
            'S', 'y', 'n', 'c'
        }; //ASecureSecretKey

    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            String decryptedString = new String(cipher.doFinal(
                        Base64.decodeBase64(strToDecrypt)));

            return decryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            String encryptedString = Base64.encodeBase64String(cipher.doFinal(
                        strToEncrypt.getBytes()));

            return encryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
