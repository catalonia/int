package com.tastesync.algo.db.queries;

public interface TSDBCommonQueries {
    public static String RESTAURANT_EXTENDED_INFO_CHAINED_SELECT_SQL = "" +
        "SELECT restaurant_extended_info.chain_id " +
        "FROM   restaurant_extended_info " +
        "WHERE  restaurant_extended_info.restaurant_id = ? ";
    public static String RESTAURANT_NEIGHBOURHOOD_CITY_SELECT_SQL = "" +
        "SELECT restaurant.restaurant_city_id, " +
        "       restaurant_neighbourhood.neighbourhood_id, " +
        "       restaurant.price_range " + "FROM   restaurant, " +
        "       restaurant_neighbourhood " +
        "WHERE  restaurant.restaurant_id = ? " +
        " AND restaurant_neighbourhood.restaurant_id =?  " +
        " AND restaurant.restaurant_id = restaurant_neighbourhood.restaurant_id";
    public static String RESTAURANT_PRICERANGE_SELECT_SQL = "" +
        "SELECT restaurant.price_range " + "FROM   restaurant, " +
        "       restaurant_neighbourhood " +
        "WHERE  restaurant.restaurant_id = ? " +
        " AND   restaurant_neighbourhood.restaurant_id = ? " +
        " AND restaurant.restaurant_id = restaurant_neighbourhood.restaurant_id";
    public static String RESTAURANT_CUISINE_SELECT_SQL = "" +
        "SELECT restaurant_cuisine.tier2_cuisine_id " +
        "FROM   restaurant_cuisine " +
        "WHERE  restaurant_cuisine.restaurant_id = ? ";
    public static String RECOREQUEST_REPLY_USER_UPDATE_SQL = "" +
        "UPDATE recorequest_reply_user " +
        "SET    recorequest_reply_user.algo_ind = ? " +
        "WHERE  recorequest_reply_user.reply_user_id = ? ";
    public static String RESTAURANT_TIPS_TASTESYNC_UPDATE_SQL = "" +
        "UPDATE restaurant_tips_tastesync " +
        "SET    restaurant_tips_tastesync.algo_ind = ? " +
        "WHERE  restaurant_tips_tastesync.user_id = ? " +
        "       AND restaurant_tips_tastesync.restaurant_id = ? ";
    public static String USER_RESTAURANT_FAV_UPDATE_SQL = "" +
            "UPDATE user_restaurant_fav " +
            "SET    user_restaurant_fav.algo_ind = ? " +
            "WHERE  user_restaurant_fav.user_id = ? ";
    public static String USER_RESTAURANT_FAV_RESTAURANT_UPDATE_SQL = "" +
        "UPDATE user_restaurant_fav " +
        "SET    user_restaurant_fav.algo_ind = ? " +
        "WHERE  user_restaurant_fav.user_id = ? " +
        "AND user_restaurant_fav.restaurant_id = ? ";
    public static String ALL_USERS_SELECT_SQL = "" + "SELECT users.user_id " +
            "FROM   users ";
}
