package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.CityNeighbourhoodVO;
import com.tastesync.algo.model.vo.UserRecoSupplyTierVO;
import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.util.List;


public interface UserRecoDAO extends BaseDAO {
    String getInitiatorUserIdFromRecorequestId(TSDataSource tsDataSource, Connection connection,String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestCuisineTier2IdList(TSDataSource tsDataSource, Connection connection,String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestCuisineTier1IdList(TSDataSource tsDataSource, Connection connection,String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestPriceIdList(TSDataSource tsDataSource, Connection connection,String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestThemeIdList(TSDataSource tsDataSource, Connection connection,String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestWhoareyouwithIdList(TSDataSource tsDataSource, Connection connection,String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestTypeofrestIdList(TSDataSource tsDataSource, Connection connection,String recoRequestId)
        throws TasteSyncException;

    List<String> getRecorequestOccasionIdList(TSDataSource tsDataSource, Connection connection,String recoRequestId)
        throws TasteSyncException;

    CityNeighbourhoodVO getRecorequestCityIdNbrIdList(TSDataSource tsDataSource, Connection connection,String recoRequestId)
        throws TasteSyncException;

    int getNumberOfSameParamRequests(TSDataSource tsDataSource, Connection connection,String initiatorUserId,
        String recoRequestId) throws TasteSyncException;

    int getDemandTierForSingleUser(TSDataSource tsDataSource, Connection connection,String userId) throws TasteSyncException;

    List<UserRecoSupplyTierVO> getUserRecoSupplyTierVO(TSDataSource tsDataSource, Connection connection,String userId, String recorequestId)
        throws TasteSyncException;

    int getNumUserCityNbrhoodMatchTopicFound(TSDataSource tsDataSource, Connection connection,String userId, String cityId,
        String neighborhoodId) throws TasteSyncException;

    boolean isUserIdLinkedWithFbFriend(TSDataSource tsDataSource, Connection connection,String userId, String otherUserFbId)
        throws TasteSyncException;

    boolean isUserIdLinkedWithReportedInfo(TSDataSource tsDataSource, Connection connection,String userId, String reportedUserId)
        throws TasteSyncException;

    int getCountUserCuistier2Match(TSDataSource tsDataSource, Connection connection,String userId,
        List<String> cuisineTier2IdList, double matchCount)
        throws TasteSyncException;

    int getCountUserCuistier2MatchMapper(TSDataSource tsDataSource, Connection connection,String userId,
        List<String> cuisineTier1IdList, double matchCount)
        throws TasteSyncException;

    int getCountUserPriceMatch(TSDataSource tsDataSource, Connection connection,String userId, List<String> priceIdList,
        double matchCount) throws TasteSyncException;

    int getCountUserThemeMatch(TSDataSource tsDataSource, Connection connection,String userId, List<String> themeIdList,
        double matchCount) throws TasteSyncException;

    int getCountUserWhoareyouwithMatch(TSDataSource tsDataSource, Connection connection,String userId,
        List<String> whoareyouwithIdList, double matchCount) throws TasteSyncException;

    int getCountUserTypeofrestMatch(TSDataSource tsDataSource, Connection connection,String userId,
        List<String> typeOfRestaurantIdList, double matchCount)
        throws TasteSyncException;

    int getCountUserOccasionMatch(TSDataSource tsDataSource, Connection connection,String userId, List<String> occasionIdList,
        double matchCount) throws TasteSyncException;

    double getUserAUserBMatchTier(TSDataSource tsDataSource, Connection connection,String userAId, String userBId)
        throws TasteSyncException;

    void submitRecorequestTsAssigned(TSDataSource tsDataSource, Connection connection,String recoRequestId, String assigneduserUserId)
        throws TasteSyncException;

    void submitPiRecorequestTsAssigned(TSDataSource tsDataSource, Connection connection,String recoRequestId, String assigneduserUserId)
            throws TasteSyncException;
    
    void submitUserRecoSupplyTier(TSDataSource tsDataSource, Connection connection,String userId, int usrSupplyInvTier,
        int userTierCalcFlag) throws TasteSyncException;

    int getCountRecorequestReplyUser(TSDataSource tsDataSource, Connection connection,String recoRequestId, String assignedUserId)
        throws TasteSyncException;
}
