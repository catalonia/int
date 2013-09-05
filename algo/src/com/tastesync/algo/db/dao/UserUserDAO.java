package com.tastesync.algo.db.dao;

import java.util.List;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RecorequestReplyUserVO;
import com.tastesync.algo.model.vo.RecorequestTsAssignedVO;
import com.tastesync.algo.model.vo.RecorequestUserVO;

public interface UserUserDAO {
    List<String> getRecorequestUserFlaggedUserList() throws TasteSyncException;

    List<String> getRecorequestTsAssignedFlaggedUserList()
        throws TasteSyncException;

    List<String> getRecorequestReplyUserFlaggedUserList()
        throws TasteSyncException;

    List<String> getRecorequestReplyUserForSameRecorequestIdFlaggedUserList()
        throws TasteSyncException;
    
    int getNumRecorequestsAssignedToday(String flaggedUserId)  throws TasteSyncException;
    
    int getNumRecorequestsAssignedTodayReplied(String flaggedUserId) throws TasteSyncException;
    
    int getNumRecorequestsAssigned7Days(String flaggedUserId) throws TasteSyncException;
    
    int getNumRecorequestsAssigned7DaysReplied(String flaggedUserId) throws TasteSyncException;
    
    int getNumRecorequestsInitiatedTotal(String flaggedUserId) throws TasteSyncException;
    

    List<RecorequestUserVO> getLastNRecorequestsUserRecorequestId (String flaggedUserId) throws TasteSyncException;
    
    RecorequestReplyUserVO recorequestsAssignedFirstReplyId(String recorequestId) throws TasteSyncException;

    List<RecorequestTsAssignedVO> getLastNRecorequestsAssignedRecorequestId (String flaggedUserId) throws TasteSyncException;
    
    int getNumRecorequestsAssignedTotal(String flaggedUserId) throws TasteSyncException;

    
    void submitUserRecoSupplyTier(String flaggedUserId, int userSupplyInvTier) throws TasteSyncException;
    
    void submitRecorrequestUser(String flaggedUserId, int algoInd) throws TasteSyncException;
    void submitRecorrequestAssigned(String flaggedUserId, int algoInd) throws TasteSyncException;
    void submitRecorrequestReplyUser(String flaggedUserId, int algoInd) throws TasteSyncException;

    
}
