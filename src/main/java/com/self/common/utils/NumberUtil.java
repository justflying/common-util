package com.self.common.utils;

import java.text.DecimalFormat;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2019/11/18 16:50
 * @Version 1.0
 */
public class NumberUtil {


    /**
     * 根据传入的double数字，保留给定的位数
     * @param num 数字
     * @param point 小说点后面保留的位数
     * @return Double
     */
    public static Double fixedPoint(double num,int point){
        DecimalFormat df = new DecimalFormat();
        df.setDecimalSeparatorAlwaysShown(false);
        df.setMaximumFractionDigits(point);
        return Double.valueOf(df.format(num));
    }
}
