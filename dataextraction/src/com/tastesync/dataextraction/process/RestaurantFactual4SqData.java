package com.tastesync.dataextraction.process;

import com.tastesync.dataextraction.db.dao.FoursquareDAO;
import com.tastesync.dataextraction.db.dao.FoursquareDAOImpl;
import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.external.foursquare.api.FoursquareApi;
import com.tastesync.dataextraction.external.foursquare.api.FoursquareApiException;
import com.tastesync.dataextraction.external.foursquare.api.Result;
import com.tastesync.dataextraction.external.foursquare.api.entities.CompactVenue;
import com.tastesync.dataextraction.external.foursquare.api.entities.CompleteTip;
import com.tastesync.dataextraction.external.foursquare.api.entities.CompleteVenue;
import com.tastesync.dataextraction.external.foursquare.api.entities.Photo;
import com.tastesync.dataextraction.external.foursquare.api.entities.PhotoGroup;
import com.tastesync.dataextraction.external.foursquare.api.entities.Size;
import com.tastesync.dataextraction.external.foursquare.api.entities.TipGroup;
import com.tastesync.dataextraction.external.foursquare.api.entities.Tips;
import com.tastesync.dataextraction.external.foursquare.api.entities.VenuesSearchResult;
import com.tastesync.dataextraction.external.foursquare.api.io.DefaultIOHandler;
import com.tastesync.dataextraction.model.FactualDataVO;
import com.tastesync.dataextraction.util.TSConstants;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;


public class RestaurantFactual4SqData {
    boolean printExtraDebug = false;

    public RestaurantFactual4SqData() {
        super();
    }

