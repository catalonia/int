package com.tastesync.push.services;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import com.tastesync.push.db.dao.PushDAO;
import com.tastesync.push.db.dao.PushDAOImpl;
import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.UserNotificationsPushVO;

import java.io.IOException;
import java.io.InputStream;

import java.util.LinkedList;
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

        List<UserNotificationsPushVO> deviceTokenNotFoundUserNotificationsPushVOList =
            new LinkedList<UserNotificationsPushVO>();

        for (UserNotificationsPushVO userNotificationsPushVO : userNotificationsPushVOList) {
            //TODO based on notification type, get template text.
            String notificationMsg = "Test msg";

            String payload = APNS.newPayload().alertBody(notificationMsg).build();

            // based on userId, get all device tokens
            deviceTokenList = pushDAO.getAllDeviceTokensForSingleUser(userNotificationsPushVO.getUserId());

            if ((deviceTokenList != null) && !deviceTokenList.isEmpty()) {
                apnsService.push(deviceTokenList, payload);
            } else {
                deviceTokenNotFoundUserNotificationsPushVOList.add(userNotificationsPushVO);
            }
        }

        // update sent status
        pushDAO.updateNotificationsSentStatus(userNotificationsPushVOList, 1);
        pushDAO.updateNotificationsSentStatus(deviceTokenNotFoundUserNotificationsPushVOList,
            2);
    }

    private ApnsService getApnsServiceInstance() {
        InputStream inputStream = this.getClass().getClassLoader()
                                      .getResourceAsStream("TasteSync.p12");
        ApnsService service = APNS.newService().withCert(inputStream, "dev123")
                                  .withSandboxDestination().build();

        return service;
    }

    public void testPushMsg(String msg, String deviceToken)
        throws IOException {
        System.out.println("Notification is to be sent.");

        ApnsService service;

        //        InputStream inputStream = this.getClass().getClassLoader()
        //                                      .getResourceAsStream("Certificates_push.p12");
        //        service = APNS.newService().withCert(inputStream, "123")
        //                      .withSandboxDestination().build();
        //
        //        InputStream inputStream = this.getClass().getClassLoader()
        //                .getResourceAsStream("DG_Certificates.p12");
        //        InputStream inputStream = this.getClass().getClassLoader()
        //                .getResourceAsStream("TasteSync.p12");
        //        service = APNS.newService().withCert(inputStream, "dev123")
        //                .withSandboxDestination().build();
        InputStream inputStream = this.getClass().getClassLoader()
                                      .getResourceAsStream("DG_Certificates.p12");
        service = APNS.newService().withCert(inputStream, "dev123")
                      .withSandboxDestination().build();

        String payload = APNS.newPayload().alertBody(msg).build();

        String token = "64a383f0 84ee1d6d 5c2f8119 1bd4f8f4 d36f26f4 4a214821 7987c3d6 26907f8e";
        token = "edfbba62 e367ac53 532c28d7 e1ee5bc4 b4cb0da8 0eb27799 58e9aa6d 73d05446";
        token = "94e2a80d 12ad0bc1 7d6ac413 ac4a27ea 9d701f68 c1668bed 22549c7c 226acc89";
        token = "94e2a80d12ad0bc17d6ac413ac4a27ea9d701f68c1668bed22549c7c226acc89";

        deviceToken = token;

        service.push(deviceToken, payload);
    }
}
