package com.backend.util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {

    /**
     *  使用 BCrypt 加密明文密码
     * @param plainText 明文密码
     * @return 加密后的密码
     */
    public static String hash(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    /**
     *  验证密码是否匹配
     * @param plainText 加密前的明文密码
     * @param hashed 加密后的密码
     * @return 如果匹配返回 true，否则返回 false
     */
    public static boolean verify(String plainText, String hashed) {
        return BCrypt.checkpw(plainText, hashed);
    }
}