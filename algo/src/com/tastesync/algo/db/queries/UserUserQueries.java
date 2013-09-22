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
        "       AND recorequest_ts_assigned.assigned_datetime >= ? ";
    public static String COUNT_RECOREQUEST_TS_ASSIGNED_REPLY_USER_SELECT_SQL = "" +
        "SELECT Count(*) " +
        "FROM   (SELECT DISTINCT recorequest_reply_user.recorequest_id " +
        "        FROM   recorequest_ts_assigned, " +
        "               recorequest_reply_user " +
        "        WHERE  recorequest_ts_assigned.assigned_user_id = ? " +
        "               AND recorequest_ts_assigned.assigned_datetime >= ? " +
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
    public static String RECOREQUEST_USER_NDAYS_SELECT_SQL = "" +
        "SELECT recorequest_user.recorequest_id " + "FROM   recorequest_user " +
        "WHERE  recorequest_user.initiator_user_id = ? " +
        "       AND recorequest_user.recorequest_sent_datetime >= ? ";
    public static String RECOREQUEST_CUISINE_TIER1_SELECT_SQL = "" +
        "SELECT recorequest_cuisine_tier1.cuisine_tier1_id " +
        "FROM   recorequest_cuisine_tier1 " +
        "WHERE  recorequest_cuisine_tier1.recorequest_id = ? ";
    public static String RECOREQUEST_CUISINE_TIER2_SELECT_SQL = "" +
        "SELECT recorequest_cuisine_tier2.cuisine_tier2_id " +
        "FROM   recorequest_cuisine_tier2 " +
        "WHERE  recorequest_cuisine_tier2.recorequest_id = ? ";
    public static String RECOREQUEST_OCCASION_SELECT_SQL = "" +
        "SELECT recorequest_occasion.occasion_id " +
        "FROM   recorequest_occasion " +
        "WHERE  recorequest_occasion.recorequest_id = ? ";
    public static String RECOREQUEST_PRICE_SELECT_SQL = "" +
        "SELECT recorequest_price.price_id " + "FROM   recorequest_price " +
        "WHERE  recorequest_price.recorequest_id = ? ";
    public static String RECOREQUEST_THEME_SELECT_SQL = "" +
        "SELECT recorequest_theme.theme_id " + "FROM   recorequest_theme " +
        "WHERE  recorequest_theme.recorequest_id = ? ";
    public static String RECOREQUEST_TYPEOFREST_SELECT_SQL = "" +
        "SELECT recorequest_typeofrest.typeofrest_id " +
        "FROM   recorequest_typeofrest " +
        "WHERE  recorequest_typeofrest.recorequest_id =? ";
    public static String RECOREQUEST_WHOAREYOUWITH_SELECT_SQL = "" +
        "SELECT recorequest_whoareyouwith.whoareyouwith_id " +
        "FROM   recorequest_whoareyouwith " +
        "WHERE  recorequest_whoareyouwith.recorequest_id = ? ";
    public static String RECOREQUEST_LOCATION_SELECT_SQL = "" +
        "SELECT recorequest_location.city_id, " +
        "       recorequest_location.neighbourhood_id " +
        "FROM   recorequest_location " +
        "WHERE  recorequest_location.recorequest_id = ? ";
    public static String RECOREQUEST_REPLY_ANSWERED_NDAYS_SELECT_SQL = "" +
        "SELECT DISTINCT recorequest_reply_user.recorequest_id " +
        "FROM   recorequest_user, " + "       recorequest_reply_user " +
        "WHERE  recorequest_user.initiator_user_id = ? " +
        "       AND recorequest_user.recorequest_id = " +
        "           recorequest_reply_user.recorequest_id " +
        "       AND recorequest_reply_user.reply_send_datetime >= ? ";
    public static String RECOREQUEST_REPLY_USER_ANSWERED_NDAYS_SELECT_SQL = "" +
        "SELECT DISTINCT recorequest_reply_user.recorequest_id " +
        "FROM   recorequest_reply_user " +
        "WHERE  recorequest_reply_user.reply_user_id = ? " +
        "       AND recorequest_reply_user.reply_send_datetime >=? ";
    public static String USER_RECO_DEMAND_INSERT_SQL = "" +
        "INSERT INTO user_reco_demand_tier_precalc " +
        "            (user_reco_demand_tier_precalc.calc_flag, " +
        "             user_reco_demand_tier_precalc.demand_tier_precalc, " +
        "             user_reco_demand_tier_precalc.user_id) " +
        "VALUES      ( ?, " + "              ?, " + "              ? ) " +
        " ON DUPLICATE KEY UPDATE " +
        " user_reco_demand_tier_precalc.calc_flag = ? " +
        " user_reco_demand_tier_precalc.demand_tier_precalc = ? ";
    public static String RECOREQUEST_REPLY_USER_RESTAURANT_REPLY_SELECT_SQL = "" +
        "SELECT recorequest_reply_user.reply_user_id, " +
        "       restaurant_reply.restaurant_id " +
        "FROM   recorequest_reply_user, " + "       restaurant_reply " +
        "WHERE  recorequest_reply_user.algo_ind = ? " +
        "       AND recorequest_reply_user.reply_id = restaurant_reply.reply_id ";
    public static String RESTAURANT_TIPS_TASTESNC_SELECT_SQL = "" +
        "SELECT restaurant_tips_tastesync.user_id, " +
        "       restaurant_tips_tastesync.restaurant_id " +
        "FROM   restaurant_tips_tastesync " +
        "WHERE  restaurant_tips_tastesync.algo_ind = ? ";
    public static String USER_RESTAURANT_FAV_SELECT_SQL = "" +
        "SELECT user_restaurant_fav.user_id, " +
        "       user_restaurant_fav.restaurant_id " +
        "FROM   user_restaurant_fav " +
        "WHERE  user_restaurant_fav.algo_ind = ?";
    public static String USER_CITY_NBRHOOD_INSERT_SQL = "" +
        "INSERT INTO user_city_nbrhood_match " +
        "            (user_city_nbrhood_match.city_id, " +
        "             user_city_nbrhood_match.match_count, " +
        "             user_city_nbrhood_match.neighborhood_id, " +
        "             user_city_nbrhood_match.user_id) " + "VALUES      ( ?, " +
        "              ?, " + "              ?, " + "              ? ) " +
        " ON DUPLICATE KEY UPDATE " +
        " user_city_nbrhood_match.match_count = user_city_nbrhood_match.match_count + 1 ";
    public static String USER_CUISTIER2_INSERT_SQL = "" +
        "INSERT INTO user_cuistier2_match " +
        "            (user_cuistier2_match.cuisine_tier2, " +
        "             user_cuistier2_match.match_count, " +
        "             user_cuistier2_match.user_id) " + "VALUES      ( ?, " +
        "              ?, " + "              ? ) " +
        " ON DUPLICATE KEY UPDATE " +
        " user_cuistier2_match.match_count = user_cuistier2_match.match_count + 1 ";
    public static String USER_PRICE_INSERT_SQL = "" +
        "INSERT INTO user_price_match " +
        "            (user_price_match.match_count, " +
        "             user_price_match.price_id, " +
        "             user_price_match.user_id) " + "VALUES      ( ?, " +
        "              ?, " + "              ? ) " +
        " ON DUPLICATE KEY UPDATE" +
        " user_price_match.match_count = user_price_match.match_count + 1";
    public static String USER_FOLLOW_DATA_SELECT_SQL = "" +
        "SELECT user_follow_data.followee_user_id, " +
        "       user_follow_data.follower_user_id " +
        "FROM   user_follow_data " + "WHERE  user_follow_data.algo_ind = ?";
    public static String COUNT_USER_FOLLOW_DATA_FOLLOWEE_FOLLOWER_SELECT_SQL = "" +
        "SELECT count(*) " + "FROM   user_follow_data " +
        "WHERE  user_follow_data.followee_user_id = ? " +
        "       AND user_follow_data.follower_user_id = ? ";
    public static String USER_RESTAURANT_FAV_UNCHAIN_EXTENDED_INFO = "" +
        "SELECT user_restaurant_fav.restaurant_id " +
        "FROM   user_restaurant_fav, " + "       restaurant_extended_info " +
        "WHERE  user_restaurant_fav.user_id = ? " +
        "AND user_restaurant_fav.restaurant_id = restaurant_extended_info.restaurant_id " +
        "AND restaurant_extended_info.chain_id IS NULL ";
    public static String RESTAURANT_NEIGHBOURHOOD_SELECT_SQL = "" +
        "SELECT restaurant_neighbourhood.restaurant_id, " +
        "       restaurant_neighbourhood.neighbourhood_id " +
        "FROM   restaurant_neighbourhood " +
        "WHERE  restaurant_neighbourhood.restaurant_id = ? ";
    public static String RESTAURANT_CLUSTER_SELECT_SQL = "" +
        "SELECT DISTINCT restaurant_cluster.cluster_id " +
        "FROM   restaurant_cluster " +
        "WHERE  restaurant_cluster.restaurant_id = ? ";
    public static String USER_CUISINE_SELECT_SQL = "" +
        "SELECT user_cuisine.cuisine_id " + "FROM   user_cuisine " +
        "WHERE  user_cuisine.user_id = ? ";
    public static String USER_RESTAURANT_FAV_CHAIN_EXTENDED_INFO = "" +
        "SELECT user_restaurant_fav.restaurant_id " +
        "FROM   user_restaurant_fav, " + "       restaurant_extended_info " +
        "WHERE  user_restaurant_fav.user_id = ? " +
        "AND user_restaurant_fav.restaurant_id = restaurant_extended_info.restaurant_id " +
        "AND restaurant_extended_info.chain_id IS NOT NULL ";
    public static String USER_USER_MATCH_TIER_INSERT_SQL = "" +
        "INSERT INTO user_user_match_tier " +
        "            (user_user_match_tier.calc_flag, " +
        "             user_user_match_tier.match_tier, " +
        "             user_user_match_tier.user_a_id, " +
        "             user_user_match_tier.user_b_id) " + "VALUES      ( ?, " +
        "              ?, " + "              ?, " + "              ? ) " +
        " ON DUPLICATE KEY UPDATE" + " user_user_match_tier.calc_flag = ?" +
        " user_user_match_tier.match_tier = ?";
    public static String USER_FOLLOW_DATA_UPDATE_SQL = "" +
        "UPDATE user_follow_data " + "SET    user_follow_data.algo_ind = ? " +
        "WHERE  user_follow_data.followee_user_id = ? " +
        "       AND user_follow_data.follower_user_id = ? ";
}
