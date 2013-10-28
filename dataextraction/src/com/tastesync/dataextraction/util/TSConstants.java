package com.tastesync.dataextraction.util;

public interface TSConstants {
    // milli seconds in 1 hour
    public static int HOUR_LIMIT_IN_MILLISECONDS = 3600000;

    //TODO check allowed requests per hour
    //public static int MAX_REQUESTS_PER_HOUR = 5000;
    //public static int APPROX_API_REQUESTS_NUMBERS = 3;
    public static String CLIENT_ID = "2FEJCWIJGKKGPXPJDX3KC2FAMTSSNZBHTLPQTQ112LBPXGLR";
    public static String CLIENT_SECRET = "F0EVIAS2HGOZLS4IJRRDQ2SRI1O34ZHGE1UTDCLMKZIKAMQ3";
    public static String REDIRECURI = "http://www.tastesync.com";
    public static int MAX_ATTEMPTS = 50;
    public static String FOURSQ_SOURCE = "4SQ";
    public static String PHOTO_GROUPTYPE_VENUE = "venue";
    public static String PHOTO_ORIGINAL_DELIMITOR = "original";
    public static String DELIMITOR = "#~";
    public static String INTENT_MATCH = "match";
    public static String INTENT_GLOBAL = "global";
    public static final String SHELL_BIN = "/bin/sh";
    public static final String[] EMPTY_STRING_ARRAY = new String[] {  };
    public static final String DAILY_PULL_4SQ_SCRIPT = "./dataextraction/DailyPullFourSquareExecSQL.sh";
    public enum DATAEXTRACTION_SOURCETYPE {FACTUAL,
        FOURSQUARE
    }
}
