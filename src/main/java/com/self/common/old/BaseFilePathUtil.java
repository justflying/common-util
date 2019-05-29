package com.self.common.old;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by lizhong on 2018/5/16.
 */
public class BaseFilePathUtil {

    public static String getFilePath(String baseFilePath, int hashCode) {
        if (StringUtils.isNotEmpty(baseFilePath)) {
            String[] baseFilePaths = baseFilePath.split(",");
            hashCode = Math.abs(hashCode);
            return baseFilePaths[hashCode%baseFilePaths.length];
        }
        return null;
    }

    /**
     * 去除字符串首空格
     * @param str
     * @return
     */
 public static String removeSpaceAtHeader(String str){
        if (str== null ){
            return null;
        }
        int len = str.length();
        char[] chars = str.toCharArray();
        int st = 0;
        while ((st < len) && (chars[st] <= ' ')) {
         st++;
     }
     return (st > 0) ? str.substring(st, len) : str;

 }
}
