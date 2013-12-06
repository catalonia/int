package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantPopularityTierVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.util.TSConstants;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RestUserMatchCounterCalc {
    private static final boolean printExtraDebug = false;
    private UserRestaurantDAO userRestaurantDAO = new UserRestaurantDAOImpl();

    public RestUserMatchCounterCalc() {
        super();
    }

    public void processAllFlaggedRestaurantListRestUserMatchCounter(
        TSDataSource tsDataSource, Connection connection)
        throws TasteSyncException, SQLException {
        int algoIndicator = 1;
        List<RestaurantUserVO> flaggedRestaurantReplyUserList = userRestaurantDAO.getFlaggedRestaurantReplyUserList(tsDataSource,
                connection, algoIndicator);
        algoIndicator = 1;

        List<RestaurantUserVO> flaggedRestaurantTipsUserList = userRestaurantDAO.getFlaggedRestaurantTipsUserList(tsDataSource,
                connection, algoIndicator);
        algoIndicator = 1;

        List<RestaurantUserVO> flaggedRestaurantFavUserList = userRestaurantDAO.getFlaggedRestaurantFavUserList(tsDataSource,
                connection, algoIndicator);

        List<RestaurantUserVO> allflaggedRestaurantUserList = new ArrayList<RestaurantUserVO>(flaggedRestaurantReplyUserList.size() +
                flaggedRestaurantTipsUserList.size() +
                flaggedRestaurantFavUserList.size());

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantReplyUserList) {
            allflaggedRestaurantUserList.add(restaurantUserVO);
        }

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantTipsUserList) {
            allflaggedRestaurantUserList.add(restaurantUserVO);
        }

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantFavUserList) {
            allflaggedRestaurantUserList.add(restaurantUserVO);
        }

        RankRestaurantsSingleUserCalcHelper rankRestaurantsSingleUserCalcHelper = new RankRestaurantsSingleUserCalcHelper();

        //For each userId, multiple restaurant ids are associated!
        int numUserRestaurantMatchCount = 0;
        tsDataSource.begin();

        for (RestaurantUserVO flaggedRestaurantUserVO : allflaggedRestaurantUserList) {
            //String chainFlag = userRestaurantDAO.getRestaurantInfoChained(flaggedRestaurantUserVO.getRestaurantId());
            LinkedList<RestaurantPopularityTierVO> restaurantPopularityTierVOList =
                userRestaurantDAO.getConsolidatedFlaggedRestaurantForSingleUser(tsDataSource,
                    connection, flaggedRestaurantUserVO);

            int counter = 0;
            ArrayList<String> restaurantIdFromRestaurantPopularityTierVOList = new ArrayList<String>(restaurantPopularityTierVOList.size());

            for (RestaurantPopularityTierVO aRestaurantPopularityTierVOList : restaurantPopularityTierVOList) {
                numUserRestaurantMatchCount = userRestaurantDAO.getRestUserMatchCounter(tsDataSource,
                        connection, flaggedRestaurantUserVO.getUserId(),
                        aRestaurantPopularityTierVOList);
                //set numUserRestaurantMatchCount
                aRestaurantPopularityTierVOList.setNumUserRestaurantMatchCount(String.valueOf(
                        numUserRestaurantMatchCount));
                // set userId
                aRestaurantPopularityTierVOList.setUserId(flaggedRestaurantUserVO.getUserId());
                ++counter;

                if (printExtraDebug) {
                    System.out.println("counter=" + counter +
                        " aRestaurantPopularityTierVOList" +
                        aRestaurantPopularityTierVOList);
                }

                restaurantIdFromRestaurantPopularityTierVOList.add(aRestaurantPopularityTierVOList.getRestaurantId());
            }

            ArrayList<RestaurantPopularityTierVO> existingRestaurantPopularityTierVOList =
                userRestaurantDAO.getExistingConsolidatedFlaggedRestaurantForSingleUser(tsDataSource,
                    connection, flaggedRestaurantUserVO.getUserId());

            for (RestaurantPopularityTierVO existingRestaurantPopularityTierVO : existingRestaurantPopularityTierVOList) {
                if (!restaurantIdFromRestaurantPopularityTierVOList.contains(
                            existingRestaurantPopularityTierVO.getRestaurantId())) {
                    restaurantPopularityTierVOList.add(existingRestaurantPopularityTierVO);
                }
            }

            List<RestaurantPopularityTierVO> list1ofrestaurants = rankRestaurantsSingleUserCalcHelper.personalisedRestaurantsResultsForSingleUser(restaurantPopularityTierVOList);
            // final insert
            userRestaurantDAO.submitAssignedRankUserRestaurantForWhole(tsDataSource,
                connection, list1ofrestaurants,
                flaggedRestaurantUserVO.getUserId());
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantReplyUserList) {
            userRestaurantDAO.submitRecorrequestReplyUserAlgo2(tsDataSource,
                connection, restaurantUserVO.getUserId(), 0);
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantTipsUserList) {
            userRestaurantDAO.submitRestaurantTipsTastesyncAlgo2(tsDataSource,
                connection, restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 0);
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantFavUserList) {
            userRestaurantDAO.submitRestaurantFav(tsDataSource, connection,
                restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 0,
                TSConstants.ALGO_TYPE.ALGO2);
        }

        tsDataSource.commit();
    }
}