    public void processRestaurantFactual4SqData(
        TSConstants.DATAEXTRACTION_SOURCETYPE dataExtractionSourceType,
        String CLIENT_ID, String CLIENT_SECRET, String redirectURI,
        DefaultIOHandler defaultIOHandler, FoursquareApi foursquareApi,
        String inputFactualIdDataFile, String outputSqlFilePath,
        String outputInvalidFactualDataFile) throws TasteSyncException {
        String readLineFactualData = "3b59f129-2838-4633-bb02-af04111ed7e3";
        String[] readLineFactualDataArray = new String[4];

        FactualDataVO factualData = new FactualDataVO();

        Date startDate = new Date();
        Date stopDate = startDate;
        long totalTimeTakenInMilliSeconds = 0;

        int i = 0;

        //read from the file
        BufferedReader br = null;
        BufferedWriter outputSqlFileWriter = null;
        BufferedWriter outputInvalidDataFileWriter = null;
        boolean callAgain = false;

        int attempts = 0;
        int requestNumber = 0;

        try {
            String sCurrentLine;

            br = new BufferedReader(new FileReader(inputFactualIdDataFile));
            outputSqlFileWriter = new BufferedWriter(new FileWriter(
                        outputSqlFilePath));
            outputInvalidDataFileWriter = new BufferedWriter(new FileWriter(
                        outputInvalidFactualDataFile));
            System.out.println(
                "***********************START**************************");

            while ((sCurrentLine = br.readLine()) != null) {
                readLineFactualData = sCurrentLine.trim();

                readLineFactualDataArray = readLineFactualData.split(TSConstants.DELIMITOR,
                        -1);

                factualData.setFactualId(readLineFactualDataArray[0]);
                factualData.setRestuarantName(readLineFactualDataArray[1]);
                factualData.setLatitude(new BigDecimal(
                        readLineFactualDataArray[2]));
                factualData.setLongtitude(new BigDecimal(
                        readLineFactualDataArray[3]));
                factualData.setPhoneNumber(readLineFactualDataArray[4]);

                ++requestNumber;
                System.out.println("Request Number" + requestNumber +
                    " ;factualData=" + factualData.toString());

                if (printExtraDebug) {
                    System.out.println("readLineFactualDataArray=" +
                        Arrays.toString(readLineFactualDataArray));
                }

                callAgain = processDataForSingleFactualId(dataExtractionSourceType,
                        foursquareApi, factualData, outputSqlFileWriter,
                        outputInvalidDataFileWriter);
                attempts = 1;

                while (callAgain) {
                    ++attempts;
                    callAgain = processDataForSingleFactualId(dataExtractionSourceType,
                            foursquareApi, factualData, outputSqlFileWriter,
                            outputInvalidDataFileWriter);

                    if (attempts > TSConstants.MAX_ATTEMPTS) {
                        throw new TasteSyncException("DONE attempts=" +
                            attempts + " Something may be wrong!! Check");
                    }
                }

                stopDate = new Date();
                totalTimeTakenInMilliSeconds = (stopDate.getTime() -
                    startDate.getTime());

                System.out.println("milliSeconds" +
                    totalTimeTakenInMilliSeconds + " ,in seconds " +
                    (totalTimeTakenInMilliSeconds / 1000) + "." +
                    (totalTimeTakenInMilliSeconds % 1000) + " ,in minutes " +
                    (totalTimeTakenInMilliSeconds / 60000));
                System.out.println(
                    "***********************END**************************");

                //TODO to be removed lator
                ++i;

                if (i == -1) {
                    return;
                }
            } // end while
        } // end try
        catch (IOException e) {
            e.printStackTrace();
        } // end catch
        finally {
            try {
                if (br != null) {
                    br.close();
                } // end if
            } // end try
            catch (IOException ex) {
                ex.printStackTrace();
            } // end catch

            if (outputSqlFileWriter != null) {
                try {
                    outputSqlFileWriter.flush();
                    outputSqlFileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputInvalidDataFileWriter != null) {
                try {
                    outputInvalidDataFileWriter.flush();
                    outputInvalidDataFileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } // end finally
    }

    private boolean processDataForSingleFactualId(
        TSConstants.DATAEXTRACTION_SOURCETYPE dataExtractionSourceType,
        FoursquareApi foursquareApi, FactualDataVO factualData,
        BufferedWriter outputSqlFileWriter,
        BufferedWriter outputInvalidDataFileWriter) throws TasteSyncException {
        boolean callAgain = false;
        String factualId = factualData.getFactualId();

        try {
            Result<VenuesSearchResult> venuesSearchResultList = null;

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
            Date now = new Date();
            String strNowDate = sdfDate.format(now);
            strNowDate = "20130405";
            strNowDate = "20131008";

            String providerId = "factual";

            String ll = null;
            String query = null;

            //String version = strNowDate;
            String foursquareVenueId = null;

            Result<CompleteVenue> completeVenueResult = null;
            CompleteVenue completeVenue = null;

            Result<PhotoGroup> photoGroupsResultList = null;
            long tipsCount = 0;
            int venuesLength = 0;
            String outputString = null;
            Tips tips = null;
            TipGroup[] tipGroups = null;
            CompleteTip[] completeTipList = null;

            StringBuffer outputStringBuffer = null;

            String venueNameFromResult = null;
            String phoneNumberFromResult = null;
            boolean matchFound = false;
            CompactVenue matchedCompactVenue = null;
            //TODO if foursquare id is availbale in restaurant_factual_4sq, then skip the below call!!
            //STEP 1
            venuesSearchResultList = foursquareApi.venuesSearch(providerId,
                    factualId, strNowDate);

            if (venuesSearchResultList.getMeta().getCode() == 200) {
                // if query was ok we can finally do something with the data
                venuesLength = venuesSearchResultList.getResult().getVenues().length;

                boolean latLonAvailable = (((factualData.getLatitude() != null) &&
                    !(factualData.getLatitude().compareTo(new BigDecimal(0)) == 0)) &&
                    ((factualData.getLongtitude() != null) &&
                    !(factualData.getLongtitude().compareTo(new BigDecimal(0)) == 0)));

                if ((venuesLength == 0) && latLonAvailable) {
                    // STEP 1.1.
                    ll = factualData.getLatitude() + "," +
                        factualData.getLongtitude();
                    query = factualData.getRestuarantName();

                    venuesSearchResultList = foursquareApi.venuesSearch(ll,
                            query, TSConstants.INTENT_MATCH, providerId,
                            factualId, strNowDate);

                    if (venuesSearchResultList.getMeta().getCode() == 200) {
                        // if query was ok we can finally  do something with the data
                        venuesLength = venuesSearchResultList.getResult()
                                                             .getVenues().length;

                        //venuesSearchResultList = foursquareApi.venuesSearch(ll,
                        //      query, TSConstants.INTENT_GLOBAL, providerId,
                        //    factualId, strNowDate);

                        //                        if (venuesSearchResultList.getMeta().getCode() == 200) {
                        //                            venuesLength = venuesSearchResultList.getResult()
                        //                                                                 .getVenues().length;

                        // if the result does not match to 1, reset to 0 for ignoring the result
                        if (venuesLength != 1) {
                            // write to a file - useful for clean up!!
                            for (CompactVenue venue : venuesSearchResultList.getResult()
                                                                            .getVenues()) {
                                venueNameFromResult = normalisedStringData(venue.getName());
                                phoneNumberFromResult = (venue.getContact() != null)
                                    ? normalisedStringData(venue.getContact()
                                                                .getPhone())
                                    : null;

                                if ((venueNameFromResult != null) &&
                                        (phoneNumberFromResult != null)) {
                                    if ((venueNameFromResult != null) &&
                                            (venueNameFromResult != "")) {
                                        if (normalisedStringData(
                                                    factualData.getRestuarantName())
                                                    .equals(venueNameFromResult)) {
                                            if (normalisedStringData(
                                                        factualData.getPhoneNumber())
                                                        .equals(phoneNumberFromResult)) {
                                                //TODO
                                                if (!matchFound) {
                                                    matchFound = true;
                                                    matchedCompactVenue = venue;
                                                } else {
                                                    matchFound = false;
                                                    matchedCompactVenue = null;

                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                                outputString = "Manual Review: More than one result, No. of SQIds found= " +
                                    venuesLength + "\t;4SQVenue1ID=" +
                                    venue.getId() + " \t;venue name=" +
                                    venue.getName() + " \t;query=" + query +
                                    " \t; factualId=" + factualId;
                                outputInvalidDataFileWriter.write(outputString);
                                outputInvalidDataFileWriter.newLine();
                                venueNameFromResult = null;
                                phoneNumberFromResult = null;
                            }
                        } else if ((venuesLength == 0)) {
                            // write to a file - useful for clean up!!
                            outputString = factualId;
                            outputInvalidDataFileWriter.write(outputString);
                            outputInvalidDataFileWriter.newLine();
                            System.out.println(
                                "-- NO Data found for factualId=" +
                                outputString);
                        }

                        //}
                    }
                }

                if (matchFound) {
                    outputString = "One match found from multiple results= " +
                        venuesLength + "\t;4SQVenue1ID=" +
                        matchedCompactVenue.getId() + " \t;venue name=" +
                        matchedCompactVenue.getName() + " \t;query=" + query +
                        " \t; factualId=" + factualId;
                    outputInvalidDataFileWriter.write(outputString);
                    outputInvalidDataFileWriter.newLine();
                    System.out.println("matchedCompactVenue=" +
                        matchedCompactVenue.toString());
                }

                if (TSConstants.DATAEXTRACTION_SOURCETYPE.FOURSQUARE.equals(
                            dataExtractionSourceType)) {
                    int pullEligInd = 0;
                    int lastMatchInd = 0;
                    String restaurantId = factualId;

                    if (matchFound) {
                        pullEligInd = 0;
                        lastMatchInd = 1;
                    } else {
                        pullEligInd = 0;
                        lastMatchInd = 0;
                    }

                    FoursquareDAO foursquareDAO = new FoursquareDAOImpl();
                    foursquareDAO.matchFoursquareStatusUpdate(pullEligInd,
                        lastMatchInd, restaurantId);
                }

                if (venuesSearchResultList.getMeta().getCode() == 200) {
                    CompactVenue venue = null;

                    if (venuesLength == 1) {
                        venue = venuesSearchResultList.getResult().getVenues()[0];
                    } else if (matchFound) {
                        venue = matchedCompactVenue;
                    } else {
                        // Do not do anything.. just ignore!!
                        return false;
                    }

                    //Save the Foursquare Venue ID and TipCount
                    foursquareVenueId = venue.getId().trim();

                    tipsCount = (venue.getTips() != null)
                        ? venue.getTips().getCount() : 0;

                    if (printExtraDebug) {
                        System.out.println("foursquareVenueId=" +
                            foursquareVenueId);
                        System.out.println("tipsCount=" + tipsCount);
                        System.out.println(venue.toString());
                    }

                    if (foursquareVenueId != null) {
                        //Step 2
                        //Get complete venue details
                        completeVenueResult = foursquareApi.venue(foursquareVenueId,
                                strNowDate);

                        if (completeVenueResult.getMeta().getCode() == 200) {
                            completeVenue = completeVenueResult.getResult();

                            outputStringBuffer = new StringBuffer();

                            //TODOD add the line for auto commmit as false

                            // delete first
                            outputStringBuffer.append(
                                "DELETE FROM RESTAURANT_FACTUAL_4SQVENUE WHERE RESTAURANT_ID =");
                            outputStringBuffer.append("\"").append(factualId)
                                              .append("\";\n");

                            outputStringBuffer.append(
                                "DELETE FROM RESTAURANT_MENU WHERE RESTAURANT_ID =");

                            outputStringBuffer.append("\"").append(factualId)
                                              .append("\";\n");

                            outputStringBuffer.append(
                                "DELETE FROM RESTAURANT_TIPS_EXTERNAL WHERE RESTAURANT_ID =");

                            outputStringBuffer.append("\"").append(factualId)
                                              .append("\";\n");

                            outputStringBuffer.append(
                                "DELETE FROM RESTAURANT_PHOTO WHERE RESTAURANT_ID =");

                            outputStringBuffer.append("\"").append(factualId)
                                              .append("\";\n");

                            outputSqlFileWriter.write(outputStringBuffer.toString());
                            outputSqlFileWriter.newLine();

                            outputStringBuffer = new StringBuffer();

                            outputStringBuffer.append(
                                "INSERT INTO RESTAURANT_FACTUAL_4SQVENUE (4SQ_VENUE_ID,FACTUAL_ID,RESTAURANT_ID,4SQ_PHOTOS_COUNT,4SQ_TIPS_COUNT,4SQ_USERS_COUNT,4SQ_CHECKINS_COUNT,4SQ_RATING) VALUES (");
                            outputStringBuffer.append("\"")
                                              .append(foursquareVenueId)
                                              .append("\",");
                            outputStringBuffer.append("\"").append(factualId)
                                              .append("\",");
                            outputStringBuffer.append("\"").append(factualId)
                                              .append("\",");

                            outputStringBuffer.append("\"")
                                              .append((completeVenue.getPhotos() != null)
                                ? completeVenue.getPhotos().getCount() : 0)
                                              .append("\",");

                            outputStringBuffer.append("\"")
                                              .append((completeVenue.getTips() != null)
                                ? completeVenue.getTips().getCount() : 0)
                                              .append("\",");

                            outputStringBuffer.append("\"")
                                              .append(completeVenue.getStats()
                                                                   .getUsersCount())
                                              .append("\",");
                            outputStringBuffer.append("\"")
                                              .append(completeVenue.getStats()
                                                                   .getCheckinsCount())
                                              .append("\",");
                            outputStringBuffer.append("\"")
                                              .append((completeVenue.getRating() != null)
                                ? completeVenue.getRating() : "").append("\"");
                            outputStringBuffer.append(" );");
                            outputSqlFileWriter.write(outputStringBuffer.toString());
                            outputSqlFileWriter.newLine();

                            if (printExtraDebug) {
                                System.out.println(completeVenue.toString());
                            }

                            //extract mobile menu data
                            if (completeVenue.getMenu() != null) {
                                outputStringBuffer = new StringBuffer();
                                outputStringBuffer.append(
                                    "INSERT INTO RESTAURANT_MENU (RESTAURANT_ID,MENU_SOURCE,MENU_SOURCE_VENUE_ID,MENU_MOBILEURL) VALUES (");
                                outputStringBuffer.append("\"").append(factualId)
                                                  .append("\",");
                                outputStringBuffer.append("\"")
                                                  .append(TSConstants.FOURSQ_SOURCE)
                                                  .append("\",");
                                outputStringBuffer.append("\"")
                                                  .append(foursquareVenueId)
                                                  .append("\",");
                                outputStringBuffer.append("\"")
                                                  .append(correctedSqlStringData(
                                        completeVenue.getMenu().getMobileUrl()))
                                                  .append("\"");
                                outputStringBuffer.append(" );");
                                outputSqlFileWriter.write(outputStringBuffer.toString());
                                outputSqlFileWriter.newLine();
                            }

                            // extract tips and Write tips relevant info.
                            tips = completeVenue.getTips();

                            if (tips != null) {
                                tipGroups = tips.getGroups();

                                for (TipGroup tipGroup : tipGroups) {
                                    completeTipList = tipGroup.getItems();

                                    for (CompleteTip completeTip : completeTipList) {
                                        outputStringBuffer = new StringBuffer();
                                        outputStringBuffer.append(
                                            "INSERT INTO RESTAURANT_TIPS_EXTERNAL (RESTAURANT_ID,TIP_SOURCE,TIP_SOURCE_VENUE_ID,TIP_ID,TIP_TEXT,TIP_LIKES_COUNT,TIP_USER_4SQ_ID,TIP_USER_FIRSTNAME,TIP_USER_LASTNAME,TIP_USER_PHOTO_PREFIX,TIP_USER_PHOTO_SUFFIX,TIP_USER_TYPE) VALUES (");
                                        outputStringBuffer.append("\"")
                                                          .append(factualId)
                                                          .append("\",");
                                        outputStringBuffer.append("\"")
                                                          .append(TSConstants.FOURSQ_SOURCE)
                                                          .append("\",");
                                        outputStringBuffer.append("\"")
                                                          .append(foursquareVenueId)
                                                          .append("\",");
                                        outputStringBuffer.append("\"")
                                                          .append(completeTip.getId())
                                                          .append("\",");
                                        outputStringBuffer.append("\"")
                                                          .append(correctedSqlStringData(
                                                completeTip.getText()))
                                                          .append("\",");

                                        if (completeTip.getLikes() != null) {
                                            outputStringBuffer.append("\"")
                                                              .append(completeTip.getLikes()
                                                                                 .getCount())
                                                              .append("\",");
                                        } else {
                                            outputStringBuffer.append("\"")
                                                              .append(0)
                                                              .append("\",");
                                        }

                                        if (completeTip.getUser() != null) {
                                            outputStringBuffer.append("\"")
                                                              .append(completeTip.getUser()
                                                                                 .getId())
                                                              .append("\",");
                                            outputStringBuffer.append("\"")
                                                              .append(correctedSqlStringData(
                                                    completeTip.getUser()
                                                               .getFirstName()))
                                                              .append("\",");
                                            outputStringBuffer.append("\"")
                                                              .append(correctedSqlStringData(
                                                    completeTip.getUser()
                                                               .getLastName()))
                                                              .append("\",");

                                            if (completeTip.getUser().getPhoto() != null) {
                                                outputStringBuffer.append("\"")
                                                                  .append(correctedSqlStringData(
                                                        completeTip.getUser()
                                                                   .getPhoto()
                                                                   .getPrefix()))
                                                                  .append("\",");
                                                outputStringBuffer.append("\"")
                                                                  .append(correctedSqlStringData(
                                                        completeTip.getUser()
                                                                   .getPhoto()
                                                                   .getSuffix()))
                                                                  .append("\",");
                                            } else {
                                                outputStringBuffer.append("\"")
                                                                  .append("")
                                                                  .append("\",");
                                                outputStringBuffer.append("\"")
                                                                  .append("")
                                                                  .append("\",");
                                            }

                                            outputStringBuffer.append("\"")
                                                              .append((completeTip.getUser()
                                                                                  .getType() != null)
                                                ? completeTip.getUser().getType()
                                                : "").append("\"");
                                        } else {
                                            outputStringBuffer.append("\"")
                                                              .append("")
                                                              .append("\",");
                                            outputStringBuffer.append("\"")
                                                              .append("")
                                                              .append("',");
                                            outputStringBuffer.append("\"")
                                                              .append("")
                                                              .append("\",");
                                            outputStringBuffer.append("\"")
                                                              .append("")
                                                              .append("\",");
                                            outputStringBuffer.append("\"")
                                                              .append("")
                                                              .append("\",");
                                            outputStringBuffer.append("\"")
                                                              .append("")
                                                              .append("\"");
                                        }

                                        outputStringBuffer.append(" );");
                                        outputSqlFileWriter.write(outputStringBuffer.toString());
                                        outputSqlFileWriter.newLine();

                                        if (printExtraDebug) {
                                            System.out.println(completeTip.toString());
                                        }
                                    }
                                }
                            }
                        }

                        //extract Photos
                        //step 3
                        photoGroupsResultList = foursquareApi.venuesPhotos(foursquareVenueId,
                                TSConstants.PHOTO_GROUPTYPE_VENUE);

                        PhotoGroup photoGroup = photoGroupsResultList.getResult();

                        // check
                        if (photoGroup != null) {
                            Photo[] photoList = photoGroup.getItems();
                            String[] urlArray = new String[2];
                            Size[] sizeList = null;
                            boolean photoOriginalFound = false;

                            //Write photo relevant info.
                            for (Photo photo : photoList) {
                                outputStringBuffer = new StringBuffer();

                                outputStringBuffer.append(
                                    "INSERT INTO RESTAURANT_PHOTO (RESTAURANT_ID,PHOTO_SOURCE,PHOTO_SOURCE_VENUE_ID,PHOTO_ID,CREATED_AT,PREFIX,SUFFIX,WIDTH,HEIGHT,ULTIMATE_SOURCE_NAME,ULTIMATE_SOURCE_URL) VALUES (");

                                outputStringBuffer.append("\"").append(factualId)
                                                  .append("\",");
                                outputStringBuffer.append("\"")
                                                  .append(TSConstants.FOURSQ_SOURCE)
                                                  .append("\",");
                                outputStringBuffer.append("\"")
                                                  .append(foursquareVenueId)
                                                  .append("\",");
                                outputStringBuffer.append("\"")
                                                  .append(photo.getId())
                                                  .append("\",");
                                outputStringBuffer.append("\"")
                                                  .append(photo.getCreatedAt())
                                                  .append("\",");
                                urlArray[0] = "";
                                urlArray[1] = "";
                                photoOriginalFound = false;

                                if ((photo.getSizes() != null) &&
                                        (photo.getSizes().getItems() != null)) {
                                    sizeList = photo.getSizes().getItems();

                                    for (Size size : sizeList) {
                                        if (size.getUrl()
                                                    .contains(TSConstants.PHOTO_ORIGINAL_DELIMITOR)) {
                                            photoOriginalFound = true;
                                            urlArray = size.getUrl()
                                                           .split(TSConstants.PHOTO_ORIGINAL_DELIMITOR,
                                                    -1);
                                            outputStringBuffer.append("\"")
                                                              .append(urlArray[0])
                                                              .append("\",");
                                            outputStringBuffer.append("\"")
                                                              .append(urlArray[1])
                                                              .append("\",");
                                            outputStringBuffer.append("")
                                                              .append(size.getWidth())
                                                              .append(",");
                                            outputStringBuffer.append("")
                                                              .append(size.getHeight())
                                                              .append(",");

                                            break;
                                        }
                                    }
                                }

                                if (!photoOriginalFound) {
                                    outputStringBuffer.append("\"").append("")
                                                      .append("\",");
                                    outputStringBuffer.append("\"").append("")
                                                      .append("\",");
                                    outputStringBuffer.append("").append(-1)
                                                      .append(",");
                                    outputStringBuffer.append("").append(-1)
                                                      .append(",");
                                }

                                outputStringBuffer.append("\"")
                                                  .append(correctedSqlStringData(
                                        photo.getSource().getName()))
                                                  .append("\",");
                                outputStringBuffer.append("\"")
                                                  .append(correctedSqlStringData(
                                        photo.getSource().getUrl())).append("\"");

                                outputStringBuffer.append(" );");

                                outputSqlFileWriter.write(outputStringBuffer.toString());
                                outputSqlFileWriter.newLine();

                                if (printExtraDebug) {
                                    System.out.println(outputString);
                                }
                            }
                        }
                    }

                    outputStringBuffer = new StringBuffer();
                    outputStringBuffer.append("commit;");
                    outputSqlFileWriter.write(outputStringBuffer.toString());
                    outputSqlFileWriter.newLine();
                }

                //                if (venuesSearchResultList.getResult().getGroups() != null) {
                //                    System.out.println(venuesSearchResultList.getResult()
                //                                                             .getGroups().length);
                //
                //                    for (VenueGroup venueGroup : venuesSearchResultList.getResult()
                //                                                                       .getGroups()) {
                //                        System.out.println(venueGroup.getName());
                //                        System.out.println(venueGroup.toString());
                //                    }
                //                }
            } else {
                // TODO: Proper error handling
                System.out.println("Error occured: HTTP code=" +
                    venuesSearchResultList.getMeta().getCode());
                //write the factual ids for which the error occurs.
                outputString = "factualId=" + factualId + "\ncode=" +
                    venuesSearchResultList.getMeta().getCode() + "\ntype=" +
                    venuesSearchResultList.getMeta().getErrorType() +
                    "\n  detail: " +
                    venuesSearchResultList.getMeta().getErrorDetail();
                //outputInvalidDataFileWriter.write(outputString);
                //outputInvalidDataFileWriter.newLine();
                System.out.println(outputString);

                if (venuesSearchResultList.getMeta().getCode() == 500) {
                    System.out.println("Some issue establishing the connection");
                }

                if (venuesSearchResultList.getMeta().getCode() == 503) {
                    System.out.println("Temprory connection not unavailable.");
                }

                if ("rate_limit_exceeded".equals(venuesSearchResultList.getMeta()
                                                                           .getErrorType())) {
                    //sleep for remaining time!!
                    try {
                        System.out.println("SLEEP for " +
                            TSConstants.HOUR_LIMIT_IN_MILLISECONDS);
                        Thread.currentThread();
                        Thread.sleep(TSConstants.HOUR_LIMIT_IN_MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    callAgain = true;
                }
            }
        } catch (FoursquareApiException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } // end catch

        return callAgain;
    }

    private String correctedSqlStringData(String inputSqlStringData) {
        String correctedInputSqlStringData = null;
        correctedInputSqlStringData = (inputSqlStringData != null)
            ? inputSqlStringData : "";
        // additonal replacements!!
        correctedInputSqlStringData = StringUtils.replace(correctedInputSqlStringData,
                "\"", "\"\"");

        return correctedInputSqlStringData;
    }

    private String normalisedStringData(String inputStringData) {
        String correctedInputStringData = null;
        correctedInputStringData = (inputStringData != null) ? inputStringData
                                                             : "";

        correctedInputStringData = correctedInputStringData.toLowerCase();
        // additonal replacements!!
        correctedInputStringData = StringUtils.replace(correctedInputStringData,
                " ", "");

        correctedInputStringData = StringUtils.replace(correctedInputStringData,
                "'", "");

        correctedInputStringData = StringUtils.replace(correctedInputStringData,
                "'", "");
        correctedInputStringData = StringUtils.replace(correctedInputStringData,
                "&", "");
        correctedInputStringData = StringUtils.replace(correctedInputStringData,
                "-", "");
        correctedInputStringData = StringUtils.replace(correctedInputStringData,
                "@", "");

        return correctedInputStringData;
    }
}
