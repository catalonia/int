package com.tastesync.push.main;

import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.services.PushService;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DailyPushServiceNotificationsMain {
    /**
     * @param args
     */
    public static void main(String[] args) {
        PushService pushService = new PushService();

        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("US/Eastern"));
            calendar.setTime(new Date());
        	//System.out.println("Executing PUSH notifications "+calendar);
            // between 11 (AM) and 23 (11 PM)
            if ((calendar.get(Calendar.HOUR_OF_DAY) >= 11) &&
                    (calendar.get(Calendar.HOUR_OF_DAY) <= 23)) {
            	System.out.println("Executing PUSH notifications "+calendar);
                pushService.dailyPushServiceNotifications();
            }

        } catch (TasteSyncException e) {
            e.printStackTrace();
        }
    }
}
