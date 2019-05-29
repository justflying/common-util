package com.self.common.old;


import com.novel.cn.dao.entity.base.BaseTimeRange;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @Author：zhaochun
 * @CreatTime： 2018/4/19 17:22
 * 时间转换
 */
@Slf4j
@Data
public class Dateutils {
    /**
     * SimpleDateFormat
     */
    private static  SimpleDateFormat format;
    /**
     * GregorianCalendar
     */
    private static  GregorianCalendar gc;
    /**
     */
    public static  String formatter = "yyyy-MM-dd HH:mm:ss";

    public static  String formatDay="yyyyMMdd";
    /**
     * 日期类型
     * @param days=n n为-,则取n天前,n为+,则取n天后的日期 n=0  当前日期 （0时0分0秒)
     * @param date
     * @param days
     * @param iSkeep true 保留时分秒 false  时分秒 为0
     * @return
     */
    public static Date getSomeDaysBeforeAfter(Date date, int days,boolean iSkeep){
        gc = new GregorianCalendar();
        gc.setTime(date);
        if(days != 0) {
            gc.add(Calendar.DAY_OF_MONTH, days);
        }
        if (!iSkeep){ //false 时分秒 为0
            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE),0,0,0);
        }
        return gc.getTime();
    }

    /**
     *  获取几天前或后的日期 时分秒归0
     * @param date days=n n为-,则取n天前,n为+,则取n天后的日期
     * @param days
     * @return
     */
    public static  Date getSomeDaysBeforeAfterAndNotKeep(Date date, int days){
         return getSomeDaysBeforeAfter(date,days,false);
    }

    /**
     * 当前时间几天前或后的日期 时分秒归0
     * @param days
     * @return
     */
    public static  Date getSomeDaysBeforeAfterNowe(int days){
        Date date = new Date();
        return getSomeDaysBeforeAfter(date,days,false);
    }

    /**
     * 获取本周周一时间
     * @param date
     * @param isKeep
     * @return iSkeep true 保留时分秒 false  时分秒 为0
     */
    public static Date getThisWeekMonday(Date date,boolean isKeep) {
        gc = new GregorianCalendar();
        gc.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = gc.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) { //国外 一个周的第一天是星期天
            gc.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        gc.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = gc.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        gc.add(Calendar.DATE, gc.getFirstDayOfWeek() - day);
        if (!isKeep){ //false 时分秒 为0
            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE),0,0,0);
        }
        return gc.getTime();
    }

    /**
     * 获取本周一的时间时分秒为0
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date){
        return  getThisWeekMonday(date,false);
    }

    /**
     * 获取日期的前几个或后几个月
     * @param date 日期
     * @param months 月数 正数标识之后 负数 表示之前
     * @param isKeep  iSkeep true 保留时分秒 false  时分秒 为0
     * @return
     */
    public static  Date getBeforOrafterMonth(Date date, int months, boolean isKeep) {
        gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.MONTH, months);
        if (!isKeep){ //false 时分秒 为0
            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE),0,0,0);
        }
        return  gc.getTime();
    }

    /**
     * 获取日期的前几个或后几个月  时分秒 为0
     * @param date 日期
     * @param months 月数 正数标识之后 负数 表示之前
     * @return
     */
    public static  Date getBeforOrafterMonth(Date date, int months){
        return getBeforOrafterMonth(date,months,false);
    }

    /**
     * 获取时间所在月的第一天
     * @param date 日期
     * @param isKeep iSkeep true 保留时分秒 false  时分秒 为0
     * @return
     */
    public static Date getFirstDayOfMonth(Date date,boolean isKeep){
        gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, gc.getActualMinimum(Calendar.DAY_OF_MONTH));
        if (!isKeep){
            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE),0,0,0);
        }
        return  gc.getTime();
    }

    /**
     * 获取时间所在月的第一天 时分秒 为0
     * @param date
     * @return
     */
    public  static  Date getFirstDayOfMonth(Date date){
        return getFirstDayOfMonth(date,false);
    }
 /*   public static  void  main(String args[])throws  Exception{
        format= new SimpleDateFormat(formatter);
        Date date = getFirstDayOfMonth( format.parse("2018-2-13 13:40:43"),false);
        System.out.println(format.format(date));
    }*/

    /**
     * 通过时间秒毫秒数判断两个时间的间隔天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔小时
     * @param date1
     * @param date2
     * @return
     */
    public static int differentHoursByMillisecond(Date date1, Date date2) {
        int hours = (int) (date2.getTime() - date1.getTime()) / (1000*3600);
        return hours;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔分钟
     * @param date1
     * @param date2
     * @return
     */
    public static int differentMinutesByMillisecond(Date date1, Date date2) {
        int minutes = (int) (date2.getTime() - date1.getTime()) / (1000*60);
        return minutes;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔秒
     * @param date1
     * @param date2
     * @return
     */
    public static int differentSecondsByMillisecond(Date date1, Date date2) {
        int seconds = (int) (date2.getTime() - date1.getTime()) / 1000;
        return seconds;
    }

    /**
     * 获取几周后或几周前的周几的时间
     * @param date 时间
     * @param week 周数 + 后 - 前
     * @param weekday 周几
     * @param isKeep 是否保留时分秒 false 时分秒为0
     * @return
     */
    public static Date getSomeDaysOfweek(Date date, int week,int weekday,boolean isKeep){
        gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.DAY_OF_MONTH,week*7); //几周后的时间
        // 获得当前日期是一个星期的第几天
        int dayWeek = gc.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) { //国外 一个周的第一天是星期天
            gc.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        gc.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = gc.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第几天的差值
        gc.add(Calendar.DATE, weekday - (day-1));
        if (!isKeep){ //false 时分秒 为0
            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE),0,0,0);
        }
        return gc.getTime();
    }

    /**
     *
     * 获取几月前后几月后的几号的时间
     * @param date 时间
     * @param month 月数 + 几月后 - 几月前
     * @param dayOfmonth 号数
     * @param isKeep 是否保留时分秒 false 时分秒为0
     * @return
     */
    public static Date getSomeDaysOfMonth(Date date,int month,int dayOfmonth,boolean isKeep){
        gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.MONTH,month); //设置月数 + 几月后 -几月前
        gc.set(Calendar.DAY_OF_MONTH,dayOfmonth); //设置号数
        if (!isKeep){ //false 时分秒 为0
            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE),0,0,0);
        }
        return gc.getTime();
    }
    public static Date getStrFormatDate(String str,String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date;
        try {
            date = df.parse(str);
            return date;
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getDateFormatString(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        if (date == null) {
            return "";
        }
        String result = formatter.format(date);
        return result;
    }
    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) {  //不同年
            int timeDistance = 0 ;
            int min = 0;
            int max = 0;
            boolean isPositive = true; //是否为正数
            if (year1 < year2) { //date2的年份大于date1的年份
                min = year1;
                max =year2;
            } else {
                min = year2;
                max = year1;
                isPositive = false; //结果返为负数
            }
            for(int i = min ; i < max ; i ++) {
                if(i%4==0 && i%100!=0 || i%400==0) {//闰年
                    timeDistance += 366;
                } else { //不是闰年
                    timeDistance += 365;
                }
            }
            return isPositive ? timeDistance + (day2-day1) : -(timeDistance + (day2-day1));
        } else {  //同一年
            return day2-day1;
        }
    }
    /**
     * 获取年份和季度（201801）
     * @param date 时间
     * @param isBefore true获取前一个季度，false获取当前时间季度
     * @author zhaolei
     * @date 2018年3月2日
     */
    public static String getSeason(Date date,boolean isBefore) {
        String season = " ";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if(isBefore) {
            c.add(Calendar.MONTH, -3);
        }
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = String.valueOf(year) + "01";
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = String.valueOf(year) + "02";
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = String.valueOf(year) + "03";
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = String.valueOf(year) + "04";
                break;
            default:
                break;
        }
        return season;
    }
 /** 
 * @Description: 获取当前季度的开始时间和结束时间
 * @Author: EX-WANGJIAN010
 * @Date: 2018/8/7 
 */ 
    public static BaseTimeRange getThisQuarter() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        return new BaseTimeRange(startCalendar.getTime(), endCalendar.getTime());
    }
    /**设置最小的时间*/
    private static void setMinTime(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    /**设置最大的时间*/
    private static void setMaxTime(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }
    /**
    * @Description:  获得上个季度的开始时间和结束时间
    * @Author: EX-WANGJIAN010
    * @Date: 2018/8/7
    */
    public static BaseTimeRange getLastQuarter() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3 - 1) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, ((int) endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        return new BaseTimeRange(startCalendar.getTime(), endCalendar.getTime());
    }

    /**
     * Description:  取当前时间的前一天的月份
     * Author: Miaohw
     * Date: 2018/8/13
     * */
    public static String getYesterdayMonth(String dateFormat, int i) {
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -i);
        date = calendar.getTime();
        return sdf.format(date);
    }
    /**
     * Description:  获取天
     * Author: Miaohw
     * Date: 2018/8/13
     * */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的月份，1-12,即yyyy-MM-dd中的MM
     * param date
     * return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日期的年,即yyyy-MM-dd中的yyyy
     * param date
     * return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    public static int calDiffMonth(String startDate,String endDate){
        int result=0;
        try {
            SimpleDateFormat sfd=new SimpleDateFormat("yyyy-MM-dd");
            Date start = sfd.parse(startDate);
            Date end = sfd.parse(endDate);
            int startYear=getYear(start);
            int startMonth=getMonth(start);
            int startDay=getDay(start);
            int endYear=getYear(end);
            int endMonth=getMonth(end);
            int endDay=getDay(end);
            if (startDay>endDay){ //1月17  大于 2月28
                if (endDay==getDaysOfMonth(getYear(new Date()),2)){   //也满足一月
                    result=(endYear-startYear)*12+endMonth-startMonth;
                }else{
                    result=(endYear-startYear)*12+endMonth-startMonth-1;
                }
            }else{
                result=(endYear-startYear)*12+endMonth-startMonth;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

}
