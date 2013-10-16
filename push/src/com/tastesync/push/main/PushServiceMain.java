package com.tastesync.push.main;

import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.UserNotificationsPushVO;
import com.tastesync.push.services.PushService;

import java.io.IOException;


public class PushServiceMain {
    public static void main(String[] args) {
        PushService pushService = new PushService();
        String msg = "test msg";
        String deviceToken = "devicetoken";

        try {
        	pushService.sendAllPushNotifucations();
//            UserNotificationsPushVO userNotificationsPushVO = new UserNotificationsPushVO("userId",
//                    "1", "100");
//            pushService.getNotificationMsg(userNotificationsPushVO);
//
//            pushService.testPushMsg(msg, deviceToken);
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (TasteSyncException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
