package com.tastesync.push.services;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.push.db.dao.PushDAO;
import com.tastesync.push.db.dao.PushDAOImpl;
import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.NotificationsPushTextDataVO;
import com.tastesync.push.model.vo.UserNotificationsPushVO;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


public class PushService {
    private PushDAO pushDAO = new PushDAOImpl();

    public PushService() {
        super();
    }

    public void dailyPushServiceNotifications(TSDataSource tsDataSource,
        Connection connection) throws TasteSyncException {
        pushDAO.updateDidYouLikeFordailyPushServiceNotifications(tsDataSource,
            connection);
        //Delay
        sendAllPushNotifucations(tsDataSource, connection);
    }

    private ApnsService getApnsServiceInstance() {
        InputStream inputStream = this.getClass().getClassLoader()
                                      .getResourceAsStream("iphone_dev.p12");

        return APNS.newService().withCert(inputStream, "dev123")
                   .withSandboxDestination().build();
    }

    private String getNotificationMsg(TSDataSource tsDataSource,
        Connection connection, UserNotificationsPushVO userNotificationsPushVO)
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

            NotificationsPushTextDataVO notificationsPushTextDataVO;

            String replaceString;

            if ("1".equals(userNotificationsPushVO.getNotificationType())) {
                notificationMsg = prop.getProperty("notificationtype.1.pushmsg");
                notificationsPushTextDataVO = pushDAO.getNotificationsPushTextData(tsDataSource,
                        connection, userNotificationsPushVO);
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
                notificationsPushTextDataVO = pushDAO.getNotificationsPushTextData(tsDataSource,
                        connection, userNotificationsPushVO);
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
                notificationsPushTextDataVO = pushDAO.getNotificationsPushTextData(tsDataSource,
                        connection, userNotificationsPushVO);
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
                notificationsPushTextDataVO = pushDAO.getNotificationsPushTextData(tsDataSource,
                        connection, userNotificationsPushVO);
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

    public void sendAllPushNotifucations(TSDataSource tsDataSource,
        Connection connection) throws TasteSyncException {
        List<UserNotificationsPushVO> userNotificationsPushVOList = pushDAO.getAllNotifcationsForPush(tsDataSource,
                connection);

        // update sent status
        try {
            //error while send push notifications
            pushDAO.updateNotificationsSentStatus(tsDataSource, connection,
                userNotificationsPushVOList, 2);

            List<String> deviceTokenList;

            ApnsService apnsService = getApnsServiceInstance();

            List<UserNotificationsPushVO> deviceTokenNotFoundUserNotificationsPushVOList =
                new LinkedList<UserNotificationsPushVO>();

            for (UserNotificationsPushVO userNotificationsPushVO : userNotificationsPushVOList) {
                // based on notification type, get template text.
                String notificationMsg = getNotificationMsg(tsDataSource,
                        connection, userNotificationsPushVO);

                String payload = APNS.newPayload().alertBody(notificationMsg)
                                     .build();

                // based on userId, get all device tokens
                deviceTokenList = pushDAO.getAllDeviceTokensForSingleUser(tsDataSource,
                        connection, userNotificationsPushVO.getUserId());

                if ((deviceTokenList != null) && !deviceTokenList.isEmpty()) {
                    System.out.println("deviceTokenList found. " +
                        deviceTokenList);

                    apnsService.push(deviceTokenList, payload);
                } else {
                    deviceTokenNotFoundUserNotificationsPushVOList.add(userNotificationsPushVO);
                    System.out.println("deviceTokenList Not found. " +
                        deviceTokenList);
                }
            }

            // update sent status
            pushDAO.updateNotificationsSentStatus(tsDataSource, connection,
                userNotificationsPushVOList, 1);
            pushDAO.updateNotificationsSentStatus(tsDataSource, connection,
                deviceTokenNotFoundUserNotificationsPushVOList, 5);
        } catch (TasteSyncException e) {
            e.printStackTrace();
            pushDAO.updateNotificationsSentStatus(tsDataSource, connection,
                userNotificationsPushVOList, 3);
            throw new TasteSyncException(
                "Error while sending push notifcations");
        }
    }

    private void testPushMsg(String msg) {
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

        String deviceToken = "94e2a80d12ad0bc17d6ac413ac4a27ea9d701f68c1668bed22549c7c226acc89";

        service.push(deviceToken, payload);
    }
}
