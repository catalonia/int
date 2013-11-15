package com.tastesync.oauth.dao;

import com.tastesync.common.utils.CommonFunctionsUtil;
import com.tastesync.common.utils.RandomStringGenerator;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.oauth.db.queries.OAuthQueries;
import com.tastesync.oauth.exception.TasteSyncException;
import com.tastesync.oauth.model.vo.OAuthDataVO;
import com.tastesync.oauth.utils.CipherUtils;

import org.apache.commons.lang3.StringUtils;

import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OAuthDAOImpl implements OAuthDAO {
    private boolean printDebugExtra = false;

    public OAuthDAOImpl() {
        super();
    }

    @Override
    public void deleteOAuthToken(TSDataSource tsDataSource,
        Connection connection, String identifierForVendor)
        throws TasteSyncException {
        PreparedStatement statement = null;

        try {
            tsDataSource.begin();

            if (identifierForVendor != null) {
                statement = connection.prepareStatement(OAuthQueries.DEVICE_IDENTIFIER_VENDOR_OAUTH_TOKEN_DELETE_SQL);
                statement.setString(1, identifierForVendor);
                statement.executeUpdate();
                statement.close();
            }

            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while deleteOAuthToken " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, null);
        }
    }

    @Override
    public String getOAuthToken(TSDataSource tsDataSource,
        Connection connection, String identifierForVendor, String userId,
        String deviceType) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            int noOfCAPSAlpha = 32;
            int noOfDigits = 8;
            int noOfSplChars = 24;
            int minLen = 64;
            int maxLen = 64;
            char[] pswd = RandomStringGenerator.generatePswd(minLen, maxLen,
                    noOfCAPSAlpha, noOfDigits, noOfSplChars);
            List<String> inputMiddleKeys = new ArrayList<String>();
            inputMiddleKeys.add("ToKeN");
            inputMiddleKeys.add(String.valueOf((System.nanoTime() / 1000)));

            String strToEncrypt = new String(pswd) + userId +
                CommonFunctionsUtil.generateUniqueKey(inputMiddleKeys);
            String oAuthToken = CipherUtils.encrypt(strToEncrypt.trim());

            if (printDebugExtra) {
                System.out.println("String to Encrypt : " + strToEncrypt);
                System.out.println("Encrypted oAuthToken: " + oAuthToken +
                    " oAuthToken.length=" + oAuthToken.length());
            }

            if (oAuthToken.length() > 255) {
                oAuthToken = oAuthToken.substring(0, 255);

                if (printDebugExtra) {
                    System.out.println("Truncated oAuthToken : " + oAuthToken);
                }
            }

            //TODO
            if (identifierForVendor == null) {
                return oAuthToken;
            }

            oAuthToken = StringUtils.replace(oAuthToken, "\r\n", "");
            tsDataSource.begin();
            //First delete any token (s) associated with this device , then insert
            statement = connection.prepareStatement(OAuthQueries.DEVICE_IDENTIFIER_VENDOR_OAUTH_TOKEN_DELETE_SQL);
            statement.setString(1, identifierForVendor);
            statement.executeUpdate();
            statement.close();

            statement = connection.prepareStatement(OAuthQueries.DEVICE_TOKEN_INSERT_SQL);
            statement.setString(1, identifierForVendor);

            DateTime currentDateTime = new DateTime();

            //Expiration
            Timestamp expirationTimestamp = new Timestamp(currentDateTime.plusMonths(
                        12).toDate().getTime());
            statement.setTimestamp(2, expirationTimestamp);

            statement.setTimestamp(3,
                new Timestamp(currentDateTime.toDate().getTime()));

            //change to MD5
            statement.setString(4, oAuthToken);
            //Expiration
            statement.setTimestamp(5, expirationTimestamp);
            statement.setTimestamp(6,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setString(7, userId);

            statement.setString(8, deviceType);
            statement.executeUpdate();
            statement.close();

            tsDataSource.commit();

            //add to the response header
            return oAuthToken;
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException("Error while getOAuthToken " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public OAuthDataVO getUserOAuthDataFrmDBBasedOnFromOAuthToken(
        TSDataSource tsDataSource, Connection connection,
        String identifierForVendor, String oauthToken)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        oauthToken = StringUtils.replace(oauthToken, "\r\n", "");

        try {
            OAuthDataVO oauthDataVO = null;

            statement = connection.prepareStatement(OAuthQueries.USER_DEVICE_TOKEN_SELECT_SQL);
            statement.setString(1, identifierForVendor);
            //convert first to md5
            statement.setString(2, oauthToken);
            resultset = statement.executeQuery();

            if (resultset.next()) {
                Date deviceTokenExpirationDatetime = resultset.getTimestamp(
                        "user_device_oauth.device_token_expiration_datetime");
                Date deviceTokenUpdatedDatetime = resultset.getTimestamp(
                        "user_device_oauth.device_token_updated_datetime");
                Date oauthExpirationDatetime = resultset.getTimestamp(
                        "user_device_oauth.oauth_expiration_datetime");
                Date oauthUpdatedDatetime = resultset.getTimestamp(
                        "user_device_oauth.oauth_updated_datetime");
                String deviceType = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_device_oauth.device_type"));

                //reconvert
                String md5_oauth_token = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_device_oauth.md5_oauth_token"));
                String userId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_device_oauth.USER_ID"));

                // check user id is not null
                if ((userId == null) || userId.isEmpty()) {
                    oauthDataVO = null;
                } else {
                    oauthDataVO = new OAuthDataVO(userId, identifierForVendor,
                            md5_oauth_token, deviceTokenUpdatedDatetime,
                            deviceTokenExpirationDatetime,
                            oauthUpdatedDatetime, oauthExpirationDatetime,
                            oauthToken, deviceType);
                }
            }

            statement.close();

            return oauthDataVO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getOAuthDatabasedOnOauthToken " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }
}
