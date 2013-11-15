package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantsSearchResultsVO;
import com.tastesync.algo.util.TSConstants;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;


public class RestaurantsSearchResultsOnlineCalc {
    private UserRestaurantDAO userRestaurantDAO = new UserRestaurantDAOImpl();

    public RestaurantsSearchResultsOnlineCalc() {
        super();
    }

    // return list of restaurantIds based on different parameters
    public RestaurantsSearchResultsVO showListOfRestaurantsSearchResults(
        TSDataSource tsDataSource, Connection connection, String userId,
        String restaurantId, String neighborhoodId, String cityId,
        String stateName, String[] priceIdList, String rating,
        String savedFlag, String favFlag, String dealFlag, String chainFlag,
        String paginationId, String[] cuisineTier2IdArray,
        String[] themeIdArray, String[] whoareyouwithIdArray,
        String[] typeOfRestaurantIdArray, String[] occasionIdArray)
        throws TasteSyncException {
        userId = CommonFunctionsUtil.converStringAsNullIfNeeded(userId);
        restaurantId = CommonFunctionsUtil.converStringAsNullIfNeeded(restaurantId);
        neighborhoodId = CommonFunctionsUtil.converStringAsNullIfNeeded(neighborhoodId);
        cityId = CommonFunctionsUtil.converStringAsNullIfNeeded(cityId);
        stateName = CommonFunctionsUtil.converStringAsNullIfNeeded(stateName);
        rating = CommonFunctionsUtil.converStringAsNullIfNeeded(rating);
        savedFlag = CommonFunctionsUtil.converStringAsNullIfNeeded(savedFlag);
        favFlag = CommonFunctionsUtil.converStringAsNullIfNeeded(favFlag);
        dealFlag = CommonFunctionsUtil.converStringAsNullIfNeeded(dealFlag);
        chainFlag = CommonFunctionsUtil.converStringAsNullIfNeeded(chainFlag);
        paginationId = CommonFunctionsUtil.converStringAsNullIfNeeded(paginationId);

        //        int searchType = TSConstants.DEFAULT_SEARCH_TYPE;

        // different cases identifications.
        //        if ((userId != null) && (restaurantId == null) &&
        //                (neighborhoodId == null) && (cityId != null) &&
        //                (priceIdList == null) && (rating == null) &&
        //                (savedFlag == null) && (favFlag == null) && (dealFlag == null) &&
        //                ((chainFlag != null) && "1".equals(chainFlag)) &&
        //                (cuisineTier2IdArray == null) && (themeIdArray == null) &&
        //                (whoareyouwithIdArray == null) &&
        //                (typeOfRestaurantIdArray == null) && (occasionIdArray == null)) {
        //            searchType = TSConstants.USER_CITY_SEARCH_TYPE;
        //        }

        //        searchType = TSConstants.DEFAULT_SEARCH_TYPE;
        RestaurantsSearchResultsVO restaurantsSearchResultsVO = userRestaurantDAO.showListOfRestaurantsSearchResults(tsDataSource,
                connection, userId, restaurantId, neighborhoodId, cityId,
                stateName, priceIdList, rating, savedFlag, favFlag, dealFlag,
                chainFlag, paginationId, cuisineTier2IdArray, themeIdArray,
                whoareyouwithIdArray, typeOfRestaurantIdArray, occasionIdArray);

        //        switch (searchType) {
        //        case TSConstants.USER_CITY_SEARCH_TYPE:
        //            restaurantsSearchResultsVO = userRestaurantDAO.showListOfRestaurantsSearchResultsBasedOnUserCity(tsDataSource, connection,userId,
        //                    cityId, paginationId);
        //
        //            break;
        //
        //        case TSConstants.DEFAULT_SEARCH_TYPE:
        //            restaurantsSearchResultsVO = userRestaurantDAO.showListOfRestaurantsSearchResults(tsDataSource, connection, userId,
        //                    restaurantId, neighborhoodId, cityId, stateName,
        //                    priceIdList, rating, savedFlag, favFlag, dealFlag,
        //                    chainFlag, paginationId, cuisineTier2IdArray, themeIdArray,
        //                    whoareyouwithIdArray, typeOfRestaurantIdArray,
        //                    occasionIdArray);
        //
        //            break;
        //
        //        default:
        //            restaurantsSearchResultsVO = userRestaurantDAO.showListOfRestaurantsSearchResults(tsDataSource, connection,userId,
        //                    restaurantId, neighborhoodId, cityId, stateName,
        //                    priceIdList, rating, savedFlag, favFlag, dealFlag,
        //                    chainFlag, paginationId, cuisineTier2IdArray, themeIdArray,
        //                    whoareyouwithIdArray, typeOfRestaurantIdArray,
        //                    occasionIdArray);
        //        }
        return restaurantsSearchResultsVO;
    }
}
