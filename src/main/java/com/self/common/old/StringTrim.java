package com.self.common.old;

import org.springframework.util.StringUtils;

/**
 * 替换字符串空格字符
 * Created by LiZhong on 2018/11/13.
 */
public class StringTrim {

    public static String trim(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return str.trim().replaceAll("\\s*", "").replaceAll((char)12288 + "", "");
    }

}
