package com.tastesync.algo.user.user;

import com.tastesync.algo.db.dao.UserUserDAO;
import com.tastesync.algo.db.dao.UserUserDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.util.TSConstants;

import com.tastesync.db.pool.TSDataSource;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class UserTopicCalc {
    private UserUserDAO userUserDAO = new UserUserDAOImpl();

    public UserTopicCalc() {
        super();
    }

    public void processAllUserFlaggedUserListUserTopic()
        throws TasteSyncException, SQLException {
        TSDataSource tsDataSource = TSDataSource.getInstance();
        int algoIndicatorIdentifyUseridList = 2;
        List<RestaurantUserVO> recorequestReplyUserRestaurantUsersList = userUserDAO.getRecorequestReplyUserRestaurant(algoIndicatorIdentifyUseridList);
        algoIndicatorIdentifyUseridList = 2;

        List<RestaurantUserVO> restaurantTipsTastesyncUsersList = userUserDAO.getRestaurantTipsTastesync(algoIndicatorIdentifyUseridList);

        algoIndicatorIdentifyUseridList = 3;

        List<RestaurantUserVO> restaurantFavUsersList = userUserDAO.getUserRestaurantFav(algoIndicatorIdentifyUseridList, true);

        //Combine all flagged userId, restaurantId lists into one list.
        List<RestaurantUserVO> restaurantUsersList = new ArrayList<RestaurantUserVO>();

        for (RestaurantUserVO restaurantUserVO : recorequestReplyUserRestaurantUsersList) {
            restaurantUsersList.add(restaurantUserVO);
        }

        for (RestaurantUserVO restaurantUserVO : restaurantTipsTastesyncUsersList) {
            restaurantUsersList.add(restaurantUserVO);
        }

        for (RestaurantUserVO restaurantUserVO : restaurantFavUsersList) {
            restaurantUsersList.add(restaurantUserVO);
        }

        tsDataSource.begin();

        for (RestaurantUserVO flaggedRestaurantUserVO : restaurantUsersList) {
            String chainFlag = userUserDAO.getRestaurantInfoChained(flaggedRestaurantUserVO.getRestaurantId());

            if ("0".equals(chainFlag)) {
                userUserDAO.submitUserCityNbrHoodAndCusineTier2Match(flaggedRestaurantUserVO);
            }
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (RestaurantUserVO restaurantUserVO : recorequestReplyUserRestaurantUsersList) {
            ArrayList<String> userIdListDone = new ArrayList<String>();

            if (!userIdListDone.contains(restaurantUserVO.getUserId())) {
                userUserDAO.submitRecorrequestReplyUserAlgo1(restaurantUserVO.getUserId(),
                    1);
                userIdListDone.add(restaurantUserVO.getUserId());
            }
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (RestaurantUserVO restaurantUserVO : restaurantTipsTastesyncUsersList) {
            userUserDAO.submitRestaurantTipsTastesyncAlgo1(restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 1);
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (RestaurantUserVO restaurantUserVO : restaurantFavUsersList) {
            userUserDAO.submitRestaurantFav(restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 2, TSConstants.ALGO_TYPE.ALGO1);
        }

        tsDataSource.commit();
        tsDataSource.begin();
    }
}
