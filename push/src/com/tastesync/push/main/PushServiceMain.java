package com.tastesync.push.main;

import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.UserNotificationsPushVO;
import com.tastesync.push.services.PushService;

import java.io.IOException;


public class PushServiceMain {
    public static void main(String[] args) {
        PushService pushService = new PushService();

        try {
            //pushService.sendAllPushNotifucations();
            UserNotificationsPushVO userNotificationsPushVO = new UserNotificationsPushVO("userId",
                    "1", "100");
            String msg = pushService.getNotificationMsg(userNotificationsPushVO);

            if (1 == 2) {
                pushService.testPushMsg(msg, "deviceToken");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (TasteSyncException e) {
            e.printStackTrace();
        }
    }
}
