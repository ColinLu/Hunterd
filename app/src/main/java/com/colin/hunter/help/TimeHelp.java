package com.colin.hunter.help;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeHelp {

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentlyTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return getTimeString(simpleDateFormat, System.currentTimeMillis());
    }

    /**
     * 获取当前时间
     *
     * @return自定义时间格式
     */
    public static String getCurrentlyTime(SimpleDateFormat simpleDateFormat) {
        return getTimeString(simpleDateFormat, System.currentTimeMillis());
    }

    /**
     * 获取时间字符串
     *
     * @param timestamp
     * @return"yyyy-MM-dd HH:mm:ss"
     */
    public static String getTimeString(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return simpleDateFormat.format(new Date(timestamp));
    }

    /**
     * 返回时间字符串
     *
     * @param simpleDateFormat 时间格式化
     * @param timestamp        时间戳
     * @return
     */
    public static String getTimeString(SimpleDateFormat simpleDateFormat, long timestamp) {
        return simpleDateFormat.format(new Date(timestamp));
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 判断两个unix时间戳是否在同一天
     *
     * @param unixTime1
     * @param unixTime2
     * @return
     */
    public static boolean onTheSameDay(long unixTime1, long unixTime2) {
        Date dateStart = new Date(unixTime1);
        Date dateEnd = new Date(unixTime2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStringStart = dateFormat.format(dateStart);
        String dateStringEnd = dateFormat.format(dateEnd);
        if (dateStringStart.equals(dateStringEnd)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得含起止时间的字符串
     *
     * @param start
     * @param end
     * @return
     */
    public static String toSingleModeString(long start, long end) {
        Date dateStart = new Date(start);
        Date dateEnd = new Date(end);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日 (EEEE) H:mm", Locale.getDefault());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        String dateStringStart = dateFormat.format(dateStart);
        String dateStringEnd = simpleDateFormat.format(dateEnd);
        return dateStringStart + " - " + dateStringEnd;
    }

    /**
     * 获得单独的日期字符串
     *
     * @param time
     * @return
     */
    public static String toMultipleModeDateString(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日 (EEEE)", Locale.getDefault());
        String dateString = dateFormat.format(date);

        return dateString;
    }

    /**
     * 获得单独的时间字符串
     *
     * @param time
     * @return
     */
    public static String toMultipleModeTimeString(long time) {
        Date date = new Date(time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("K:mm", Locale.getDefault());
        String timeString = timeFormat.format(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int time_24_system = calendar.get(Calendar.HOUR_OF_DAY);

        String prefix = "";
        if (time_24_system >= 0 && time_24_system < 6) {
            prefix = "凌晨 ";
        } else if (time_24_system >= 6 && time_24_system < 11) {
            prefix = "上午 ";
        } else if (time_24_system >= 11 && time_24_system < 13) {
            prefix = "中午 ";
        } else if (time_24_system >= 13 && time_24_system < 18) {
            prefix = "下午 ";
        } else {
            prefix = "晚上 ";
        }

        return prefix + timeString;
    }

    /**
     * 获得-分隔的日期
     *
     * @param time
     * @return
     */
    public static String toDateString(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String dateString = dateFormat.format(date);
        return dateString;
    }

    /**
     * 根据unix时间戳，获得年龄
     *
     * @param birthday
     * @return
     */
    public static int getAge(long birthday) {
        Calendar calendarBirthday = Calendar.getInstance();
        calendarBirthday.setTimeInMillis(birthday);
        Calendar calendarToday = Calendar.getInstance();
        return calendarToday.get(Calendar.YEAR) - calendarBirthday.get(Calendar.YEAR);
    }

    /**
     * 根据时间字符串变成时间戳
     *
     * @param string
     * @return
     */
    public static long getTimeStemp(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
        Date date = null;
        try {
            date = simpleDateFormat.parse(string);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        long timeStemp = date.getTime();
        return timeStemp;
    }

    /**
     * 根据时间字符串变成时间戳
     *
     * @param string
     * @return
     */
    public static String getTimeStempToString(long timeStemp) {
        Date dateStart = new Date(timeStemp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
        String dateStringStart = dateFormat.format(dateStart);
        return dateStringStart;
    }

    /**
     * 根据时间字符串变成时间戳
     *
     * @param string
     * @return
     */
    public static String getRedPackedTime(long timeStemp) {
        Date dateStart = new Date(timeStemp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStringStart = dateFormat.format(dateStart);
        return dateStringStart;
    }

    /**
     * 获取两个日期相差的日历天数（即，不考虑当天的具体时间） 例如，2013年5月15日23点4分与2013年5月16日0点30分，相差一天
     * Date类型精确到天 星期几 Calendar 格式化时间
     *
     * @param calendar1 减数
     * @param calendar2 被减数
     * @return calendar1 - calendar2
     */
    public static long DateDiff(Calendar calendar1, Calendar calendar2) {
        Calendar calendar1temp = Calendar.getInstance();
        calendar1temp.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH));

        Calendar calendar2temp = Calendar.getInstance();
        calendar2temp.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH),
                calendar2.get(Calendar.DAY_OF_MONTH));
        long datediff = (calendar1temp.getTimeInMillis() - calendar2temp.getTimeInMillis()) / (24 * 60 * 60 * 1000);

        return datediff;
    }

    /**
     * 消息在五秒以内
     *
     * @param sent_at
     * @param sent_at2
     * @return
     */
    public static boolean isCloseEnough(long sent_at, long sent_at2) {
        if (Math.abs(sent_at - sent_at2) < 5000) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 规定时间内取消运动报名
     *
     * @param actvity_start
     * @param now
     * @param hour
     * @return
     */
    public static boolean canCancle(long actvity_start, long now, int hour) {
        long time = (long) (hour * 1000 * 60 * 60);
        if (Math.abs(actvity_start - now) > time) {
            return true;
        } else {
            return false;
        }
    }

    private final static double ONE_MINUTE = 60000;
    private final static double ONE_HOUR = ONE_MINUTE * 60;
    private final static double ONE_DAY = ONE_HOUR * 24;
    private final static double TWO_DAY = ONE_DAY * 2;
    private final static double THREE_DAY = ONE_DAY * 3;

    /**
     * 评论时间
     *
     * @param time
     * @return
     */
    public static String getCommontTime(Long time) {
        // double residue_time = (double) (System.currentTimeMillis() - time);
        // if (residue_time < ONE_MINUTE || time == 0) {
        // return "刚刚";
        // } else if (residue_time < ONE_HOUR) {
        // int minute = (int) Math.ceil(residue_time / ONE_MINUTE);
        // return String.valueOf(minute) + "分钟前";
        // } else if (residue_time < ONE_DAY) {
        // int hour = (int) Math.ceil(residue_time / ONE_HOUR);
        // return String.valueOf(hour) + "小时前";
        // } else if (residue_time < THREE_DAY) {
        // return "昨天";
        // } else {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
        // Locale.CHINA);
        // return getTimeString(sdf, time);
        // }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        if (null == time || time == 0) {
            time = System.currentTimeMillis();
        }
        return getTimeString(sdf, time);
    }

    public static String getMessageTime(long time) {
        if (null != String.valueOf(time) && time != 0) {
            double residue_time = (double) (System.currentTimeMillis() - time);
            if (residue_time < ONE_MINUTE || time == 0) {
                return "刚刚";
            } else if (residue_time < ONE_HOUR) {
                int minute = (int) Math.ceil(residue_time / ONE_MINUTE);
                return String.valueOf(minute) + "分钟前";
            } else if (residue_time < ONE_DAY) {
                int hour = (int) Math.ceil(residue_time / ONE_HOUR);
                return String.valueOf(hour) + "小时前";
            } else if (residue_time < THREE_DAY) {
                return "昨天";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                        Locale.CHINA);
                return getTimeString(sdf, time);
            }
        }
        return "刚刚";
    }

    /**
     * "yyyy-MM-dd  HH:mm"
     *
     * @param time
     * @return
     */
    public static String getFeedbackTimeString(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm", Locale.getDefault());
        String dateString = dateFormat.format(date);
        return dateString;
    }
}
