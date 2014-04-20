package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantCityVO;
import com.tastesync.algo.model.vo.RestaurantPopularityTierVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.model.vo.RestaurantsSearchResultsVO;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public interface UserRestaurantDAO extends BaseDAO {
    LinkedList<RestaurantPopularityTierVO> getConsolidatedFlaggedRestaurantForSingleUser(
        TSDataSource tsDataSource, Connection connection,
        RestaurantUserVO restaurantUserVO) throws TasteSyncException;

    ArrayList<RestaurantPopularityTierVO> getExistingConsolidatedFlaggedRestaurantForSingleUser(
        TSDataSource tsDataSource, Connection connection, String userId)
        throws TasteSyncException;

    List<String> getFlaggedCityIdList(TSDataSource tsDataSource,
        Connection connection, int algoIndicatorIdentifyRestaurantIdList)
        throws TasteSyncException;

    List<RestaurantUserVO> getFlaggedRestaurantFavUserList(
        TSDataSource tsDataSource, Connection connection, int algoIndicator)
        throws TasteSyncException;

    List<RestaurantCityVO> getFlaggedRestaurantList(TSDataSource tsDataSource,
        Connection connection, int algoIndicatorIdentifyRestaurantIdList)
        throws TasteSyncException;

    List<RestaurantUserVO> getFlaggedRestaurantReplyUserList(
        TSDataSource tsDataSource, Connection connection, int algoIndicator)
        throws TasteSyncException;

    List<RestaurantUserVO> getFlaggedRestaurantTipsUserList(
        TSDataSource tsDataSource, Connection connection, int algoIndicator)
        throws TasteSyncException;

    int getMedianvalueForSingleCityIdList(TSDataSource tsDataSource,
        Connection connection, String cityId) throws TasteSyncException;

    int getRestUserMatchCounter(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId,
        RestaurantPopularityTierVO flaggedRestaurantPopularityTierVO)
        throws TasteSyncException;

    int getRestaurantInfoTierId(TSDataSource tsDataSource,
        Connection connection, String userId, String restaurantId)
        throws TasteSyncException;

    int getUserMatchCounter(TSDataSource tsDataSource, Connection connection,
        String userId, String restaurantId) throws TasteSyncException;

    void processSingleRestaurantIdCalc(TSDataSource tsDataSource,
        Connection connection, String restaurantId, int medianUsersNumberForCity)
        throws TasteSyncException;

    RestaurantsSearchResultsVO showListOfRestaurantsSearchResults(
        TSDataSource tsDataSource, Connection connection, String userId,
        String restaurantId, String[] neighborhoodIdList, String cityId,
        String stateName, String[] priceIdList, String rating,
        String savedFlag, String favFlag, String dealFlag, String chainFlag,
        String paginationId, String[] cuisineTier2IdArray,
        String[] themeIdArray, String[] whoareyouwithIdArray,
        String[] typeOfRestaurantIdArray, String[] occasionIdArray)
        throws TasteSyncException;

    RestaurantsSearchResultsVO showListOfRestaurantsSearchResultsBasedOnUserCity(
        TSDataSource tsDataSource, Connection connection, String userId,
        String cityId, String paginationId) throws TasteSyncException;

    void submitAssignedRankUserRestaurant(TSDataSource tsDataSource,
        Connection connection,
        List<RestaurantPopularityTierVO> restaurantPopularityTierVOList)
        throws TasteSyncException;

    void submitAssignedRankUserRestaurantForWhole(TSDataSource tsDataSource,
        Connection connection,
        List<RestaurantPopularityTierVO> restaurantPopularityTierVOList, String userId)
        throws TasteSyncException;

    void submitFlaggedRestaurant(TSDataSource tsDataSource,
        Connection connection, String restaurantId, int algoIndicator)
        throws TasteSyncException;

    void submitRestaurantInfoPopularityTier(TSDataSource tsDataSource,
        Connection connection, String restaurantId, int tierId)
        throws TasteSyncException;
}
