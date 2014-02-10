package com.tastesync.fb.db.queries;

public interface FbQueries {
    public static String FB_LOCATION_DATA_INSERT_SQL = "" +
        "INSERT INTO FB_LOCATION_DATA " +
        "            (FB_LOCATION_DATA.FB_CITY, " +
        "             FB_LOCATION_DATA.FB_COUNTRY, " +
        "             FB_LOCATION_DATA.FB_LAT, " +
        "             FB_LOCATION_DATA.FB_LOCATION_ID, " +
        "             FB_LOCATION_DATA.FB_LOCATION_NAME, " +
        "             FB_LOCATION_DATA.FB_LONG, " +
        "             FB_LOCATION_DATA.FB_STATE, " +
        "             FB_LOCATION_DATA.FB_ZIP) " + "VALUES      ( ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ? ) " + "ON DUPLICATE KEY UPDATE " +
        "FB_LOCATION_DATA.FB_LOCATION_ID=FB_LOCATION_DATA.FB_LOCATION_ID ";
    public static String COUNT_FACEBOOK_USER_DATA_SELECT_SQL = "" +
        "SELECT Count(*) " + "FROM   FACEBOOK_USER_DATA " +
        "WHERE  USER_FB_ID =? ";
    public static String FACEBOOK_USER_DATA_INSERT_SQL = "" +
        "INSERT INTO FACEBOOK_USER_DATA " +
        "            (FACEBOOK_USER_DATA.AGE_RANGE, " +
        "             FACEBOOK_USER_DATA.BIRTHDAY, " +
        "             FACEBOOK_USER_DATA.CREATED, " +
        "             FACEBOOK_USER_DATA.CURRENT_LOCATION_FB_ID, " +
        "             FACEBOOK_USER_DATA.DEVICES, " +
        "             FACEBOOK_USER_DATA.EMAIL, " +
        "             FACEBOOK_USER_DATA.FIRST_NAME, " +
        "             FACEBOOK_USER_DATA.FRIENDLISTS, " +
        "             FACEBOOK_USER_DATA.GENDER, " +
        "             FACEBOOK_USER_DATA.HOMETOWN, " +
        "             FACEBOOK_USER_DATA.HOMETOWN_LOCATION_FB_ID, " +
        "             FACEBOOK_USER_DATA.LAST_NAME, " +
        "             FACEBOOK_USER_DATA.LIKES, " +
        "             FACEBOOK_USER_DATA.LINK, " +
        "             FACEBOOK_USER_DATA.LOCALE, " +
        "             FACEBOOK_USER_DATA.LOCATION, " +
        "             FACEBOOK_USER_DATA.MIDDLE_NAME, " +
        "             FACEBOOK_USER_DATA.NAME, " +
        "             FACEBOOK_USER_DATA.PICTURE, " +
        "             FACEBOOK_USER_DATA.THIRD_PARTY_ID, " +
        "             FACEBOOK_USER_DATA.TIMEZONE, " +
        "             FACEBOOK_USER_DATA.UPDATE_TIME, " +
        "             FACEBOOK_USER_DATA.USER_FB_ID, " +
        "             FACEBOOK_USER_DATA.USERNAME, " +
        "             FACEBOOK_USER_DATA.VERIFIED) " + "VALUES      ( ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ? )";
    public static String FACEBOOK_USER_DATA_UPDATE_SQL = "" +
        "UPDATE FACEBOOK_USER_DATA " +
        "SET    FACEBOOK_USER_DATA.AGE_RANGE = ?, " +
        "       FACEBOOK_USER_DATA.BIRTHDAY = ?, " +
        "       FACEBOOK_USER_DATA.CURRENT_LOCATION_FB_ID = ?, " +
        "       FACEBOOK_USER_DATA.DEVICES = ?, " +
        "       FACEBOOK_USER_DATA.EMAIL = ?, " +
        "       FACEBOOK_USER_DATA.FIRST_NAME = ?, " +
        "       FACEBOOK_USER_DATA.FRIENDLISTS = ?, " +
        "       FACEBOOK_USER_DATA.GENDER = ?, " +
        "       FACEBOOK_USER_DATA.HOMETOWN = ?, " +
        "       FACEBOOK_USER_DATA.HOMETOWN_LOCATION_FB_ID = ?, " +
        "       FACEBOOK_USER_DATA.LAST_NAME = ?, " +
        "       FACEBOOK_USER_DATA.LIKES = ?, " +
        "       FACEBOOK_USER_DATA.LINK = ?, " +
        "       FACEBOOK_USER_DATA.LOCALE = ?, " +
        "       FACEBOOK_USER_DATA.LOCATION = ?, " +
        "       FACEBOOK_USER_DATA.MIDDLE_NAME = ?, " +
        "       FACEBOOK_USER_DATA.NAME = ?, " +
        "       FACEBOOK_USER_DATA.PICTURE = ?, " +
        "       FACEBOOK_USER_DATA.THIRD_PARTY_ID = ?, " +
        "       FACEBOOK_USER_DATA.TIMEZONE = ?, " +
        "       FACEBOOK_USER_DATA.UPDATE_TIME = ?, " +
        "       FACEBOOK_USER_DATA.USERNAME = ?, " +
        "       FACEBOOK_USER_DATA.VERIFIED = ? " +
        "WHERE  FACEBOOK_USER_DATA.USER_FB_ID = ? ";

