package com.tastesync.dataextraction.util;

public interface DBQueries {
    public static final String CITY_FACTUAL_SELECT_SQL = "";

    //public static final String FACTUAL_ID_SELECT_SQL = "select factual_id,  name, latitude, longitude from `restaurants.2012.11.20`";
    public static final String FACTUAL_ID_SELECT_SQL = "select factualId,  restaurantName, latitude, longitude, tel from mobioneer_factual_extended2";
    public static final String DATEDIFF_28_4SQ_FACTUAL_EXTRACT_UPDATE_SQL = "" +
        "UPDATE 4sq_factual_extract " +
        "SET    4sq_factual_extract.4sq_pull_elig_ind = 2 " +
        "WHERE  Datediff(Now(), 4sq_factual_extract.last_updated) > 28 " +
        "       AND 4sq_factual_extract.last_match_ind = 1";
    public static final String USERACESS_30_DATEDIFF_28_4SQ_FACTUAL_EXTRACT_UPDATE_SQL =
        "" + "UPDATE 4sq_factual_extract " +
        "SET    4sq_factual_extract.4sq_pull_elig_ind = 3 " +
        "WHERE  Datediff(Now(), 4sq_factual_extract.last_updated) > 28 " +
        "       AND 4sq_factual_extract.last_match_ind = 1 " +
        "       AND 4sq_factual_extract.restaurant_id = (SELECT " +
        "           historical_restaurant_detail_access.restaurant_id " +
        "                                               FROM " +
        "           historical_restaurant_detail_access " +
        "                                               WHERE " +
        "               Datediff(Now(), " +
        "               historical_restaurant_detail_access.access_datetime) < " +
        "               30)";
    public static final String FAILED_ATTEMPT_45_4SQ_FACTUAL_EXTRACT_UPDATE_SQL = "" +
        "UPDATE 4sq_factual_extract " +
        "SET    4sq_factual_extract.4sq_pull_elig_ind = 1 " +
        "WHERE  Datediff(Now(), 4sq_factual_extract.last_updated) > 45 " +
        "       AND 4sq_factual_extract.last_match_ind = 0";
    public static final String USERACESS_30_FAILED_ATTEMPT_DATEDIFF_28_4SQ_FACTUAL_EXTRACT_UPDATE_SQL =
        "" + "UPDATE 4sq_factual_extract " +
        "SET    4sq_factual_extract.4sq_pull_elig_ind = 1 " +
        "WHERE  Datediff(Now(), 4sq_factual_extract.last_updated) > 28 " +
        "       AND 4sq_factual_extract.last_match_ind = 0 " +
        "       AND 4sq_factual_extract.restaurant_id = (SELECT " +
        "           historical_restaurant_detail_access.restaurant_id " +
        "                                               FROM " +
        "           historical_restaurant_detail_access " +
        "                                               WHERE " +
        "               Datediff(Now(), " +
        "               historical_restaurant_detail_access.access_datetime) < " +
        "               30)";
    public static final String DATA_EXTRACTED_4SQ_RESTAURANT_LIST_SELECT_SQL = "" +
        "SELECT y.restaurant_id, " + "       y.4sq_pull_elig_ind, " +
        "       y.factual_id, " + "       y.restaurant_name, " +
        "       y.restaurant_lat, " + "       y.restaurant_lon, " +
        "       restaurant_extended_info.tel " +
        "FROM   (SELECT x.restaurant_id, " +
        "               x.4sq_pull_elig_ind, " +
        "               restaurant.factual_id, " +
        "               restaurant.restaurant_name, " +
        "               restaurant.restaurant_lat, " +
        "               restaurant.restaurant_lon " +
        "        FROM   (SELECT 4sq_factual_extract.restaurant_id, " +
        "                       4sq_factual_extract.4sq_pull_elig_ind " +
        "                FROM   4sq_factual_extract " +
        "                WHERE  4sq_factual_extract.4sq_pull_elig_ind > 0 " +
        "                ORDER  BY 4sq_factual_extract.4sq_pull_elig_ind DESC " +
        "               -- END OF This query gets the restaurant list " +
        "               )x, " + "               restaurant " +
        "        WHERE  x.restaurant_id = restaurant.restaurant_id)y " +
        "       LEFT OUTER JOIN restaurant_extended_info " +
        "                    ON y.restaurant_id = restaurant_extended_info.restaurant_id ";
    public static final String MATCH_FOUND_STATUS_4SQ_FACTUAL_EXTRACT_UPDATE_SQL =
        "" + "UPDATE 4sq_factual_extract " +
        "SET    4sq_factual_extract.4sq_pull_elig_ind = 0 " +
        "                                             AND " +
        "       4sq_factual_extract.last_match_ind = 1 " +
        "                                             AND " +
        "       4sq_factual_extract.last_updated = ? " +
        "WHERE  4sq_factual_extract.restaurant_id = ?";
}
