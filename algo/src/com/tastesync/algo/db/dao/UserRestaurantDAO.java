package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;

import java.util.List;


public interface UserRestaurantDAO {
    List<String> getFlaggedRestaurantList(
        int algoIndicatorIdentifyRestaurantIdList) throws TasteSyncException;

    void submitRestaurantInfoPopularityTier(String restaurantId, int tierId)
        throws TasteSyncException;

    void submitFlaggedRestaurant(String restaurantId, int algoIndicator)
        throws TasteSyncException;

    void processSingleRestaurantIdCalc(String restaurantId)
        throws TasteSyncException;
}
