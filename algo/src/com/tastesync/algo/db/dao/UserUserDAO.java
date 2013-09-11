package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RecorequestReplyUserVO;
import com.tastesync.algo.model.vo.RecorequestTsAssignedVO;
import com.tastesync.algo.model.vo.RecorequestUserVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;

import java.util.List;


public interface UserUserDAO extends BaseDAO {
    List<String> getRecorequestUserFlaggedUserList(
        int algoIndicatorIdentifyUseridListOne) throws TasteSyncException;

    List<String> getRecorequestTsAssignedFlaggedUserList(
        int algoIndicatorIdentifyUseridListSecond) throws TasteSyncException;

    List<String> getRecorequestReplyUserFlaggedUserList(
        int algoIndicatorIdentifyUseridListThird) throws TasteSyncException;

    List<String> getRecorequestReplyUserForSameRecorequestIdFlaggedUserList(
        int algoIndicatorIdentifyUseridListFourth) throws TasteSyncException;

    int getNumRecorequestsAssignedToday(String flaggedUserId)
        throws TasteSyncException;

    int getNumRecorequestsAssignedTodayReplied(String flaggedUserId)
        throws TasteSyncException;

    int getNumRecorequestsAssignedNDays(String flaggedUserId, int nDays)
        throws TasteSyncException;

    int getNumRecorequestsAssignedNDaysReplied(String flaggedUserId, int nDays)
        throws TasteSyncException;

    int getNumRecorequestsInitiatedTotal(String flaggedUserId)
        throws TasteSyncException;

    List<RecorequestUserVO> getLastNRecorequestsUserRecorequestId(
        String flaggedUserId) throws TasteSyncException;

    RecorequestReplyUserVO recorequestsAssignedFirstReplyId(
        String recorequestId) throws TasteSyncException;

    List<RecorequestTsAssignedVO> getLastNRecorequestsAssignedRecorequestId(
        String flaggedUserId) throws TasteSyncException;

    int getNumRecorequestsAssignedTotal(String flaggedUserId)
        throws TasteSyncException;

    void submitUserRecoSupplyTier(String flaggedUserId, int userSupplyInvTier)
        throws TasteSyncException;

    void submitRecorrequestUser(String flaggedUserId, int algoInd)
        throws TasteSyncException;

    void submitRecorrequestAssigned(String flaggedUserId, int algoInd)
        throws TasteSyncException;

    List<String> getRecoRequestsLastNDays(String flaggedUserId, int nDays)
        throws TasteSyncException;

    List<String> getRecorequestCuisineTier1(String recorequestId)
        throws TasteSyncException;

    List<String> getRecorequestCuisineTier2(String recorequestId)
        throws TasteSyncException;

    List<String> getRecorequestOccasion(String recorequestId)
        throws TasteSyncException;

    List<String> getRecorequestPrice(String recorequestId)
        throws TasteSyncException;

    List<String> getRecorequestTheme(String recorequestId)
        throws TasteSyncException;

    List<String> getRecorequestTypeofRest(String recorequestId)
        throws TasteSyncException;

    List<String> getRecorequestWhoarewithyou(String recorequestId)
        throws TasteSyncException;

    List<String> getRecorequestLocation(String recorequestId)
        throws TasteSyncException;

    List<String> getRecoRequestsReplyAnsweredLastNDays(String flaggedUserId,
        int nDays) throws TasteSyncException;

    List<String> getRecoRequestsReplyUserAnsweredLastNDays(
        String flaggedUserId, int nDays) throws TasteSyncException;

    void submitUserRecoDemandTierPrecalc(String flaggedUserId,
        int demandTierFlag) throws TasteSyncException;

    List<RestaurantUserVO> getRecorequestReplyUserRestaurant(
        int algoIndicatorIdentifyUseridListThree) throws TasteSyncException;

    List<RestaurantUserVO> getRestaurantTipsTastesync(
        int algoIndicatorIdentifyUseridListFour) throws TasteSyncException;

    List<RestaurantUserVO> getUserRestaurantFav(
        int algoIndicatorIdentifyUseridListFive) throws TasteSyncException;

    void submitUserCityNbrHoodAndCusineTier2Match(
        RestaurantUserVO flaggedRestaurantUserVO) throws TasteSyncException;
}
