package com.tastesync.algo.db.queries;

public interface PiUserRecoQueries extends UserRecoQueries {
    public static String USER_CATEGORY_CITY_NBRHOOD_SELECT_SQL = "" +
        "SELECT DISTINCT users_category.user_id " +
        "FROM   user_city_nbrhood_match, " + "       users_category " +
        "WHERE  users_category.category_id = ? " +
        "       AND user_city_nbrhood_match.user_id = users_category.user_id " +
        "       AND user_city_nbrhood_match.match_count >= ? " +
        "       AND user_city_nbrhood_match.city_id = ? " +
        "       AND user_city_nbrhood_match.neighborhood_id = ? ";
    public static String USER_CATEGORY_CITY_SELECT_SQL = "" +
        "SELECT DISTINCT users_category.user_id " +
        "FROM   user_city_nbrhood_match, " + "       users_category " +
        "WHERE  users_category.category_id = ? " +
        "       AND user_city_nbrhood_match.user_id = users_category.user_id " +
        "       AND user_city_nbrhood_match.match_count >= ? " +
        "       AND user_city_nbrhood_match.city_id = ? ";
    public static String PI_RECO_LOG_SELECT_SQL = "" +
        "SELECT DISTINCT pi_reco_log.pi_user_id " + "FROM   pi_reco_log " +
        "WHERE  pi_reco_log.user_id = ? ";
    public static String PI_RECOMMENDATIONS_ALL_SELECT_SQL = "" +
        "SELECT pi_recommendations.recommendation_id " +
        "FROM   pi_recommendations ";
    public static String COUNT_PI_RECO_CUISTIER2_MATCH_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   pi_reco_cuisine_tier2 " +
        "WHERE  pi_reco_cuisine_tier2.recommendation_id = ? " +
        "       AND pi_reco_cuisine_tier2.cuisine_tier2_id IN (1_REPLACE_PARAM) ";
    public static String COUNT_PI_RECO_CUISTIER_TIER2_MATCH_CUSINE_TIER_MAPPER_SELECT_SQL =
        "" + "SELECT Count(*) " + "FROM   pi_reco_cuisine_tier2, " +
        "       cuisine_tier_mapper " +
        "WHERE  pi_reco_cuisine_tier2.recommendation_id = ? " +
        "       AND cuisine_tier_mapper.tier1_cuisine_id IN (1_REPLACE_PARAM) " +
        "       AND pi_reco_cuisine_tier2.cuisine_tier2_id = cuisine_tier_mapper.tier2_cuisine_id ";
    public static String COUNT_PI_RECO_PRICE_MATCH_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   pi_reco_price " +
        "WHERE  pi_reco_price.recommendation_id = ? " +
        "       AND pi_reco_price.price_id IN (1_REPLACE_PARAM) ";
    public static String COUNT_PI_USER_THEME_MATCH_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   pi_reco_theme " +
        "WHERE  pi_reco_theme.recommendation_id = ? " +
        "       AND pi_reco_theme.theme_id IN (1_REPLACE_PARAM) ";
    public static String COUNT_PI_RECO_WHOAREYOUWITH_MATCH_SELECT_SQL = "" +
        "SELECT pi_reco_whoareyouwith.recommendation_id " +
        "FROM   pi_reco_whoareyouwith " +
        "WHERE  pi_reco_whoareyouwith.recommendation_id = ? " +
        "       AND pi_reco_whoareyouwith.whoareyouwith_id IN (1_REPLACE_PARAM) ";
    public static String COUNT_PI_RECO_TYPEOFREST_MATCH_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   pi_reco_typeofrest " +
        "WHERE  pi_reco_typeofrest.recommendation_id = ? " +
        "       AND pi_reco_typeofrest.typeofrest_id IN (1_REPLACE_PARAM) ";
    public static String COUNT_PI_RECO_OCCASION_MATCH_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   pi_reco_occasion " +
        "WHERE  pi_reco_occasion.recommendation_id = ? " +
        "       AND pi_reco_occasion.occasion_id IN (1_REPLACE_PARAM) ";
    public static String EXCLUDE_RESTAURANT_ID_FRM_PI_RECOMMENDATIONS_SELECT_SQL =
        "" + "SELECT pi_recommendations.recommendation_id " +
        "FROM   pi_recommendations " +
        "WHERE  pi_recommendations.restaurant_id IN (SELECT " +
        "       pi_recommendations.restaurant_id " +
        "                                           FROM   pi_recommendations, " +
        "                                                  pi_reco_log " +
        "                                           WHERE  pi_reco_log.user_id = ? " +
        "                                                  AND " +
        "              pi_reco_log.recommendation_id = " +
        "              pi_recommendations.recommendation_id " +
        "                                          ) ";
    public static String PI_RECOMMEDATION_ID_TEXT_SELECT_SQL = "" +
        "SELECT pi_recommendations.restaurant_id, " +
        "       pi_recommendations.recommendation_text " +
        "FROM   pi_recommendations " +
        "WHERE  pi_recommendations.recommendation_id = ? ";
    public static String PI_RECO_LOG_INSERT_SQL = "" +
        "INSERT INTO pi_reco_log " +
        "            (pi_reco_log.pi_reco_log_id, " +
        "             pi_reco_log.pi_user_id, " +
        "             pi_reco_log.recommendation_id, " +
        "             pi_reco_log.recommended_datetime, " +
        "             pi_reco_log.user_id) " + "VALUES      ( ?, " +
        "              ?, " + "              ?," + "              ?, " +
        "              ? " + ")";
    
