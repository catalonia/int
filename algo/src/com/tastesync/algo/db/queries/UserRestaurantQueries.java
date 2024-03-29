package com.tastesync.algo.db.queries;

public interface UserRestaurantQueries extends TSDBCommonQueries {
    public static String RESTAURANT_FLAGGED_SELECT_SQL = "" +
        "SELECT restaurant.restaurant_id, " + "restaurant.restaurant_city_id " +
        "FROM   restaurant " + "WHERE  restaurant.algo2_ind = ?";
    public static String FLAGGED_RESTAURANT_CITY_SELECT_SQL = "" +
        "SELECT DISTINCT restaurant.restaurant_city_id " +
        "FROM   restaurant " + "WHERE  restaurant.algo2_ind = ?";
    public static String CALCULATE_MEDIAN_USRS_NUMBER_FOR_CITY_SELECT_SQL = "" +
        " SELECT " + " t1.RESTAURANT_CITY_ID," + " t1.4SQ_USERS_COUNT" +
        " FROM " + " (SELECT" + " @rownum:=@rownum+1 as row_number," +
        " t0.RESTAURANT_ID," + " t0.RESTAURANT_CITY_ID," +
        " t0.4SQ_USERS_COUNT" + " FROM" + " (" + " SELECT" +
        " restaurant.RESTAURANT_ID," + " restaurant.RESTAURANT_CITY_ID," +
        " restaurant_factual_4sqvenue.4SQ_USERS_COUNT" +
        " FROM restaurant, restaurant_factual_4sqvenue" + " WHERE" +
        " restaurant.RESTAURANT_CITY_ID = ? AND" +
        " restaurant.RESTAURANT_ID = restaurant_factual_4sqvenue.RESTAURANT_ID" +
        " )t0, (SELECT @rownum:=0) r" +
        " ORDER BY t0.RESTAURANT_CITY_ID, t0.4SQ_USERS_COUNT" + " ) t1," +
        " (" + " SELECT t10.RESTAURANT_CITY_ID, count(*) as total_rows" +
        " FROM" + " (" + " SELECT " + " restaurant.RESTAURANT_ID," +
        " restaurant.RESTAURANT_CITY_ID, " +
        " restaurant_factual_4sqvenue.4SQ_USERS_COUNT" +
        " FROM restaurant, restaurant_factual_4sqvenue" + " WHERE" +
        " restaurant.RESTAURANT_CITY_ID = ? AND " +
        " restaurant.RESTAURANT_ID = restaurant_factual_4sqvenue.RESTAURANT_ID" +
        " ) t10" + " group by " + " t10.RESTAURANT_CITY_ID" + " ) t2" +
        " WHERE" + " t1.RESTAURANT_CITY_ID = t2.RESTAURANT_CITY_ID AND" +
        " t1.row_number = round(total_rows/2)" +
        " group by t1.RESTAURANT_CITY_ID";
    public static String RESTAURANT_RESTAURANT_FACTUAL_4SQVENUE_SELECT_SQL = "" +
        "SELECT restaurant.restaurant_id, " +
        "       restaurant.restaurant_city_id, " +
        "       restaurant_factual_4sqvenue.4SQ_USERS_COUNT " +
        "FROM   restaurant, " + "       restaurant_factual_4sqvenue " +
        "WHERE  restaurant.restaurant_id = restaurant_factual_4sqvenue.restaurant_id " +
        "ORDER  BY restaurant.restaurant_city_id,, restaurant_factual_4sqvenue.4SQ_USERS_COUNT ";
    public static String RESTAURANT_SELECT_SQL = "" +
        "SELECT restaurant.restaurant_name, " +
        "       restaurant.factual_rating, " +
        "       restaurant.price_range, " +
        "       restaurant.restaurant_lat, " +
        "       restaurant.restaurant_lon, " +
        "       restaurant.restaurant_city_id, " +
        "       restaurant.restaurant_hours " + "FROM   restaurant " +
        "WHERE  restaurant.restaurant_id = ? ";
    public static String RESTAURANT_EXTENDED_INFO_SELECT_SQL = "" +
        "SELECT restaurant_extended_info.address, " +
        "       restaurant_extended_info.tel " +
        "FROM   restaurant_extended_info " +
        "WHERE  restaurant_extended_info.restaurant_id = ? ";
    public static String RESTAURANT_FACTUAL_4SQVENUE_SELECT_SQL = "" +
        "SELECT restaurant_factual_4sqvenue.4SQ_PHOTOS_COUNT, " +
        "       restaurant_factual_4sqvenue.4SQ_CHECKINS_COUNT, " +
        "       restaurant_factual_4sqvenue.4SQ_TIPS_COUNT, " +
        "       restaurant_factual_4sqvenue.4SQ_USERS_COUNT " +
        "FROM restaurant_factual_4sqvenue " +
        "WHERE restaurant_factual_4sqvenue.RESTAURANT_ID = ? ";
    public static String RESTAURANT_MENU_SELECT_SQL = "" +
        "SELECT restaurant_menu.menu_mobileurl " + "FROM   restaurant_menu " +
        "WHERE  restaurant_menu.restaurant_id = ? ";
    public static String RESTAURANT_INFO_POPULARITY_TIER_INSERT_SQL = "" +
        "INSERT INTO restaurant_info_popularity_tier " +
        "            (restaurant_info_popularity_tier.restaurant_id, " +
        "             restaurant_info_popularity_tier.tier_id) " +
        "VALUES      ( ?, " + "              ? ) " +
        "ON DUPLICATE KEY UPDATE " +
        "restaurant_info_popularity_tier.tier_id = ? ";
    public static String RECOREQUEST_REPLY_USER_SELECT_SQL = "" +
        "SELECT recorequest_reply_user.reply_user_id, " +
        "       restaurant_reply.restaurant_id " +
        "FROM   recorequest_reply_user, " + "       restaurant_reply " +
        "WHERE  recorequest_reply_user.algo2_ind = ? " +
        "       AND recorequest_reply_user.reply_id = restaurant_reply.reply_id ";
    public static String RESTAURANT_TIPS_TASTESYNC_SELECT_SQL = "" +
        "SELECT restaurant_tips_tastesync.user_id, " +
        "       restaurant_tips_tastesync.restaurant_id " +
        "FROM   restaurant_tips_tastesync " +
        "WHERE  restaurant_tips_tastesync.algo2_ind = ? ";
    public static String USER_RESTAURANT_FAV_SELECT_SQL = "" +
        "SELECT user_restaurant_fav.user_id, " +
        "       user_restaurant_fav.restaurant_id " +
        "FROM   user_restaurant_fav " +
        "WHERE  user_restaurant_fav.algo2_ind = ? ";
    public static String RESTAURANT_NEIGHBOURHOOD_BASEDON_NEIGHBOURHOODID_SELECT_SQL =
        "" + "SELECT restaurant_neighbourhood.restaurant_id " +
        "FROM   restaurant_neighbourhood " +
        "WHERE  restaurant_neighbourhood.neighbourhood_id = ? ";
    public static String RESTAURANT_NEIGHBOURHOOD_BASEDON_CUISINEID_SELECT_SQL = "" +
        "SELECT restaurant_neighbourhood.restaurant_id " +
        "FROM   restaurant_cuisine " +
        "WHERE  restaurant_cuisine.tier2_cuisine_id = ? ";
    public static String RESTAURANT_NEIGHBOURHOOD_BASEDON_PRICERANGE_SELECT_SQL = "" +
        "SELECT restaurant.restaurant_id " + "FROM   restaurant " +
        "WHERE  restaurant.price_range = ? ";
    public static String COUNT_USER_PRICE_RESTAURANT_MATCH_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_price_match, " +
        "       restaurant " + "WHERE  restaurant.restaurant_id = ? " +
        "       AND restaurant.price_range = user_price_match.price_id " +
        "       AND user_price_match.match_count >= 1 " +
        "       AND user_price_match.user_id = ? ";
    public static String COUNT_USER_FOLLOW_USER_RESTAURANT_FAV_MATCH_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_follow_data, " +
        "       user_restaurant_fav " +
        "WHERE  user_follow_data.follower_user_id = ? " +
        "       AND user_follow_data.followee_user_id = user_restaurant_fav.user_id " +
        "       AND user_restaurant_fav.restaurant_id = ? ";
    public static String COUNT_USER_FOLLOW_USER_RESTAURANT_RECO_MATCH_SELECT_SQL =
        "" + "SELECT Count(*) " + "FROM   user_follow_data, " +
        "       user_restaurant_reco " +
        "WHERE  user_follow_data.follower_user_id = ? " +
        "       AND user_follow_data.followee_user_id = " +
        "           user_restaurant_reco.recommender_user_id " +
        "       AND user_restaurant_reco.restaurant_id = ? ";
    public static String COUNT_USER_FRIEND_USER_RESTAURANT_FAV_MATCH_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_friend_tastesync, " +
        "       user_restaurant_fav " +
        "WHERE  user_friend_tastesync.user_id = ? " +
        "       AND user_friend_tastesync.friend_id = user_restaurant_fav.user_id " +
        "       AND user_friend_tastesync.friend_trusted_flag = 1 " +
        "       AND user_restaurant_fav.restaurant_id = ? ";
    public static String COUNT_USER_FRIEND_USER_RESTAURANT_RECO_MATCH_SELECT_SQL =
        "" + "SELECT Count(*) " + "FROM   user_friend_tastesync, " +
        "       user_restaurant_reco " +
        "WHERE  user_friend_tastesync.user_id = ? " +
        "       AND user_friend_tastesync.friend_id = " +
        "           user_restaurant_reco.recommender_user_id " +
        "       AND user_friend_tastesync.friend_trusted_flag = 1 " +
        "       AND user_restaurant_reco.restaurant_id = ? ";
    public static String USER_RESTAURANT_MATCH_COUNTER_MATCH_INSERT_SQL1 = "" +
        "INSERT INTO user_restaurant_match_counter " +
        "            (user_restaurant_match_counter.calc_flag, " +
        "             user_restaurant_match_counter.match_counter, " +
        "             user_restaurant_match_counter.restaurant_id, " +
        "             user_restaurant_match_counter.user_id) " +
        "VALUES      ( ?, " + "              ?, " + "              ?, " +
        "              ? ) ";
    public static String RESTAURANT_FLAGGED_UPDATE_SQL = "" +
        "UPDATE restaurant " + "SET    restaurant.algo2_ind = ? " +
        "WHERE  restaurant.restaurant_id = ?";
    public static String RESTAURANT_FROM_NGBRHOOD_SELECT_SQL = "" +
        "SELECT restaurant_neighbourhood.restaurant_id, " +
        "       restaurant_info_popularity_tier.tier_id " +
        "FROM   restaurant_neighbourhood,restaurant_info_popularity_tier " +
        "WHERE  restaurant_neighbourhood.neighbourhood_id = ? " +
        "       AND restaurant_info_popularity_tier.restaurant_id = restaurant_neighbourhood.restaurant_id";
    public static String RESTAURANT_FROM_PRICERANGE_SELECT_SQL = "" +
        "SELECT restaurant.restaurant_id, " +
        "       restaurant_info_popularity_tier.tier_id " +
        "FROM   restaurant, restaurant_info_popularity_tier " +
        "WHERE  restaurant.price_range = ? " +
        "       AND restaurant_info_popularity_tier.restaurant_id = restaurant.restaurant_id";
    public static String RESTAURANT_FROM_CUISINE_SELECT_SQL = "" +
        "SELECT restaurant_cuisine.restaurant_id, " +
        "       restaurant_info_popularity_tier.tier_id " +
        "FROM   restaurant_cuisine,restaurant_info_popularity_tier " +
        "WHERE  restaurant_cuisine.tier2_cuisine_id = ? " +
        "       AND restaurant_info_popularity_tier.restaurant_id = restaurant_cuisine.restaurant_id";
    public static String COUNT_USER_CITY_RESTAURANT_NHBR_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_city_nbrhood_match, " +
        "       restaurant_neighbourhood " +
        "WHERE  restaurant_neighbourhood.restaurant_id = ? " +
        "       AND restaurant_neighbourhood.neighbourhood_id = " +
        "           user_city_nbrhood_match.neighborhood_id " +
        "       AND user_city_nbrhood_match.match_count >= 1 " +
        "       AND user_city_nbrhood_match.user_id = ? ";
    public static String COUNT_USER_CUISINETIER2_RESTAURANT_CUISINE_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_cuistier2_match, " +
        "       restaurant_cuisine " +
        "WHERE  restaurant_cuisine.restaurant_id = ? " +
        "       AND restaurant_cuisine.tier2_cuisine_id = " +
        "           user_cuistier2_match.cuisine_tier2 " +
        "       AND user_cuistier2_match.match_count >= 1 " +
        "       AND user_cuistier2_match.user_id = ? ";
    public static String COUNT_USER_PRICE_RESTAURANT_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_price_match, " +
        "       restaurant " + "WHERE  restaurant.restaurant_id = ? " +
        "       AND restaurant.price_range = user_price_match.price_id " +
        "       AND user_price_match.match_count >= 1 " +
        "       AND user_price_match.user_id = ? ";
    public static String COUNT_USER_FOLLOW_DATA_USER_RESTAURANT_FAV_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_follow_data, " +
        "       user_restaurant_fav " +
        "WHERE  user_follow_data.follower_user_id = ? " +
        "       AND user_follow_data.followee_user_id = user_restaurant_fav.user_id " +
        "       AND user_restaurant_fav.restaurant_id = ? ";
    public static String COUNT_USER_FOLLOW_DATA_USER_RESTAURANT_RECO_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_follow_data, " +
        "       user_restaurant_reco " +
        "WHERE  user_follow_data.follower_user_id = ? " +
        "       AND user_follow_data.followee_user_id = " +
        "           user_restaurant_reco.recommender_user_id " +
        "       AND user_restaurant_reco.restaurant_id = ? ";
    public static String COUNT_USER_FRIEND_USER_RESTAURANT_FAV_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_friend_tastesync, " +
        "       user_restaurant_fav " +
        "WHERE  user_friend_tastesync.user_id = ? " +
        "       AND user_friend_tastesync.friend_id = user_restaurant_fav.user_id " +
        "       AND user_friend_tastesync.friend_trusted_flag = 1 " +
        "       AND user_restaurant_fav.restaurant_id = ? ";
    public static String COUNT_USER_FRIEND_RESTAURANT_RECO_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   user_friend_tastesync, " +
        "       user_restaurant_reco " +
        "WHERE  user_friend_tastesync.user_id = ? " +
        "       AND user_friend_tastesync.friend_id = " +
        "           user_restaurant_reco.recommender_user_id " +
        "       AND user_friend_tastesync.friend_trusted_flag = 1 " +
        "       AND user_restaurant_reco.restaurant_id = ? ";
    public static String USER_RESTAURANT_MATCH_COUNTER_DELETE_SQL = "DELETE FROM USER_RESTAURANT_MATCH_COUNTER " +
        "WHERE USER_ID = ?";
    public static String USER_RESTAURANT_MATCH_COUNTER_INSERT_SQL = "" +
        "INSERT INTO user_restaurant_match_counter " +
        "            (user_restaurant_match_counter.calc_flag, " +
        "             user_restaurant_match_counter.match_counter, " +
        "             user_restaurant_match_counter.restaurant_id, " +
        "             user_restaurant_match_counter.user_id, " +
        "             user_restaurant_match_counter.user_restaurant_rank) " +
        "VALUES      ( ?, " + "              ?, " + "              ?, " +
        "              ?,  " + "              ? )" +
        " ON DUPLICATE KEY UPDATE " +
        " user_restaurant_match_counter.calc_flag = ?, " +
        " user_restaurant_match_counter.match_counter = ?," +
        " user_restaurant_match_counter.user_restaurant_rank = ?";
    public static String USER_MATCH_COUNTER_SELECT_SQL = "" +
        "SELECT user_restaurant_match_counter.match_counter " +
        "FROM   user_restaurant_match_counter " +
        "WHERE  user_restaurant_match_counter.user_id = ? " +
        "       AND user_restaurant_match_counter.restaurant_id = ? ";
    public static String RESTAURANT_INFO_POPULARITY_TIER_SELECT_SQL = "" +
        "SELECT restaurant_info_popularity_tier.tier_id " +
        "FROM   restaurant_info_popularity_tier " +
        "WHERE  restaurant_info_popularity_tier.restaurant_id = ? ";
    public static String RANK_USER_RESTAURANT_MATCH_COUNTER_INSERT_SQL = "" +
        "INSERT INTO user_restaurant_match_counter " +
        "            (user_restaurant_match_counter.restaurant_id, " +
        "             user_restaurant_match_counter.user_id, " +
        "             user_restaurant_match_counter.user_restaurant_rank) " +
        "VALUES      ( ?, " + "              ?, " + "              ? ) " +
        " ON DUPLICATE KEY UPDATE " +
        " user_restaurant_match_counter.user_restaurant_rank = ?";
    public static String COUNT_USER_CITY_RESTAURANT_SEARCH_RESULTS_SELECT_SQL = "" +
        "SELECT COUNT(*) " + "FROM   RESTAURANT x " +
        "       LEFT OUTER JOIN USER_RESTAURANT_MATCH_COUNTER y " +
        "                    ON x.RESTAURANT_CITY_ID = ? " +
        "                       AND x.RESTAURANT_ID = y.RESTAURANT_ID " +
        "                       AND y.USER_ID = ? " +
        "ORDER  BY Isnull(y.USER_RESTAURANT_RANK), " +
        "          y.USER_RESTAURANT_RANK ASC ";
    public static String USER_CITY_RESTAURANT_SEARCH_RESULTS_SELECT_SQL = "" +
        "SELECT x.RESTAURANT_ID, " + "       y.USER_RESTAURANT_RANK " +
        "FROM   RESTAURANT x " +
        "       LEFT OUTER JOIN USER_RESTAURANT_MATCH_COUNTER y " +
        "                    ON x.RESTAURANT_CITY_ID = ? " +
        "                       AND x.RESTAURANT_ID = y.RESTAURANT_ID " +
        "                       AND y.USER_ID = ? " +
        "ORDER  BY Isnull(y.USER_RESTAURANT_RANK), " +
        "          y.USER_RESTAURANT_RANK ASC " + "LIMIT ?, ? ";
    public static String EXISTING_USER_RESTAURANT_MATCH_COUNTER_SELECT_SQL = "" +
        "SELECT USER_RESTAURANT_MATCH_COUNTER.RESTAURANT_ID, " +
        "       USER_RESTAURANT_MATCH_COUNTER.MATCH_COUNTER, " +
        "       RESTAURANT_INFO_POPULARITY_TIER.TIER_ID, " +
        "       USER_RESTAURANT_MATCH_COUNTER.USER_RESTAURANT_RANK " +
        "FROM   USER_RESTAURANT_MATCH_COUNTER, " +
        "       RESTAURANT_INFO_POPULARITY_TIER " +
        "WHERE  USER_RESTAURANT_MATCH_COUNTER.USER_ID = ? " +
        "       AND USER_RESTAURANT_MATCH_COUNTER.RESTAURANT_ID = " +
        "           RESTAURANT_INFO_POPULARITY_TIER.RESTAURANT_ID ";
}
