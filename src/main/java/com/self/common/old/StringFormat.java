package com.self.common.old;

import java.util.Map;

/**
 * @Author：zhaochun
 * @CreatTime： 2018/7/6 11:42
 * 字符串占位符替换
 */
public class StringFormat {
    /**
     * 替换字符串中 #{key} 在map中的key,对应的值的值
     * @param str
     * @param parms
     * @return
     */
    public static  String repalceFormart(String str, Map<String,Object> parms) {
        for (Map.Entry<String, Object> entry : parms.entrySet()) {
            str = str.replace("#{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }
        return str;
    }
}
