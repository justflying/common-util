package com.self.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/*
 * @Description 用于日期转换工具,把这个类定义成抽象，在使用的时候，继承这个类，
 *  写的通用的就不会全部堆到这个类，方法太多的话看起来就头大， 那些特别定制的，写在子类里面，
 *  方便我们直接查看
 * @Author wan
 * @Date 2019/5/20 11:59
 * @Version 1.0
 */
public abstract class AbstractDateUtil {

    // 默认区域Id
    private static final ZoneId defaultZoneId = ZoneId.systemDefault();
    // yyyyMMdd 格式
    private static final DateTimeFormatter YYYYMMDD  = DateTimeFormatter.BASIC_ISO_DATE;
    // yyyy-MM-dd 格式
    private static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ISO_LOCAL_DATE;
    // HH:mm:ss 格式
    private static final DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");
    // HH:mm:ss.SSS 格式
    private static final DateTimeFormatter HH_MM_SS_SSS = DateTimeFormatter.ISO_LOCAL_TIME;
    // yyyy-MM-dd HH:mm:ss 格式
    private static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //xxxx年x月x日 星期x xx:xx:xx 格式
    private static final DateTimeFormatter LOCAL_DATETIME_FORMAT  = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM);



    public static void main(String[] args) throws Exception{

//        System.out.println(LocalDateTime.now().with(ChronoField.MILLI_OF_DAY, 0));
//        System.out.println(LocalDateTime.now().toEpochSecond());
//        LocalDateTime local = LocalDateTime.of(2019, 3, 10, 12, 0, 0);
//        System.out.println("当前日期 : "+LocalDateTime.now());
//        System.out.println("对应时区时间 : "+LocalDateTime.now(ZoneId.of("Africa/Cairo")));
//        System.out.println("处理后的时区时间 : "+ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Africa/Cairo")));
//        System.out.println("处理后的时区时间2 :"+ transferWithZone(LocalDateTime.now(),ZoneId.of("Africa/Cairo")));
        System.out.println(getWeekOfYear(LocalDateTime.now()));
    }

    /**
     * 根据传入的日期以及格式来格式化日期
     * 注意： 如果传入的格式不合格，这里是没办法校验的，需要使用者自己确定格式正确性
     * @param date 日期
     * @param format 格式
     * @return String  格式化日期
     */
    public static String format(Date date,String format){
        if (AbstractObjectsUtil.isAnyNull(date,format))
            return null;
        return format(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 传入日期，以及格式化方式，把日期类型转换为字符串
     * @param date 日期 java8之前使用
     * @param dateTimeFormatter 格式 java8以后的格式
     * @return String 格式化后的日期字符串
     */
    public static String format(Date date,DateTimeFormatter dateTimeFormatter) {
        if (AbstractObjectsUtil.isAnyNull(date,dateTimeFormatter))
            return null;
        return formatWithZone(date,dateTimeFormatter,defaultZoneId);
    }

    /**
     * 传入日期、格式化方式，时区id，把日期类型转换为字符串
     * 首先会把Date 转换为 java8形式的LocalDateTime这个类保存日期和时间
     * Java8 之前Date 包含时间和日期， Java8之后 LocalDateTime 才包含日期+时间
     * @param date 日期 java8之前使用
     * @param dateTimeFormatter 格式 java8以后的格式
     * @param zoneId 时区id  java8以后的
     * @return String 格式化后的日期字符串
     */
    public static String formatWithZone(Date date,DateTimeFormatter dateTimeFormatter,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(date,zoneId))
            return null;
        return formatWithZone(dateToLocalDateTime(date, zoneId),dateTimeFormatter,zoneId);
    }

    /**
     *  根据传入的日期和格式来格式化日期
     *  注意： 如果传入的格式不合格，这里是没办法校验的，需要使用者自己确定格式正确性
     * @param localDate 日期
     * @param format 格式
     * @return String 格式化日期
     */
    public static String format(LocalDate localDate,String format){
        if (AbstractObjectsUtil.isAnyNull(localDate,format))
            return null;
        return format(localDate,DateTimeFormatter.ofPattern(format));
    }
    /**
     * 传入日期，格式化方式  把日期类型转换为字符串
     * @param localDate 日期 java8以后常用的日期类
     * @param dateTimeFormatter 格式
     * @return String 格式化后的日期字符串
     */
    public static String format(LocalDate localDate,DateTimeFormatter dateTimeFormatter) {
        if (AbstractObjectsUtil.isAnyNull(localDate,dateTimeFormatter))
            return null;
        return formatWithZone(localDate,dateTimeFormatter,defaultZoneId);
    }

    /**
     * 传入日期、格式化方式，时区id，把日期类型转换为字符串
     * 这里解释下，我为什么会把LocalDate转换成LocalDateTime,
     * 原因是:无法确定使用者在使用的时候，会传入什么样奇怪的format,当使用者传入有时间格式的时候，
     *       LocalDate.format(dateTimeFormatter) 必然会报错，因为它没有时间，也无法获取时间
     *       所以我在这里选择了把它转换为LocalDateTime 你既然想要时间，那我就给你个00:00:00
     * @param localDate 日期 java8以后常用的日期类
     * @param dateTimeFormatter 格式
     * @param zoneId 时区id  java8以后的
     * @return String 格式化后的日期字符串
     */
    public static String formatWithZone(LocalDate localDate,DateTimeFormatter dateTimeFormatter,ZoneId zoneId) {
        if (AbstractObjectsUtil.isAnyNull(localDate,dateTimeFormatter,zoneId))
            return null;
        return formatWithZone(localDate.atStartOfDay(),dateTimeFormatter,zoneId);
    }

    /**
     * 传入日期，格式化方式 把日期类型转换为字符串
     * @param localDateTime 日期
     * @param dateTimeFormatter 格式
     * @return  String格式化后的日期字符串
     */
    public static String format(LocalDateTime localDateTime,DateTimeFormatter dateTimeFormatter){
        if(AbstractObjectsUtil.isAnyNull(localDateTime,dateTimeFormatter))
            return null;
        return formatWithZone(localDateTime,dateTimeFormatter,defaultZoneId);
    }

    /**
     * 传入日期，格式化方式，时区  把日期类型转换为字符串
     * @param localDateTime  日期
     * @param dateTimeFormatter 格式
     * @param zoneId 时区
     * @return String格式化后的日期字符串
     */
    public static String formatWithZone(LocalDateTime localDateTime,DateTimeFormatter dateTimeFormatter,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(localDateTime,dateTimeFormatter,zoneId))
            return null;
        return localDateTime.atZone(zoneId).format(dateTimeFormatter);
    }
    /**
     * 传入时间、格式化方式，把时间类型转换为字符串
     * @param localTime 时间 java8以后常用的时间类
     * @param dateTimeFormatter 格式
     * @return String 格式化后的日期字符串
     */
    public static String format(LocalTime localTime,DateTimeFormatter dateTimeFormatter){
        if(AbstractObjectsUtil.isAnyNull(localTime,dateTimeFormatter))
            return null;
        return formatWithZone(localTime,dateTimeFormatter,defaultZoneId);
    }

    /**
     * 传入时间、格式化方式，时区 把时间类型转换为字符串
     * 这里也是把LocalTime 转化为了 LocalDateTime 方便时区操作
     * @param localTime 时间
     * @param dateTimeFormatter 格式
     * @param zoneId 时区
     * @return String 格式化后的日期字符串
     */
    public static String formatWithZone(LocalTime localTime,DateTimeFormatter dateTimeFormatter,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(localTime,dateTimeFormatter,zoneId))
            return null;
        return localTime.atDate(LocalDate.now()).atZone(zoneId).format(dateTimeFormatter);
    }


    /**
     * 把字符串类型根据传入格式化方式转化为日期
     * 该方法是针对日期 只包含日期，不包含时间，使用的时候需要注意
     * @param string 日期字符串
     * @param dateTimeFormatter 格式
     * @return LocalDate 格式化后的时间
     */
    public static LocalDate parseLocalDate(String string,DateTimeFormatter dateTimeFormatter){
        if(AbstractObjectsUtil.isAnyNull(string,dateTimeFormatter))
            return null;
        return parseLocalDateWithZone(string,dateTimeFormatter,defaultZoneId);
    }

    /**
     * 根据字符串、格式化方式和时区格式化日期
     * @param string  日期字符串
     * @param dateTimeFormatter  格式
     * @param zoneId 时区
     * @return LocalDate 格式化后的日期
     */
    public static LocalDate parseLocalDateWithZone(String string,DateTimeFormatter dateTimeFormatter,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(string,dateTimeFormatter,zoneId))
            return null;
        return LocalDate.parse(string, dateTimeFormatter).atStartOfDay(zoneId).toLocalDate();
    }

    /**
     * 把字符串，根据传入的格式化方式转换为日期
     * @param string 日期字符串  改字符串需要包含时间，不然会报错
     * @param dateTimeFormatter 格式
     * @return LocalDateTime 日期
     */
    public static LocalDateTime parseLocalDateTime(String string,DateTimeFormatter dateTimeFormatter){
        if(AbstractObjectsUtil.isAnyNull(string,dateTimeFormatter))
            return null;
        return parseLocalDateTimeWithZone(string,dateTimeFormatter,defaultZoneId);
    }

    /**
     * 根据字符串、格式化方式以及时区来转换为日期
     * @param string 日期字符串
     * @param dateTimeFormatter 日期格式
     * @param zoneId 时区
     * @return LocalDateTime 日期
     */
    public static LocalDateTime parseLocalDateTimeWithZone(String string,DateTimeFormatter dateTimeFormatter,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(string,dateTimeFormatter,zoneId))
            return null;
        return LocalDateTime.parse(string, dateTimeFormatter).atZone(zoneId).toLocalDateTime();
    }

    /**
     * 把Date 转换为 LocalTime
     * @param date 日期类型   java8之前，
     * @return LocalTime 时间
     */
    public static LocalTime dateToLocalTime(Date date) {
        if (date == null)
            return null;
        return dateToLocalTime(date, defaultZoneId);
    }

    /**
     * 根据日期，时区把Date转化为 LocalTime
     * @param date 日期
     * @param zoneId 区域id
     * @return LocalTime 时间
     */
    public static LocalTime  dateToLocalTime(Date date,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(date,zoneId))
            return null;
        return dateToLocalDateTime(date,zoneId) == null ? null : dateToLocalDateTime(date,zoneId).toLocalTime();
    }

    /**
     * 把Date 转换为 LocalDate
     * @param date 日期
     * @return LocalDate 日期
     */
    public static LocalDate  dateToLocalDate(Date date){
        if (date == null)
            return null;
        return dateToLocalDate(date, defaultZoneId);
    }

    /**
     * 根据日期和时区 把Date 转化为LocalDate
     * @param date 日期
     * @param zoneId 时区
     * @return LocalDate 日期
     */
    public static LocalDate  dateToLocalDate(Date date,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(date,zoneId))
            return null;
        return dateToLocalDateTime(date,zoneId) == null ? null :  dateToLocalDateTime(date,zoneId).toLocalDate();
    }

    /**
     * 把Date 转化为 LocalDateTime
     * @param date 日期
     * @return LocalDateTime 日期
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null)
            return null;
        return dateToLocalDateTime(date, defaultZoneId);
    }

    /**
     *  根据日期和时区 把Date 转化为LocalDateTime
     * @param date 日期
     * @param zoneId 时区
     * @return LocalDateTime 日期
     */
    public static LocalDateTime dateToLocalDateTime(Date date,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(date,zoneId))
            return null;
        return LocalDateTime.ofInstant(date.toInstant(), zoneId);
    }

    /**
     * 把LocalDate 转换为 Date
     * @param localDate 日期
     * @return Date 日期
     */
    public static Date localDateToDate(LocalDate localDate){
        if(localDate == null)
            return null;
        return localDateToDate(localDate,defaultZoneId);
    }

    /**
     * 根据日期，时区把LocalDate 转化为 Date
     * @param localDate 日期
     * @param zoneId 时区
     * @return  Date 日期
     */
    public static Date localDateToDate(LocalDate localDate,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(localDate,zoneId))
            return null;
        return localDateTimeToDate(localDate.atStartOfDay(),zoneId);
    }

    /**
     * 把LocalDateTime 转为 Date
     * @param localDateTime 日期
     * @return Date 日期
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        if(localDateTime == null)
            return null;
        return localDateTimeToDate(localDateTime,defaultZoneId);
    }

    /**
     * 根据日期和时区 把LocalDateTime 转化为Date
     * @param localDateTime 日期
     * @param zoneId 时区
     * @return Date 日期
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime,ZoneId zoneId){
        if(AbstractObjectsUtil.isAnyNull(localDateTime,zoneId))
            return null;
        return Date.from(transferWithZone(localDateTime,zoneId).atZone(defaultZoneId).toInstant());
    }

    /**
     * 传入某个日期，然后获取该日期的凌晨时间
     * eg: 传入 : 2019-05-21 16:55:55 Date类型
     *     输出 : 2019-05-21 00:00:00 Date类型
     * @param date 日期
     * @return Date 目的日期
     */
    public static Date getBeginOfDay(Date date){
        if (date == null)
            return null;
        return localDateTimeToDate(getBeginOfDay(dateToLocalDateTime(date)));
    }

    /**
     * 传入某个日期，然后获取该日期当天最后时间
     * eg: 传入 : 2019-05-21 16:55:55 Date类型
     *     输出 : 2019-05-21 23:59:59 Date类型
     * @param date 日期
     * @return Date 目的日期
     */
    public static Date getEndOfDay(Date date){
        if(date ==null)
            return null;
        return localDateTimeToDate(getEndOfDay(dateToLocalDateTime(date)));
    }

    /**
     * 传入某个日期，然后获取该日期的凌晨时间
     * eg: 传入 : 2019-05-21 16:55:55 LocalDateTime类型
     *     输出 : 2019-05-21 00:00:00 LocalDateTime类型
     * @param localDateTime 日期
     * @return LocalDateTime 目的日期
     */
    public static LocalDateTime getBeginOfDay(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;
        return withZeroMillsOfDay(localDateTime);
    }

    /**
     * 传入某个日期，然后获取该日期当天最后时间
     * eg: 传入 : 2019-05-21 16:55:55 LocalDateTime类型
     *     输出 : 2019-05-21 23:59:59 LocalDateTime类型
     * @param localDateTime 日期
     * @return LocalDateTime 目的日期
     */
    public static LocalDateTime getEndOfDay(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;
        return localDateTime.withHour(23).withMinute(59).withSecond(59);
    }

    /**
     * 在某个日期上 增加N天
     * 理论上直接 localDateTime.plusDays()也能实现，在这只是做一个封装
     * 提供出去，虽然有点多余，但是从Util的角度上，还是把它写下来了
     * @param localDateTime 源日期
     * @param days 天数  为负数的时候就是减
     * @return LocalDateTime 目的日期
     */
    public static LocalDateTime plusDays(LocalDateTime localDateTime, long days) {
        return plusWithUnit(localDateTime,days,ChronoUnit.DAYS);
    }

    /**
     * 在某个日期上 增加N小时
     * 效果等同 localDateTime.plusHours()
     * @param localDateTime 源日期
     * @param hours 小时数
     * @return LocalDateTime 目的日期
     */
    public static LocalDateTime plusHours(LocalDateTime localDateTime,long hours){
        return plusWithUnit(localDateTime,hours,ChronoUnit.HOURS);
    }

    /**
     * 在某个日期上，增加几分钟
     * @param localDateTime 源日期
     * @param minutes 分钟数
     * @return LocalDateTime 目的日期
     */
    public static LocalDateTime plusMinutes(LocalDateTime localDateTime,long minutes){
        return plusWithUnit(localDateTime,minutes,ChronoUnit.MINUTES);
    }

    /**
     * 在某个日期上，增加几个月
     * @param localDateTime 源日期
     * @param months 月数
     * @return LocalDateTime 目的日期
     */
    public static LocalDateTime plusMonths(LocalDateTime localDateTime,long months){
        return plusWithUnit(localDateTime,months,ChronoUnit.MONTHS);
    }

    /**
     * 在某个日期上，增加几年
     * @param localDateTime 源日期
     * @param years 年数
     * @return LocalDateTime 目的日期
     */
    public static LocalDateTime plusYears(LocalDateTime localDateTime,long years){
        return plusWithUnit(localDateTime,years, ChronoUnit.YEARS);
    }

    /**
     * 传入日期，单位，计数 然后计算
     * @param localDateTime  源日期
     * @param amountToAdd  要被加的数据
     * @param unit 单位
     * @return LocalDateTime 目的日期
     */
    public static LocalDateTime plusWithUnit(LocalDateTime localDateTime, long amountToAdd, TemporalUnit unit){
        if(AbstractObjectsUtil.isAnyNull(localDateTime,amountToAdd,unit))
            return null;
        return localDateTime.plus(amountToAdd,unit);
    }

    /**
     * 该方法的启蒙来自joda-time 这款优秀的时间处理工具
     * 主要作用就是把传入的时间时分秒全部替换成0,
     * joda-time 里面能够自己传入Mills,没它做的强大，这里直接默认0。
     * @return LocalDateTime 目的日期
     */
    public static LocalDateTime withZeroMillsOfDay(LocalDateTime localDateTime){
        if(localDateTime ==null)
            return null;
        return localDateTime.with(ChronoField.MILLI_OF_DAY,0);
    }

    /**
     * 根据传进来的日期，时区，把传入的时间换算成对应时区的时间
     * @param source 源日期
     * @param zoneId 时区
     * @return LocalDateTime 目标日期
     */
    public static LocalDateTime transferWithZone(LocalDateTime source,ZoneId zoneId){
        if(AbstractObjectsUtil.isAllNull(source,zoneId)){
            return null;
        }
        return LocalDateTime.ofInstant(source.atZone(defaultZoneId).toInstant(),zoneId);
    }

    /**
     * 获取当前年和本周是一年的第多少周
     * 例如: 2019-22  就是19年的第22周
     * @param localDateTime 时间
     * @return String 结果
     */
    public static String getWeekOfYear(LocalDateTime localDateTime){
<<<<<<< HEAD
        int week = localDateTime.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int year = localDateTime.getYear();
        return week+"-"+year;
=======
        if(localDateTime == null)
            return null;
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-WW"));
    }

    /**
     * 获取当前时间是一周的第几天
     * @param localDateTime 时间
     * @return Integer 第几天
     */
    public static Integer getDayOfWeek(LocalDateTime localDateTime){
        if(localDateTime == null)
            return null;
        return localDateTime.getDayOfWeek().getValue();
>>>>>>> a7b4904d228053b5c7e3f26e50454938dba5d342
    }
}
