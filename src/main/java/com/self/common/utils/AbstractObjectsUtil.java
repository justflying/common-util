package com.self.common.utils;

import java.util.Arrays;
import java.util.Objects;

/*
 * @Description 用于常用Object的操作，抽象的原因在 AbstractDateUtil里面
 * @Author wan
 * @Date 2019/5/20 15:29
 * @Version 1.0
 */
public abstract class AbstractObjectsUtil {


    /**
     * 判断传入数据是否有为空的
     * 结果:
     *  1. true  存在为空的数据
     *  2. false 不存在为空的数据
     * @param args 数据
     * @param <T> 泛型
     * @return boolean
     */
    public static <T> boolean isAnyNull(T...args){
        if(args == null)
            return true;
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    /**
     * 判断传入数据是否全为空的
     * 结果:
     *  1. true  全为空
     *  2. false 不全为空
     * @param args 数据
     * @param <T> 泛型
     * @return boolean
     */
    public static <T> boolean isAllNull(T...args){
        if(args == null)
            return true;
        return Arrays.stream(args).allMatch(Objects::isNull);
    }
}
