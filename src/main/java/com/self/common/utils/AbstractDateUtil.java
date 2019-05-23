package com.self.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
//        System.out.println(format(new Date(),YYYY_MM_DD));
//        System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_TIME));
//        System.out.println(dateToLocalDateTime(new Date()).format(LOCAL_DATETIME_FORMAT));
//        System.out.println(LocalDateTime.parse("2019-02-02 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        System.out.println(LocalDate.parse("2019-02-02", YYYY_MM_DD));
//        Date parse = new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-02");
//        System.out.println(parse);
//        System.out.println(getBeginOfDay(new Date()));

//        System.out.println(LocalDateTime.now(ZoneId.of("Africa/Cairo")));
//        System.out.println(Date.from(localDateTime.atZone(defaultZoneId).toInstant()));
//        System.out.println(transferWithZone(LocalDateTime.now(), ZoneId.of("Africa/Cairo")));
//        System.out.println(localDateTimeToDate(LocalDateTime.now(),ZoneId.of("Africa/Cairo")));
//        System.out.println(dateToLocalDateTime(new Date(), defaultZoneId));
//        System.out.println(dateToLocalDateTime(new Date(), ZoneId.of("Africa/Cairo")));
//        System.out.println(dateToLocalDateTime(new Date(), ZoneId.of("Africa/Cairo")));
//        System.out.println(localDateTimeToDate(LocalDateTime.now(), ZoneId.of("Africa/Cairo")));
        System.out.println(format(new Date(), YYYY_MM_DD_HH_MM_SS));
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


//    public static Date getBeginOfDay(Date date){
//        return getBeginOfDay(date,defaultZoneId);
//    }

//    public static Date getBeginOfDay(Date date,ZoneId zoneId){
//        if(AbstractObjectsUtil.isAnyNull(date,zoneId))
//            return null;
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(),zoneId);
//        LocalDateTime begin = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonthValue(),
//                localDateTime.getDayOfMonth(), 0, 0, 0);
//        return Date.from(begin.atZone(zoneId).toInstant());
//    }

//    public static Date getEndOfDay(){
//
//    }
//
//
//    public static LocalDateTime withMillsOfDay()
    // 大佬牛逼
    public static LocalDateTime transferWithZone(LocalDateTime source,ZoneId zoneId){
        Clock clock = Clock.system(zoneId);
        final Instant now = clock.instant();  // called once
        ZoneOffset offset = clock.getZone().getRules().getOffset(now);
        return LocalDateTime.ofEpochSecond(now.getEpochSecond(), now.getNano(), offset);
    }
}