    //    public static String USER_FRIEND_FB_INSERT_UPDATE_SQL = "" +
    //        "INSERT INTO USER_FRIEND_FB " +
    //        "            (USER_FRIEND_FB.BIRTHDAY, " +
    //        "             USER_FRIEND_FB.CURRENT_LOCATION_FB_ID, " +
    //        "             USER_FRIEND_FB.DEVICES, " +
    //        "             USER_FRIEND_FB.FB_UPDATE_DATETIME, " +
    //        "             USER_FRIEND_FB.FIRST_NAME, " +
    //        "             USER_FRIEND_FB.FRIENDLISTS, " +
    //        "             USER_FRIEND_FB.GENDER, " +
    //        "             USER_FRIEND_FB.HOMETOWN_LOCATION_FB_ID, " +
    //        "             USER_FRIEND_FB.LAST_NAME, " +
    //        "             USER_FRIEND_FB.LOCALE, " +
    //        "             USER_FRIEND_FB.MIDDLE_NAME, " +
    //        "             USER_FRIEND_FB.NAME, " +
    //        "             USER_FRIEND_FB.PICTURE, " +
    //        "             USER_FRIEND_FB.SUBSCRIBER_COUNT, " +
    //        "             USER_FRIEND_FB.THIRD_PARTY_ID, " +
    //        "             USER_FRIEND_FB.USER_FB_ID, " +
    //        "             USER_FRIEND_FB.USER_FRIEND_FB, " +
    //        "             USER_FRIEND_FB.USER_FRIEND_FB_STATUS, " +
    //        "             USER_FRIEND_FB.USER_ID, " +
    //        "             USER_FRIEND_FB.USERNAME, " +
    //        "             USER_FRIEND_FB.VERIFIED) " + "VALUES      ( ?, " +
    //        "              ?, " + "              ?, " + "              ?, " +
    //        "              ?, " + "              ?, " + "              ?, " +
    //        "              ?, " + "              ?, " + "              ?, " +
    //        "              ?, " + "              ?, " + "              ?, " +
    //        "              ?, " + "              ?, " + "              ?, " +
    //        "              ?, " + "              ?, " + "              ?, " +
    //        "              ?, " + "              ? )" + "ON DUPLICATE KEY UPDATE " +
    //        "USER_FRIEND_FB.BIRTHDAY = ?, " +
    //        " USER_FRIEND_FB.CURRENT_LOCATION_FB_ID = ?, " +
    //        "USER_FRIEND_FB.DEVICES = ?, " +
    //        "USER_FRIEND_FB.FB_UPDATE_DATETIME = ?, " +
    //        "USER_FRIEND_FB.FIRST_NAME = ?, " + "USER_FRIEND_FB.FRIENDLISTS = ?, " +
    //        "USER_FRIEND_FB.GENDER = ?, " +
    //        "USER_FRIEND_FB.HOMETOWN_LOCATION_FB_ID = ?, " +
    //        "USER_FRIEND_FB.LAST_NAME = ?, " + "USER_FRIEND_FB.LOCALE = ?, " +
    //        "USER_FRIEND_FB.MIDDLE_NAME = ?, " + "USER_FRIEND_FB.NAME = ?, " +
    //        "USER_FRIEND_FB.PICTURE = ?, " +
    //        "USER_FRIEND_FB.SUBSCRIBER_COUNT = ?, " +
    //        "USER_FRIEND_FB.THIRD_PARTY_ID = ?, " +
    //        "USER_FRIEND_FB.USER_FRIEND_FB_STATUS = ?, " +
    //        "USER_FRIEND_FB.USERNAME = ?, " + "USER_FRIEND_FB.VERIFIED = ? ";
    public static String USER_FRIEND_FB_FROM_USER_FRIEND_FB_SELECT_SQL = "" +
        "SELECT USER_FRIEND_FB.USER_FRIEND_FB " + "FROM   USER_FRIEND_FB " +
        "WHERE  USER_FRIEND_FB.USER_ID =? " + "";
    public static String USER_FRIEND_FB_STATUS_UPDATE_SQL = "" +
        "UPDATE USER_FRIEND_FB " + "SET    USER_FRIEND_FB_STATUS = ? " +
        "WHERE  USER_FRIEND_FB.USER_FRIEND_FB =? ";
    public static String TS_USER_ID_FROM_USERS_SELECT_SQL = "" +
        "SELECT USERS.USER_ID " + "FROM   USERS " +
        "WHERE  USERS.USER_FB_ID =? ";
    

