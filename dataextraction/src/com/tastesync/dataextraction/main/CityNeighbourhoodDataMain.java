package com.tastesync.dataextraction.main;

import com.tastesync.dataextraction.process.CityNeighbourhoodData;


public class CityNeighbourhoodDataMain {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //source of data
        //http://www.census.gov/geo/www/tiger/latlng.txt
        //
        String datafilePath = "./config/latlonData.txt";
        datafilePath = "./config/Factual_Cities_Median_LatLng.csv";
        String outputFilePath = "./outputDir/outputFile.sql";
        outputFilePath = "./outputDir/outputFile.txt";

        double distanceLimit = 100; //in km //TODO
        boolean considerDistanceLimit = true;

        CityNeighbourhoodData cityNeighbourhoodData = new CityNeighbourhoodData();
        cityNeighbourhoodData.processCityNeighbourhoodData(datafilePath,
            outputFilePath, distanceLimit, considerDistanceLimit);
    }
}
