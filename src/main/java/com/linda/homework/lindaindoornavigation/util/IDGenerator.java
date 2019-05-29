package com.linda.homework.lindaindoornavigation.util;

import org.springframework.util.DigestUtils;

public class IDGenerator {
    public static String generateNodeId(String nodeName){
        return DigestUtils.md5DigestAsHex(nodeName.getBytes());
    }
}