    public static String RECOREQUEST_REPLY_USER_INSERT_SQL = "" +
            "INSERT INTO recorequest_reply_user " +
            "            (recorequest_reply_user.recorequest_id, " +
            "             recorequest_reply_user.reply_id, " +
            "             recorequest_reply_user.reply_like_initiator, " +
            "             recorequest_reply_user.reply_send_datetime, " +
            "             recorequest_reply_user.reply_text, " +
            "             recorequest_reply_user.reply_ts_user_id, " +
            "             recorequest_reply_user.reply_user_id, " +
            "             recorequest_reply_user.algo1_ind, " +
            "             recorequest_reply_user.algo2_ind) " +
            "VALUES      ( ?, " + "              ?, " + "              ?, " +
            "              ?, ?, " + "              ?,?,?, " + "              ? )";
    public static final String RECOREQUEST_USER_FRIEND_SELECT_SQL = "" +
            "SELECT recorequest_user.initiator_user_id, " +
            "       recorequest_user.recorequest_free_text " +
            "FROM   recorequest_user " +
            "WHERE  recorequest_user.recorequest_id = ? ";
    
    public static String RESTAURANT_REPLY_INSERT_SQL = "" +
            "INSERT INTO restaurant_reply " +
            "            (restaurant_reply.created, " +
            "             restaurant_reply.initiator_went_yn, " +
            "             restaurant_reply.reply_id, " +
            "             restaurant_reply.restaurant_id) " + "VALUES      ( ?, " +
            "              ?, " + "              ?, " + "              ? )";
    
    public static String USER_RESTAURANT_INSERT_SQL = "" +
            "INSERT INTO user_restaurant_reco " +
            "            (user_restaurant_reco.recommendee_user_id, " +
            "             user_restaurant_reco.recommender_user_id, " +
            "             user_restaurant_reco.reply_id, " +
            "             user_restaurant_reco.restaurant_id, " +
            "             user_restaurant_reco.updated_datetime) " +
            "VALUES      ( ?, " + "              ?, " + "              ?, " +
            "              ?, " + "              ? )";
    public static String COUNT_REPLIES_RECOREQUEST_REPLY_USER_SELECT_SQL = "" +
            "SELECT Count(*) " + "FROM   recorequest_reply_user " +
            "WHERE  recorequest_reply_user.recorequest_id = ? " +
            "       AND recorequest_reply_user.reply_user_id = ?";
    
    public static String USER_POINTS_UPDATE_SQL = "" + "UPDATE users " +
            "SET    users.user_points = users.user_points + ? " +
            "WHERE  users.user_id = ? ";
    
    
}
