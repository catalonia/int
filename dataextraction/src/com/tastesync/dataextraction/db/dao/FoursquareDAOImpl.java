package com.tastesync.dataextraction.db.dao;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.dataextraction.db.DBConnection;
import com.tastesync.dataextraction.db.queries.FoursquareQueries;
import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.model.FactualDataVO;
import com.tastesync.dataextraction.util.DBQueries;

import com.tastesync.db.pool.TSDataSource;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FoursquareDAOImpl implements FoursquareDAO {
    private DecimalFormat df = new DecimalFormat("#0.00000");
    private boolean printExtraDebug = false;

    @Override
    public List<FactualDataVO> getSortedFactualIdListFromFactualDB()
        throws TasteSyncException {
        System.out.println(
            "************ START of getSortedFactualIdList ************");

        List<FactualDataVO> factualIdList = new ArrayList<FactualDataVO>();
        DBConnection dbConnectionInstance = new DBConnection();
        Connection dbConnection = null;
        PreparedStatement preparedStatementSelect = null;
        ResultSet resultSet = null;

        try {
            dbConnection = dbConnectionInstance.openDBConnection();

            preparedStatementSelect = dbConnection.prepareStatement(DBQueries.FACTUAL_ID_SELECT_SQL);
            resultSet = preparedStatementSelect.executeQuery();

            String factualId = null;
            String restuarantName = null;
            String latitude = null;
            String longitude = null;
            FactualDataVO factualDataVo = null;
            BigDecimal latitudeBD = null;
            BigDecimal longitudeBD = null;
            String phoneNumber = null;

            while (resultSet.next()) {
                factualId = resultSet.getString(1);

                if (resultSet.getString(1) != null) {
                    factualId = factualId.trim();

                    if (printExtraDebug) {
                        System.out.println("factual id=" + factualId);
                    }

                    restuarantName = (resultSet.getString(2) != null)
                        ? resultSet.getString(2).trim() : null;
                    latitude = ((resultSet.getString(3) != null) &&
                        !resultSet.getString(3).isEmpty())
                        ? resultSet.getString(3).trim() : null;
                    longitude = ((resultSet.getString(4) != null) &&
                        !resultSet.getString(4).isEmpty())
                        ? resultSet.getString(4).trim() : null;
                    latitudeBD = null;

                    if (latitude != null) {
                        latitudeBD = new BigDecimal(df.format(Double.valueOf(
                                        latitude)));
                    }

                    longitudeBD = null;

                    if (longitude != null) {
                        longitudeBD = new BigDecimal(df.format(Double.valueOf(
                                        longitude)));
                    }

                    phoneNumber = (resultSet.getString(5) != null)
                        ? resultSet.getString(5).trim() : "";

                    factualDataVo = new FactualDataVO(factualId,
                            restuarantName, latitudeBD, longitudeBD, phoneNumber);
                    factualIdList.add(factualDataVo);

                    if (printExtraDebug) {
                        System.out.println("To be written: factualData=" +
                            factualDataVo.toString());
                        System.out.println(
                            "Atleast either latitude or longitude  is NULL!!");
                    }
                } else {
                    System.out.println("factualId is NULL. Should not be!!");
                }
            }

            preparedStatementSelect.close();
            System.out.println("************ END of DB reseult ************");

            Collections.sort(factualIdList,
                new FactualDataVO().new FactualDataComparator());

            return factualIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getFactualDataVOListsForExtraction= " +
                e.getMessage());
        } finally {
            if ((dbConnectionInstance != null) && (dbConnection != null)) {
                dbConnectionInstance.closeDBConnection(dbConnection);
            }

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatementSelect != null) {
                try {
                    preparedStatementSelect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(
                "************ End of getSortedFactualIdList ************");
        }
    }

    @Override
    public void identifyRestaurantsFourSqExtractUsingUpdate(int pullEligInd,
        int beforeNDays, int lastMatchInd) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();
            statement = connection.prepareStatement(FoursquareQueries.IDENTIFY_STATUS_ATTEMPT_BEFORE_NDAYS_FOURSQ_FACTUAL_EXTRACT_UPDATE_SQL);

            statement.setInt(1, pullEligInd);
            statement.setInt(2, beforeNDays);
            statement.setInt(3, lastMatchInd);

            statement.executeUpdate();

            statement.close();
            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while identifyRestaurantsFourSqExtractUsingUpdate= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void identifyUserAccessRestaurantAttempted(int pullEligInd,
        int lastUpdatedNDays, int lastMatchInd, int accessNDays)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();
            statement = connection.prepareStatement(FoursquareQueries.USER_ACCESS_NDAYS_STATUS_ATTEMPT_BEFORE_NDAYS_FOURSQ_FACTUAL_EXTRACT_UPDATE_SQL);

            statement.setInt(1, pullEligInd);
            statement.setInt(2, lastUpdatedNDays);
            statement.setInt(3, lastMatchInd);

            statement.setInt(4, accessNDays);
            statement.executeUpdate();

            statement.close();
            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while IdentifyUserAccessRestaurantAttempted= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<FactualDataVO> getFactualDataVOListsForExtraction()
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();

            List<FactualDataVO> factualDataVOList = new ArrayList<FactualDataVO>();

            statement = connection.prepareStatement(FoursquareQueries.RESTAURANT_DATA_LIST_SELECT_SQL);
            statement.setInt(1, 0);
            resultset = statement.executeQuery();

            String restaurantId;
            String pullEligIndOrder;
            String factualId;
            String restuarantName;
            String latitude;
            String longitude;

            BigDecimal latitudeBD;
            BigDecimal longitudeBD;
            String phoneNumber;

            FactualDataVO factualDataVO;

            while (resultset.next()) {
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "RESTAURANT_ID"));

                pullEligIndOrder = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "4SQ_PULL_ELIG_IND"));

                factualId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "FACTUAL_ID"));

                restuarantName = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "RESTAURANT_NAME"));
                latitude = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "RESTAURANT_LAT"));

                longitude = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "RESTAURANT_LON"));

                latitudeBD = null;

                if ((latitude != null) && !latitude.isEmpty()) {
                    latitudeBD = new BigDecimal(df.format(Double.valueOf(
                                    latitude)));
                }

                longitudeBD = null;

                if ((longitude != null) && !longitude.isEmpty()) {
                    longitudeBD = new BigDecimal(df.format(Double.valueOf(
                                    longitude)));
                }

                phoneNumber = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "RESTAURANT_EXTENDED_INFO.TEL"));

                factualDataVO = new FactualDataVO(factualId, restuarantName,
                        latitudeBD, longitudeBD, phoneNumber);

                factualDataVOList.add(factualDataVO);
            }

            statement.close();

            return factualDataVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getFactualDataVOListsForExtraction= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void matchFoursquareStatusUpdate(int pullEligInd, int lastMatchInd,
        String restaurantId) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();
            statement = connection.prepareStatement(FoursquareQueries.MATCH_FOURSQUARE_STATUS_UPDATE_SQL);

            statement.setInt(1, pullEligInd);
            statement.setInt(2, lastMatchInd);

            statement.setTimestamp(3,
                CommonFunctionsUtil.getCurrentDateTimestamp());

            statement.setString(4, restaurantId);
            statement.executeUpdate();

            statement.close();
            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while matchFoursquareStatusUpdate= " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }
}
