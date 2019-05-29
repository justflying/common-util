package com.self.common.old;

import java.util.UUID;

/**
 * @Author：zhaochun
 * @CreatTime： 2018/4/11 17:50
 * 生成32
 */
public class GenerateUUID {
    /**
     * 生产32为UUID
     * @return
     */
    public static String makeUUID(){
        String uuidStr = UUID.randomUUID().toString().replaceAll("-", "");
        return uuidStr;
    }

}
