package com.tastesync.dataextraction.process;

import com.tastesync.dataextraction.db.dao.FoursquareDAO;
import com.tastesync.dataextraction.db.dao.FoursquareDAOImpl;
import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.model.FactualDataVO;
import com.tastesync.dataextraction.util.TSConstants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.text.DecimalFormat;

import java.util.List;


public class WriteFactualIdDataInfo {
    private boolean printExtraDebug = false;
    private DecimalFormat df = new DecimalFormat("#0.00000");
    FoursquareDAO foursquareDAO = new FoursquareDAOImpl();

    public WriteFactualIdDataInfo() {
    }

    public List<FactualDataVO> getSortedFactualIdListFromFactualDB()
        throws TasteSyncException {
        return foursquareDAO.getSortedFactualIdListFromFactualDB();
    }

    public void writeFactualDataToFile(String outputFilePath,
        List<FactualDataVO> factualIdList) {
        BufferedWriter outputFileWriter = null;
        System.out.println(
            "************ Start of writeFactualDataToFile ************");

        try {
            outputFileWriter = new BufferedWriter(new FileWriter(outputFilePath));

            StringBuffer outputStringBuffer = new StringBuffer();

            for (FactualDataVO factualIdListElement : factualIdList) {
                outputStringBuffer = new StringBuffer();
                outputStringBuffer = outputStringBuffer.append(factualIdListElement.getFactualId())
                                                       .append(TSConstants.DELIMITOR);
                outputStringBuffer = outputStringBuffer.append(factualIdListElement.getRestuarantName())
                                                       .append(TSConstants.DELIMITOR);
                outputStringBuffer = outputStringBuffer.append(factualIdListElement.getLatitude())
                                                       .append(TSConstants.DELIMITOR);
                outputStringBuffer = outputStringBuffer.append(factualIdListElement.getLongtitude())
                                                       .append(TSConstants.DELIMITOR);
                outputStringBuffer = outputStringBuffer.append(factualIdListElement.getPhoneNumber())
                                                       .append(TSConstants.DELIMITOR);

                if (printExtraDebug) {
                    System.out.println("writting outputString=" +
                        outputStringBuffer.toString() +
                        " in file outputFilePath " + outputFilePath);
                }

                outputFileWriter.write(outputStringBuffer.toString());
                outputFileWriter.newLine();
            }

            factualIdList = null;
            System.out.println(
                "************ End of writeFactualDataToFile ************");
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
}
