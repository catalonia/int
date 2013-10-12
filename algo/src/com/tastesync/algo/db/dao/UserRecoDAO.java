package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.CityNeighbourhoodVO;
import com.tastesync.algo.model.vo.UserRecoSupplyTierVO;

import java.util.List;


public interface UserRecoDAO extends BaseDAO {
    String getInitiatorUserIdFromRecorequestId(String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestCuisineTier2IdList(String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestCuisineTier1IdList(String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestPriceIdList(String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestThemeIdList(String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestWhoareyouwithIdList(String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestTypeofrestIdList(String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestOccasionIdList(String recoRequestId)
        throws TasteSyncException;

    CityNeighbourhoodVO getRecorequestCityIdNbrIdList(String recoRequestId)
        throws TasteSyncException;

    int getNumberOfSameParamRequests(String initiatorUserId,
        String recoRequestId) throws TasteSyncException;

    int getDemandTierForSingleUser(String userId) throws TasteSyncException;

    List<UserRecoSupplyTierVO> getUserRecoSupplyTierVO(String userId, String recorequestId)
        throws TasteSyncException;

    int getNumUserCityNbrhoodMatchTopicFound(String userId, String cityId,
        String neighborhoodId) throws TasteSyncException;

    boolean isUserIdLinkedWithFbFriend(String userId, String otherUserFbId)
        throws TasteSyncException;

    boolean isUserIdLinkedWithReportedInfo(String userId, String reportedUserId)
        throws TasteSyncException;

    int getCountUserCuistier2Match(String userId,
        List<String> cuisineTier2IdList, double matchCount)
        throws TasteSyncException;

    int getCountUserCuistier2MatchMapper(String userId,
        List<String> cuisineTier1IdList, double matchCount)
        throws TasteSyncException;

    int getCountUserPriceMatch(String userId, List<String> priceIdList,
        double matchCount) throws TasteSyncException;

    int getCountUserThemeMatch(String userId, List<String> themeIdList,
        double matchCount) throws TasteSyncException;

    int getCountUserWhoareyouwithMatch(String userId,
        List<String> whoareyouwithIdList, double matchCount) throws TasteSyncException;

    int getCountUserTypeofrestMatch(String userId,
        List<String> typeOfRestaurantIdList, double matchCount)
        throws TasteSyncException;

    int getCountUserOccasionMatch(String userId, List<String> occasionIdList,
        double matchCount) throws TasteSyncException;

    double getUserAUserBMatchTier(String userAId, String userBId)
        throws TasteSyncException;

    void submitRecorequestTsAssigned(String recoRequestId, String assigneduserUserId)
        throws TasteSyncException;

    void submitUserRecoSupplyTier(String userId, int usrSupplyInvTier,
        int userTierCalcFlag) throws TasteSyncException;

    int getCountRecorequestReplyUser(String recoRequestId, String assignedUserId)
        throws TasteSyncException;
}
