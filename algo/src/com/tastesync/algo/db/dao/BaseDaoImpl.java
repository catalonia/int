package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.queries.TSDBCommonQueries;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.util.TSConstants;
import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.db.pool.TSDataSource;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseDaoImpl implements BaseDAO {
    protected Logger logger = Logger.getLogger(getClass());

    @Override
    public String getRestaurantInfoChained(String restaurantId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(TSDBCommonQueries.RESTAURANT_EXTENDED_INFO_CHAINED_SELECT_SQL);

            statement.setString(1, restaurantId);
            resultset = statement.executeQuery();

            String chainFlag = "0";

            if (resultset.next()) {
                String chainId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_extended_info.chain_id"));

                if (chainId != null && !chainId .isEmpty()) {
                    chainFlag = "1";
                }
            }

            statement.close();

            return chainFlag;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRestaurantInfoChained= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public void submitRecorrequestReplyUserAlgo1(String flaggedUserId, int algoInd)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(TSDBCommonQueries.ALGO1_RECOREQUEST_REPLY_USER_UPDATE_SQL);
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while submitRecorrequestReplyUser = " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public void submitRecorrequestReplyUserAlgo2(String flaggedUserId, int algoInd)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(TSDBCommonQueries.ALGO2_RECOREQUEST_REPLY_USER_UPDATE_SQL);
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while submitRecorrequestReplyUser = " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }
    
    @Override
    public void submitRestaurantTipsTastesyncAlgo1(String flaggedUserId,
        String restaurantId, int algoInd) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(TSDBCommonQueries.ALGO1_RESTAURANT_TIPS_TASTESYNC_UPDATE_SQL);
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);
            statement.setString(3, restaurantId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while submitRestaurantTipsTastesyncAlgo1 = " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public void submitRestaurantTipsTastesyncAlgo2(String flaggedUserId,
        String restaurantId, int algoInd) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(TSDBCommonQueries.ALGO2_RESTAURANT_TIPS_TASTESYNC_UPDATE_SQL);
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);
            statement.setString(3, restaurantId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while submitRestaurantTipsTastesyncAlgo2 = " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }
    
    @Override
    public void submitRestaurantFav(String flaggedUserId, String restaurantId,
        int algoInd, TSConstants.ALGO_TYPE algoType) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            if (algoType.equals(TSConstants.ALGO_TYPE.ALGO1)) {
            	statement = connection.prepareStatement(TSDBCommonQueries.ALGO1_USER_RESTAURANT_FAV_UPDATE_SQL);
            } else if (algoType.equals(TSConstants.ALGO_TYPE.ALGO2)) {
            	statement = connection.prepareStatement(TSDBCommonQueries.ALGO2_USER_RESTAURANT_FAV_RESTAURANT_UPDATE_SQL);
            } else {
            	throw new IllegalArgumentException("Unknown ALGO_TYPE");
            }
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);

            if (algoType.equals(TSConstants.ALGO_TYPE.ALGO2)) {
                statement.setString(3, restaurantId);
            }

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while submitRecorrequestUser = " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public List<String> getAllUsers() throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(TSDBCommonQueries.ALL_USERS_SELECT_SQL);

            resultset = statement.executeQuery();

            List<String> usersList = new ArrayList<String>();

            while (resultset.next()) {
                usersList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.user_id")));
            }

            statement.close();

            return usersList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while getAllUsers= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public String getFBUserId(String userId) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(TSDBCommonQueries.USERS_FB_ID_SELECT_SQL);

            statement.setString(1, userId);
            resultset = statement.executeQuery();

            String fbUserId = null;

            if (resultset.next()) {
                fbUserId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "users.user_fb_id"));
            }

            statement.close();

            return fbUserId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while getFBUserId= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }
}
