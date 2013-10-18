package com.tastesync.algo.util;

public interface TSConstants {
    public static final String TSDB_JNDI = "jdbc/TastesyncDB";
    public static final String EMPTY_STRING = "";
    public static final String[] EMPTY_STRING_ARRAY = new String[] {  };
    public static final int MAX_RECOREQUEST_CONSIDERED = 10;
    public static final int PAGINATION_GAP=10;
    //public static final int SLEEP_TIME_BETWEEN_TWO_REQUEST_ITERATION_IN_MILLISECONDS=600000;
    //public static final int DEMAND_TIER3_START_SLEEP_TIME=300000;
    public static final int SLEEP_TIME_BETWEEN_TWO_REQUEST_ITERATION_IN_MILLISECONDS=60000;
    public static final int DEMAND_TIER3_START_SLEEP_TIME=30000;
    public static final String SEND_PUSH_NOTIFICATIONS_SCRIPT = "./scripts/pushnotification/SendPushNotifications.sh";

}
