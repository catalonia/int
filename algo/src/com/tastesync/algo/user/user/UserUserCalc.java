package com.tastesync.algo.user.user;

import com.tastesync.algo.db.dao.UserUserDAO;
import com.tastesync.algo.db.dao.UserUserDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantNeighbourhoodVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.model.vo.UserAUserBVO;
import com.tastesync.algo.model.vo.UserFolloweeUserFollowerVO;

import java.util.ArrayList;
import java.util.List;


public class UserUserCalc {
    private UserUserDAO userUserDAO = new UserUserDAOImpl();

    public UserUserCalc() {
        super();
    }

    public void processAllUserFlaggedUserListUserUser()
        throws TasteSyncException {
        // get pair of userA and userB
        // remove duplicates
        // remove where userA /UserB is same as UserB/userA
        //final list
        int algoIndicatorIdentifyUseridList = 2;

        //userA list
        List<RestaurantUserVO> restaurantFavUsersList = userUserDAO.getUserRestaurantFav(algoIndicatorIdentifyUseridList);

        List<String> userAList = new ArrayList<String>(restaurantFavUsersList.size());

        for (String userA : userAList) {
            if (!userAList.contains(userA)) {
                userAList.add(userA);
            }
        }

        //userA + userB list
        List<String> usersIdAllList = userUserDAO.getAllUsers();

        List<String> userBList = new ArrayList<String>(usersIdAllList.size() -
                restaurantFavUsersList.size());

        for (String userB : usersIdAllList) {
            if (!userAList.contains(userB)) {
                if (!userBList.contains(userB)) {
                    userBList.add(userB);
                }
            }
        }

        
        // add user A and user B pair to a file
        List<UserAUserBVO> userAUserBVOList = new ArrayList<UserAUserBVO>(userAList.size()*userBList.size());
        UserAUserBVO userAUserBVO = null;
        // create userA userB pair
        for (String userA : userAList) {
            for (String userB : userBList) {
            	
                userAUserBVO = new UserAUserBVO(userA,
                		userB);
                if (!userAUserBVOList.contains(userAUserBVO)) {
                    userAUserBVOList.add(userAUserBVO);
                	
                } else {
                	System.out.println("Pair UserA/UserB already found. "+userAUserBVO.toString());
                }
            }        	
        }
        	
        // reset list to null
        userAList = null;
        userBList = null;
        
        // TODO create A and B pairs
        algoIndicatorIdentifyUseridList = 1;

        List<UserFolloweeUserFollowerVO> userFolloweeUserFollowerVOList = userUserDAO.getUserFolloweeUserFollowerFollowData(algoIndicatorIdentifyUseridList);

        for (UserFolloweeUserFollowerVO userFolloweeUserFollowerVOElement : userFolloweeUserFollowerVOList) {
            userAUserBVO = new UserAUserBVO(userFolloweeUserFollowerVOElement.getUserFollowee(),
                    userFolloweeUserFollowerVOElement.getUserFollower());
            if (!userAUserBVOList.contains(userAUserBVO)) {
                userAUserBVOList.add(userAUserBVO);
            } else {
            	System.out.println("From Follow data. - Pair UserA/UserB already found. "+userAUserBVO.toString());
            }
        }
        
        // reset list to null
        userFolloweeUserFollowerVOList= null;
        System.out.println("Number of pairs UserA/UserB found. "+userAUserBVOList.size());
        //pair are available
        for (UserAUserBVO userAUserBVOValue : userAUserBVOList) {
            double userAFollowUserB = userUserDAO.getUserFollowerFirstFollowingUserFolloweeTwo(userAUserBVOValue.getUserB(),
                    userAUserBVOValue.getUserA());

            double userBFollowUserA = userUserDAO.getUserFollowerFirstFollowingUserFolloweeTwo(userAUserBVOValue.getUserA(),
                    userAUserBVOValue.getUserB());

            //int numUserAFavNCRest = userUserDAO.getUserXFavNCRest(userAUserBVOValue.getUserA());
            List<String> userAFavNCRest = userUserDAO.getUserXFavNCRest(userAUserBVOValue.getUserA());
            double numUserAFavNCRest = userAFavNCRest.size();

            List<String> userBFavNCRest = userUserDAO.getUserXFavNCRest(userAUserBVOValue.getUserB());

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
                        userAFavNCRestElement));
            }

            List<RestaurantNeighbourhoodVO> userBfavNonChainRestNbrhoodIdList = new ArrayList<RestaurantNeighbourhoodVO>((int) numUserBFavNCRest);

            for (String userBFavNCRestElement : userBFavNCRest) {
                userBfavNonChainRestNbrhoodIdList.add(userUserDAO.getRestaurantNeighbourhoodList(
                        userBFavNCRestElement));
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

            List<String> userAfavNonChainRestClusterId = null;

            double numUserAFavNCRestClusters = 0;

            for (String userAFavNCRestElement : userAFavNCRest) {
                userAfavNonChainRestClusterId = userUserDAO.getRestaurantClusterIdList(userAFavNCRestElement);
                numUserAFavNCRestClusters = numUserAFavNCRestClusters +
                    userAfavNonChainRestClusterId.size();
            }

            List<String> userBfavNonChainRestClusterId = null;
            double numUserBFavNCRestClusters = 0;

            for (String userBFavNCRestElement : userBFavNCRest) {
                userBfavNonChainRestClusterId = userUserDAO.getRestaurantClusterIdList(userBFavNCRestElement);
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
                if (userBfavNonChainRestClusterId.contains(
                            userAfavNonChainRestClusterIdElement)) {
                    ++numCommonNCFavRestClusters;
                }
            }

            List<String> userAFavNativeCuisIdList = userUserDAO.getUserCuisineIdList(userAUserBVOValue.getUserA());
            double numUserAFavNativeCuisines = userAFavNativeCuisIdList.size();

            List<String> userBFavNativeCuisIdList = userUserDAO.getUserCuisineIdList(userAUserBVOValue.getUserB());

            double numUserBFavNativeCuisines = userBFavNativeCuisIdList.size();

            double numCommonFavNativeCuisines = 0;

            for (String userAFavNativeCuisId : userAFavNativeCuisIdList) {
                if (userBFavNativeCuisIdList.contains(userAFavNativeCuisId)) {
                    ++numCommonFavNativeCuisines;
                }
            }

            List<String> userAFavChainRestaurantIdList = userUserDAO.getUserXFavCRest(userAUserBVOValue.getUserA());
            double numUserAFavChainRest = userAFavChainRestaurantIdList.size();

            List<String> userBFavChainRestaurantIdList = userUserDAO.getUserXFavCRest(userAUserBVOValue.getUserB());
            double numUserBFavChainRest = userBFavChainRestaurantIdList.size();

            // -- Tier 1 logic
            if ((userAFollowUserB == 1) || (userBFollowUserA == 1) ||
                    ((numCommonNCFavRest / minNumUserABFavNCRest) >= 0.5) ||
                    ((numCommonNbrhoodNCFavRest / minNumUserABFavNCRest) >= 0.7) ||
                    ((numCommonNCFavRestClusters / minNumUserABFavNCRestClusters) == 1.0)) {
                userUserDAO.sumbitAssignedUserUserMatchTier(userAUserBVOValue.getUserA(),
                    userAUserBVOValue.getUserB(), 1);
            } else {
                // -- Tier 2 logic
                if ((numCommonNCFavRestClusters / minNumUserABFavNCRestClusters) >= 0.7) {
                    userUserDAO.sumbitAssignedUserUserMatchTier(userAUserBVOValue.getUserA(),
                        userAUserBVOValue.getUserB(), 2);
                } else {
                    // -- Tier 3 logic TODO
                    if ((numCommonFavNativeCuisines >= 1) ||
                            (((numUserAFavChainRest / (numUserAFavNCRest +
                            numUserAFavChainRest)) >= 0.5) &&
                            ((numUserBFavChainRest / (numUserBFavNCRest +
                            numUserBFavChainRest)) >= 0.5))) {
                        userUserDAO.sumbitAssignedUserUserMatchTier(userAUserBVOValue.getUserA(),
                            userAUserBVOValue.getUserB(), 3);
                    } else {
                        userUserDAO.sumbitAssignedUserUserMatchTier(userAUserBVOValue.getUserA(),
                            userAUserBVOValue.getUserB(), 4);
                    }
                }
            }

            //TODO
            //USER_RESTAURANT_FAV_UPDATE_SQL shud have a different algo_indicator? 
            userUserDAO.submitRestaurantFav(userAUserBVOValue.getUserA(), null,
                0);
            userUserDAO.submitUserFollowDataUpdate(userAUserBVOValue.getUserA(),
                userAUserBVOValue.getUserB(), 0);
            
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
