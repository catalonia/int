package com.tastesync.push.db.dao;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.push.db.dao.PushDAO;
import com.tastesync.push.db.queries.PushQueries;
import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.UserNotificationsPushVO;
import com.tastesync.push.util.CommonFunctionsUtil;

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
            UserNotificationsPushVO userNotificationsPushVO = null;

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

            while (resultset.next()) {
                deviceTokenList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("USER_DEVICE_OAUTH.DEVICE_TOKEN")));
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
        List<UserNotificationsPushVO> userNotificationsPushVOList)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();

            statement = connection.prepareStatement(PushQueries.PUSH_NOTIFICATIONS_ALL_UPDATE_SQL);

            for (UserNotificationsPushVO userNotificationsPushVO : userNotificationsPushVOList) {
                statement.setString(1, userNotificationsPushVO.getUserId());
                statement.setString(2,
                    userNotificationsPushVO.getNotificationType());
                statement.setString(3, userNotificationsPushVO.getLinkedId());

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
}
