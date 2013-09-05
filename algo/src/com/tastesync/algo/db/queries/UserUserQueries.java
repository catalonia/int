package com.tastesync.algo.db.queries;

public interface UserUserQueries extends TSDBCommonQueries {
    //2 for calculation 1.
    public static String RECOREQUEST_USER_ALGO_SELECT_SQL = "" +
        "SELECT DISTINCT recorequest_user.initiator_user_id " +
        "FROM   recorequest_user " + "WHERE  recorequest_user.algo_ind = ? ";

    //1 for calculation 1
    public static String RECOREQUEST_TS_ASSIGNED_ALGO_SELECT_SQL = "" +
        "SELECT DISTINCT recorequest_ts_assigned.assigned_user_id " +
        "FROM   recorequest_ts_assigned " +
        "WHERE  recorequest_ts_assigned.algo_ind = ? ";

    //4 for calculation 1
    public static String RECOREQUEST_REPLY_USER_ALGO_SELECT_SQL = "" +
        "SELECT DISTINCT recorequest_reply_user.reply_user_id " +
        "FROM   recorequest_reply_user " +
        "WHERE  recorequest_reply_user.algo_ind = ? ";

    //4 for calculation 1
    public static String RECOREQUEST_REPLY_USER_USER_ALGO_SELECT_SQL = "" +
        "SELECT DISTINCT recorequest_user.initiator_user_id " +
        "FROM   recorequest_reply_user, " + "       recorequest_user " +
        "WHERE  recorequest_reply_user.algo_ind = ? " +
        "       AND recorequest_reply_user.recorequest_id = " +
        "           recorequest_user.recorequest_id ";
    public static String COUNT_RECOREQUEST_TS_ASSIGNED_DATETIME_SELECT_SQL = "" +
        "SELECT count(*) " + "FROM   recorequest_ts_assigned " +
        "WHERE  recorequest_ts_assigned.assigned_user_id = ? " +
        "       AND recorequest_ts_assigned.assigned_datetime = ? ";
    public static String COUNT_RECOREQUEST_TS_ASSIGNED_REPLY_USER_SELECT_SQL = "" +
        "SELECT Count(*) " +
        "FROM   (SELECT DISTINCT recorequest_reply_user.recorequest_id " +
        "        FROM   recorequest_ts_assigned, " +
        "               recorequest_reply_user " +
        "        WHERE  recorequest_ts_assigned.assigned_user_id = ? " +
        "               AND recorequest_ts_assigned.assigned_datetime = ? " +
        "               AND recorequest_ts_assigned.recorequest_id = " +
        "                   recorequest_reply_user.recorequest_id) x ";
    public static String COUNT_RECOREQUEST_USER_SELECT_SQL = "" +
        "SELECT count(*) " + "FROM   recorequest_user " +
        "WHERE  recorequest_user.initiator_user_id = ? ";

    // limit 10
    public static String COUNT_RECOREQUEST_USER_LIMIT_SELECT_SQL = "" +
        "SELECT recorequest_user.recorequest_id, " +
        "       recorequest_user.recorequest_sent_datetime " +
        "FROM   recorequest_user " +
        "WHERE  recorequest_user.initiator_user_id = ? " +
        "ORDER  BY recorequest_user.recorequest_sent_datetime DESC " +
        "LIMIT  ? ";
    public static String RECOREQUEST_REPLY_USER_SELECT_SQL = "" +
        "SELECT recorequest_reply_user.reply_id, " +
        "       recorequest_reply_user.reply_send_datetime " +
        "FROM   recorequest_reply_user " +
        "WHERE  recorequest_reply_user.recorequest_id = ? " +
        "ORDER  BY recorequest_reply_user.reply_send_datetime ASC " +
        "LIMIT  ? ";
    public static String COUNT_RECOREQUEST_TS_ASSIGNED_SELECT_SQL = "" +
        "SELECT count(*) " + "FROM   recorequest_ts_assigned " +
        "WHERE  recorequest_ts_assigned.assigned_user_id = ? ";

    // limit 10
    public static String RECOREQUEST_TS_ASSIGNED_SELECT_SQL = "" +
        "SELECT recorequest_ts_assigned.recorequest_id, " +
        "       recorequest_ts_assigned.assigned_datetime " +
        "FROM   recorequest_ts_assigned " +
        "WHERE  recorequest_ts_assigned.assigned_user_id = ? " +
        "ORDER  BY recorequest_ts_assigned.assigned_datetime DESC " +
        "LIMIT  ? ";
    public static String RECOREQUEST_REPLY_USER_MORE_SELECT_SQL = "" +
        "SELECT recorequest_reply_user.reply_id, " +
        "       recorequest_reply_user.reply_send_datetime " +
        "FROM   recorequest_reply_user " +
        "WHERE  recorequest_reply_user.recorequest_id = ? " +
        "       AND recorequest_reply_user.reply_user_id = ? " +
        "ORDER  BY recorequest_reply_user.reply_send_datetime ASC " +
        "LIMIT  1";
    public static String USER_RECO_SUPPLY_TIER_INSERT_SQL = "" +
        "INSERT INTO user_reco_supply_tier " +
        "            (user_reco_supply_tier.user_id, " +
        "             user_reco_supply_tier.user_supply_inv_tier, " +
        "             user_reco_supply_tier.user_tier_calc_flag) " +
        "VALUES      ( ?, " + "              ?, " + "              ? ) " + "" +
        "	ON DUPLICATE KEY UPDATE " +
        " user_reco_supply_tier.user_supply_inv_tier = ?, " +
        " user_reco_supply_tier.user_tier_calc_flag = ? ";
    public static String RECOREQUEST_USER_UPDATE_SQL = "" +
        "UPDATE recorequest_user " + "SET    recorequest_user.algo_ind = ? " +
        "WHERE  recorequest_user.initiator_user_id = ? ";
    public static String RECOREQUEST_TS_ASSIGNED_UPDATE_SQL = "" +
        "UPDATE recorequest_ts_assigned " +
        "SET    recorequest_ts_assigned.algo_ind = ? " +
        "WHERE  recorequest_ts_assigned.assigned_user_id = ? ";
    public static String RECOREQUEST_REPLY_USER_UPDATE_SQL = "" +
        "UPDATE recorequest_reply_user " +
        "SET    recorequest_reply_user.algo_ind = ? " +
        "WHERE  recorequest_reply_user.reply_user_id = ? ";
}
