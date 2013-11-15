package com.tastesync.oauth.bos;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.oauth.dao.OAuthDAO;
import com.tastesync.oauth.dao.OAuthDAOImpl;
import com.tastesync.oauth.exception.TasteSyncException;
import com.tastesync.oauth.model.vo.OAuthDataVO;

import java.sql.Connection;


public class OAuthBOImpl implements OAuthBO {
    private OAuthDAO oauthDAO = new OAuthDAOImpl();

    @Override
    public void deleteOAuthToken(TSDataSource tsDataSource,
        Connection connection, String identifierForVendor)
        throws TasteSyncException {
        oauthDAO.deleteOAuthToken(tsDataSource, connection, identifierForVendor);
    }

    @Override
    public OAuthDataVO getUserOAuthDataFrmDBBasedOnFromOAuthToken(TSDataSource tsDataSource,
            Connection connection,
        String identifierForVendor, String oauthToken)
        throws TasteSyncException {
        return oauthDAO.getUserOAuthDataFrmDBBasedOnFromOAuthToken(tsDataSource, connection, identifierForVendor,
            oauthToken);
    }

    @Override
    public String getOAuthToken(TSDataSource tsDataSource,
            Connection connection, String identifierForVendor, String userId,
        String deviceType) throws TasteSyncException {
        return oauthDAO.getOAuthToken(tsDataSource, connection, identifierForVendor, userId, deviceType);
    }
}
