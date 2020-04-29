package com.self.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*
 * @Description 用于常用Object的操作，抽象的原因在 AbstractDateUtil里面
 * @Author wan
 * @Date 2019/5/20 15:29
 * @Version 1.0
 */
public class ObjectsUtil {


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
        if(args == null){
            return true;
        }
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    /**
     * 判断传入的数据是否全为空
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

    /**
     * 判断传入数据是否全不为空的
     * 结果:
     *  1. true 全部不为空
     *  2. false 全部为空
     * @param args 入参
     * @param <T> 泛型
     * @return boolean
     */
    public static <T> boolean isAllNotNull(T...args){
        if(args == null)
            return false;
        return ! isAllNull(args);
    }

    /**
     * 根据传入对象，获取对象定义字段
     * @param t  对象
     * @param <T> 泛型
     * @return List<String> 对象字段名集合
     */
    public static <T> List<String> getFieldNames(T t){
        if(t == null){
            return new ArrayList<>();
        }
        Field[] declaredFields = t.getClass().getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            fieldNames.add(name);
        }
        return fieldNames;
    }


}
