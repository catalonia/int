package com.tastesync.push.db.dao;

import com.tastesync.push.db.dao.BaseDAO;
import com.tastesync.push.exception.TasteSyncException;
import com.tastesync.push.model.vo.NotificationsPushTextDataVO;
import com.tastesync.push.model.vo.UserNotificationsPushVO;

import java.util.List;


public interface PushDAO extends BaseDAO {
    List<UserNotificationsPushVO> getAllNotifcationsForPush()
        throws TasteSyncException;

    List<String> getAllDeviceTokensForSingleUser(String userId)
        throws TasteSyncException;

    void updateSingleNotificationSentStatus(
            UserNotificationsPushVO userNotificationsPushVO,
            int statusFlag) throws TasteSyncException;
    
    void updateNotificationsSentStatus(
        List<UserNotificationsPushVO> userNotificationsPushVOList,
        int statusFlag) throws TasteSyncException;

    NotificationsPushTextDataVO getNotificationsPushTextData(UserNotificationsPushVO userNotificationsPushVO)
        throws TasteSyncException;
    
    void updateDidYouLikeFordailyPushServiceNotifications() throws TasteSyncException;
}
