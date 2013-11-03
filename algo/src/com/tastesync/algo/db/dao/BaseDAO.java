package com.tastesync.algo.db.dao;

import java.util.List;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.util.TSConstants;


public interface BaseDAO {
    String getRestaurantInfoChained(String restaurantId)
        throws TasteSyncException;

    void submitRecorrequestReplyUserAlgo1(String flaggedUserId, int algoInd)
        throws TasteSyncException;

    void submitRecorrequestReplyUserAlgo2(String flaggedUserId, int algoInd)
            throws TasteSyncException;
    
    void submitRestaurantTipsTastesyncAlgo1(String flaggedUserId,
        String restaurantId, int algoInd) throws TasteSyncException;

    void submitRestaurantTipsTastesyncAlgo2(String flaggedUserId,
            String restaurantId, int algoInd) throws TasteSyncException;
    
    void submitRestaurantFav(String flaggedUserId, String restaurantId,
        int algoInd, TSConstants.ALGO_TYPE algoType) throws TasteSyncException;
    
    public List<String> getAllUsers() throws TasteSyncException;

    public String getFBUserId(String userId) throws TasteSyncException;
}