    public static String USER_FACEBOOK_INSERT_SQL = "INSERT INTO users " +
        "(TS_USER_EMAIL, " + "USER_CREATED_INITIAL_DATETIME, " +
        "TS_FIRST_NAME, " + "TS_LAST_NAME, " + "USER_GENDER, " +
        "USER_CITY_ID, " + "USER_STATE, " + "USER_COUNTRY," + " USER_FB_ID," +
        "USER_ID," + "TS_USER_ID)" +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static String FACEBOOK_INSERT_SQL = "INSERT INTO facebook_user_data " +
        "(USER_FB_ID, " + "NAME, " + "FIRST_NAME, " + "MIDDLE_NAME, " +
        "LAST_NAME, " + "GENDER, " + "LOCALE, " + "LINK, " + "USERNAME, " +
        "AGE_RANGE, " + "BIRTHDAY, " + "THIRD_PARTY_ID, " + "FRIENDLISTS, " +
        "INSTALLED, " + "TIMEZONE, " + "UPDATE_TIME, " + "VERIFIED, " +
        "DEVICES, " + "EMAIL, " + "HOMETOWN, " + "LOCATION, " + "PICTURE, " +
        "RELATIONSHIP_STATUS, " + "CHECKINS, " + "FRIENDS, " + "LIKES, " +
        "PERMISSIONS, " + "CREATED)" +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static String FACEBOOK_UPDATE_SQL = "UPDATE facebook_user_data SET " +
        "NAME = ?, " + "FIRST_NAME = ?, " + "MIDDLE_NAME = ?, " +
        "LAST_NAME = ?, " + "GENDER = ?, " + "LOCALE = ?, " + "LINK = ?, " +
        "USERNAME = ?, " + "AGE_RANGE = ?, " + "BIRTHDAY = ?, " +
        "THIRD_PARTY_ID = ?, " + "FRIENDLISTS = ?, " + "INSTALLED = ?, " +
        "TIMEZONE = ?, " + "UPDATE_TIME = ?, " + "VERIFIED = ?, " +
        "DEVICES = ?, " + "EMAIL = ?, " + "HOMETOWN = ?, " + "LOCATION = ?, " +
        "PICTURE = ?, " + "RELATIONSHIP_STATUS = ?, " + "CHECKINS = ?, " +
        "FRIENDS = ?, " + "LIKES = ?, " + "PERMISSIONS = ?, " + "CREATED = ? " +
        "WHERE USER_FB_ID = ?";
    public static String FACEBOOK_FRIEND_DATA_INSERT_UPDATE_SQL = "" +
        "INSERT INTO FACEBOOK_FRIEND_DATA " +
        "            (FACEBOOK_FRIEND_DATA.BIRTHDAY, " +
        "             FACEBOOK_FRIEND_DATA.CURRENT_LOCATION_FB_ID, " +
        "             FACEBOOK_FRIEND_DATA.DEVICES, " +
        "             FACEBOOK_FRIEND_DATA.FB_UPDATE_DATETIME, " +
        "             FACEBOOK_FRIEND_DATA.FIRST_NAME, " +
        "             FACEBOOK_FRIEND_DATA.FRIENDLISTS, " +
        "             FACEBOOK_FRIEND_DATA.GENDER, " +
        "             FACEBOOK_FRIEND_DATA.HOMETOWN_LOCATION_FB_ID, " +
        "             FACEBOOK_FRIEND_DATA.LAST_NAME, " +
        "             FACEBOOK_FRIEND_DATA.LOCALE, " +
        "             FACEBOOK_FRIEND_DATA.MIDDLE_NAME, " +
        "             FACEBOOK_FRIEND_DATA.NAME, " +
        "             FACEBOOK_FRIEND_DATA.PICTURE, " +
        "             FACEBOOK_FRIEND_DATA.SUBSCRIBER_COUNT, " +
        "             FACEBOOK_FRIEND_DATA.THIRD_PARTY_ID, " +
        "             FACEBOOK_FRIEND_DATA.USER_FRIEND_FB, " +
        "             FACEBOOK_FRIEND_DATA.USERNAME, " +
        "             FACEBOOK_FRIEND_DATA.VERIFIED) " + "VALUES      ( ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ?, " + "              ? ) " +
        "ON DUPLICATE KEY UPDATE " +
        "            FACEBOOK_FRIEND_DATA.BIRTHDAY = ?, " +
        "             FACEBOOK_FRIEND_DATA.CURRENT_LOCATION_FB_ID= ?, " +
        "             FACEBOOK_FRIEND_DATA.DEVICES= ?, " +
        "             FACEBOOK_FRIEND_DATA.FB_UPDATE_DATETIME= ?, " +
        "             FACEBOOK_FRIEND_DATA.FIRST_NAME= ?, " +
        "             FACEBOOK_FRIEND_DATA.FRIENDLISTS= ?, " +
        "             FACEBOOK_FRIEND_DATA.GENDER= ?, " +
        "             FACEBOOK_FRIEND_DATA.HOMETOWN_LOCATION_FB_ID= ?, " +
        "             FACEBOOK_FRIEND_DATA.LAST_NAME= ?, " +
        "             FACEBOOK_FRIEND_DATA.LOCALE= ?, " +
        "             FACEBOOK_FRIEND_DATA.MIDDLE_NAME= ?, " +
        "             FACEBOOK_FRIEND_DATA.NAME= ?, " +
        "             FACEBOOK_FRIEND_DATA.PICTURE= ?, " +
        "             FACEBOOK_FRIEND_DATA.SUBSCRIBER_COUNT= ?, " +
        "             FACEBOOK_FRIEND_DATA.THIRD_PARTY_ID= ?, " +
        "             FACEBOOK_FRIEND_DATA.USERNAME= ?, " +
        "             FACEBOOK_FRIEND_DATA.VERIFIED = ?";
    public static String USER_FRIEND_FB_INSERT_UPDATE_SQL = "" +
        "INSERT INTO USER_FRIEND_FB " +
        "            (USER_FRIEND_FB.FB_UPDATE_DATETIME, " +
        "             USER_FRIEND_FB.USER_FB_ID, " +
        "             USER_FRIEND_FB.USER_FRIEND_FB, " +
        "             USER_FRIEND_FB.USER_FRIEND_FB_STATUS, " +
        "             USER_FRIEND_FB.USER_ID) " + "VALUES      ( ?, " +
        "              ?, " + "              ?, " + "              ?, " +
        "              ? ) " + "ON DUPLICATE KEY UPDATE " +
        "USER_FRIEND_FB.FB_UPDATE_DATETIME=?," +
        "USER_FRIEND_FB.USER_FRIEND_FB_STATUS = ?";
    
