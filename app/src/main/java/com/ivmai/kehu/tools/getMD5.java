package com.ivmai.kehu.tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by MR-SHAREONE on 2017/8/13.
 */
public class getMD5 {
    public static String getMD5() {
        String str = randomnum();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String randomnum() {
        int max = 20;
        int min = 10;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s + "";
    }
}
