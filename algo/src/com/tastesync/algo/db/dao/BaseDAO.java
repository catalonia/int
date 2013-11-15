package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.util.TSConstants;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;

import java.util.List;


public interface BaseDAO {
    public List<String> getAllUsers(TSDataSource tsDataSource,
        Connection connection) throws TasteSyncException;

    public String getFBUserId(TSDataSource tsDataSource, Connection connection,
        String userId) throws TasteSyncException;

    String getRestaurantInfoChained(TSDataSource tsDataSource,
        Connection connection, String restaurantId) throws TasteSyncException;

    void submitRecorrequestReplyUserAlgo1(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, int algoInd)
        throws TasteSyncException;

    void submitRecorrequestReplyUserAlgo2(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, int algoInd)
        throws TasteSyncException;

    void submitRestaurantFav(TSDataSource tsDataSource, Connection connection,
        String flaggedUserId, String restaurantId, int algoInd,
        TSConstants.ALGO_TYPE algoType) throws TasteSyncException;

    void submitRestaurantTipsTastesyncAlgo1(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, String restaurantId,
        int algoInd) throws TasteSyncException;

    void submitRestaurantTipsTastesyncAlgo2(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, String restaurantId,
        int algoInd) throws TasteSyncException;
}
