package com.tastesync.push.db.dao;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.push.db.dao.PushDAO;
import com.tastesync.push.db.queries.PushQueries;
import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.NotificationsPushTextDataVO;
import com.tastesync.push.model.vo.UserNotificationsPushVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class PushDAOImpl implements PushDAO {
    @Override
    public List<UserNotificationsPushVO> getAllNotifcationsForPush()
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();

            List<UserNotificationsPushVO> userNotificationsPushVOList = new ArrayList<UserNotificationsPushVO>();

            statement = connection.prepareStatement(PushQueries.PUSH_NOTIFICATIONS_ALL_SELECT_SQL);

            resultset = statement.executeQuery();

            String userId;
            String notificationType;
            String linkedId;
            UserNotificationsPushVO userNotificationsPushVO;

            while (resultset.next()) {
                userId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "NOTIFICATIONS_ALL.USER_ID"));

                notificationType = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "NOTIFICATIONS_ALL.NOTIFICATION_TYPE"));

                linkedId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "NOTIFICATIONS_ALL.LINKED_ID"));

                userNotificationsPushVO = new UserNotificationsPushVO(userId,
                        notificationType, linkedId);
                userNotificationsPushVOList.add(userNotificationsPushVO);
            }

            statement.close();

            return userNotificationsPushVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getAllNotifcationsForPush= " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getAllDeviceTokensForSingleUser(String userId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();

            List<String> deviceTokenList = new ArrayList<String>();

            statement = connection.prepareStatement(PushQueries.DEVICE_TOKEN_SELECT_SQL);
            statement.setString(1, userId);

            resultset = statement.executeQuery();

            String deviceToken;

            while (resultset.next()) {
                //deviceToken = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                  //          "USER_DEVICE_OAUTH.DEVICE_TOKEN"));
                deviceToken = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                        "DEVICE_TOKEN"));

                if ((deviceToken != null) && !deviceToken.isEmpty()) {
                    deviceTokenList.add(deviceToken);
                }
            }

            statement.close();

            return deviceTokenList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getAllDeviceTokensForSingleUser= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void updateNotificationsSentStatus(
        List<UserNotificationsPushVO> userNotificationsPushVOList,
        int statusFlag) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();

            statement = connection.prepareStatement(PushQueries.PUSH_NOTIFICATIONS_ALL_UPDATE_SQL);

            for (UserNotificationsPushVO userNotificationsPushVO : userNotificationsPushVOList) {
                statement.setInt(1, statusFlag);
                statement.setString(2, userNotificationsPushVO.getUserId());
                statement.setString(3,
                    userNotificationsPushVO.getNotificationType());
                statement.setString(4, userNotificationsPushVO.getLinkedId());

                statement.executeUpdate();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while updateNotificationsSentStatus= " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public NotificationsPushTextDataVO getNotificationsPushTextData(
        UserNotificationsPushVO userNotificationsPushVO)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();

            String sql;

            if ("1".equals(userNotificationsPushVO.getNotificationType())) {
                sql = PushQueries.NOTIFICATIONTYPE1_TEXTDATA_SELECT_SQL;
            } else if ("3".equals(userNotificationsPushVO.getNotificationType())) {
                sql = PushQueries.NOTIFICATIONTYPE3_TEXTDATA_SELECT_SQL;
            } else if ("4".equals(userNotificationsPushVO.getNotificationType())) {
                sql = PushQueries.NOTIFICATIONTYPE4_TEXTDATA_SELECT_SQL;
            } else if ("5".equals(userNotificationsPushVO.getNotificationType())) {
                sql = PushQueries.NOTIFICATIONTYPE5_TEXTDATA_SELECT_SQL;
            } else {
                throw new TasteSyncException("Unknown notificationtype=" +
                    userNotificationsPushVO.getNotificationType());
            }

            statement = connection.prepareStatement(sql);
            statement.setString(1, userNotificationsPushVO.getLinkedId());

            resultset = statement.executeQuery();

            String firstName = "";
            String lastName = "";

            while (resultset.next()) {
                firstName = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "users.TS_FIRST_NAME"));
                lastName = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "users.TS_LAST_NAME"));
            }

            statement.close();

            return new NotificationsPushTextDataVO(firstName,
                    lastName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while updateNotificationsSentStatus= " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void updateDidYouLikeFordailyPushServiceNotifications()
        throws TasteSyncException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        TSDataSource tsDataSource = TSDataSource.getInstance();
        PreparedStatement statementInner = null;

        try {
            connection = tsDataSource.getConnection();

            statement = connection.prepareStatement(PushQueries.REPLY_VIEWED_INITIATOR_RECOREQUEST_USER_SELECT_SQL);
            statement.setInt(1, 3);
            statement.setInt(2, 2);
            statement.setInt(3, 1);

            resultset = statement.executeQuery();

            String recoRequestId;
            statementInner = connection.prepareStatement(PushQueries.RECOREPLY_DIDYOULIKE_NOTIF_INSERT_SQL);

            while (resultset.next()) {
                recoRequestId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "RECOREQUEST_USER.RECOREQUEST_ID"));

                statementInner.setTimestamp(1,
                    CommonFunctionsUtil.getCurrentDateTimestamp());

                statementInner.setInt(2, 0);

                statementInner.setString(1, recoRequestId);
            }

            statementInner.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while updateDidYouLikeFordailyPushServiceNotifications= " +
                e.getMessage());
        } finally {
            if (statementInner != null) {
                try {
                    statementInner.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void updateSingleNotificationSentStatus(
        UserNotificationsPushVO userNotificationsPushVO, int statusFlag)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();

            statement = connection.prepareStatement(PushQueries.PUSH_NOTIFICATIONS_ALL_UPDATE_SQL);
            statement.setInt(1, statusFlag);
            statement.setString(2, userNotificationsPushVO.getUserId());
            statement.setString(3, userNotificationsPushVO.getNotificationType());
            statement.setString(4, userNotificationsPushVO.getLinkedId());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while updateSingleNotificationSentStatus= " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }
}
