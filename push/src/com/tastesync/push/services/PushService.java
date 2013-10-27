package com.tastesync.push.services;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import com.tastesync.push.db.dao.PushDAO;
import com.tastesync.push.db.dao.PushDAOImpl;
import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.NotificationsPushTextDataVO;
import com.tastesync.push.model.vo.UserNotificationsPushVO;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


public class PushService {
    private PushDAO pushDAO = new PushDAOImpl();

    public PushService() {
        super();
    }

    public void sendAllPushNotifucations() throws TasteSyncException {
        List<UserNotificationsPushVO> userNotificationsPushVOList = pushDAO.getAllNotifcationsForPush();

        // update sent status
        try {
        	//error while send push notifications
            pushDAO.updateNotificationsSentStatus(userNotificationsPushVOList, 2);

            List<String> deviceTokenList = null;

            ApnsService apnsService = getApnsServiceInstance();

            List<UserNotificationsPushVO> deviceTokenNotFoundUserNotificationsPushVOList =
                new LinkedList<UserNotificationsPushVO>();

            for (UserNotificationsPushVO userNotificationsPushVO : userNotificationsPushVOList) {
                // based on notification type, get template text.
                String notificationMsg = getNotificationMsg(userNotificationsPushVO);

                String payload = APNS.newPayload().alertBody(notificationMsg)
                                     .build();

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
        } catch (TasteSyncException e) {
            e.printStackTrace();
            pushDAO.updateNotificationsSentStatus(userNotificationsPushVOList, 3);
            throw new TasteSyncException(
                "Error while sending push notifcations");
        }
    }

    public String getNotificationMsg(
        UserNotificationsPushVO userNotificationsPushVO)
        throws TasteSyncException {
        String notificationMsg = null;
        InputStream ifile = null;
        Properties prop = new Properties();

        try {
            //ClassLoader loader = this.getClass().getClassLoader();
            //loader.getResourceAsStream("Resources/SomeConfig.xml");
            ifile = this.getClass().getClassLoader()
                        .getResourceAsStream("PushNotification.properties");

            //load a properties file
            prop.load(ifile);

            NotificationsPushTextDataVO notificationsPushTextDataVO = new NotificationsPushTextDataVO("",
                    "");

            String replaceString = null;

            if ("1".equals(userNotificationsPushVO.getNotificationType())) {
                notificationMsg = prop.getProperty("notificationtype.1.pushmsg");
                notificationsPushTextDataVO = pushDAO.getNotificationsPushTextData(userNotificationsPushVO);
                replaceString = notificationsPushTextDataVO.getFirstName().trim() +
                    " " + notificationsPushTextDataVO.getLastName().trim();

                //if (replaceString.trim().equals("")) {
                if (notificationsPushTextDataVO.getFirstName().trim().equals("")) {
                    replaceString = "Someone";
                }

                notificationMsg = StringUtils.replace(notificationMsg,
                        "<firstName> <lastName>", replaceString.trim());
            } else if ("2".equals(userNotificationsPushVO.getNotificationType())) {
                notificationMsg = prop.getProperty("notificationtype.2.pushmsg");
            } else if ("3".equals(userNotificationsPushVO.getNotificationType())) {
                notificationMsg = prop.getProperty("notificationtype.3.pushmsg");
                notificationsPushTextDataVO = pushDAO.getNotificationsPushTextData(userNotificationsPushVO);
                replaceString = notificationsPushTextDataVO.getFirstName().trim() +
                    " " + notificationsPushTextDataVO.getLastName().trim();

                //if (replaceString.trim().equals("")) {
                if (notificationsPushTextDataVO.getFirstName().trim().equals("")) {
                    replaceString = "Someone";
                }

                notificationMsg = StringUtils.replace(notificationMsg,
                        "<firstName> <lastName>", replaceString.trim());
            } else if ("4".equals(userNotificationsPushVO.getNotificationType())) {
                notificationMsg = prop.getProperty("notificationtype.4.pushmsg");
                notificationsPushTextDataVO = pushDAO.getNotificationsPushTextData(userNotificationsPushVO);
                replaceString = notificationsPushTextDataVO.getFirstName().trim() +
                    " " + notificationsPushTextDataVO.getLastName().trim();

                //if (replaceString.trim().equals("")) {
                if (notificationsPushTextDataVO.getFirstName().trim().equals("")) {
                    replaceString = "Someone";
                }

                notificationMsg = StringUtils.replace(notificationMsg,
                        "<firstName> <lastName>", replaceString.trim());
            } else if ("5".equals(userNotificationsPushVO.getNotificationType())) {
                notificationMsg = prop.getProperty("notificationtype.5.pushmsg");
                notificationsPushTextDataVO = pushDAO.getNotificationsPushTextData(userNotificationsPushVO);
                replaceString = notificationsPushTextDataVO.getFirstName().trim() +
                    " " + notificationsPushTextDataVO.getLastName().trim();

                //if (replaceString.trim().equals("")) {
                if (notificationsPushTextDataVO.getFirstName().trim().equals("")) {
                    replaceString = "Someone";
                }

                notificationMsg = StringUtils.replace(notificationMsg,
                        "<firstName> <lastName>", replaceString.trim());
            } else if ("6".equals(userNotificationsPushVO.getNotificationType())) {
                notificationMsg = prop.getProperty("notificationtype.6.pushmsg");
            } else {
                throw new TasteSyncException("Unknown notificationtype=" +
                    userNotificationsPushVO.getNotificationType());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new TasteSyncException(ex.getMessage());
        } finally {
            if (ifile != null) {
                try {
                    ifile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return notificationMsg;
    }

    private ApnsService getApnsServiceInstance() {
        InputStream inputStream = this.getClass().getClassLoader()
                                      .getResourceAsStream("iphone_dev.p12");
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
                                      .getResourceAsStream("iphone_dev.p12");
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

    public void dailyPushServiceNotifications() throws TasteSyncException {
        // -- TODO: Create script to trigger push notification for Did You Like using recoreply_didyoulike_notif 
        // -- see below. This script should be executed between 11AM and 5pm EST so that notifications are not sent to Users at night.
        //Check time
        pushDAO.updateDidYouLikeFordailyPushServiceNotifications();
        sendAllPushNotifucations();
    }
}
