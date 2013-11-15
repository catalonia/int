package com.tastesync.push.main;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.services.PushService;

import java.sql.Connection;
import java.sql.SQLException;

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

                // -- invoke via script to trigger push notification for Did You Like using recoreply_didyoulike_notif 
                // -- see below. This script should be executed between 8AM and 23pm EST so that notifications are not sent to Users at night.
                //Check time
                if ((calendar.get(Calendar.HOUR_OF_DAY) >= startHour) &&
                        (calendar.get(Calendar.HOUR_OF_DAY) <= endHour)) {
                    System.out.println("Executing PUSH notifications " +
                        calendar);

                    TSDataSource tsDataSource = TSDataSource.getInstance();
                    Connection connection = null;

                    try {
                        connection = tsDataSource.getConnection();
                        pushService.dailyPushServiceNotifications(tsDataSource,
                            connection);
                        tsDataSource.closeConnection(connection);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        tsDataSource.closeConnection(connection);
                    }

                    // sleep for 1 hour
                    // further logics to wait
                    System.out.println("hours=" +
                        (calendar.get(Calendar.HOUR_OF_DAY)) + "SLEEP for " +
                        (360000));
                    Thread.currentThread();

                    try {
                        Thread.sleep(360000);
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
