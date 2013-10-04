package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantCityVO;
import com.tastesync.algo.model.vo.RestaurantPopularityTierVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class UserRestRankOrderCalc {
    private UserRestaurantDAO userRestaurantDAO = new UserRestaurantDAOImpl();

    public void updateUserRestRankOrderCalc() throws TasteSyncException {
        int algoIndicatorIdentifyRestaurantIdList = 0;
        List<RestaurantCityVO> flaggedRestaurantList = userRestaurantDAO.getFlaggedRestaurantList(algoIndicatorIdentifyRestaurantIdList);
        List<String> usersIdList = userRestaurantDAO.getAllUsers();

        RankRestaurantsSingleUserCalcHelper rankRestaurantsSingleUserCalcHelper = new RankRestaurantsSingleUserCalcHelper();
        RestaurantUserVO restaurantUserVO = null;
        String numUserRestaurantMatchCount = null;
        String restaurantTier = null;

        for (String userId : usersIdList) {
            List<RestaurantUserVO> restaurantUserVOList = new ArrayList<RestaurantUserVO>(flaggedRestaurantList.size());

            //get userid-restaurant id list for each user
            for (RestaurantCityVO restaurantCityVO : flaggedRestaurantList) {
                restaurantUserVO = new RestaurantUserVO(userId,
                        restaurantCityVO.getRestaurantId());

                restaurantUserVOList.add(restaurantUserVO);
            }

            for (RestaurantUserVO flaggedRestaurantUserVO : restaurantUserVOList) {
                LinkedList<RestaurantPopularityTierVO> restaurantPopularityTierVOList =
                    userRestaurantDAO.getConsolidatedFlaggedRestaurantForSingleUser(flaggedRestaurantUserVO);

                //numUserRestaurantMatchCount restaurantTier
                for (RestaurantPopularityTierVO aRestaurantPopularityTierVOList : restaurantPopularityTierVOList) {
                    numUserRestaurantMatchCount = String.valueOf(userRestaurantDAO.getUserMatchCounter(
                            userId,
                            flaggedRestaurantUserVO.getRestaurantId()));
                    aRestaurantPopularityTierVOList
                            .setNumUserRestaurantMatchCount(numUserRestaurantMatchCount);
                    restaurantTier = String.valueOf(userRestaurantDAO.getRestaurantInfoTierId(
                            userId,
                            flaggedRestaurantUserVO.getRestaurantId()));
                    aRestaurantPopularityTierVOList
                            .setPopularityTierId(restaurantTier);
                    aRestaurantPopularityTierVOList.setUserId(userId);
                }

                // check numUserRestaurantMatchCount
                List<RestaurantPopularityTierVO> list1ofrestaurants = rankRestaurantsSingleUserCalcHelper.personalisedRestaurantsResultsForSingleUser(restaurantPopularityTierVOList);

                // final insert
                userRestaurantDAO.submitAssignedRankUserRestaurant(list1ofrestaurants);

                userRestaurantDAO.submitFlaggedRestaurant(flaggedRestaurantUserVO.getRestaurantId(),
                    -1);
            }
        }
    }
}
