package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantUserVO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RestUserMatchCounterCalc {
    private UserRestaurantDAO userRestaurantDAO = new UserRestaurantDAOImpl();

    public void processAllFlaggedRestaurantListRestUserMatchCounter()
        throws TasteSyncException {
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

        //For each userId, multiple restaurant ids are associated!
        for (RestaurantUserVO flaggedRestaurantUserVO : allflaggedRestaurantUserList) {
            //String chainFlag = userRestaurantDAO.getRestaurantInfoChained(flaggedRestaurantUserVO.getRestaurantId());
            LinkedList<String> restaurantIdList = userRestaurantDAO.getConsolidatedFlaggedRestaurantForSingleUser(flaggedRestaurantUserVO);

            for (String restaurantId : restaurantIdList) {
                userRestaurantDAO.processRestUserMatchCounter(flaggedRestaurantUserVO.getUserId(),
                    restaurantId);
            }
        }

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantReplyUserList) {
            userRestaurantDAO.submitRecorrequestReplyUser(restaurantUserVO.getUserId(),
                0);
        }

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantTipsUserList) {
            userRestaurantDAO.submitRestaurantTipsTastesync(restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 0);
        }

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantFavUserList) {
            userRestaurantDAO.submitRestaurantFav(restaurantUserVO.getUserId(),
                restaurantUserVO.getRestaurantId(), 0);
        }
    }
}
