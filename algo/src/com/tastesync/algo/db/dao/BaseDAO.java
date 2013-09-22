package com.tastesync.algo.db.dao;

import java.util.List;

import com.tastesync.algo.exception.TasteSyncException;


public interface BaseDAO {
    String getRestaurantInfoChained(String restaurantId)
        throws TasteSyncException;

    void submitRecorrequestReplyUser(String flaggedUserId, int algoInd)
        throws TasteSyncException;

    void submitRestaurantTipsTastesync(String flaggedUserId,
        String restaurantId, int algoInd) throws TasteSyncException;

    void submitRestaurantFav(String flaggedUserId, String restaurantId,
        int algoInd) throws TasteSyncException;
    
    public List<String> getAllUsers() throws TasteSyncException;

}
