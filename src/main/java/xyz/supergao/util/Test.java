package xyz.supergao.util;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(new Random().nextInt(8999)+1000);
        }

        //SmsMessageUtil smsMessageUtil = new SmsMessageUtil("AKID1u13yqJqjYN9vYyCE7oH1XoppbGn9PUL","e6h4B2PXrwnRq0cGhxzpTt68xb6QaYNz","ap-guangzhou","1400690992","1461439","我的个人学习网站");
        //String s = smsMessageUtil.sendSmsMessageVerificationCode("18179028109");
    }
}
