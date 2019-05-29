package com.self.common.old;

import java.util.UUID;

/**
 * 生成uuid工具类
 * Created by lizhong on 2018/4/17.
 */

public class StringUUID {

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

}
