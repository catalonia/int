package com.tastesync.oauth.bos;

import java.sql.Connection;

import com.tastesync.db.pool.TSDataSource;
import com.tastesync.oauth.exception.TasteSyncException;
import com.tastesync.oauth.model.vo.OAuthDataVO;


public interface OAuthBO {
    public OAuthDataVO getUserOAuthDataFrmDBBasedOnFromOAuthToken(TSDataSource tsDataSource,
            Connection connection, String vendorIdentifier,
        String oauthToken) throws TasteSyncException;
    public String getOAuthToken(TSDataSource tsDataSource,
            Connection connection, String identifierForVendor, String userId, String deviceType)
            throws TasteSyncException;
    public void deleteOAuthToken(TSDataSource tsDataSource,
            Connection connection, String identifierForVendor)
            throws TasteSyncException;
        
}
