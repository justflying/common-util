package com.self.common.utils;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractEnumUtil {

    public  static <T,K,M extends IBaseEnum<T,K>> Optional<K> getDes(Class<M> clazz, T t){
        Optional<K> k = Optional.empty();
        if (!clazz.isEnum()) {
            throw  new IllegalArgumentException("Class["+ clazz + "]不是枚举类型");
        }
        if(Objects.isNull(t)){
            return k;
        }
        IBaseEnum[] enumConstants = clazz.getEnumConstants();
        if (Objects.isNull(enumConstants) || enumConstants.length == 0){
            return k;
        }
        for (IBaseEnum enumConstant : enumConstants) {
            if (enumConstant.getCode().equals(t)) {
                k = Optional.ofNullable((K) enumConstant.getDes());
                break;
            }
        }
        return k;
    }
}
