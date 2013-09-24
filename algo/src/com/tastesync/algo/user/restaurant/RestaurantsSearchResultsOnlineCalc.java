package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantsSearchResultsVO;


public class RestaurantsSearchResultsOnlineCalc {
    private UserRestaurantDAO userRestaurantDAO = new UserRestaurantDAOImpl();

    public RestaurantsSearchResultsOnlineCalc() {
        super();
    }

    // return list of restaurantIds based on different parameters
    public RestaurantsSearchResultsVO showListOfRestaurantsSearchResults(
        String userId, String restaurantId, String neighborhoodId,
        String cityId, String stateName, String[] cuisineTier1IdArray,
        String[] priceIdList, String rating, String savedFlag, String favFlag,
        String dealFlag, String chainFlag, String paginationId,
        String[] cuisineTier2IdArray, String[] themeIdArray,
        String[] whoareyouwithIdArray, String[] typeOfRestaurantIdArray,
        String[] occasionIdArray) throws TasteSyncException {
        return userRestaurantDAO.showListOfRestaurantsSearchResults(userId,
            restaurantId, neighborhoodId, cityId, stateName,
            cuisineTier1IdArray, priceIdList, rating, savedFlag, favFlag,
            dealFlag, chainFlag, paginationId, cuisineTier2IdArray,
            themeIdArray, whoareyouwithIdArray, typeOfRestaurantIdArray,
            occasionIdArray);
    }
}
