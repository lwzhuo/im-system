package com.zhuo.imsystem.http.util;

import org.springframework.util.DigestUtils;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    /**
     * 盐的长度
     */
    private static final int SALT_LENGTH = 16;

    /**
     * 生成盐
     *
     * @return String
     */
    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 得到md5加密口令
     *
     * @param password
     * @param salt
     * @return String
     */
    public static String getMd5Password(String password, String salt) {
        String passwordMD5 = DigestUtils.md5DigestAsHex(password.getBytes());
        String totalMD5 = DigestUtils.md5DigestAsHex((passwordMD5+"-"+salt).getBytes());
        return totalMD5;
    }

    /**
     * 将数组转换成16进制字符串
     * @param salt
     * @return String
     */
    private static String byteToHexString(byte[] salt) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < salt.length; i++) {
            String hex = Integer.toHexString(salt[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 判断密码正确性
     * @param originPassword
     * @param md5Password
     * @param salt
     * @return boolean
     */
    public static boolean isValid(String originPassword,String md5Password,String salt){
        String password = getMd5Password(originPassword,salt);
        if(password.equals(md5Password))
            return true;
        else
            return false;
    }

    public static void main(String[] args) throws Exception{
//        1389FB39AD46B68D46F7E9C508A3FBA7
        String res = PasswordUtil.getMd5Password("aaa","bbb");
        System.out.println(res);
    }
}
