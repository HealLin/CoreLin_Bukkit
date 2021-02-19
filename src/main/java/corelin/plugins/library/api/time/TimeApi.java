package corelin.plugins.library.api.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * 对时间操作
 * @author 择忆霖心
 */
public class TimeApi {


    /**
     * 获取当前之间，如果没有规定格式，默认yyyy年MM月dd日HH时mm分ss秒
     * @param pattern 规定格式
     * @return 时间
     */
    public static String getTime(String pattern){
        if (pattern == null || pattern.isEmpty()){
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            return df.format(new Date());
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    /**
     * 获取当前时间，单位为秒
     * @return 现在时间
     */
    public static long getTime(){
        return System.currentTimeMillis() / 1000;
    }

    public static long getDay(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //将小时至0
        cal.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        cal.set(Calendar.MINUTE, 0);
        //将秒至0
        cal.set(Calendar.SECOND,0);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() / 1000;
    }

    public static long getHandleDay(HandleDay handleDay){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //将小时至0
        cal.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        cal.set(Calendar.MINUTE, 0);
        //将秒至0
        cal.set(Calendar.SECOND,0);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);
        handleDay.run(cal);
        return cal.getTimeInMillis() / 1000;
    }

    public interface HandleDay{

        void run(Calendar cal);
    }

    /**
     * 添加时间
     * @param pattern 返回格式
     * @param day 自定义增加多少时间
     * @return 返回时间格式
     */
    public static String addTime(String pattern , HandleDay day){
        if (pattern == null || pattern.isEmpty()){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            day.run(cal);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String format = sdf.format(cal.getTime());
            return format;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        day.run(cal);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(cal.getTime());
        return format;
    }

    public static String dateToTimeString(Date date , String pattern){
        SimpleDateFormat simpleDateFormat;
        if (pattern == null || pattern.isEmpty()){
            simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        }
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 添加时间
     * @param day 自定义增加多少时间
     * @return 返回Date
     */
    public static Date addTime( HandleDay day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        day.run(cal);
        return cal.getTime();
    }


    public static String snoozeDay(String pattern){
        if (pattern == null || pattern.isEmpty()){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE,1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String format = sdf.format(cal.getTime());
            return format;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE,1);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(cal.getTime());
        return format;
    }

    public static long snoozeDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND,0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE,100);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        sdf.format(date);
        return (date.getTime() / 1000);
    }
}
