package com.tastesync.push.db.queries;

public interface PushQueries {
    public static String SINGLE_PUSH_NOTIFICATIONS_ALL_SELECT_SQL = "" +
        "SELECT NOTIFICATIONS_ALL.USER_ID, " +
        "       NOTIFICATIONS_ALL.NOTIFICATION_TYPE, " +
        "       NOTIFICATIONS_ALL.LINKED_ID " + "FROM   NOTIFICATIONS_ALL " +
        "WHERE  PUSH_NOTIFICATION_SENT <> 1" +
        "       AND NOTIFICATIONS_ALL.LINKED_ID = ?";
    public static String PUSH_NOTIFICATIONS_ALL_SELECT_SQL = "" +
        "SELECT NOTIFICATIONS_ALL.USER_ID, " +
        "       NOTIFICATIONS_ALL.NOTIFICATION_TYPE, " +
        "       NOTIFICATIONS_ALL.LINKED_ID " + "FROM   NOTIFICATIONS_ALL " +
        "WHERE  PUSH_NOTIFICATION_SENT <> 1 " +
        "AND push_notification_sent <> 2";
    public static String DEVICE_TOKEN_SELECT_SQL = "" +
        "SELECT DISTINCT USER_DEVICE_OAUTH.DEVICE_TOKEN " +
        "FROM   USER_DEVICE_OAUTH " + "WHERE  USER_ID = ?";
    public static String PUSH_NOTIFICATIONS_ALL_UPDATE_SQL = "" +
        "UPDATE NOTIFICATIONS_ALL " +
        "SET    NOTIFICATIONS_ALL.PUSH_NOTIFICATION_SENT = ? " +
        "WHERE  NOTIFICATIONS_ALL.USER_ID = ? " +
        "       AND NOTIFICATIONS_ALL.NOTIFICATION_TYPE = ? " +
        "       AND NOTIFICATIONS_ALL.LINKED_ID = ?";
    public static String NOTIFICATIONTYPE1_TEXTDATA_SELECT_SQL = "" +
        "SELECT users.TS_FIRST_NAME, " + "users.TS_LAST_NAME " +
        "FROM recorequest_user, users " +
        "WHERE recorequest_user.RECOREQUEST_ID = ? AND " +
        "recorequest_user.INITIATOR_USER_ID = users.USER_ID ";
    public static String NOTIFICATIONTYPE3_TEXTDATA_SELECT_SQL = "" +
        "SELECT users.TS_FIRST_NAME, " + "users.TS_LAST_NAME " +
        "FROM restaurant_question_user, users " +
        "WHERE restaurant_question_user.QUESTION_ID = ? AND " +
        "restaurant_question_user.INITIATOR_USER_ID = users.USER_ID ";
    public static String NOTIFICATIONTYPE4_TEXTDATA_SELECT_SQL = "" +
        "SELECT users.TS_FIRST_NAME, " + "users.TS_LAST_NAME " +
        "FROM user_message, users " + "WHERE user_message.MESSAGE_ID = ? AND " +
        "user_message.SENDER_ID = users.USER_ID ";
    public static String NOTIFICATIONTYPE5_TEXTDATA_SELECT_SQL = "" +
        "SELECT users.TS_FIRST_NAME, " + "users.TS_LAST_NAME " +
        "FROM reco_like, users " + "WHERE reco_like.ID = ? AND " +
        "reco_like.LIKE_USER_ID = users.USER_ID ";
    public static String DEVICE_TOKEN_LOGOUT_DELETE_SQL = "" +
        "DELETE FROM USER_DEVICE_OAUTH " +
        "WHERE  USER_DEVICE_OAUTH.USER_ID = ? " +
        "       AND USER_DEVICE_OAUTH.DEVICE_TOKEN = ?";
    public static String REPLY_VIEWED_INITIATOR_RECOREQUEST_USER_SELECT_SQL = "" +
        "SELECT DISTINCT RECOREQUEST_USER.RECOREQUEST_ID " +
        "FROM   RECOREQUEST_USER, " + "       RECOREQUEST_REPLY_USER " +
        "WHERE  RECOREQUEST_USER.RECOREQUEST_SENT_DATETIME BETWEEN " +
        "              Sysdate() - INTERVAL ? day AND Sysdate() - INTERVAL ? day " +
        "       AND RECOREQUEST_USER.RECOREQUEST_ID = " +
        "           RECOREQUEST_REPLY_USER.RECOREQUEST_ID " +
        "       AND RECOREQUEST_REPLY_USER.REPLY_VIEWED_INITIATOR = ? ";
    public static String RECOREPLY_DIDYOULIKE_NOTIF_INSERT_SQL = "" +
        "INSERT INTO RECOREPLY_DIDYOULIKE_NOTIF " +
        "            (RECOREPLY_DIDYOULIKE_NOTIF.NOTIF_DATETIME, " +
        "             RECOREPLY_DIDYOULIKE_NOTIF.NOTIF_VIEWED, " +
        "             RECOREPLY_DIDYOULIKE_NOTIF.RECOREQUEST_ID) " +
        "VALUES      ( ?, " + "              ?, " + "              ? )" +
        "ON DUPLICATE KEY UPDATE " +
        "recoreply_didyoulike_notif.RECOREQUEST_ID = recoreply_didyoulike_notif.RECOREQUEST_ID ";
}
