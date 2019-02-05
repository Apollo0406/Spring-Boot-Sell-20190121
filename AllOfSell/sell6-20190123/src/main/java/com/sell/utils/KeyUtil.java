package com.sell.utils;

import java.util.Random;

//专门生成随机数的方法
//格式：时间+随机数
public class KeyUtil {
    public static synchronized String genUniqueKey(){
        Random random = new Random();

        System.currentTimeMillis();

        Integer number = random.nextInt(900000)+100000;

        return System.currentTimeMillis()+String.valueOf(number);
    }
}
