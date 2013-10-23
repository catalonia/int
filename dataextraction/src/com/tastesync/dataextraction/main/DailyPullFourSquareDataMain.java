package com.tastesync.dataextraction.main;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.external.foursquare.api.FoursquareApi;
import com.tastesync.dataextraction.external.foursquare.api.io.DefaultIOHandler;
import com.tastesync.dataextraction.model.FactualDataVO;
import com.tastesync.dataextraction.process.DailyRestaurantFactual4SqData;
import com.tastesync.dataextraction.process.RestaurantFactual4SqData;
import com.tastesync.dataextraction.process.WriteFactualIdDataInfo;
import com.tastesync.dataextraction.util.TSConstants;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


public class DailyPullFourSquareDataMain {
    /**
     * @param args
     */
    public static void main(String[] args) {
        DefaultIOHandler defaultIOHandler = new DefaultIOHandler();
        FoursquareApi foursquareApi = new FoursquareApi(TSConstants.CLIENT_ID,
                TSConstants.CLIENT_SECRET, TSConstants.REDIRECURI,
                defaultIOHandler);

        //create input files Daily_SortedData_FactualId
        System.out.println("************ Start ************");

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date now = new Date();
        String strNowDate = sdfDate.format(now);

        WriteFactualIdDataInfo writeFactualIdDataInfo = new WriteFactualIdDataInfo();
        String outputFilePath = "./outputDir/" + strNowDate +
            "_daily_outputFile_sortedData_FactualId.txt";

        List<FactualDataVO> factualIdList;
        DailyRestaurantFactual4SqData dailyRestaurantFactual4SqData = new DailyRestaurantFactual4SqData();

        try {
            int pullEligInd = 2;
            int beforeNDays = 28;
            int lastMatchInd = 1;
            dailyRestaurantFactual4SqData.identifyRestaurantsFourSqExtractUsingUpdate(pullEligInd,
                beforeNDays, lastMatchInd);

            pullEligInd = 3;

            int lastUpdatedNDays = 28;

            lastMatchInd = 1;

            int accessNDays = 30;
            dailyRestaurantFactual4SqData.identifyUserAccessRestaurantAttempted(pullEligInd,
                lastUpdatedNDays, lastMatchInd, accessNDays);

            pullEligInd = 1;
            beforeNDays = 45;
            lastMatchInd = 0;

            dailyRestaurantFactual4SqData.identifyRestaurantsFourSqExtractUsingUpdate(pullEligInd,
                beforeNDays, lastMatchInd);

            pullEligInd = 1;
            lastUpdatedNDays = 28;

            lastMatchInd = 1;
            accessNDays = 30;
            dailyRestaurantFactual4SqData.identifyUserAccessRestaurantAttempted(pullEligInd,
                lastUpdatedNDays, lastMatchInd, accessNDays);

            factualIdList = dailyRestaurantFactual4SqData.getFactualDataVOListsForExtraction();
            writeFactualIdDataInfo.writeFactualDataToFile(outputFilePath,
                factualIdList);

            // check that input file is available.
            // if not, then use WriteFactualIdDataInfoMain to create the file 
            // and copy in the config directorz

            //String inputFactualIdDataFile = "./config/Daily_SortedData_FactualId.txt";
            String inputFactualIdDataFile = outputFilePath;
            String outputSqlFilePath = "./outputDir/" + strNowDate +
                "_daily_Factual_4SQ_outputFile.sql";
            String outputInvalidFactualDataFile = "./outputDir/" + strNowDate +
                "_daily_INVALID_Factual_4SQ_outputFile.txt";
            RestaurantFactual4SqData restaurantFactual4SqData = new RestaurantFactual4SqData();

            restaurantFactual4SqData.processRestaurantFactual4SqData(TSConstants.DATAEXTRACTION_SOURCETYPE.FOURSQUARE,
                TSConstants.CLIENT_ID, TSConstants.CLIENT_SECRET,
                TSConstants.REDIRECURI, defaultIOHandler, foursquareApi,
                inputFactualIdDataFile, outputSqlFilePath,
                outputInvalidFactualDataFile);

            // process the output file via script or run in SQL!!!
            String oExecResult = null;

            try {
                oExecResult = CommonFunctionsUtil.exec(TSConstants.DAILY_PULL_4SQ_SCRIPT + " " +
                        outputSqlFilePath);
            } catch (com.tastesync.common.exception.TasteSyncException e) {
                e.printStackTrace();
            }
            if (oExecResult.contains("ERROR:")) {
            	//TODO rename the output sql file
            	
            }
        } catch (TasteSyncException e) {
            e.printStackTrace();
        }

        System.out.println("************ End ************");
    }
}
