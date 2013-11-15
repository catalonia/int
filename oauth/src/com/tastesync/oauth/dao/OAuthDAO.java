package com.tastesync.oauth.dao;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.oauth.exception.TasteSyncException;
import com.tastesync.oauth.model.vo.OAuthDataVO;

import java.sql.Connection;


public interface OAuthDAO {
    public void deleteOAuthToken(TSDataSource tsDataSource,
        Connection connection, String identifierForVendor)
        throws TasteSyncException;

    public OAuthDataVO getUserOAuthDataFrmDBBasedOnFromOAuthToken(TSDataSource tsDataSource,
            Connection connection,
        String identifierForVendor, String oauthToken)
        throws TasteSyncException;

    public String getOAuthToken(TSDataSource tsDataSource,
            Connection connection, String identifierForVendor, String userId,
        String deviceType) throws TasteSyncException;
}
