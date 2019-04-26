package com.example.mrc.campus_social.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mr.C on 2018/4/18.
 */

public class TimeInfo {
    public static String geTimeNoS(){
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("MM-dd HH:mm");
        String time=df.format(date);
        return time;
    }
    public static String geTime() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
        String time = df.format(date);
        return time;
    }
}
