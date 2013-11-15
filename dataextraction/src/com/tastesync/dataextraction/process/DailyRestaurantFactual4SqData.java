package com.tastesync.dataextraction.process;

import com.tastesync.dataextraction.db.dao.FoursquareDAO;
import com.tastesync.dataextraction.db.dao.FoursquareDAOImpl;
import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.model.FactualDataVO;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;


public class DailyRestaurantFactual4SqData {
    FoursquareDAO foursquareDAO = new FoursquareDAOImpl();

    public DailyRestaurantFactual4SqData() {
        super();
    }

    public List<FactualDataVO> getFactualDataVOListsForExtraction(
        TSDataSource tsDataSource, Connection connection)
        throws TasteSyncException {
        return foursquareDAO.getFactualDataVOListsForExtraction(tsDataSource,
            connection);
    }

    public void identifyRestaurantsFourSqExtractUsingUpdate(
        TSDataSource tsDataSource, Connection connection, int pullEligInd,
        int beforeNDays, int lastMatchInd) throws TasteSyncException {
        try {
            tsDataSource.begin();
            foursquareDAO.identifyRestaurantsFourSqExtractUsingUpdate(tsDataSource,
                connection, pullEligInd, beforeNDays, lastMatchInd);

            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(e.getMessage());
        }
    }

    public void identifyUserAccessRestaurantAttempted(
        TSDataSource tsDataSource, Connection connection, int pullEligInd,
        int lastUpdatedNDays, int lastMatchInd, int accessNDays)
        throws TasteSyncException {
        try {
            tsDataSource.begin();
            foursquareDAO.identifyUserAccessRestaurantAttempted(tsDataSource,
                connection, pullEligInd, lastUpdatedNDays, lastMatchInd,
                accessNDays);
            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(e.getMessage());
        }
    }

    public void matchFoursquareStatusUpdate(TSDataSource tsDataSource,
        Connection connection, int pullEligInd, int lastMatchInd,
        String restaurantId) throws TasteSyncException {
        try {
            tsDataSource.begin();
            foursquareDAO.matchFoursquareStatusUpdate(tsDataSource, connection,
                pullEligInd, lastMatchInd, restaurantId);
            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(e.getMessage());
        }
    }
}
