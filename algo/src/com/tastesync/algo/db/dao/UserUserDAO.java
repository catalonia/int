package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RecorequestReplyUserVO;
import com.tastesync.algo.model.vo.RecorequestTsAssignedVO;
import com.tastesync.algo.model.vo.RecorequestUserVO;
import com.tastesync.algo.model.vo.RestaurantNeighbourhoodVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.model.vo.UserFolloweeUserFollowerVO;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;

import java.util.List;


public interface UserUserDAO extends BaseDAO {
    List<RecorequestTsAssignedVO> getLastNRecorequestsAssignedRecorequestId(
    		TSDataSource tsDataSource, Connection connection,String flaggedUserId) throws TasteSyncException;

    List<RecorequestUserVO> getLastNRecorequestsUserRecorequestId(
        TSDataSource tsDataSource, Connection connection, String flaggedUserId)
        throws TasteSyncException;

    int getNPercentilePoints(TSDataSource tsDataSource, Connection connection,
        double percentileN) throws TasteSyncException;

    int getNumRecorequestsAssignedNDays(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, int nDays)
        throws TasteSyncException;

    int getNumRecorequestsAssignedNDaysReplied(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, int nDays)
        throws TasteSyncException;

    int getNumRecorequestsAssignedToday(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId) throws TasteSyncException;

    int getNumRecorequestsAssignedTodayReplied(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId) throws TasteSyncException;

    int getNumRecorequestsAssignedTotal(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId) throws TasteSyncException;

    int getNumRecorequestsInitiatedTotal(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId) throws TasteSyncException;

    int getNumUserFavNvTierNRestaurant(TSDataSource tsDataSource,
        Connection connection, String userId, int tierId)
        throws TasteSyncException;

    List<String> getRecoRequestsLastNDays(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, int nDays)
        throws TasteSyncException;

    List<String> getRecoRequestsReplyAnsweredLastNDays(
        TSDataSource tsDataSource, Connection connection, String flaggedUserId,
        int nDays) throws TasteSyncException;

    List<String> getRecoRequestsReplyUserAnsweredLastNDays(
        TSDataSource tsDataSource, Connection connection, String flaggedUserId,
        int nDays) throws TasteSyncException;

    List<String> getRecorequestCuisineTier1(TSDataSource tsDataSource,
        Connection connection, String recorequestId) throws TasteSyncException;

    List<String> getRecorequestCuisineTier2(TSDataSource tsDataSource,
        Connection connection, String recorequestId) throws TasteSyncException;

    List<String> getRecorequestLocation(TSDataSource tsDataSource,
        Connection connection, String recorequestId) throws TasteSyncException;

    List<String> getRecorequestOccasion(TSDataSource tsDataSource,
        Connection connection, String recorequestId) throws TasteSyncException;

    List<String> getRecorequestPrice(TSDataSource tsDataSource,
        Connection connection, String recorequestId) throws TasteSyncException;

    List<String> getRecorequestReplyUserFlaggedUserList(
        TSDataSource tsDataSource, Connection connection,
        int algoIndicatorIdentifyUseridListThird) throws TasteSyncException;

    List<String> getRecorequestReplyUserForSameRecorequestIdFlaggedUserList(
        TSDataSource tsDataSource, Connection connection,
        int algoIndicatorIdentifyUseridListFourth) throws TasteSyncException;

    List<RestaurantUserVO> getRecorequestReplyUserRestaurant(
        TSDataSource tsDataSource, Connection connection,
        int algoIndicatorIdentifyUseridListThree) throws TasteSyncException;

    List<String> getRecorequestTheme(TSDataSource tsDataSource,
        Connection connection, String recorequestId) throws TasteSyncException;

    List<String> getRecorequestTsAssignedFlaggedUserList(
        TSDataSource tsDataSource, Connection connection,
        int algoIndicatorIdentifyUseridListSecond) throws TasteSyncException;

    List<String> getRecorequestTypeofRest(TSDataSource tsDataSource,
        Connection connection, String recorequestId) throws TasteSyncException;

    List<String> getRecorequestUserFlaggedUserList(TSDataSource tsDataSource,
        Connection connection, int algoIndicatorIdentifyUseridListOne)
        throws TasteSyncException;

    List<String> getRecorequestWhoarewithyou(TSDataSource tsDataSource,
        Connection connection, String recorequestId) throws TasteSyncException;

    List<String> getRestaurantClusterIdList(TSDataSource tsDataSource,
        Connection connection, String restaurantId) throws TasteSyncException;

    RestaurantNeighbourhoodVO getRestaurantNeighbourhoodList(
        TSDataSource tsDataSource, Connection connection, String restaurantId)
        throws TasteSyncException;

    List<RestaurantUserVO> getRestaurantTipsTastesync(
        TSDataSource tsDataSource, Connection connection,
        int algoIndicatorIdentifyUseridListFour) throws TasteSyncException;

    List<String> getUserCuisineIdList(TSDataSource tsDataSource,
        Connection connection, String userId) throws TasteSyncException;

    List<UserFolloweeUserFollowerVO> getUserFolloweeUserFollowerFollowData(
        TSDataSource tsDataSource, Connection connection,
        int algoIndicatorIdentifyUseridList) throws TasteSyncException;

    int getUserFollowerFirstFollowingUserFolloweeTwo(
        TSDataSource tsDataSource, Connection connection,
        String userFolloweeId, String userFollowerId) throws TasteSyncException;

    int getUserPoints(TSDataSource tsDataSource, Connection connection,
        String userId) throws TasteSyncException;

    List<RestaurantUserVO> getUserRestaurantFav(TSDataSource tsDataSource,
        Connection connection, int algoIndicatorIdentifyUseridList,
        boolean restaurantNeeded) throws TasteSyncException;

    List<String> getUserXFavCRest(TSDataSource tsDataSource,
        Connection connection, String userIdX) throws TasteSyncException;

    List<String> getUserXFavNCRest(TSDataSource tsDataSource,
        Connection connection, String userIdX) throws TasteSyncException;

    boolean isUserOnlin(TSDataSource tsDataSource, Connection connection,
        String userId) throws TasteSyncException;

    RecorequestReplyUserVO recorequestsAssignedFirstReplyId(
        TSDataSource tsDataSource, Connection connection, String recorequestId)
        throws TasteSyncException;

    void submitAssignedUserUserMatchTier(TSDataSource tsDataSource,
        Connection connection, String userIdA, String userIdB, int matchTier)
        throws TasteSyncException;

    void submitRecorrequestAssigned(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, int algoInd)
        throws TasteSyncException;

    void submitRecorrequestUser(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, int algoInd)
        throws TasteSyncException;

    void submitUserCityNbrHoodAndCusineTier2Match(TSDataSource tsDataSource,
        Connection connection, RestaurantUserVO flaggedRestaurantUserVO)
        throws TasteSyncException;

    void submitUserFollowDataUpdate(TSDataSource tsDataSource,
        Connection connection, String userIdA, String userIdB, int algoInd)
        throws TasteSyncException;

    void submitUserRecoDemandTierPrecalc(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, int demandTierFlag)
        throws TasteSyncException;

    void submitUserRecoSupplyTier(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId, int userSupplyInvTier)
        throws TasteSyncException;
}
