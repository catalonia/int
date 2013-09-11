package com.tastesync.algo.user.user;

import com.tastesync.algo.db.dao.UserUserDAO;
import com.tastesync.algo.db.dao.UserUserDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantUserVO;

import java.util.ArrayList;
import java.util.List;


public class UserTopicCalc {
    private UserUserDAO userUserDAO = new UserUserDAOImpl();

    public UserTopicCalc() {
        super();
    }

    public void processAllUserFlaggedUserListUserTopic()
        throws TasteSyncException {
        int algoIndicatorIdentifyUseridList = 2;
        List<RestaurantUserVO> recorequestReplyUserRestaurantUsersList = userUserDAO.getRecorequestReplyUserRestaurant(algoIndicatorIdentifyUseridList);
        algoIndicatorIdentifyUseridList = 2;

        List<RestaurantUserVO> restaurantTipsTastesyncUsersList = userUserDAO.getRestaurantTipsTastesync(algoIndicatorIdentifyUseridList);

        algoIndicatorIdentifyUseridList = 3;

        List<RestaurantUserVO> restaurantFavUsersList = userUserDAO.getUserRestaurantFav(algoIndicatorIdentifyUseridList);

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

        for (RestaurantUserVO flaggedRestaurantUserVO : restaurantUsersList) {
            String chainFlag = userUserDAO.getRestaurantInfoChained(flaggedRestaurantUserVO.getRestaurantId());

            if ("0".equals(chainFlag)) {
                userUserDAO.submitUserCityNbrHoodAndCusineTier2Match(flaggedRestaurantUserVO);
            }
        }

        for (RestaurantUserVO restaurantUserVO : recorequestReplyUserRestaurantUsersList) {
            ArrayList<String> userIdListDone = new ArrayList<String>();

            if (!userIdListDone.contains(restaurantUserVO.getUserId())) {
                userUserDAO.submitRecorrequestReplyUser(restaurantUserVO.getUserId(),
                    1);
                userIdListDone.add(restaurantUserVO.getUserId());
            }
        }

        for (RestaurantUserVO restaurantUserVO : restaurantTipsTastesyncUsersList) {
            userUserDAO.submitRestaurantTipsTastesync(restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 1);
        }

        for (RestaurantUserVO restaurantUserVO : restaurantFavUsersList) {
            userUserDAO.submitRestaurantFav(restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 2);
        }
    }
}
