package com.tastesync.common.utils;

import com.tastesync.common.exception.TasteSyncException;

import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CommonFunctionsUtil {
    private static final Logger logger = Logger.getLogger(CommonFunctionsUtil.class);
    public static final String[] EMPTY_STRING_ARRAY = new String[] {  };
    private static final DateTimeFormatter tsDateTimeFormatWithMilliseconds = DateTimeFormat.forPattern(
            "yyyyMMddHHmmssSSS");
    private static final String EST_DST_TIME_ZONE = "US/Eastern";

    public static String getModifiedValueString(String inputString) {
        return ((inputString == null) ? "" : inputString.trim());

        //return inputString;
    }

    public static int getModifiedValueInteger(String inputString) {
        if (inputString == null) {
            return 0;
        }

        if ((getModifiedValueString(inputString) == null) ||
                getModifiedValueString(inputString).isEmpty()) {
            return 0;
        }

        return (Integer.valueOf(inputString));

        //return inputString;
    }

    public static String converStringAsNullIfNeeded(String inputString) {
        if ((inputString == null) || inputString.isEmpty()) {
            return null;
        }

        return inputString.trim();
    }

    public static String getCurrentDatetimeAppendField() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        return formatter.format(currentDate.getTime());
    }

    public static String generateRandomString(int loai, int dodai) {
        ////////Code ho tro lay chu ngau nhien, phat trien boi Congdongjava - Tran Huy
        //Loai : kieu ran dom
        //0 : ngau nhien chi cac chu cai thuong
        //1 : ngau nhien chi cac chu cai hoa
        //2 : ngau nhien ca chu hoa va thuong
        //3 : ngau nhien chu va so
        String ketqua = "";
        String hoa = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String thuong = hoa.toLowerCase();
        String so = "123456789012345678901234567890123456789012345678901234567890";
        String randomchuoi = "";

        if ((loai > 4) || (loai < 0)) {
            ketqua = "Loai khong hop le, cho phep tu 0 - 4";
        } else if (loai == 0) {
            randomchuoi = thuong;
        } else if (loai == 1) {
            randomchuoi = hoa;
        } else if (loai == 2) {
            randomchuoi = hoa + thuong;
        } else if (loai == 3) {
            randomchuoi = hoa + thuong + so;
        } else if (loai == 4) {
            randomchuoi = so;
        }

        for (int i = 0; i < dodai; i++) {
            int temp = (int) Math.round(Math.random() * randomchuoi.length());
            ketqua += randomchuoi.charAt(temp);
        }

        return ketqua;
    }

    public static String[] convertStringListAsArrayList(String inputString) {
        if (inputString == null) {
            return EMPTY_STRING_ARRAY;
        }

        if (inputString.isEmpty()) {
            return EMPTY_STRING_ARRAY;
        }

        inputString = inputString.replaceAll("\\s", "");

        return (inputString != null) ? inputString.trim().split(",")
                                     : EMPTY_STRING_ARRAY;
    }

    public static String getCurrentDatetime() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(currentDate.getTime());
    }

    private static String getCurrentDateTime() {
        //TODO YYYYMMDDHHMMSS.SSS  (TIME ZONE set to EST/EDT!)
        return tsDateTimeFormatWithMilliseconds.print(new DateTime());
    }

    public static String generateUniqueKey() {
        return getCurrentDateTime() + System.nanoTime();
    }

    public static String generateUniqueKey(List<String> inputMiddleKeys) {
        StringBuffer key = new StringBuffer();

        for (String middleKey : inputMiddleKeys) {
            key.append(middleKey).append("-");
        }

        return getCurrentDateTime() + key.toString() + System.nanoTime();
    }

    public static Timestamp getCurrentDateTimestamp() {
        //TODO YYYYMMDDHHMMSS.SSS  (TIME ZONE set to EST/EDT!)
        return new Timestamp(new java.util.Date().getTime());
    }

    public static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();

        return new java.sql.Date(today.getTime());
    }

    public static java.sql.Date getDateNDaysBeforeCurrentDate(int nDays) {
        java.util.Date today = new java.util.Date();
        today.setTime(today.getTime() + (nDays * 1000 * 60 * 60 * 24));

        return new java.sql.Date(today.getTime());
    }

    public static String exec(String inputStrCmd) throws TasteSyncException {
        String scriptRootDir = System.getProperty("SCRIPT_ROOT");
        String envType = System.getProperty("ENV_TYPE");

        if (logger.isDebugEnabled()) {
            logger.debug("scriptRootDir=" + scriptRootDir);
            logger.debug("envType=" + envType);
        } // end if

        if ((scriptRootDir == null) || scriptRootDir.isEmpty()) {
            throw new TasteSyncException("scriptRootDir=" + scriptRootDir +
                " is not properly defined.");
        }

        if ((envType == null) || envType.isEmpty()) {
            throw new TasteSyncException("envType=" + scriptRootDir +
                " is not properly defined.");
        }
        scriptRootDir = scriptRootDir +"/"+envType+ "/";
        
        String strCmd = scriptRootDir +inputStrCmd + " " + scriptRootDir;

        if (logger.isDebugEnabled()) {
            logger.debug("exec - start: strCmd=" + strCmd);
        } // end if

        StringBuffer lResult = new StringBuffer();
        StringBuffer lResultDebug = new StringBuffer();

        try {
            // Execute Script
            strCmd = strCmd.replaceAll("\\n", "");

            String[] strScript = { "sh", "-c", strCmd };
            Process lProcess = Runtime.getRuntime().exec(strScript);
            BufferedReader lIn = new BufferedReader(new InputStreamReader(
                        lProcess.getInputStream()));
            BufferedReader lErr = new BufferedReader(new InputStreamReader(
                        lProcess.getErrorStream()));
            String lineDebug = lIn.readLine();
            lResultDebug.append(lineDebug).append("\n"); // Command output

            if (logger.isDebugEnabled()) {
                logger.debug(strCmd + " command debug:" + lineDebug);
            } // end if

            // Read Output
            while ((lineDebug = lIn.readLine()) != null) {
                lResultDebug.append(lineDebug).append("\n"); // Command output
                logger.debug(strCmd + " command debug:" + lineDebug);

                if (lineDebug.contains("ERROR:")) {
                    lResult.append(lineDebug);
                } // end if
            } // end while

            lIn.close();

            // Read Error
            boolean lError = false;
            String line;

            //TODO lError for true case???
            while ((line = lErr.readLine()) != null) {
                if (!lError) {
                    lResult.append("ERROR:");
                } // end if

                lResult.append(line).append("\n");

                if (!lError) {
                    logger.info(strCmd + " command info:" + line);
                } // end if
                else {
                    logger.error(strCmd + " command err:" + line);
                } // end else
            } // end while

            lErr.close();

            // remove command file
            lProcess.waitFor();
            
            

            if (lResult.toString().contains("ERROR:")) {
            	if (!lResult.toString().contains("ERROR:Warning:")) {
                    throw new TasteSyncException(lResult.toString());
            	} else {
            		System.out.println("Warning message: "+lResult.toString());
            	}
            }
        } // end try
        catch (Exception lEx) {
            logger.error("exec(String)", lEx);
            lEx.printStackTrace();
        } // end catch

        String returnString = lResult.toString();

        if (logger.isDebugEnabled()) {
            logger.debug("exec - end strCmd: " + strCmd + " returnString=" +
                returnString);
        } // end if

        return returnString;
    } // end exec()

    public static void execAsync(String inputStrCmd, String baseScriptName) throws TasteSyncException {
        String scriptRootDir = System.getProperty("SCRIPT_ROOT");
        String envType = System.getProperty("ENV_TYPE");
        
        if (logger.isDebugEnabled()) {
            logger.debug("scriptRootDir=" + scriptRootDir);
            logger.debug("envType=" + envType);
        } // end if

        if ((scriptRootDir == null) || scriptRootDir.isEmpty()) {
            throw new TasteSyncException("scriptRootDir=" + scriptRootDir +
                " is not properly defined.");
        }

        if ((envType == null) || envType.isEmpty()) {
            throw new TasteSyncException("envType=" + scriptRootDir +
                " is not properly defined.");
        }
        
        scriptRootDir = scriptRootDir +"/"+envType;
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date now = new Date();
        String strNowDate = sdfDate.format(now);
//        String scriptName = StringUtils.substringAfterLast(inputStrCmd, "/");
//        String newinputStrCmd = StringUtils.replace(inputStrCmd,
//                "/" + scriptName, "");
//        String dirNameWithScript = StringUtils.substringAfterLast(newinputStrCmd,
//                "/");

        String outputLogFilePath = " >& " + scriptRootDir + "/" +
            "/logs/" + strNowDate +"_"+System.nanoTime()+"_"+
            String.valueOf((int) (Math.random() * 100000)) + "_" + baseScriptName +
            ".log";

        String strCmd = scriptRootDir + "/"+inputStrCmd + " " + scriptRootDir +
            outputLogFilePath;

        if (logger.isDebugEnabled()) {
            logger.debug("exec - start: strCmd=" + strCmd);
        } // end if

        try {
            // Execute Script
            strCmd = strCmd.replaceAll("\\n", "");
            logger.info("Within execAsync, finally, call script with strCmd=" +
                strCmd);

            String[] strScript = { "sh", "-c", strCmd };
            Runtime.getRuntime().exec(strScript);
        } // end try
        catch (Exception lEx) {
            logger.error("execAsync(String)", lEx);
            lEx.printStackTrace();
        } // end catch

        if (logger.isDebugEnabled()) {
            logger.debug("execAsync - end strCmd: " + strCmd);
        } // end if
    } // end execAsync()

    public static Date convertJodaTimezone(LocalDateTime date,
        String localTimeZone, String destTimeZone) {
        DateTime srcDateTime = date.toDateTime(DateTimeZone.forID(localTimeZone));
        DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.forID(
                    destTimeZone));

        return dstDateTime.toLocalDateTime().toDateTime().toDate();
    }

    public static Date convertJodaTimezone(Date inputDate,
        String localTimeZone, String destTimeZone) {
        LocalDateTime date = LocalDateTime.fromDateFields(inputDate);
        DateTime srcDateTime = date.toDateTime(DateTimeZone.forID(localTimeZone));
        DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.forID(
                    destTimeZone));

        return dstDateTime.toLocalDateTime().toDateTime().toDate();
    }

    public static Date convertJodaTimezoneFromCurrentTimezoneToEST(Date inputDate) {
        LocalDateTime date = LocalDateTime.fromDateFields(inputDate);
        //DateTime srcDateTime = date.toDateTime(DateTimeZone.forID(
          //          GMT_LOCAL_TIME_ZONE));
        Calendar calendar = Calendar.getInstance();
        //System.out.println(calendar.getTimeZone().getID());
        DateTime srcDateTime = date.toDateTime(DateTimeZone.forID(
        		calendar.getTimeZone().getID()));
        
        DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.forID(
                    EST_DST_TIME_ZONE));

        return dstDateTime.toLocalDateTime().toDateTime().toDate();
    }
}
