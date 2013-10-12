package com.tastesync.algo.db.queries;

public interface UserRecoQueries extends UserUserQueries {
    public static String RECOREQUEST_USER_SELECT_SQL = "" +
        "SELECT recorequest_user.initiator_user_id " +
        "FROM   recorequest_user " +
        "WHERE  recorequest_user.recorequest_id = ?";
    public static String RECOREQUEST_RESTAURANT_CUISINE_SELECT_SQL = "" +
        "SELECT recorequest_cuisine_tier2.cuisine_tier2_id " +
        "FROM   recorequest_cuisine_tier2 " +
        "WHERE  recorequest_cuisine_tier2.recorequest_id = ? ";
    public static String RECOREQUEST_RESTAURANT_CUISINE_TIER1_SELECT_SQL = "" +
        "SELECT recorequest_cuisine_tier1.cuisine_tier1_id " +
        "FROM   recorequest_cuisine_tier1 " +
        "WHERE  recorequest_cuisine_tier1.recorequest_id = ? ";
    public static String RECOREQUEST_PRICE_SELECT_SQL = "" +
        "SELECT recorequest_price.price_id " + "FROM   recorequest_price " +
        "WHERE  recorequest_price.recorequest_id = ? ";
    public static String RECOREQUEST_THEME_SELECT_SQL = "" +
        "SELECT recorequest_theme.theme_id " + "FROM   recorequest_theme " +
        "WHERE  recorequest_theme.recorequest_id = ? ";
    public static String RECOREQUEST_WHOAREYOUWITH_SELECT_SQL = "" +
        "SELECT recorequest_whoareyouwith.whoareyouwith_id " +
        "FROM   recorequest_whoareyouwith " +
        "WHERE  recorequest_whoareyouwith.recorequest_id = ? ";
    public static String RECOREQUEST_TYPEOFREST_SELECT_SQL = "" +
        "SELECT recorequest_typeofrest.typeofrest_id " +
        "FROM   recorequest_typeofrest " +
        "WHERE  recorequest_typeofrest.recorequest_id = ? ";
    public static String RECOREQUEST_OCCASION_SELECT_SQL = "" +
        "SELECT recorequest_occasion.occasion_id " +
        "FROM   recorequest_occasion " +
        "WHERE  recorequest_occasion.recorequest_id = ? ";
    public static String RECOREQUEST_LOCATION_SELECT_SQL = "" +
        "SELECT recorequest_location.city_id, " +
        "       recorequest_location.neighbourhood_id " +
        "FROM   recorequest_location " +
        "WHERE  recorequest_location.recorequest_id = ? ";
    public static String COUNT_MATCH_FOUND_RECOREQUEST_MERGEDPARAMETER_SELECT_SQL =
        "" + "SELECT Count(*) " + "FROM   recorequest_user " +
        "WHERE  recorequest_user.RECOREQUEST_SENT_DATETIME > SYSDATE() - INTERVAL ? DAY " +
        "       AND recorequest_user.initiator_user_id = ? " +
        "       AND recorequest_user.recorequest_parameter_selection_merged = " +
        "( " +
        "SELECT recorequest_user.recorequest_parameter_selection_merged " +
        "FROM   recorequest_user " +
        "WHERE  recorequest_user.recorequest_id = ? " + ")";
    public static String USER_RECO_DEMAND_TIER_PRECALC_SELECT_SQL = "" +
        "SELECT user_reco_demand_tier_precalc.demand_tier_precalc " +
        "FROM   user_reco_demand_tier_precalc " +
        "WHERE  user_reco_demand_tier_precalc.user_id = ? ";
    public static String USER_RECO_SUPPLY_TIER_SELECT_SQL = "" +
        "SELECT user_reco_supply_tier.user_id, " +
        "       user_reco_supply_tier.user_supply_inv_tier " +
        "FROM   user_reco_supply_tier " +
        "WHERE  ( ( user_reco_supply_tier.user_supply_inv_tier = 1 ) " +
        "          OR ( user_reco_supply_tier.user_supply_inv_tier = 2 ) " +
        "          OR ( user_reco_supply_tier.user_supply_inv_tier = 3 ) ) " +
        "       AND Date_format(Now(), '%H') BETWEEN 9 AND 23 " +
        "       AND user_reco_supply_tier.user_id != ? " +
        "       AND  user_reco_supply_tier.user_id NOT IN " +
        "       ( SELECT users_category.USER_ID FROM users_category " +
        "        WHERE users_category.CATEGORY_ID = 5 " +
        "       ) AND user_reco_supply_tier.user_id NOT IN " +
        "       ( SELECT recorequest_ts_assigned.ASSIGNED_USER_ID " +
        "        FROM recorequest_ts_assigned WHERE recorequest_ts_assigned.RECOREQUEST_ID = ? )";
    public static String COUNT_NOT_USER_TOPIC_MATCH_4_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_city_nbrhood_match " +
        "WHERE  user_city_nbrhood_match.match_count >= ? " +
        "       AND user_city_nbrhood_match.city_id = ? " +
        "       AND user_city_nbrhood_match.user_id = ? ";
    public static String COUNT_NOT_USER_TOPIC_MATCH_4_WITH_NBRHD_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_city_nbrhood_match " +
        "WHERE  user_city_nbrhood_match.match_count >= ? " +
        "       AND user_city_nbrhood_match.city_id = ? " +
        "       AND user_city_nbrhood_match.user_id = ? " +
        "       AND user_city_nbrhood_match.neighborhood_id = ? ";
    public static String COUNT_USER_FRIEND_FB_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_friend_fb " +
        "WHERE  user_friend_fb.user_id = ? " +
        "       AND user_friend_fb.user_friend_fb = ? ";
    public static String COUNT_USER_REPORTED_INFO_REPORTED_OTHER_USERS_SELECT_SQL =
        "" + "SELECT Count(*) " + "FROM   user_reported_info " +
        "WHERE  user_reported_info.user_id = ? " +
        "       AND user_reported_info.reported_user_id = ? ";
    public static String COUNT_USER_CUISTIER2_MATCH_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_cuistier2_match " +
        "WHERE  user_cuistier2_match.user_id = ? " +
        "       AND user_cuistier2_match.match_count >= ? " +
        "       AND user_cuistier2_match.cuisine_tier2 IN (1_REPLACE_PARAM) ";
    public static String COUNT_USER_CUISTIER2_MATCH_CUSINE_TIER_MAPPER_SELECT_SQL =
        "" + "SELECT  Count(*) " + "FROM   user_cuistier2_match, " +
        "       cuisine_tier_mapper " +
        "WHERE  user_cuistier2_match.user_id = ? " +
        "       AND user_cuistier2_match.match_count >= ? " +
        "       AND user_cuistier2_match.cuisine_tier2 = cuisine_tier_mapper.tier2_cuisine_id " +
        "       AND cuisine_tier_mapper.tier1_cuisine_id IN (1_REPLACE_PARAM) ";
    public static String COUNT_USER_PRICE_MATCH_SELECT_SQL = "" +
        "SELECT  Count(*) " + "FROM   user_price_match " +
        "WHERE  user_price_match.user_id = ? " +
        "       AND user_price_match.match_count >= ? " +
        "       AND user_price_match.price_id IN (1_REPLACE_PARAM) ";
    public static String COUNT_USER_THEME_MATCH_SELECT_SQL = "" +
        "SELECT  Count(*) " + "FROM   user_theme_match " +
        "WHERE  user_theme_match.user_id = ? " +
        "       AND user_theme_match.match_count >= ? " +
        "       AND user_theme_match.theme_id IN ( 1_REPLACE_PARAM ) ";
    public static String COUNT_USER_WHOAREYOUWITH_MATCH_SELECT_SQL = "" +
        "SELECT user_whoareyouwith_match.user_id " +
        "FROM   user_whoareyouwith_match " +
        "WHERE  user_whoareyouwith_match.user_id = ? " +
        "       AND user_whoareyouwith_match.match_count >= ? " +
        "       AND user_whoareyouwith_match.whoareyouwith_id IN ( 1_REPLACE_PARAM ) ";
    public static String COUNT_USER_TYPEOFREST_MATCH_SELECT_SQL = "" +
        "SELECT user_typeofrest_match.user_id " +
        "FROM   user_typeofrest_match " +
        "WHERE  user_typeofrest_match.user_id = ? " +
        "       AND user_typeofrest_match.match_count >= ? " +
        "       AND user_typeofrest_match.typeofrest_id = ? ";
    public static String COUNT_USER_OCCASION_MATCH_SELECT_SQL = "" +
        "SELECT user_occasion_match.user_id " + "FROM   user_occasion_match " +
        "WHERE  user_occasion_match.user_id = ? " +
        "       AND user_occasion_match.match_count >= ? " +
        "       AND user_occasion_match.occasion_id IN ( 1_REPLACE_PARAM ) ";
    public static String USER_USER_MATCH_TIER_SELECT_SQL = "" +
        "SELECT user_user_match_tier.match_tier " +
        "FROM   user_user_match_tier " +
        "WHERE  user_user_match_tier.user_a_id = ? " +
        "       AND user_user_match_tier.user_b_id = ? ";
    public static String RECOREQUEST_TS_ASSIGNED_INSERT_SQL = "" +
        "INSERT INTO recorequest_ts_assigned " +
        "            (recorequest_ts_assigned.assigned_datetime, " +
        "             recorequest_ts_assigned.assigned_prepopulated_yn, " +
        "             recorequest_ts_assigned.assigned_trusted_type, " +
        "             recorequest_ts_assigned.assigned_user_id, " +
        "             recorequest_ts_assigned.assigned_user_registered_yn, " +
        "             recorequest_ts_assigned.assigned_usertype, " +
        "             recorequest_ts_assigned.algo1_ind, " +
        "             recorequest_ts_assigned.recorequest_id) " +
        "VALUES      ( ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?,    ? ) " + "ON DUPLICATE KEY UPDATE " +
        "recorequest_ts_assigned.assigned_user_id = recorequest_ts_assigned.assigned_user_id";
    public static String USER_RECO_SUPPLY_TIER_INSERT_SQL = "" +
        "INSERT INTO user_reco_supply_tier " +
        "            (user_reco_supply_tier.user_id, " +
        "             user_reco_supply_tier.user_supply_inv_tier, " +
        "             user_reco_supply_tier.user_tier_calc_flag) " +
        "VALUES      ( ?, " + "              ?, " + "              ? ) " +
        "ON DUPLICATE KEY UPDATE " +
        "user_reco_supply_tier.user_supply_inv_tier = user_reco_supply_tier.user_supply_inv_tier, " +
        "user_reco_supply_tier.user_tier_calc_flag = ?";
    public static String COUNT_RECOREQUEST_REPLY_USER_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   recorequest_reply_user " +
        "WHERE  recorequest_reply_user.recorequest_id = ? " +
        "       AND recorequest_reply_user.reply_user_id = ? ";
}
