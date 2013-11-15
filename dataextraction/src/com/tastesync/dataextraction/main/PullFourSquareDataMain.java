package com.tastesync.dataextraction.main;

import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.external.foursquare.api.FoursquareApi;
import com.tastesync.dataextraction.external.foursquare.api.io.DefaultIOHandler;
import com.tastesync.dataextraction.process.RestaurantFactual4SqData;
import com.tastesync.dataextraction.util.TSConstants;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.Date;


public class PullFourSquareDataMain {
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

        // check that input file is available.
        // if not, then use WriteFactualIdDataInfoMain to create the file 
        // and copy in the config directorz
        String inputFactualIdDataFile = "./config/SortedData_FactualId.txt";
        String outputFilePath = "./outputDir/" + strNowDate +
            "Factual_4SQ_outputFile.txt";

        String outputInvalidFactualDataFile = "./outputDir/" + strNowDate +
            "_INVALID_Factual_4SQ_outputFile.txt";
        RestaurantFactual4SqData restaurantFactual4SqData = new RestaurantFactual4SqData();
        TSDataSource tsDataSource = TSDataSource.getInstance();
        Connection connection = null;

        try {
            connection = tsDataSource.getConnection();
            restaurantFactual4SqData.processRestaurantFactual4SqData(tsDataSource,
                connection, TSConstants.DATAEXTRACTION_SOURCETYPE.FACTUAL,
                TSConstants.CLIENT_ID, TSConstants.CLIENT_SECRET,
                TSConstants.REDIRECURI, defaultIOHandler, foursquareApi,
                inputFactualIdDataFile, outputFilePath,
                outputInvalidFactualDataFile);
        } catch (TasteSyncException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("************ End ************");
    }
}
