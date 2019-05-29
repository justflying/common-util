package com.self.common.old;

/**
 * @Author：zhaochun
 * @CreatTime： 2018/4/9 11:47
 * 防止sql 注入
 */
public class SqlInjectUtil {
    /***
     * 替换sql注入特殊字符
     * @param reStr
     * @return
     */
    public static  String replacesqlInject(String reStr){
        if (reStr != null ){
           if( reStr.contains("%")) {
                reStr = reStr.replaceAll("\\%","\\\\%");
            }
            if (reStr.contains("_")) {
                reStr =  reStr.replaceAll("\\_","\\\\_");
            }
        }
        return reStr;
    }
}
