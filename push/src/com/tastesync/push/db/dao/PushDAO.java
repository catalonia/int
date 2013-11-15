package com.tastesync.push.db.dao;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.push.db.dao.BaseDAO;
import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.NotificationsPushTextDataVO;
import com.tastesync.push.model.vo.UserNotificationsPushVO;

import java.sql.Connection;

import java.util.List;


public interface PushDAO extends BaseDAO {
    List<String> getAllDeviceTokensForSingleUser(TSDataSource tsDataSource,
        Connection connection, String userId) throws TasteSyncException;

    List<UserNotificationsPushVO> getAllNotifcationsForPush(
        TSDataSource tsDataSource, Connection connection)
        throws TasteSyncException;

    NotificationsPushTextDataVO getNotificationsPushTextData(
        TSDataSource tsDataSource, Connection connection,
        UserNotificationsPushVO userNotificationsPushVO)
        throws TasteSyncException;

    void updateDidYouLikeFordailyPushServiceNotifications(
        TSDataSource tsDataSource, Connection connection)
        throws TasteSyncException;

    void updateNotificationsSentStatus(TSDataSource tsDataSource,
        Connection connection,
        List<UserNotificationsPushVO> userNotificationsPushVOList,
        int statusFlag) throws TasteSyncException;

    void updateSingleNotificationSentStatus(TSDataSource tsDataSource,
        Connection connection, UserNotificationsPushVO userNotificationsPushVO,
        int statusFlag) throws TasteSyncException;
}
