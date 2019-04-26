package com.example.mrc.campus_social.provider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mr.C on 2018/6/18.
 */

public class TimeTransform {
    /**
     * 输入时间戳变星期和日期时间
     *
     * @param time
     * @return
     */
    public static String stampToTime(Long time) {
        SimpleDateFormat dateString = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat timeString = new SimpleDateFormat("HH:mm");
        String times = timeString.format(new Date(time));
        String dates = dateString.format(new Date(time));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = dateString.parse(dates);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        String result = dates + " " + week + "   " + times;
        return result;

    }
}
