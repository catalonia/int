package com.tastesync.dataextraction.db.queries;

public interface FoursquareQueries {
    public static String IDENTIFY_STATUS_ATTEMPT_BEFORE_NDAYS_FOURSQ_FACTUAL_EXTRACT_UPDATE_SQL =
        "" + "UPDATE 4SQ_FACTUAL_EXTRACT " +
        "SET    4SQ_FACTUAL_EXTRACT.4SQ_PULL_ELIG_IND = ? " +
        "WHERE  Datediff(Now(), 4SQ_FACTUAL_EXTRACT.LAST_UPDATED) > ? " +
        "       AND 4SQ_FACTUAL_EXTRACT.LAST_MATCH_IND = ?";
    public static String USER_ACCESS_NDAYS_STATUS_ATTEMPT_BEFORE_NDAYS_FOURSQ_FACTUAL_EXTRACT_UPDATE_SQL =
        "" + "UPDATE 4SQ_FACTUAL_EXTRACT " +
        "SET    4SQ_FACTUAL_EXTRACT.4SQ_PULL_ELIG_IND = ? " +
        "WHERE  Datediff(Now(), 4SQ_FACTUAL_EXTRACT.LAST_UPDATED) > ? " +
        "       AND 4SQ_FACTUAL_EXTRACT.LAST_MATCH_IND = ? " +
        "       AND 4SQ_FACTUAL_EXTRACT.RESTAURANT_ID = (SELECT " +
        "           HISTORICAL_RESTAURANT_DETAIL_ACCESS.RESTAURANT_ID " +
        "                                                       FROM " +
        "               HISTORICAL_RESTAURANT_DETAIL_ACCESS " +
        "                                                       WHERE " +
        "               Datediff(Now(), " +
        "               HISTORICAL_RESTAURANT_DETAIL_ACCESS.ACCESS_DATETIME) < " +
        "               ?)";
    public static String UNSUCCESSFUL_ATTEMPT_BEFORE_NDAYS_FOURSQ_FACTUAL_EXTRACT_UPDATE_SQL =
        "" + "UPDATE 4SQ_FACTUAL_EXTRACT " +
        "SET    4SQ_FACTUAL_EXTRACT.4SQ_PULL_ELIG_IND = 1 " +
        "WHERE  Datediff(Now(), 4SQ_FACTUAL_EXTRACT.LAST_UPDATED) > 45 " +
        "       AND 4SQ_FACTUAL_EXTRACT.LAST_MATCH_IND = 0";
    public static String RESTAURANT_DATA_LIST_SELECT_SQL = "" +
        "SELECT y.RESTAURANT_ID, " + "       y.4SQ_PULL_ELIG_IND, " +
        "       y.FACTUAL_ID, " + "       y.RESTAURANT_NAME, " +
        "       y.RESTAURANT_LAT, " + "       y.RESTAURANT_LON, " +
        "       RESTAURANT_EXTENDED_INFO.TEL " +
        "FROM   (SELECT x.RESTAURANT_ID, " +
        "               x.4SQ_PULL_ELIG_IND, " +
        "               RESTAURANT.FACTUAL_ID, " +
        "               RESTAURANT.RESTAURANT_NAME, " +
        "               RESTAURANT.RESTAURANT_LAT, " +
        "               RESTAURANT.RESTAURANT_LON " +
        "        FROM   (SELECT 4SQ_FACTUAL_EXTRACT.RESTAURANT_ID, " +
        "                       4SQ_FACTUAL_EXTRACT.4SQ_PULL_ELIG_IND " +
        "                FROM   4SQ_FACTUAL_EXTRACT " +
        "                WHERE  4SQ_FACTUAL_EXTRACT.4SQ_PULL_ELIG_IND > ? " +
        "                ORDER  BY 4SQ_FACTUAL_EXTRACT.4SQ_PULL_ELIG_IND " +
        "                          DESC) x, " + "               RESTAURANT " +
        "        WHERE  x.RESTAURANT_ID = RESTAURANT.RESTAURANT_ID) y " +
        "       LEFT OUTER JOIN RESTAURANT_EXTENDED_INFO " +
        "                    ON y.RESTAURANT_ID = RESTAURANT_EXTENDED_INFO.RESTAURANT_ID ";
    public static String MATCH_FOURSQUARE_STATUS_UPDATE_SQL = "" +
        "UPDATE 4SQ_FACTUAL_EXTRACT " +
        "SET    4SQ_FACTUAL_EXTRACT.4SQ_PULL_ELIG_IND = ? " +
        "                                                             , " +
        "              4SQ_FACTUAL_EXTRACT.LAST_MATCH_IND = ? " +
        "                                                             , " +
        "              4SQ_FACTUAL_EXTRACT.LAST_UPDATED = ? " +
        "WHERE  4SQ_FACTUAL_EXTRACT.RESTAURANT_ID = ?";
}
