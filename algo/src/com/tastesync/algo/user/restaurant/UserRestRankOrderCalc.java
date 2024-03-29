package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantCityVO;
import com.tastesync.algo.model.vo.RestaurantPopularityTierVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class UserRestRankOrderCalc {
    private UserRestaurantDAO userRestaurantDAO = new UserRestaurantDAOImpl();

    public void updateUserRestRankOrderCalc(TSDataSource tsDataSource,
        Connection connection) throws TasteSyncException {
        int algoIndicatorIdentifyRestaurantIdList = 0;
        List<RestaurantCityVO> flaggedRestaurantList = userRestaurantDAO.getFlaggedRestaurantList(tsDataSource,
                connection, algoIndicatorIdentifyRestaurantIdList);

        RankRestaurantsSingleUserCalcHelper rankRestaurantsSingleUserCalcHelper = new RankRestaurantsSingleUserCalcHelper();
        RestaurantUserVO restaurantUserVO = null;
        int numUserRestaurantMatchCount = 0;
        int restaurantTier = 0;

        if ((flaggedRestaurantList != null) &&
                (flaggedRestaurantList.size() != 0)) {
            List<String> usersIdList = userRestaurantDAO.getAllUsers(tsDataSource,
                    connection);

            for (String userId : usersIdList) {
                List<RestaurantUserVO> restaurantUserVOList = new ArrayList<RestaurantUserVO>(flaggedRestaurantList.size());

                //get userid-restaurant id list for each user
                for (RestaurantCityVO restaurantCityVO : flaggedRestaurantList) {
                    restaurantUserVO = new RestaurantUserVO(userId,
                            restaurantCityVO.getRestaurantId());

                    restaurantUserVOList.add(restaurantUserVO);
                }

                for (RestaurantUserVO flaggedRestaurantUserVO : restaurantUserVOList) {
                    //NOT Needed anymore

                    //                    LinkedList<RestaurantPopularityTierVO> restaurantPopularityTierVOList =
                    //                        userRestaurantDAO.getConsolidatedFlaggedRestaurantForSingleUser(tsDataSource,
                    //                            connection, flaggedRestaurantUserVO);
                    //
                    //                    //numUserRestaurantMatchCount restaurantTier
                    //                    for (RestaurantPopularityTierVO aRestaurantPopularityTierVOList : restaurantPopularityTierVOList) {
                    //                        numUserRestaurantMatchCount = userRestaurantDAO.getUserMatchCounter(
                    //                                    tsDataSource, connection, userId,
                    //                                    flaggedRestaurantUserVO.getRestaurantId());
                    //                        aRestaurantPopularityTierVOList.setNumUserRestaurantMatchCount(numUserRestaurantMatchCount);
                    //                        restaurantTier = userRestaurantDAO.getRestaurantInfoTierId(
                    //                                    tsDataSource, connection, userId,
                    //                                    flaggedRestaurantUserVO.getRestaurantId());
                    //                        aRestaurantPopularityTierVOList.setPopularityTierId(restaurantTier);
                    //                        aRestaurantPopularityTierVOList.setUserId(userId);
                    //                    }
                    //
                    //                    // check numUserRestaurantMatchCount
                    //                    List<RestaurantPopularityTierVO> list1ofrestaurants = rankRestaurantsSingleUserCalcHelper.personalisedRestaurantsResultsForSingleUser(restaurantPopularityTierVOList);
                    //
                    //                    // final insert
                    //                    userRestaurantDAO.submitAssignedRankUserRestaurant(tsDataSource,
                    //                        connection, list1ofrestaurants);
                    //                	
                    userRestaurantDAO.submitFlaggedRestaurant(tsDataSource,
                        connection, flaggedRestaurantUserVO.getRestaurantId(),
                        -1);
                }
            }
        }
    }
}
