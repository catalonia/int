package com.tastesync.push.services;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import com.tastesync.push.db.dao.PushDAO;
import com.tastesync.push.db.dao.PushDAOImpl;
import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.UserNotificationsPushVO;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;


public class PushService {
    private PushDAO pushDAO = new PushDAOImpl();

    public PushService() {
        super();
    }

    public void sendAllPushNotifucations() throws TasteSyncException {
        List<UserNotificationsPushVO> userNotificationsPushVOList = pushDAO.getAllNotifcationsForPush();
        List<String> deviceTokenList = null;

        ApnsService apnsService = getApnsServiceInstance();

        for (UserNotificationsPushVO userNotificationsPushVO : userNotificationsPushVOList) {
            //TODO based on notification type, get template text.
            String notificationMsg = "Test msg";

            String payload = APNS.newPayload().alertBody(notificationMsg).build();

            // based on userId, get all device tokens
            deviceTokenList = pushDAO.getAllDeviceTokensForSingleUser(userNotificationsPushVO.getUserId());

            apnsService.push(deviceTokenList, payload);
        }

        // TODO update sent status
        pushDAO.updateNotificationsSentStatus(userNotificationsPushVOList);
    }

    private ApnsService getApnsServiceInstance() {
        InputStream inputStream = this.getClass().getClassLoader()
                                      .getResourceAsStream("Certificates_push.p12");
        

//        InputStream inputStream = this.getClass().getClassLoader()
//                .getResourceAsStream("TasteSync.p12");
        
        ApnsService service = APNS.newService().withCert(inputStream, "123")
                                  .withSandboxDestination().build();

        return service;
    }

    public void testPushMsg(String msg, String deviceToken)
        throws IOException {
        System.out.println("Notification is to be sent.");

        InputStream inputStream = this.getClass().getClassLoader()
                                      .getResourceAsStream("Certificates_push.p12");
        ApnsService service;
        service = APNS.newService().withCert(inputStream, "123")
                      .withSandboxDestination().build();

        String payload = APNS.newPayload().alertBody(msg).build();
        
        String token = "64a383f0 84ee1d6d 5c2f8119 1bd4f8f4 d36f26f4 4a214821 7987c3d6 26907f8e";
        //token="12231234sd223452345ds345";
        deviceToken = token;
        
        service.push(deviceToken, payload);
    }
}
