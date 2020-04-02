package com.self.common.utils;

import java.util.Arrays;
import java.util.UUID;
/*
 * @Description 创建一个抽象的常用的StringUtil.
 * @Author wan
 * @Date 2019/5/30 17:39
 * @Version 1.0
 */
public abstract class AbstractStringUtil {

    /**
     *  生成UUID
     * @return String uuid
     */
    public static  String getUUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * 该函数思想完全来自于org.apache.commons.lang3的StringUtils.isBlank ,
     * 也看过Spring的StringUtils.isEmpty,我一开始想写的就是它这种，最后我还是觉得
     * apache的更适合我想表达的意思，而且使用CharSequence  它包含更多的子类。
     * 正如我开始写这个这个工具的时候说的，优秀的思想和写法，我要借鉴，也要明白怎么写的。
     *
     * 检测传进来的CharSequence 是否空格（"  "）或 空字符串("") 或 null
     * eg :
     *    StringUtils.isBlank(null)      = true
     *    StringUtils.isBlank("")        = true
     *    StringUtils.isBlank(" ")       = true
     *    StringUtils.isBlank("bob")     = false
     *    StringUtils.isBlank("  bob  ") = false
     * @param cs 要检查的字符序列
     * @return boolean 结果
     */
    public static boolean isBlank(final CharSequence cs){
        int strLen;
        if(cs == null || (strLen = cs.length()) == 0){
            return true;
        }
        for (int i = 0; i < strLen ; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测传进来的CharSequence 是否空字符串("") 或 null
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * @param cs 要检查的字符序列
     * @return boolean 结果
     */
    public static boolean isEmpty(final CharSequence cs){
        return cs ==null || cs.length() ==0;
    }

    /**
     * 判断传入的多个字符串是否含有 null ,"","   "
     * isAnyBlank("xxx","   ")        = true
     * isAnyBlank("xxx","")           = true
     * isAnyBlank("xxx",null)         = true
     * isAnyBlank("xxx"," java ")     = false
     * @param charSequences 字符串列表
     * @return boolean 判断结果
     */
    public static boolean isAnyBlank(CharSequence...charSequences){
        if (charSequences == null) {
            return true;
        }
        return Arrays.stream(charSequences).anyMatch(AbstractStringUtil::isBlank);
    }

    /**
     * 判断传入的多个字符串是否含有null或者""
     * isAnyBlank("xxx","")           = true
     * isAnyBlank("xxx",null)         = true
     * isAnyBlank("xxx","   ")        = false
     * isAnyBlank("xxx"," java ")     = false
     * @param charSequences 字符串列表
     * @return boolean 判断结果
     */
    public static boolean isAnyEmpty(CharSequence...charSequences){
        if (charSequences == null) {
            return true;
        }
        return Arrays.stream(charSequences).anyMatch(AbstractStringUtil::isEmpty);
    }

    /**
     * 判断传入的多个字符串是否全部为空
     * @param charSequences 字符串列表
     * @return boolean
     */
    public static boolean isAllEmpty(CharSequence...charSequences){
        if(charSequences == null){
            return true;
        }
        return Arrays.stream(charSequences).allMatch(AbstractStringUtil::isEmpty);
    }
}
