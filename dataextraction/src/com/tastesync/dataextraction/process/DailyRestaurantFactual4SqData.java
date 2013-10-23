package com.tastesync.dataextraction.process;

import com.tastesync.dataextraction.db.dao.FoursquareDAO;
import com.tastesync.dataextraction.db.dao.FoursquareDAOImpl;
import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.model.FactualDataVO;

import java.util.List;


public class DailyRestaurantFactual4SqData {
    FoursquareDAO foursquareDAO = new FoursquareDAOImpl();

    public DailyRestaurantFactual4SqData() {
        super();
    }

    public List<FactualDataVO> getFactualDataVOListsForExtraction()
        throws TasteSyncException {
        return foursquareDAO.getFactualDataVOListsForExtraction();
    }

    public void identifyRestaurantsFourSqExtractUsingUpdate(int pullEligInd,
        int beforeNDays, int lastMatchInd) throws TasteSyncException {
        foursquareDAO.identifyRestaurantsFourSqExtractUsingUpdate(pullEligInd,
            beforeNDays, lastMatchInd);
    }

    public void identifyUserAccessRestaurantAttempted(int pullEligInd,
        int lastUpdatedNDays, int lastMatchInd, int accessNDays)
        throws TasteSyncException {
        foursquareDAO.identifyUserAccessRestaurantAttempted(pullEligInd,
            lastUpdatedNDays, lastMatchInd, accessNDays);
    }

    public void matchFoursquareStatusUpdate(int pullEligInd, int lastMatchInd,
        String restaurantId) throws TasteSyncException {
        foursquareDAO.matchFoursquareStatusUpdate(pullEligInd, lastMatchInd,
            restaurantId);
    }
}
