package com.tastesync.algo.util;

public interface TSConstants {
    public static final String TSDB_JNDI = "jdbc/TastesyncDB";
    public static final String EMPTY_STRING = "";
    public static final String[] EMPTY_STRING_ARRAY = new String[] {  };
    public static final int MAX_RECOREQUEST_CONSIDERED = 10;
    public static final int PAGINATION_GAP=10;
    public static final int SLEEP_TIME_BETWEEN_TWO_REQUEST_ITERATION_IN_MILLISECONDS=60000;
}
