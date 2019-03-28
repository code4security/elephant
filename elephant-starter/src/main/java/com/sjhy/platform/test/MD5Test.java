package com.sjhy.platform.test;

import com.sjhy.platform.https.IosMD5;

public class MD5Test {
    private final static String MD5KEY = "shijun";
    public static void main(String[] args) {
        String a = "564984sa13d21a89s43a2s1d89a4d3a1d56sa4d98ada12fgaf8ad0920(*&%D&^$)sd4a";
        try {
            String b = IosMD5.md5(a,MD5KEY);
            System.out.println(b);
            System.out.println(IosMD5.verify(a,MD5KEY,b));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
