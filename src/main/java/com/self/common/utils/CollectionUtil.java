package com.self.common.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * @Description 集合工具
 * @Author wan
 * @Date 2020/4/24 16:26
 * @Version 1.0
 */
public class CollectionUtil {


    /**
     * 获取对象集合中某个字段的集合
     * eg:User 有个sn属性    userList - > snList
     * @param value 对象集合
     * @param mapper 获取字段的规则
     * @return List<String>
     */
    public static <T,R> List<R> getObjectFieldList(List<T> value, Function<T,R> mapper){
        // 第一个filter去掉实体为null的，第二个filter去掉字段值为null的
        return value.stream().filter(Objects::nonNull).map(mapper).filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
    }
}
