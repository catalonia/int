package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantCityVO;
import com.tastesync.algo.model.vo.RestaurantPopularityTierVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.model.vo.RestaurantsSearchResultsVO;

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

    LinkedList<RestaurantPopularityTierVO> getConsolidatedFlaggedRestaurantForSingleUser(
        RestaurantUserVO restaurantUserVO) throws TasteSyncException;

    int getRestUserMatchCounter(String flaggedUserId,
        RestaurantPopularityTierVO flaggedRestaurantPopularityTierVO)
        throws TasteSyncException;

    RestaurantsSearchResultsVO showListOfRestaurantsSearchResults(
        String userId, String restaurantId, String neighborhoodId,
        String cityId, String stateName, String[] priceIdList, String rating,
        String savedFlag, String favFlag, String dealFlag, String chainFlag,
        String paginationId, String[] cuisineTier2IdArray,
        String[] themeIdArray, String[] whoareyouwithIdArray,
        String[] typeOfRestaurantIdArray, String[] occasionIdArray)
        throws TasteSyncException;

    RestaurantsSearchResultsVO showListOfRestaurantsSearchResultsBasedOnUserCity(
        String userId, String cityId, String paginationId)
        throws TasteSyncException;

    void submitAssignedRankUserRestaurantForWhole(
        List<RestaurantPopularityTierVO> restaurantPopularityTierVOList)
        throws TasteSyncException;

    int getUserMatchCounter(String userId, String restaurantId)
        throws TasteSyncException;

    int getRestaurantInfoTierId(String userId, String restaurantId)
        throws TasteSyncException;

    void submitAssignedRankUserRestaurant(
        List<RestaurantPopularityTierVO> restaurantPopularityTierVOList)
        throws TasteSyncException;
}
