package com.ivmai.kehu.tools;

import java.util.Random;

/**
 * Created by MR-SHAREONE on 2017/8/18.
 * 生成随机字符串
 */

public class RandomString {
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
