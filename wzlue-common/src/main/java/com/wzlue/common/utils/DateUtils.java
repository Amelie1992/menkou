package com.wzlue.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期处理
 *
 * @author chenshun
 * @email wzlue.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     *
     * @param startAt 开始时间，例：2017-04-04
     * @param endAt   结束时间，例：2017-04-11
     * @return 返回日期数组
     */
    public static List<String> queryData(String startAt, String endAt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<String> dates = new ArrayList<>();
        try {
            Date startDate = dateFormat.parse(startAt);
            Date endDate = dateFormat.parse(endAt);
            dates.addAll(queryData(startDate, endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     *
     * @param startAt 开始时间，例：2017-04-04
     * @param endAt   结束时间，例：2017-04-11
     * @return 返回日期数组
     */
    public static List<String> queryData(Date startAt, Date endAt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dates = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(startAt);
        Calendar end = Calendar.getInstance();
        end.setTime(endAt);
        while (start.before(end) || start.equals(end)) {
            dates.add(dateFormat.format(start.getTime()));
            start.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dates;
    }

    //判断是否是周末  周六或周日
    public static boolean isWeekend(String date, Integer workDay) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = format1.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        Boolean flag = false;
        switch (workDay) {
            case 5:
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    flag = true;
                }
                break;
            case 6:
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    flag = true;
                }
                break;
            case 7:
                flag = true;
                break;

        }
        return flag;
    }

    /**
     * 将timestamp转为LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime timestamToDatetime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }


    /**
     * @Author：
     * @Description：更加输入日期，获取输入日期的前一天
     * @Date：
     * @strData：参数格式：yyyy-MM-dd
     * @return：返回格式：yyyy-MM-dd
     */
    public static String getPreDateByDate(String strData) {
        String preDate = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(strData);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 - 1);
        preDate = sdf.format(c.getTime());
        return preDate;
    }
}
