package com.tastesync.push.main;

import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.services.PushService;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DailyPushServiceNotificationsMain {
    /**
     * @param args input args
     */
    public static void main(String[] args) {
        PushService pushService = new PushService();

        try {
            while (true) {
                int startHour = 8;
                int endHour = 23;
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(
                            "US/Eastern"));
                calendar.setTime(currentDate);
                sleepNeeded(startHour, endHour, calendar);

                // between 8 (AM) and 23 (11 PM)
                if ((calendar.get(Calendar.HOUR_OF_DAY) >= startHour) &&
                        (calendar.get(Calendar.HOUR_OF_DAY) <= endHour)) {
                    System.out.println("Executing PUSH notifications " +
                        calendar);
                    pushService.dailyPushServiceNotifications();

                    // sleep for 1 hour
                    // further logics to wait
                    System.out.println("hours=" +
                        (calendar.get(Calendar.HOUR_OF_DAY)) + "SLEEP for " +
                        (360000 * startHour));
                    Thread.currentThread();

                    try {
                        Thread.sleep(360000 * startHour);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (TasteSyncException e) {
            e.printStackTrace();
        }
    }

    private static void sleepNeeded(int startHour, int endHour,
        Calendar calendar) {
        //sleep
        if (calendar.get(Calendar.HOUR_OF_DAY) < startHour) {
            // further logics to wait
            System.out.println("hours=" +
                (startHour - calendar.get(Calendar.HOUR_OF_DAY)) +
                "SLEEP for " +
                (3600000 * (startHour - calendar.get(Calendar.HOUR_OF_DAY))));
            Thread.currentThread();

            try {
                Thread.sleep(3600000 * (startHour -
                    calendar.get(Calendar.HOUR_OF_DAY)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (calendar.get(Calendar.HOUR_OF_DAY) > endHour) {
            // further logics to wait
            System.out.println("hours=" +
                ((11 + 24) - calendar.get(Calendar.HOUR_OF_DAY)) +
                "SLEEP for " +
                (3600000 * ((startHour + 24) -
                calendar.get(Calendar.HOUR_OF_DAY))));
            Thread.currentThread();

            try {
                Thread.sleep(3600000 * ((startHour + 24) -
                    calendar.get(Calendar.HOUR_OF_DAY)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
