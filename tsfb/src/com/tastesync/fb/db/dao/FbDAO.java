package com.tastesync.fb.db.dao;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.fb.db.dao.BaseDAO;
import com.tastesync.fb.exception.TasteSyncException;
import com.tastesync.fb.model.fqlmodel.FqlFriend;
import com.tastesync.fb.model.fqlmodel.FqlUser;
import com.tastesync.fb.vo.TsUser;

import java.sql.Connection;

import java.util.List;


public interface FbDAO extends BaseDAO {
    void createOrUpdateUserFrmFbData(TSDataSource tsDataSource,
        Connection connection, FqlUser currentUserFB,
        String identifierForVendor, String accessToken)
        throws TasteSyncException;

    List<String> getAllDeviceTokensForSingleUser(TSDataSource tsDataSource,
        Connection connection, String userId) throws TasteSyncException;

    String getExistingUserIdBasedOnFBId(TSDataSource tsDataSource,
        Connection connection, String userFbId) throws TasteSyncException;

    void submitFriendsData(TSDataSource tsDataSource, Connection connection,
        List<FqlFriend> friends, String userFbId, String existingUserId)
        throws TasteSyncException;

    void submitUsersData(TSDataSource tsDataSource, Connection connection,
        List<FqlUser> users) throws TasteSyncException;
    
    public TsUser getUserInformationByFacebookID(TSDataSource tsDataSource,
            Connection connection, String userFbId) throws TasteSyncException;
    
    public void submitTrustedFriends(TSDataSource tsDataSource,
            Connection connection, List<TsUser> friendsListUsingTasteSync, String userID) throws TasteSyncException;
}
