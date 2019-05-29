package com.linda.homework.lindaindoornavigation.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Util {

    private static final String JPEG_BASE64_PREFIX = "data:image/jpeg;base64,";

    /**
     * 将二进制数据编码为BASE64字符串
     * @param binaryData
     * @return
     */
    public static String encode(byte[] binaryData) {
        try {
            return new String(Base64.encodeBase64(binaryData), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 将BASE64字符串恢复为二进制数据
     * @param base64String
     * @return
     */
    public static byte[] decode(String base64String) {
        try {
            return Base64.decodeBase64(base64String.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 去除掉jpeg图片中base64前缀
     * @param str
     * @return
     */
    public static String deleteJPEGBase64Prefix(String str){
        return str.replaceAll(JPEG_BASE64_PREFIX, "");
    }

}
