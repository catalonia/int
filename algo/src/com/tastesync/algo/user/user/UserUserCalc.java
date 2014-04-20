package com.tastesync.algo.user.user;

import com.tastesync.algo.db.dao.UserUserDAO;
import com.tastesync.algo.db.dao.UserUserDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantNeighbourhoodVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.model.vo.UserAUserBVO;
import com.tastesync.algo.model.vo.UserFolloweeUserFollowerVO;
import com.tastesync.algo.util.TSConstants;

import com.tastesync.db.pool.TSDataSource;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class UserUserCalc {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(UserUserCalc.class);
    private UserUserDAO userUserDAO = new UserUserDAOImpl();
    private boolean printDebugExtra = false;

    public UserUserCalc() {
        super();
    }

    public void processAllUserFlaggedUserListUserUser(
        TSDataSource tsDataSource, Connection connection)
        throws TasteSyncException, SQLException {
        // get pair of userA and userB
        // remove duplicates
        // remove where userA /UserB is same as UserB/userA
        //final list
        int algoIndicatorIdentifyUseridList = 2;

        //userA list
        List<RestaurantUserVO> restaurantFavUsersList = userUserDAO.getUserRestaurantFav(tsDataSource,
                connection, algoIndicatorIdentifyUseridList, false);

        List<String> userAList = new ArrayList<String>(restaurantFavUsersList.size());

        for (RestaurantUserVO userARestaurantFavUsers : restaurantFavUsersList) {
            if (!userAList.contains(userARestaurantFavUsers.getUserId())) {
                userAList.add(userARestaurantFavUsers.getUserId());
            }
        }

        //userA + userB list
        List<String> userBothBandAList = userUserDAO.getAllUsers(tsDataSource,
                connection);

        //        List<String> userBList = new ArrayList<String>(usersIdAllList.size());
        //
        //        for (String userB : usersIdAllList) {
        //            if (!userAList.contains(userB)) {
        //                if (!userBList.contains(userB)) {
        //                    userBList.add(userB);
        //                }
        //            }
        //        }

        // add user A and user B pair to a file
        List<UserAUserBVO> userAUserBVOList = new ArrayList<UserAUserBVO>(userAList.size() * userBothBandAList.size());
        UserAUserBVO userAUserBVO = null;

        // create userA userB pair
        for (String userA : userAList) {
            for (String userB : userBothBandAList) {
                if (!userA.equals(userB)) {
                    userAUserBVO = new UserAUserBVO(userA, userB);

                    if (!userAUserBVOList.contains(userAUserBVO)) {
                        userAUserBVOList.add(userAUserBVO);
                    } else {
                        if (printDebugExtra) {
                            if (logger.isInfoEnabled()) {
                                logger.info("Pair UserA/UserB already found. " +
                                    userAUserBVO.toString());
                            }
                        }
                    }
                }
            }
        }

        // reset list to null
        userAList = null;
        userBothBandAList = null;

        //  create A and B pairs
        algoIndicatorIdentifyUseridList = 1;

        List<UserFolloweeUserFollowerVO> userFolloweeUserFollowerVOList = userUserDAO.getUserFolloweeUserFollowerFollowData(tsDataSource,
                connection, algoIndicatorIdentifyUseridList);

        for (UserFolloweeUserFollowerVO userFolloweeUserFollowerVOElement : userFolloweeUserFollowerVOList) {
            userAUserBVO = new UserAUserBVO(userFolloweeUserFollowerVOElement.getUserFollower(),
                    userFolloweeUserFollowerVOElement.getUserFollowee());

            if (!userAUserBVOList.contains(userAUserBVO)) {
                userAUserBVOList.add(userAUserBVO);
            } else {
                if (printDebugExtra) {
                    if (logger.isDebugEnabled()) {
                        logger.info(
                            "From Follow data. - Pair UserA/UserB already found. " +
                            userAUserBVO.toString());
                    }
                }
            }
        }

        // reset list to null
        userFolloweeUserFollowerVOList = null;

        if (printDebugExtra) {
            if (logger.isDebugEnabled()) {
                logger.debug("Number of pairs UserA/UserB found. " +
                    userAUserBVOList.size());
            }
        }

        //pair are available
        for (UserAUserBVO userAUserBVOValue : userAUserBVOList) {
            double userAFollowUserB = userUserDAO.getUserFollowerFirstFollowingUserFolloweeTwo(tsDataSource,
                    connection, userAUserBVOValue.getUserB(),
                    userAUserBVOValue.getUserA());

            double userBFollowUserA = userUserDAO.getUserFollowerFirstFollowingUserFolloweeTwo(tsDataSource,
                    connection, userAUserBVOValue.getUserA(),
                    userAUserBVOValue.getUserB());

            //int numUserAFavNCRest = userUserDAO.getUserXFavNCRest(userAUserBVOValue.getUserA());
            List<String> userAFavNCRest = userUserDAO.getUserXFavNCRest(tsDataSource,
                    connection, userAUserBVOValue.getUserA());
            double numUserAFavNCRest = userAFavNCRest.size();

            List<String> userBFavNCRest = userUserDAO.getUserXFavNCRest(tsDataSource,
                    connection, userAUserBVOValue.getUserB());

            double numUserBFavNCRest = userBFavNCRest.size();

            // find the common
            double numCommonNCFavRest = 0;

            for (String userAFavNCRestElement : userAFavNCRest) {
                if (userBFavNCRest.contains(userAFavNCRestElement)) {
                    ++numCommonNCFavRest;
                }
            }

            double minNumUserABFavNCRest = 0;

            if (numUserAFavNCRest < numUserBFavNCRest) {
                minNumUserABFavNCRest = numUserAFavNCRest;
            } else {
                minNumUserABFavNCRest = numUserBFavNCRest;
            }

            List<RestaurantNeighbourhoodVO> userAfavNonChainRestNbrhoodIdList = new ArrayList<RestaurantNeighbourhoodVO>((int) numUserAFavNCRest);

            for (String userAFavNCRestElement : userAFavNCRest) {
                userAfavNonChainRestNbrhoodIdList.add(userUserDAO.getRestaurantNeighbourhoodList(
                        tsDataSource, connection, userAFavNCRestElement));
            }

            List<RestaurantNeighbourhoodVO> userBfavNonChainRestNbrhoodIdList = new ArrayList<RestaurantNeighbourhoodVO>((int) numUserBFavNCRest);

            for (String userBFavNCRestElement : userBFavNCRest) {
                userBfavNonChainRestNbrhoodIdList.add(userUserDAO.getRestaurantNeighbourhoodList(
                        tsDataSource, connection, userBFavNCRestElement));
            }

            double numCommonNbrhoodNCFavRest = 0;
            boolean commonNbrHoodFound = false;

            for (RestaurantNeighbourhoodVO userArestaurantNeighbourhoodVOElement : userAfavNonChainRestNbrhoodIdList) {
                commonNbrHoodFound = false;

                for (String userANeighbourhoodId : userArestaurantNeighbourhoodVOElement.getNeighbourhoodIdList()) {
                    //if this userANeighbourhoodId present in any of userBNeighbourhoodId, increment and breaks!
                    for (RestaurantNeighbourhoodVO userBestaurantNeighbourhoodVOElement : userBfavNonChainRestNbrhoodIdList) {
                        if (userBestaurantNeighbourhoodVOElement.getNeighbourhoodIdList()
                                                                    .contains(userANeighbourhoodId)) {
                            ++numCommonNbrhoodNCFavRest;
                            commonNbrHoodFound = true;

                            break;
                        }
                    }

                    if (commonNbrHoodFound) {
                        break;
                    }
                }
            }

            List<String> userAfavNonChainRestClusterId = new ArrayList<String>();

            double numUserAFavNCRestClusters = 0;

            for (String userAFavNCRestElement : userAFavNCRest) {
                userAfavNonChainRestClusterId = userUserDAO.getRestaurantClusterIdList(tsDataSource,
                        connection, userAFavNCRestElement);
                numUserAFavNCRestClusters = numUserAFavNCRestClusters +
                    userAfavNonChainRestClusterId.size();
            }

            List<String> userBfavNonChainRestClusterId = new ArrayList<String>();
            double numUserBFavNCRestClusters = 0;

            for (String userBFavNCRestElement : userBFavNCRest) {
                userBfavNonChainRestClusterId = userUserDAO.getRestaurantClusterIdList(tsDataSource,
                        connection, userBFavNCRestElement);
                numUserBFavNCRestClusters = numUserBFavNCRestClusters +
                    userBfavNonChainRestClusterId.size();
            }

            double minNumUserABFavNCRestClusters = 0;

            if (numUserAFavNCRestClusters < numUserBFavNCRestClusters) {
                minNumUserABFavNCRestClusters = numUserAFavNCRestClusters;
            } else {
                minNumUserABFavNCRestClusters = numUserBFavNCRestClusters;
            }

            double numCommonNCFavRestClusters = 0;

            for (String userAfavNonChainRestClusterIdElement : userAfavNonChainRestClusterId) {
                if ((userBfavNonChainRestClusterId != null) &&
                        userBfavNonChainRestClusterId.contains(
                            userAfavNonChainRestClusterIdElement)) {
                    ++numCommonNCFavRestClusters;
                }
            }

            List<String> userAFavNativeCuisIdList = userUserDAO.getUserCuisineIdList(tsDataSource,
                    connection, userAUserBVOValue.getUserA());
            double numUserAFavNativeCuisines = userAFavNativeCuisIdList.size();

            List<String> userBFavNativeCuisIdList = userUserDAO.getUserCuisineIdList(tsDataSource,
                    connection, userAUserBVOValue.getUserB());

            double numUserBFavNativeCuisines = userBFavNativeCuisIdList.size();

            double numCommonFavNativeCuisines = 0;

            for (String userAFavNativeCuisId : userAFavNativeCuisIdList) {
                if (userBFavNativeCuisIdList.contains(userAFavNativeCuisId)) {
                    ++numCommonFavNativeCuisines;
                }
            }

            List<String> userAFavChainRestaurantIdList = userUserDAO.getUserXFavCRest(tsDataSource,
                    connection, userAUserBVOValue.getUserA());
            double numUserAFavChainRest = userAFavChainRestaurantIdList.size();

            List<String> userBFavChainRestaurantIdList = userUserDAO.getUserXFavCRest(tsDataSource,
                    connection, userAUserBVOValue.getUserB());
            double numUserBFavChainRest = userBFavChainRestaurantIdList.size();

            double numUserAFavNCPopTier1Rest = userUserDAO.getNumUserFavNvTierNRestaurant(tsDataSource,
                    connection, userAUserBVOValue.getUserA(), 1);
            double numUserBFavNCPopTier1Rest = userUserDAO.getNumUserFavNvTierNRestaurant(tsDataSource,
                    connection, userAUserBVOValue.getUserB(), 1);

            if (printDebugExtra) {
                System.out.println("userAFollowUserB=" + userAFollowUserB +
                    " userBFollowUserA=" + userBFollowUserA +
                    " numCommonNCFavRest=" + numCommonNCFavRest +
                    " minNumUserABFavNCRest=" + minNumUserABFavNCRest +
                    " (numCommonNCFavRest / minNumUserABFavNCRest)=" +
                    (numCommonNCFavRest / minNumUserABFavNCRest) +
                    "numCommonNbrhoodNCFavRest=" + numCommonNbrhoodNCFavRest +
                    "minNumUserABFavNCRest=" + minNumUserABFavNCRest +
                    "numCommonNbrhoodNCFavRest / minNumUserABFavNCRest)=" +
                    (numCommonNbrhoodNCFavRest / minNumUserABFavNCRest) +
                    " numCommonNCFavRestClusters=" +
                    numCommonNCFavRestClusters +
                    " minNumUserABFavNCRestClusters=" +
                    minNumUserABFavNCRestClusters +
                    " (numCommonNCFavRestClusters / minNumUserABFavNCRestClusters)=" +
                    (numCommonNCFavRestClusters / minNumUserABFavNCRestClusters));
            }

            // -- Tier 1 logic
            if ((userAFollowUserB == 1) || (userBFollowUserA == 1) ||
                    ((numCommonNCFavRest / minNumUserABFavNCRest) >= 0.5) ||
                    ((numCommonNbrhoodNCFavRest / minNumUserABFavNCRest) >= 0.7) ||
                    ((numCommonNCFavRestClusters / minNumUserABFavNCRestClusters) == 1.0)) {
                userUserDAO.submitAssignedUserUserMatchTier(tsDataSource,
                    connection, userAUserBVOValue.getUserA(),
                    userAUserBVOValue.getUserB(), 1);
                userUserDAO.submitAssignedUserUserMatchTier(tsDataSource,
                    connection, userAUserBVOValue.getUserB(),
                    userAUserBVOValue.getUserA(), 1);
            } else {
                // -- Tier 2 logic
                if ((numCommonNCFavRestClusters / minNumUserABFavNCRestClusters) >= 0.7) {
                    userUserDAO.submitAssignedUserUserMatchTier(tsDataSource,
                        connection, userAUserBVOValue.getUserA(),
                        userAUserBVOValue.getUserB(), 2);
                    userUserDAO.submitAssignedUserUserMatchTier(tsDataSource,
                        connection, userAUserBVOValue.getUserB(),
                        userAUserBVOValue.getUserA(), 2);
                } else {
                    // -- Tier 3 logic TODO
                    if ((numCommonFavNativeCuisines >= 1) ||
                            (((numUserAFavChainRest / (numUserAFavNCRest +
                            numUserAFavChainRest)) >= 0.5) &&
                            ((numUserBFavChainRest / (numUserBFavNCRest +
                            numUserBFavChainRest)) >= 0.5)) ||
                            ((numUserAFavNCPopTier1Rest >= (0.7 * numUserAFavNCRest)) &&
                            (numUserBFavNCPopTier1Rest >= (0.7 * numUserBFavNCRest)))) {
                        userUserDAO.submitAssignedUserUserMatchTier(tsDataSource,
                            connection, userAUserBVOValue.getUserA(),
                            userAUserBVOValue.getUserB(), 3);
                        userUserDAO.submitAssignedUserUserMatchTier(tsDataSource,
                            connection, userAUserBVOValue.getUserB(),
                            userAUserBVOValue.getUserA(), 3);
                    } else {
                        userUserDAO.submitAssignedUserUserMatchTier(tsDataSource,
                            connection, userAUserBVOValue.getUserA(),
                            userAUserBVOValue.getUserB(), 4);
                        userUserDAO.submitAssignedUserUserMatchTier(tsDataSource,
                            connection, userAUserBVOValue.getUserB(),
                            userAUserBVOValue.getUserA(), 4);
                    }
                }
            }

            //TODO
            //USER_RESTAURANT_FAV_UPDATE_SQL shud have a different algo_indicator? 
            userUserDAO.submitRestaurantFav(tsDataSource, connection,
                userAUserBVOValue.getUserA(), null, 0,
                TSConstants.ALGO_TYPE.ALGO1);
            userUserDAO.submitUserFollowDataUpdate(tsDataSource, connection,
                userAUserBVOValue.getUserA(), userAUserBVOValue.getUserB(), 0);
            //reset list to null!
            userAFavNCRest = null;
            userBFavNCRest = null;
            userAfavNonChainRestNbrhoodIdList = null;
            userBfavNonChainRestNbrhoodIdList = null;
            userAfavNonChainRestClusterId = null;
            userBfavNonChainRestClusterId = null;
            userAFavNativeCuisIdList = null;
            userBFavNativeCuisIdList = null;
            userAFavChainRestaurantIdList = null;
            userBFavChainRestaurantIdList = null;
        }

        //reset to null
        userAUserBVOList = null;
    }
}
