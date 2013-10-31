package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantCityVO;
import com.tastesync.db.pool.TSDataSource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class RestInfoPopularityTierCalc {
    private UserRestaurantDAO userRestaurantDAO = new UserRestaurantDAOImpl();

    public RestInfoPopularityTierCalc() {
		super();
	}

	// get restaurant if based on alog_ind
    //TODO need to be updated/reset whenever data from factual is updated
    public void processAllFlaggedRestaurantListRestInfoPopularityTier()
        throws TasteSyncException, SQLException {
    	TSDataSource tsDataSource = TSDataSource.getInstance();
        int algoIndicatorIdentifyRestaurantIdList = 1;
        List<RestaurantCityVO> recorequestUserFlaggedUserList = userRestaurantDAO.getFlaggedRestaurantList(algoIndicatorIdentifyRestaurantIdList);
        List<String> cityIdList = userRestaurantDAO.getFlaggedCityIdList(algoIndicatorIdentifyRestaurantIdList);
        int MAX_NUMBER_CITY_COVERAGE = 20;

        if (cityIdList.size() > MAX_NUMBER_CITY_COVERAGE) {
            throw new TasteSyncException(" covers more cities" +
                cityIdList.size() + " than the number= " +
                MAX_NUMBER_CITY_COVERAGE + " exected cities!! ");
        }

        HashMap<String, Integer> cityMedianHashMap = new HashMap<String, Integer>(cityIdList.size());
        int medianValueForSingleCityId = 0;

        for (String cityId : cityIdList) {
            medianValueForSingleCityId = userRestaurantDAO.getMedianvalueForSingleCityIdList(cityId);
            cityMedianHashMap.put(cityId,
                    medianValueForSingleCityId);
        }

        int algoIndicatorDone = 0;
        int medianUsersNumberForCity = 0;

        for (RestaurantCityVO restaurantCityVO : recorequestUserFlaggedUserList) {
            if (!cityMedianHashMap.containsKey(restaurantCityVO.getCityId())) {
                throw new TasteSyncException(
                    "Unknown median value for the city id " +
                    restaurantCityVO.getCityId());
            }

            medianUsersNumberForCity = cityMedianHashMap.get(restaurantCityVO.getCityId());
            tsDataSource.begin();
            userRestaurantDAO.processSingleRestaurantIdCalc(restaurantCityVO.getRestaurantId(),
                medianUsersNumberForCity);
            tsDataSource.commit();
            tsDataSource.begin();
            userRestaurantDAO.submitFlaggedRestaurant(restaurantCityVO.getRestaurantId(),
                algoIndicatorDone);
            tsDataSource.commit();
        }
    }
}
