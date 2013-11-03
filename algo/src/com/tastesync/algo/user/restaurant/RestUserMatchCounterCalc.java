package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantPopularityTierVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.util.TSConstants;

import com.tastesync.db.pool.TSDataSource;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RestUserMatchCounterCalc {
    private UserRestaurantDAO userRestaurantDAO = new UserRestaurantDAOImpl();

    public RestUserMatchCounterCalc() {
        super();
    }

    public void processAllFlaggedRestaurantListRestUserMatchCounter()
        throws TasteSyncException, SQLException {
        TSDataSource tsDataSource = TSDataSource.getInstance();
        int algoIndicator = 1;
        List<RestaurantUserVO> flaggedRestaurantReplyUserList = userRestaurantDAO.getFlaggedRestaurantReplyUserList(algoIndicator);
        algoIndicator = 1;

        List<RestaurantUserVO> flaggedRestaurantTipsUserList = userRestaurantDAO.getFlaggedRestaurantTipsUserList(algoIndicator);
        algoIndicator = 1;

        List<RestaurantUserVO> flaggedRestaurantFavUserList = userRestaurantDAO.getFlaggedRestaurantFavUserList(algoIndicator);

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

        for (RestaurantUserVO flaggedRestaurantUserVO : allflaggedRestaurantUserList) {
            //String chainFlag = userRestaurantDAO.getRestaurantInfoChained(flaggedRestaurantUserVO.getRestaurantId());
            LinkedList<RestaurantPopularityTierVO> restaurantPopularityTierVOList =
                userRestaurantDAO.getConsolidatedFlaggedRestaurantForSingleUser(flaggedRestaurantUserVO);

            for (RestaurantPopularityTierVO aRestaurantPopularityTierVOList : restaurantPopularityTierVOList) {
                numUserRestaurantMatchCount = userRestaurantDAO.getRestUserMatchCounter(flaggedRestaurantUserVO.getUserId(),
                        aRestaurantPopularityTierVOList);
                //set numUserRestaurantMatchCount
                aRestaurantPopularityTierVOList.setNumUserRestaurantMatchCount(String.valueOf(
                        numUserRestaurantMatchCount));
                // set userId
                aRestaurantPopularityTierVOList.setUserId(flaggedRestaurantUserVO.getUserId());
            }

            List<RestaurantPopularityTierVO> list1ofrestaurants = rankRestaurantsSingleUserCalcHelper.personalisedRestaurantsResultsForSingleUser(restaurantPopularityTierVOList);
            tsDataSource.begin();
            // final insert
            userRestaurantDAO.submitAssignedRankUserRestaurantForWhole(list1ofrestaurants);
            tsDataSource.commit();
        }

        tsDataSource.begin();

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantReplyUserList) {
            userRestaurantDAO.submitRecorrequestReplyUserAlgo2(restaurantUserVO.getUserId(),
                0);
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantTipsUserList) {
            userRestaurantDAO.submitRestaurantTipsTastesyncAlgo2(restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 0);
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantFavUserList) {
            userRestaurantDAO.submitRestaurantFav(restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 0, TSConstants.ALGO_TYPE.ALGO2);
        }

        tsDataSource.commit();
    }
}