    public static String USERS_INSERT_SQL = ""
    		+ "INSERT INTO USERS "
    		+ "            (TS_USER_EMAIL, "
    		+ "             USER_CREATED_INITIAL_DATETIME, "
    		+ "             TS_FIRST_NAME, "
    		+ "             TS_LAST_NAME, "
    		+ "             USER_GENDER, "
    		+ "             USER_CITY_ID, "
    		+ "             USER_STATE, "
    		+ "             USER_COUNTRY, "
    		+ "             USER_FB_ID, "
    		+ "             USER_ID, "
    		+ "             TS_USER_ID) "
    		+ "VALUES      (?, "
    		+ "             ?, "
    		+ "             ?, "
    		+ "             ?, "
    		+ "             ?, "
    		+ "             ?, "
    		+ "             ?, "
    		+ "             ?, "
    		+ "             ?, "
    		+ "             ?, "
    		+ "             ?) ";

    public static String USERS_UPDATE_SQL = ""
    		+ "UPDATE USERS "
    		+ "SET    TS_USER_EMAIL = ?, "
    		+ "       TS_FIRST_NAME = ?, "
    		+ "       TS_LAST_NAME = ?, "
    		+ "       USER_GENDER = ?, "
    		+ "       USER_CITY_ID = ?, "
    		+ "       USER_STATE = ?, "
    		+ "       USER_COUNTRY = ? "
    		+ "WHERE  USERS.USER_ID =? ";
    
