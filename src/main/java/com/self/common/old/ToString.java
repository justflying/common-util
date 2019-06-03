package com.self.common.old;

import java.lang.reflect.Field;


public class ToString {

    public static String toString(Object clazs) {
        StringBuffer sb = new StringBuffer();
        getParamAndValue(clazs, clazs.getClass(),sb);
        return sb.toString();
    }

    private static void getParamAndValue(Object object, Class<?> clazz,StringBuffer sb) {
        Class<?> sc = clazz.getSuperclass();
        Field[] sfields = sc.getDeclaredFields();
        if (sfields.length > 0) {
            getParamAndValue(object, sc,sb);
        }
        Field[] fields = clazz.getDeclaredFields();
        String className = clazz.getSimpleName();
        sb.append(className+"{");
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (null != f.get(object)){
                    sb.append(f.getName() + "=" + f.get(object) + ",");
                }else {
                    sb.append(f.getName() + "=" + null + ",");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //删除最后一个逗号
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
    }
}
