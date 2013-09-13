package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.db.dao.UserRestaurantDAO;
import com.tastesync.algo.db.dao.UserRestaurantDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantUserVO;

import java.util.ArrayList;
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

        List<RestaurantUserVO> allflaggedRestaurantFavUserList = new ArrayList<RestaurantUserVO>(flaggedRestaurantReplyUserList.size() +
                flaggedRestaurantTipsUserList.size() +
                flaggedRestaurantFavUserList.size());

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantReplyUserList) {
            allflaggedRestaurantFavUserList.add(restaurantUserVO);
        }

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantTipsUserList) {
            allflaggedRestaurantFavUserList.add(restaurantUserVO);
        }

        for (RestaurantUserVO restaurantUserVO : flaggedRestaurantFavUserList) {
            allflaggedRestaurantFavUserList.add(restaurantUserVO);
        }
    }
}
