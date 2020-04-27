package com.yyb.yyblib.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeUtil {
    /**
     * 获取当前时间
     *
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @return 当前时间
     */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static long getCurrentTimeMillis() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * 获取当前时间为本月的第几周
     *
     * @return WeekOfMonth
     */
    public static int getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        return week - 1;
    }

    /**
     * 获取当前时间为本周的第几天
     *
     * @return DayOfWeek
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }

    /**
     * 获取当前时间的年份
     *
     * @return 年份
     */
    public static int getYear() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月份
     *
     * @return 月份
     */
    public static int getMonth() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取当前时间是哪天
     *
     * @return 哪天
     */
    public static int getDay() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 时间比较大小
     *
     * @param date1  date1
     * @param date2  date2
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @return 1:date1大于date2；
     * -1:date1小于date2
     */
    public static int compareDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间比较大小
     *
     * @param date1 date1
     * @param date2 date2
     * @return 1:date1大于date2；
     * -1:date1小于date2
     */
    public static int compareDate(Date date1, Date date2) {
        try {
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间加减
     *
     * @param day       如"2015-09-22"
     * @param dayAddNum 加减值
     * @return 结果
     */
    public static String timeAddSubtract(String day, int dayAddNum) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date newDate = new Date(simpleDateFormat.parse(day).getTime() + dayAddNum * 24 * 60 * 60 * 1000);
            return simpleDateFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 毫秒格式化
     * 使用unixTimestamp2BeijingTime方法
     *
     * @param millisecond 如"1449455517602"
     * @param format      如"yyyy-MM-dd HH:mm:ss"
     * @return 格式化结果
     */
    @Deprecated
    public static String millisecond2String(long millisecond, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(millisecond);
    }

    /**
     * 时间戳转北京时间
     *
     * @param millisecond 如"1449455517602"
     * @param format      如"yyyy-MM-dd HH:mm:ss"
     * @return 格式化结果
     */
    public static String unixTimestamp2BeijingTime(long millisecond, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(millisecond);
    }

    /**
     * 北京时间转时间戳
     * 注意第一个参数和第二个参数格式必须一样
     *
     * @param beijingTime 如"2016-6-26 20:35:9"
     * @param format      如"yyyy-MM-dd HH:mm:ss"
     * @return 时间戳
     */
    public static long beijingTime2UnixTimestamp(String beijingTime, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        long unixTimestamp = 0;
        try {
            Date date = simpleDateFormat.parse(beijingTime);
            unixTimestamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixTimestamp;
    }

    public static String unixTimestamp2BeijingTime(String beijingTime, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(beijingTime);
            long unixTimestamp = date.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return dateFormat.format(unixTimestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 通过给定的时间计算多少分钟后的时间
     *
     * @param date
     * @param minute
     * @return 小时 如 12:00
     * @author drowtram
     */
    public static String calTimeByAddMinute(long date, int minute) {
        long d = date + minute * 60 * 1000;
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(d);
    }

    /**
     * 计算指定时间和给定分钟数后的时间段字符串
     *
     * @param date
     * @param minute
     * @return 小时 如 12:00
     * @author drowtram
     */
    public static String calTime(long date, int minute) {
        long d = date + minute * 60 * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return new StringBuilder().append(sdf.format(date)).append("-").append(sdf.format(d)).toString();
    }

    /**
     * 根据毫秒数获取时长
     *
     * @param length
     * @return
     */
    public static String getTimeLength(long length) {
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long day = length / nd;// 计算差多少天
        long hour = length % nd / nh;// 计算差多少小时
        long min = length % nd % nh / nm;// 计算差多少分钟
        long sec = length % nd % nh % nm / ns;// 计算差多少秒
        if (day > 0) {
            return day + "天" + hour + "时" + min + "分" + sec + "秒";
        }
        if (hour > 0) {
            return hour + "时" + min + "分" + sec + "秒";
        }
        if (min > 0) {
            return min + "分" + sec + "秒";
        }
        if (sec > 0) {
            return sec + "秒";
        }
        return length + "毫秒";
    }

    /**
     * 根据秒数获取时长
     *
     * @param length 单位秒
     * @return 格式 23:34
     */
    public static String getTimeString(long length) {
        long sec = length % 60;
        long min = length / 60;
        String secStr;
        String minStr;
        if (sec >= 10) {
            secStr = String.valueOf(sec);
        } else {
            secStr = "0" + sec;
        }
        if (min >= 10) {
            minStr = String.valueOf(min);
        } else {
            minStr = "0" + min;
        }
        return minStr + ":" + secStr;
    }


    /**
     * 根据秒数获取时长
     *
     * @param length 单位秒
     * @param split  分和秒之前的分隔符
     * @return 根据分隔符返回不同的字符串 例如 23:34
     */
    public static String getTimeString(long length, String split) {
        long sec = length % 60;
        long min = length / 60;
        return String.format(Locale.getDefault(), "%1$02d%2$s%3$02d", min, split, sec);
    }

    /**
     * 根据时间字符串获取毫秒数
     *
     * @param strTime
     * @return
     */
    public static long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnMillis;
    }

    /**
     * 传入开始时间和结束时间字符串来计算消耗时长
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static String getTimeExpend(String startTime, String endTime) {
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数

        return longHours + "小时" + longMinutes + "分";
    }

    /**
     * 传入开始时间和结束时间字符串来计算消耗时长
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static long getTimeExpend2(String startTime, String endTime) {
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数

        return longHours;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String getWeek(String pTime) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 1) {
//            Week += "天";
            Week += "日";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 2) {
            Week += "一";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 3) {
            Week += "二";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 4) {
            Week += "三";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 5) {
            Week += "四";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 6) {
            Week += "五";
        }
        if ((c.get(Calendar.DAY_OF_WEEK)) == 7) {
            Week += "六";
        }

        return Week;
    }

    /**
     * 时间戳算周几
     *
     * @param data
     * @return
     */
    public static String getWeekOfDate(long data) {
        Date date = new Date(data);
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        date = null;
        return weekDays[w];
    }

    /**
     * 根据时间戳获取指定时间相对于当前时间是今天还是昨天还是哪一天
     *
     * @param date
     * @return
     */
    public static String getTodayOrYesterday(long date) {//date 是存储的时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        //所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (date + offSet) / 86400000;
        long intervalTime = start - today;
        //-2:前天,-1：昨天,0：今天,1：明天,2：后天
        String strDes = "";
        if (intervalTime == 0) {
            strDes = "今天";//今天
        } else if (intervalTime == -1) {
            strDes = "昨天";//昨天
        } /*else if (intervalTime==-2){
            strDes= "前天";//前天
        } */ else if (intervalTime == 1) {
            strDes = "明天";
        } /*else if (intervalTime==2) {
            strDes = "后天";
        } */ else {
            strDes = simpleDateFormat.format(date);
        }
        return strDes;
    }

    /**
     * 获取网络时间
     *
     * @return
     */
    public static long getNetTime() {
        long localTime = System.currentTimeMillis();
        return localTime;
    }

    public static String formatDurationPB(int duration) {
        int _duration = Math.abs(duration);
        int hour = _duration / 3600;
        int temp = _duration % 3600;

        int minutes = temp / 60;
        temp = temp % 60;
        int seconds = temp % 60;

        String res;
        if (hour > 0) {
            res = String.format("%02d:%02d:%02d", hour, minutes, seconds);
        } else {
            res = String.format("%02d:%02d", minutes, seconds);
        }

        return duration >= 0 ? res : "- " + res;
    }

    public static String formatDurationPB(int duration, boolean hasHour) {
        int _duration = Math.abs(duration);
        int hour = _duration / 3600;
        int temp = _duration % 3600;

        int minutes = temp / 60;
        temp = temp % 60;
        int seconds = temp % 60;

        String res;
        if (hour > 0 || hasHour) {
            res = String.format("%02d:%02d:%02d", hour, minutes, seconds);
        } else {
            res = String.format("%02d:%02d", minutes, seconds);
        }

        return duration >= 0 ? res : "- " + res;
    }

    //判断两个时间是否为同一天
    public static boolean isSameDate(long time1, long time2) {
        Date date1 = new Date(time1);
        Date date2 = new Date(time2);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    // -2:前天             -1：昨天            0：今天             1：明天             2：后天
    public static long getDayString(long time) {
        Date startDate = new Date(time);
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (startDate.getTime() + offSet) / 86400000;
        return (start - today);
    }

    /**
     * 返回 2：00-3：00
     *
     * @param hour
     * @return
     */
    public static String setHour(int hour) {
        int hour1 = hour + 1;
        return hour + ":00" + "-" + hour1 + ":00";
    }
}
