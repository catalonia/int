package com.tastesync.dataextraction.db.dao;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.dataextraction.db.queries.FactualCrosswalkQueries;
import com.tastesync.dataextraction.exception.TasteSyncException;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class FactualCrosswalkDAOImpl implements FactualCrosswalkDAO {
    @Override
    public void addInvalidCrossWalkData(TSDataSource tsDataSource,
        Connection connection, String restaurantId, String factualId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(FactualCrosswalkQueries.INVALID_OPENTABLE_FACTUAL_ID_INSERT_SQL);
            statement.setString(1, factualId);
            statement.setString(2, restaurantId);
            statement.setTimestamp(3,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setTimestamp(4,
                CommonFunctionsUtil.getCurrentDateTimestamp());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while addOpenTableFactualCrossWalkData= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void addOpenTableFactualCrossWalkData(TSDataSource tsDataSource,
        Connection connection, String restaurantId, String factualId,
        String reservationSource, String reservationSourceId,
        String reservationUrl) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(FactualCrosswalkQueries.OPENTABLE_FACTUAL_ID_INSERT_SQL);

            statement.setString(1, factualId);
            statement.setString(2, reservationSource);
            statement.setString(3, reservationSourceId);
            statement.setString(4, reservationUrl);
            statement.setString(5, restaurantId);
            statement.setTimestamp(6,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setString(7, reservationSource);
            statement.setString(8, reservationSourceId);
            statement.setString(9, reservationUrl);
            statement.setTimestamp(10,
                CommonFunctionsUtil.getCurrentDateTimestamp());

            statement.executeUpdate();
            
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while addOpenTableFactualCrossWalkData= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getFactualIds(TSDataSource tsDataSource,
        Connection connection) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(FactualCrosswalkQueries.FACTUAL_IDS_SELECT_SQL);
            resultset = statement.executeQuery();

            String factualId;
            List<String> factualIdList = new ArrayList<String>();

            while (resultset.next()) {
                factualId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "FACTUAL_ID"));
                factualIdList.add(factualId);
            }

            statement.close();

            return factualIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while getFactualIds= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }
}