    public static String USER_FBID_SELECT_SQL = "SELECT users.USER_ID, users.TS_USER_ID, users.TS_USER_EMAIL, users.TS_FIRST_NAME,users.TS_LAST_NAME,users.MAX_INVITES," +
            "users.USER_CREATED_INITIAL_DATETIME, users.USER_POINTS, users.TWITTER_USR_URL, users.USER_DISABLED_FLAG, users.USER_ACTIVATION_KEY, users.USER_GENDER, " +
            "users.USER_CITY_ID, users.USER_STATE, users.IS_ONLINE, users.USER_COUNTRY, users.ABOUT, users.CURRENT_STATUS, users.USER_FB_ID" +
            " FROM users WHERE User_FB_ID = ? AND (CURRENT_STATUS = ? OR CURRENT_STATUS = ?)";
    
    public static String CITY_STATE_SELECT_SQL = "" + "SELECT CITIES.CITY_ID, " +
            "CITIES.COUNTRY, "+
            "CITIES.STATE, "+
            "CITIES.CITY "+
            "FROM   CITIES, " + "       STATE_NAME " +
            "WHERE  CITIES.STATE = STATE_NAME.STATE_ID " +
            "       AND STATE_NAME.STATE_NAME = ? " +
            "       AND REPLACE(REPLACE(Lower(CITIES.CITY), ' ', ''), '.', '') = REPLACE( " +
            "           REPLACE(Lower(?), ' ', ''), '.', '')";
    
    public static String USER_FB_ACCESS_INSERT_SQL = "" +
            "INSERT INTO USER_FB_ACCESS " +
            "            (USER_FB_ACCESS.FB_ACCESS_TOKEN, " +
            "             USER_FB_ACCESS.FB_ACCESS_TOKEN_CREATED, " +
            "             USER_FB_ACCESS.FB_ACCESS_TOKEN_ID, " +
            "             USER_FB_ACCESS.FB_INFO_STATUS, " +
            "             USER_FB_ACCESS.FB_INFO_UPDATED_DATETIME, " +
            "             USER_FB_ACCESS.USER_FB_ID, " +
            "             USER_FB_ACCESS.USER_ID, " +
            "             USER_FB_ACCESS.VENDOR_IDENTIFIER) " +
            "VALUES      ( ?, " + "              ?, " + "              ?, " +
            "              ?, " + "              ?, " + "              ?, " +
            "              ?, " + "              ? ) " +
            "ON DUPLICATE KEY UPDATE " +
            "USER_FB_ACCESS.FB_INFO_UPDATED_DATETIME = ?, " +
            "USER_FB_ACCESS.VENDOR_IDENTIFIER = ?";
    public static String USER_ONLINE_UPDATE_SQL = "UPDATE users " +
            "SET IS_ONLINE = ? , IS_ONLINE_UPDATED_DATETIME = ? " +
            "WHERE USER_ID = ?";
    
    public static String USER_FRIEND_TASTESYNC_TRUST_INSERT_UPDATE_SQL = "" +
            "INSERT INTO USER_FRIEND_TASTESYNC " +
            "            (USER_FRIEND_TASTESYNC.FRIEND_ID, " +
            "             USER_FRIEND_TASTESYNC.USER_ID) " + "VALUES      ( ?, " +
            "              ? ) " + "ON DUPLICATE KEY UPDATE " +
            "USER_FRIEND_TASTESYNC.FRIEND_ID = USER_FRIEND_TASTESYNC.FRIEND_ID, " +
            "USER_FRIEND_TASTESYNC.USER_ID = USER_FRIEND_TASTESYNC.USER_ID";
}
