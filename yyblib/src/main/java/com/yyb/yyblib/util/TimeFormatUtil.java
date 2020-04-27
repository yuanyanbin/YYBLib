package com.yyb.yyblib.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName: TimeFormatUtil
 * Description: TODO<时间转换工具类>
 * Author: Zuobb
 * Date: 2019-05-07 10:48
 * Version: v1.0
 */
public class TimeFormatUtil {

    /**
     * @param time 时间
     * @return 时间戳转换为时间字符串yyyy-MM-dd HH:mm:ss   年—月—日  小时:分钟:秒
     */
    public static String formatLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return df.format(new Date(time));
    }

    /**
     * @return 时间戳转换为时间字符串 MM-dd HH:mm   月份-日 小时:分钟
     */
    public static String formatMMddLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");

        return df.format(new Date(time));
    }

    /**
     * @return 时间戳转换为时间字符串yyyy-MM-dd     年—月—日
     */
    public static String formatStringdate(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(new Date(time));
    }

    /**
     * @return 时间戳转换为时间字符串yyyy-MM   年—月
     */
    public static String formatyyyyMMLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");

        return df.format(new Date(time));
    }

    /**
     * @return 时间戳转换为时间字符串MM-dd   月—日
     */
    public static String formatMM_DDLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        return df.format(new Date(time));
    }

    //把yyyymmdd转成MM-dd格式

    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sfstr;
    }

    /**
     * @return 时间戳转换为年份   yyyy   年
     */
    public static int formatYYYYLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String s_time = df.format(new Date(time));
        return Integer.parseInt(s_time);
    }

    /**
     * @return 时间戳转换为月份  MM   月
     */
    public static int formatMMLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("MM");
        String s_time = df.format(new Date(time));
        return Integer.parseInt(s_time);
    }

    /**
     * @return 时间戳转换为时间字符串  yyyy 年 MM 月 dd 日
     */
    public static String formatMMDDLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("MM" + "月" + "dd" + "日");
        return df.format(new Date(time));
    }

    /**
     * @return 时间戳转换为时间字符串  yyyy 年 MM 月
     */
    public static String formatYYYYMMLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy" + "年" + "MM" + "月");

        return df.format(new Date(time));
    }

    /**
     * @return 时间戳转换为时间字符串   dd    日
     */
    public static int formatDDLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("dd");

        String s_time = df.format(new Date(time));
        return Integer.parseInt(s_time);
    }

    /**
     * @return 时间戳转换为时间字符串   HH:mm    小时:分钟
     */
    public static String formatHHMMLong(long time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        return df.format(new Date(time));
    }

    /**
     * 毫秒转成固定格式时间--用于视频计时
     *
     * @param milliseconds 毫秒值
     * @return String 时间长度 例如：06:20:36
     */
    public static String millisecondsToTime(long milliseconds) {
        String timeStr = null;
        int time = Integer.parseInt((milliseconds / 1000) + "");

        int hour = 0;// 时
        int minute = 0;// 分
        int seconds = 0;// 秒

        if (milliseconds <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                seconds = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(seconds);
            } else {
                hour = minute / 60;
                minute = minute % 60;
                seconds = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
                        + unitFormat(seconds);
            }
        }
        return timeStr;
    }

    /**
     * @param i 0到9
     * @return String类型的00——09
     */
    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * @return 根据出生年月日算年龄
     */
    public static int getAge(String birthDay) {
        int age = 0;
        try {
            if (birthDay != null
                    && birthDay.contains("年")) {
                int birthYear = Integer.valueOf(birthDay.substring(
                        0,
                        birthDay.indexOf("年")));

                Date now = new Date();
                SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
                String this_year = format_y.format(now);

                age = Integer.valueOf(this_year) - birthYear;
            }
        } catch (Exception ex) {
        }

        return age;
    }

    /**
     * 根据出生日期返回年龄
     *
     * @param birthDate 出生日期
     * @return 年龄大小
     */
    public static int getAge(Date birthDate) {
        if (birthDate == null)
            throw new RuntimeException("出生日期不能为null");

        int age = 0;

        Date now = new Date();

        SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_M = new SimpleDateFormat("MM");

        String birth_year = format_y.format(birthDate);
        String this_year = format_y.format(now);

        String birth_month = format_M.format(birthDate);
        String this_month = format_M.format(now);

        // 初步，估算
        age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);

        // 如果未到出生月份，则age - 1
        if (this_month.compareTo(birth_month) < 0)
            age -= 1;
        if (age < 0)
            age = 0;
        return age;
    }

    /**
     * @return 获取月、日（简化去除0）
     */
    @SuppressLint("SimpleDateFormat")
    public static String lockFromatMMDD(long time) {
        String str = null;
        SimpleDateFormat sd = new SimpleDateFormat("MM"
                + "月"
                + "dd"
                + "日");
        str = sd.format(new Date(time));
        if (str.substring(0, 1).equals("0")) {
            str = str.substring(1, str.length());
        }
        if (str.substring(
                str.indexOf("月") + 1,
                str.indexOf("月") + 2).equals("0")) {
            str = str.substring(
                    0,
                    str.indexOf("月") + 1)
                    + str.substring(str.indexOf("月") + 2, str.length());
        }
        return str;
    }

    /**
     * @return 获取当前为星期几
     */
    public static String lockFormatWeek(long time) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        int hour = c.get(Calendar.DAY_OF_WEEK);
        String str = "" + hour;
        if ("1".equals(str)) {
            str = "星期日";
        } else if ("2".equals(str)) {
            str = "星期一";
        } else if ("3".equals(str)) {
            str = "星期二";
        } else if ("4".equals(str)) {
            str = "星期三";
        } else if ("5".equals(str)) {
            str = "星期四";
        } else if ("6".equals(str)) {
            str = "星期五";
        } else if ("7".equals(str)) {
            str = "星期六";
        }
        return str;
    }

    /**
     * @return 获取当前日期的星期
     */
    public static String getWeek(long day) {
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = smdf.format(new Date(day));
        Date dt = null;
        try {
            dt = smdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] weekDays = {
                "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * @return 获取2个日期相隔天数
     */
    public static int getDays(long startDate, long endDate) {
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
        String st = smdf.format(new Date(startDate));
        String en = smdf.format(new Date(endDate));
        Date start = null;
        Date end = null;
        try {
            start = smdf.parse(st);
            end = smdf.parse(en);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int t = (int) ((end.getTime() - start.getTime()) / (3600 * 24 * 1000));
        return t;
    }

    /**
     * 判断一天内第一个时间是否大于第二个时间
     */
    public static boolean getMinutes(long date, long date1) {
        SimpleDateFormat smdf = new SimpleDateFormat("HH：mm");
        String st = smdf.format(new Date(date));
        String en = smdf.format(new Date(date1));
        Date start = null;
        Date end = null;
        try {
            start = smdf.parse(st);
            end = smdf.parse(en);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (start.compareTo(end) < 0) { //结束日期早于开始日期
            return true;
        }
        return false;
    }

    /**
     * @param year  年
     * @param month 月
     * @return 获取这个月的最大天数
     */
    public static int getMonthDays(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * @return 获取当前时间的本月开始和结束时间
     */
    public static long[] getStartAndStopTimeOfMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        Calendar mcalendar = Calendar.getInstance();
        mcalendar.set(year, month, 0);
        long startTime = mcalendar.getTimeInMillis();
        if (month + 1 > 12) {
            mcalendar.set(year + 1, 1, 0);
        } else {
            mcalendar.set(year, month + 1, 0);
        }
        long endTime = mcalendar.getTimeInMillis();

        return new long[]{startTime, endTime};
    }

    /**
     * @return 判断是否是同一天
     */
    public static boolean isTheSameDay(long bTime, long eTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(bTime);
        int bDay = calendar.get(Calendar.DAY_OF_MONTH);
        int bYear = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(eTime);
        int eDay = calendar.get(Calendar.DAY_OF_MONTH);
        int eYear = calendar.get(Calendar.YEAR);
        if (bDay == eDay && bYear == eYear) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param time 传入的时间
     * @return true今天 false不是
     */
    public static boolean isToday(long time) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param time 传入的时间
     * @return true今天 false不是
     */
    public static boolean isYesterday(long time) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

}
