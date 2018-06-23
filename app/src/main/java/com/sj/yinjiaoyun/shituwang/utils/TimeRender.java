package com.sj.yinjiaoyun.shituwang.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wanzhiying on 2017/3/2.
 */
public class TimeRender {
    private static SimpleDateFormat formatBuilder;
   private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static String getDate(String format) {
        formatBuilder = new SimpleDateFormat(format,Locale.getDefault());
        return formatBuilder.format(new Date());
    }

    public static String getDate() {
        return getDate("MM-dd  hh:mm:ss");
    }

    /**
     * 设置显示样式显示
     * @param time
     * @return
     */
    public static String FriendlyDate(long time) {
        Date compareDate=new Date(time);
        int dayDiff = daysOfTwo(compareDate);

        if (dayDiff <= 0)
            return new SimpleDateFormat("HH:mm",Locale.getDefault()).format(compareDate);
        else
            return new SimpleDateFormat("MM月dd日 ",Locale.getDefault()).format(compareDate);
    }
    /**
     * 比较日期
     * @param compareDateDate
     * @return
     */
    public static int daysOfTwo( Date compareDateDate) {
        Date originalDate=new Date();
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(originalDate);
        int originalDay = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(compareDateDate);
        int compareDay = aCalendar.get(Calendar.DAY_OF_YEAR);

        return originalDay - compareDay;
    }

    /**
     * 由出生年月算出年龄
     * @param time
     * @return
     * @throws ParseException
     */
    public static int getAge( long time) throws ParseException {
        Calendar cal = Calendar.getInstance();
            if (cal.before(sdf.parse(getBirthday(time)))) {
                throw new IllegalArgumentException(
                        "The birthDay is before Now.It's unbelievable!");
            }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(sdf.parse(getBirthday(time)));

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }
    public static String FormatString(long time) {
        Date compareDate=new Date(time);
        long dayDiff =daysOfTwo(compareDate);
        if(dayDiff<=0){
            return new SimpleDateFormat("HH:mm",Locale.getDefault()).format(compareDate);
        }else  if( dayDiff==1){
            return "昨 天 ";
        }else if(1<dayDiff && dayDiff<=5) {
           return getWeek(time);
        }else if(5<dayDiff && dayDiff<365) {
            return new SimpleDateFormat("MM-dd",Locale.getDefault()).format(compareDate);
        }else{
            return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(compareDate);
        }


    }
  public static String getWeek(long time){
      String[] weeks={"周日","周一","周二","周三","周四","周五","周六"};
      Calendar cal= Calendar.getInstance();
      Date date=new Date(time);
      cal.setTime(date);
      int day = cal.get(Calendar.DAY_OF_WEEK);
      String today = null;
      if (day == 2) {
          today = "周一 ";
      } else if (day == 3) {
          today = "周二 ";
      } else if (day == 4) {
          today = "周三 ";
      } else if (day == 5) {
          today = "周四 ";
      } else if (day == 6) {
          today = "周五 ";
      } else if (day == 7) {
          today = "周六 ";
      } else if (day == 1) {
          today = "周日 ";
      }
      System.out.println("Today is:- " + today);
      return today;
  }
    public static String FormatChat(long time) {
        Date compareDate=new Date(time);
        long dayDiff =daysOfTwo(compareDate);
        if(dayDiff<=0){
            return new SimpleDateFormat("HH:mm",Locale.getDefault()).format(compareDate);
        }else  if( dayDiff==1){
            return "昨 天 "+new SimpleDateFormat("HH:mm",Locale.getDefault()).format(compareDate);
        }else if(1<dayDiff && dayDiff<=5) {
            return getWeekChat(time);
        }else if(5<dayDiff && dayDiff<365) {
            return new SimpleDateFormat("MM-dd HH:mm",Locale.getDefault()).format(compareDate);
        }else{
            return new SimpleDateFormat("YY-MM-dd HH:mm",Locale.getDefault()).format(compareDate);
        }


    }
    public static String getWeekChat(long time){
        String[] weeks={"周日","周一","周二","周三","周四","周五","周六"};
        Calendar cal= Calendar.getInstance();
        Date date=new Date(time);
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        String today = null;
        if (day == 2) {
            today = "周一 ";
        } else if (day == 3) {
            today = "周二 ";
        } else if (day == 4) {
            today = "周三 ";
        } else if (day == 5) {
            today = "周四 ";
        } else if (day == 6) {
            today = "周五 ";
        } else if (day == 7) {
            today = "周六 ";
        } else if (day == 1) {
            today = "周日 ";
        }
        System.out.println("Today is:- " + today);
        return today+new SimpleDateFormat("HH:mm",Locale.getDefault()).format(time);
    }

    public static String getFormat(long time){
           Date compareDate=new Date(time*1000);
    return new SimpleDateFormat("yyyy-MM",Locale.getDefault()).format(compareDate);
    }
    public static String getForm(long time){
         Date compareDate=new Date(time*1000);
         return new SimpleDateFormat("yyyy.MM",Locale.getDefault()).format(compareDate);
    }
    public static String getBirthday(long time){
        Date compareDate=new Date(time*1000);
        return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(compareDate);
    }
    public static String getTime(long time){
        Date compareDate=new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(compareDate);
    }
    public static String getBirthday1(long time){
        Date compareDate=new Date(time);
        return new SimpleDateFormat("yy-MM-dd",Locale.getDefault()).format(compareDate);
    }
    public static String getBirthday2(long time){
        Date compareDate=new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).format(compareDate);
    }
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        return format.format(date);
    }
    public static String getHours(Long time) {//可根据需要自行截取数据显示
        Date compareDate=new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm",Locale.getDefault());
        return format.format(compareDate);
    }
}
