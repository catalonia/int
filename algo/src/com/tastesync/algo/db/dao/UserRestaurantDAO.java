package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantCityVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;

import java.util.LinkedList;
import java.util.List;


public interface UserRestaurantDAO extends BaseDAO {
    List<RestaurantCityVO> getFlaggedRestaurantList(
        int algoIndicatorIdentifyRestaurantIdList) throws TasteSyncException;

    List<String> getFlaggedCityIdList(int algoIndicatorIdentifyRestaurantIdList)
        throws TasteSyncException;

    int getMedianvalueForSingleCityIdList(String cityId)
        throws TasteSyncException;

    void submitRestaurantInfoPopularityTier(String restaurantId, int tierId)
        throws TasteSyncException;

    void submitFlaggedRestaurant(String restaurantId, int algoIndicator)
        throws TasteSyncException;

    void processSingleRestaurantIdCalc(String restaurantId,
        int medianUsersNumberForCity) throws TasteSyncException;

    List<RestaurantUserVO> getFlaggedRestaurantReplyUserList(int algoIndicator)
        throws TasteSyncException;

    List<RestaurantUserVO> getFlaggedRestaurantTipsUserList(int algoIndicator)
        throws TasteSyncException;

    List<RestaurantUserVO> getFlaggedRestaurantFavUserList(int algoIndicator)
        throws TasteSyncException;

    LinkedList<String> getConsolidatedFlaggedRestaurantForSingleUser(
        RestaurantUserVO restaurantUserVO) throws TasteSyncException;

    void processRestUserMatchCounter(String flaggedUserId,
        String flaggedRestaurantId) throws TasteSyncException;

    public List<String> showListOfRestaurantsSearchResults(String userId,
        String restaurantId, String neighborhoodId, String cityId,
        String stateName, String[] cuisineTier1IdArray, String[] priceIdList,
        String rating, String savedFlag, String favFlag, String dealFlag,
        String chainFlag, String paginationId, String[] cuisineTier2IdArray,
        String[] themeIdArray, String[] whoareyouwithIdArray,
        String[] typeOfRestaurantIdArray, String[] occasionIdArray)
        throws TasteSyncException;
}
