import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * test01
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: PACKAGE_NAME
 * Author: lazydsr
 * Create: 2018/06/18 20:14
 * Version: 0.1
 * Info: @TODO:....
 */
public class test01 {
    public static void main(String[] args) {
        //System.out.println(calculateHour("2018-06-18 09:00:00", "2018-06-18 10:30:00"));
        List getdate = getdate("2018-09-11", "2018-09-11");
        for (int i = 0; i < getdate.size(); i++) {
            System.out.println(getdate.get(i));

        }

    }

    public static List getdate(String strfromDate, String strtoDate) {
        System.out.println(strfromDate+"---"+strtoDate);
        List list = new ArrayList();
        Calendar endCalendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = null;
        Date startDate = null;
        try {
            endDate = df.parse(strtoDate);
            startDate = df.parse(strfromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endCalendar.setTime(endDate);
        startCalendar.setTime(startDate);
        if (endDate.getTime()<startDate.getTime()){
            return list;
        }else if (endDate.getTime()==startDate.getTime()){
            list.add(df.format(startCalendar.getTime()));
            return list;
        }
        //不属于上述两种情况下，进行下面的处理

        //加入开始的时间
        list.add(df.format(startCalendar.getTime()));
        //处理中间的时间
        while (true) {
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            if (startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {
                list.add(df.format(startCalendar.getTime()));

            } else {
                break;
            }
        }
        //加入结束的时间
        list.add(df.format(endCalendar.getTime()));

        return list;

    }

    public static double calculateHour(String startDateTime, String endDateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Calendar cal = Calendar.getInstance();
        double time1 = 0;
        double time2 = 0;
        try {
            time1 = sdf.parse(startDateTime).getTime();
            time2 = sdf.parse(endDateTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time2);
        System.out.println(time1);
        System.out.println(time2 - time1);
        double count = (time2 - time1) / (1000 * 60 * 60);
        //count+=(time2 - time1) %(1000 * 60 * 60)/(1000*60)/60;
        return count;
    }
}
