package com.tastesync.dataextraction.process;

import com.tastesync.dataextraction.model.CityLatLonDataVO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Arrays;


public class CityNeighbourhoodData {
    private static boolean printDebugExtra = false;
    private ArrayList<CityLatLonDataVO> cityLatLonDataList = new ArrayList<CityLatLonDataVO>();
    private DecimalFormat df = new DecimalFormat("#0.00");

    public CityNeighbourhoodData() {
        super();
    }

    public void processCityNeighbourhoodData(String datafilePath,
        String outputFilePath, double distanceLimit,
        boolean considerDistanceLimit) {
        cityLatLonDataList = getCityLatLonDataList(datafilePath);

        for (CityLatLonDataVO cityLatLonDataListElement : cityLatLonDataList) {
            System.out.println(cityLatLonDataListElement.toString());
        }

        //for each city , calculate distance with all other cities.
        // check if the distance is less than certain distance.
        //if yes, write to output file in some format
        CityLatLonDataVO inputCityLatLonData = null;

        double distanceCalculatedFromInputCity = 0;

        StringBuffer outputStringBuffer = null;
        BufferedWriter outputFileWriter = null;

        try {
            outputFileWriter = new BufferedWriter(new FileWriter(outputFilePath));
            outputFileWriter.write("DELETE FROM CLOSEST_CITIES;");
            outputFileWriter.newLine();

            for (int i = 0; i < cityLatLonDataList.size(); ++i) {
                inputCityLatLonData = cityLatLonDataList.get(i);

                for (int j = 0; j < cityLatLonDataList.size(); ++j) {
                    if (inputCityLatLonData.getIdentifierID() != (cityLatLonDataList.get(
                                j).getIdentifierID())) {
                        distanceCalculatedFromInputCity = distFromUsingHaversineDistance(inputCityLatLonData.getiNTPTLAT()
                                                                                                            .doubleValue(),
                                inputCityLatLonData.getiNTPTLNG().doubleValue(),
                                cityLatLonDataList.get(j).getiNTPTLAT()
                                                  .doubleValue(),
                                cityLatLonDataList.get(j).getiNTPTLNG()
                                                  .doubleValue());

                        //TODO
                        //insert into closest_cities (source_city_id, source_city, destination_city_id, destination_city, distance)
                        //values()
                        outputStringBuffer = null;

                        if ((distanceCalculatedFromInputCity <= distanceLimit) &&
                                considerDistanceLimit) {
                            outputStringBuffer = new StringBuffer();

                            outputStringBuffer.append(
                                "INSERT INTO CLOSEST_CITIES (SOURCE_CITY_ID, SOURCE_CITY, SOURCE_STATE, DESTINATION_CITY_ID, DESTINATION_CITY, DESTINATION_STATE, DISTANCE) VALUES (");
                            outputStringBuffer.append("\"")
                                              .append(inputCityLatLonData.getIdentifierID())
                                              .append("\",");
                            outputStringBuffer.append("\"")
                                              .append(inputCityLatLonData.getPlace())
                                              .append("\",");
                            outputStringBuffer.append("\"")
                                              .append(inputCityLatLonData.getState())
                                              .append("\",");

                            outputStringBuffer.append("\"")
                                              .append(cityLatLonDataList.get(j)
                                                                        .getIdentifierID())
                                              .append("\",");
                            outputStringBuffer.append("\"")
                                              .append(cityLatLonDataList.get(j)
                                                                        .getPlace())
                                              .append("\",");
                            outputStringBuffer.append("\"")
                                              .append(cityLatLonDataList.get(j)
                                                                        .getState())
                                              .append("\",");

                            outputStringBuffer.append("\"")
                                              .append(df.format(
                                    distanceCalculatedFromInputCity))
                                              .append("\"");
                            ;
                            outputStringBuffer.append(" );");

                            outputFileWriter.write(outputStringBuffer.toString());
                            outputFileWriter.newLine();

                            if (printDebugExtra) {
                                System.out.println(outputStringBuffer.toString());
                            }
                        } else if (!considerDistanceLimit) {
                            outputStringBuffer = new StringBuffer();
                            System.out.println("NOT Completed!");
                            outputFileWriter.write(outputStringBuffer.toString());
                            outputFileWriter.newLine();
                        } else {
                            if (printDebugExtra) {
                                System.out.println(
                                    "Nothing is to be written!! distanceCalculatedFromInputCity=" +
                                    distanceCalculatedFromInputCity);
                            }
                        }
                    }
                }
            }

            outputFileWriter.write("COMMIT");
            outputFileWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputFileWriter != null) {
                try {
                    outputFileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList<CityLatLonDataVO> getCityLatLonDataList(
        String datafilePath) {
        // Open the file
        FileInputStream fstream;
        DataInputStream in = null;

        try {
            fstream = new FileInputStream(datafilePath);
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            String[] latlonDataForEachline = new String[7];
            int strLength = 0;
            CityLatLonDataVO cityLatLonDataVo = null;
            boolean skipLine = true;
            int i = 0;

            String[] strLineDataFromCSV = null;

            //Read File Line By Line
            try {
                while ((strLine = br.readLine()) != null) {
                    if (!skipLine) {
                        System.out.println(strLine);
                        strLength = strLine.length();

                        //CSV file
                        if (datafilePath.endsWith(".csv")) {
                            strLineDataFromCSV = strLine.split(",", -1);
                            System.out.println(Arrays.toString(
                                    strLineDataFromCSV));

                            if (!strLineDataFromCSV[2].isEmpty() &&
                                    !strLineDataFromCSV[3].isEmpty()) {
                                ++i;
                                cityLatLonDataVo = new CityLatLonDataVO(i,
                                        strLineDataFromCSV[0],
                                        strLineDataFromCSV[1],
                                        new BigDecimal(strLineDataFromCSV[2]),
                                        new BigDecimal(strLineDataFromCSV[3]));
                            }
                        } else {
                            if (strLength != 0) {
                                latlonDataForEachline[0] = strLine.substring(0,
                                        3).trim();
                                latlonDataForEachline[1] = strLine.substring(4,
                                        9).trim();
                                latlonDataForEachline[2] = strLine.substring(10,
                                        36).trim();
                                latlonDataForEachline[3] = strLine.substring(36,
                                        38).trim();
                                latlonDataForEachline[4] = strLine.substring(39,
                                        49).trim();
                                latlonDataForEachline[5] = strLine.substring(49,
                                        strLine.length()).trim();
                                ++i;
                            }

                            cityLatLonDataVo = new CityLatLonDataVO(i,
                                    latlonDataForEachline[0],
                                    latlonDataForEachline[1],
                                    latlonDataForEachline[2],
                                    latlonDataForEachline[3],
                                    new BigDecimal(latlonDataForEachline[4]),
                                    new BigDecimal(latlonDataForEachline[5]));
                        }

                        cityLatLonDataList.add(cityLatLonDataVo);
                    }

                    skipLine = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Close the input stream
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return cityLatLonDataList;
    }

    /**
     * This is the implementation Haversine Distance Algorithm between two places
     *  R = earth’s radius (mean radius = 6,371km)
     *  Δlat = lat2− lat1
     * Δlong = long2− long1
     *a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
     *c = 2.atan2(√a, √(1−a))
     *d = R.c
     *
     */
    public double distFromUsingHaversineDistance(double lat1, double lng1,
        double lat2, double lng2) {
        double earthRadius = 6371; // Radious of the earth
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) +
            (Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(
                    lat2)));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist;
    }
}
