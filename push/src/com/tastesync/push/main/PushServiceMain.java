package com.tastesync.push.main;

import com.tastesync.db.pool.TSDataSource;
import com.tastesync.push.exception.TasteSyncException;
//import com.tastesync.push.model.vo.UserNotificationsPushVO;
import com.tastesync.push.services.PushService;

//import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class PushServiceMain {
    public static void main(String[] args) {
        PushService pushService = new PushService();


        try {
            int startHour = 8;
            int endHour = 23;

            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(
                        "US/Eastern"));
            calendar.setTime(currentDate);

            //System.out.println("Executing PUSH notifications "+calendar);
            // between 8 (AM) and 23 (11 PM)
            if ((calendar.get(Calendar.HOUR_OF_DAY) >= startHour) &&
                    (calendar.get(Calendar.HOUR_OF_DAY) <= endHour)) {
                TSDataSource tsDataSource = TSDataSource.getInstance();
                Connection connection = null;
            	try {
					connection = tsDataSource.getConnection();

	                pushService.sendAllPushNotifucations(tsDataSource, connection);
	                tsDataSource.closeConnection(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					tsDataSource.closeConnection(connection);
				}
            }

            //            UserNotificationsPushVO userNotificationsPushVO = new UserNotificationsPushVO("userId",
            //                    "1", "100");
            //            String msg = pushService.getNotificationMsg(userNotificationsPushVO);
            //
            //            if (1 == 2) {
            //                pushService.testPushMsg(msg, "deviceToken");
            //            }
            //        } catch (IOException e1) {
            //            e1.printStackTrace();
        } catch (TasteSyncException e) {
            e.printStackTrace();
        }
    }
}
