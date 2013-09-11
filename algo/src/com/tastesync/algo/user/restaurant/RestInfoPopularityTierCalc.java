package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;

import java.util.List;


public class RestInfoPopularityTierCalc {
    private UserRestaurantDAO userRestaurantDAO = new UserRestaurantDAOImpl();

    // get restaurant if based on alog_ind
    //TODO need to be updated/reset whenever data from factual is updated
    public void processAllFlaggedRestaurantListRestInfoPopularityTier()
        throws TasteSyncException {
        int algoIndicatorIdentifyRestaurantIdList = 1;
        List<String> recorequestUserFlaggedUserList = userRestaurantDAO.getFlaggedRestaurantList(algoIndicatorIdentifyRestaurantIdList);
        int algoIndicatorDone = 0;

        for (String restaurantId : recorequestUserFlaggedUserList) {
            userRestaurantDAO.processSingleRestaurantIdCalc(restaurantId);

            userRestaurantDAO.submitFlaggedRestaurant(restaurantId,
                algoIndicatorDone);
        }
    }
}
