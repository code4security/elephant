package com.sjhy.platform.biz.test;

public class FloatTest {
    public static void main(String[] args) {
        String a = "2.1";
        String b = "2.3";
        if (Float.valueOf(a)<Float.valueOf(b)){
            System.out.println("true");
        }
    }
}
