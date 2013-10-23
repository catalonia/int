package com.tastesync.dataextraction.db.dao;

import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.model.FactualDataVO;

import java.util.List;


public interface FoursquareDAO {
    List<FactualDataVO> getSortedFactualIdListFromFactualDB()
        throws TasteSyncException;

    //-- Identify list of restaurants to process in 4SQ extraction program
    // -- IF Restaurant was last successfully attempted more than 28 days back
    //	-- IF Restaurant was last unsuccessfully attempted more than 45 days back
    void identifyRestaurantsFourSqExtractUsingUpdate(int pullEligInd,
        int beforeNDays, int lastMatchInd) throws TasteSyncException;

    //-- IF Restaurant was last successfully attempted more than 28 days back AND restaurant detail was accessed by a user in last 30 days
    //	-- IF Restaurant was last unsuccessfully attempted more than 28 days back AND restaurant detail was accessed by a user in last 30 days
    void identifyUserAccessRestaurantAttempted(int pullEligInd,
        int lastUpdatedNDays, int lastMatchInd, int accessNDays)
        throws TasteSyncException;

    List<FactualDataVO> getFactualDataVOListsForExtraction()
        throws TasteSyncException;

    void matchFoursquareStatusUpdate(int pullEligInd, int lastMatchInd,
        String restaurantId) throws TasteSyncException;
}
