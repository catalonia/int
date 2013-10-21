package com.tastesync.push.main;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.services.PushService;

import java.util.Calendar;
import java.util.Date;


public class DailyPushServiceNotificationsMain {
    /**
     * @param args
     */
    public static void main(String[] args) {
        PushService pushService = new PushService();

        try {
            Date currentDateTime = CommonFunctionsUtil.convertJodaTimezoneFromCurrentTimezoneToEST(new Date());
            // between 11 AM and 23 PM
            if ((currentDateTime.getHours() >= 11) &&
                    (currentDateTime.getHours() <= 23)) {
                pushService.dailyPushServiceNotifications();
            }
        } catch (TasteSyncException e) {
            e.printStackTrace();
        }
    }
}
