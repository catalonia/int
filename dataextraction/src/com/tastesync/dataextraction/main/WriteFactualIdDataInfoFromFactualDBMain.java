package com.tastesync.dataextraction.main;

import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.model.FactualDataVO;
import com.tastesync.dataextraction.process.WriteFactualIdDataInfo;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


public class WriteFactualIdDataInfoFromFactualDBMain {
    /**
     * @param args
     */
    public static void main(String[] args) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String strNowDate = sdfDate.format(now);

        WriteFactualIdDataInfo writeFactualIdDataInfo = new WriteFactualIdDataInfo();
        String outputFilePath = "./outputDir/" + strNowDate +
            "_outputFile_sortedData_FactualId.txt";

        List<FactualDataVO> factualIdList;
        System.out.println("************ Start ************");

        try {
            Date startTime = new Date();

            factualIdList = writeFactualIdDataInfo.getSortedFactualIdListFromFactualDB();
            writeFactualIdDataInfo.writeFactualDataToFile(outputFilePath,
                factualIdList);

            Date stopTime = new Date();
            long timeTakenInMilliSeconds = stopTime.getTime() -
                    startTime.getTime();
            String timeTakenInSeconds = (timeTakenInMilliSeconds / 1000) + "." +
                (timeTakenInMilliSeconds % 1000);
            System.out.println("Time taken=" + timeTakenInMilliSeconds +
                " in milliSeconds and " + timeTakenInSeconds + " in seconds.");
        } catch (TasteSyncException e) {
            e.printStackTrace();
        }

        System.out.println("************ End ************");
    }
}
