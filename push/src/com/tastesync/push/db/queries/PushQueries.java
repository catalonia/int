package com.tastesync.push.db.queries;

public class PushQueries {
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
}
