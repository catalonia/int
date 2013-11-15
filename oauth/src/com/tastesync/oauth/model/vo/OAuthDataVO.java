package com.tastesync.oauth.model.vo;

import java.io.Serializable;

import java.util.Date;


public class OAuthDataVO implements Serializable {
    private static final long serialVersionUID = 5284136236864519328L;
    private Date deviceTokenExpirationDatetime;
    private Date deviceTokenUpdatedDatetime;
    private Date oauthExpirationDatetime;
    private Date oauthUpdatedDatetime;
    private String deviceType;
    private String md5_oauth_token;
    private String oauthToken;
    private String userId;
    private String identifierForVendor;

    public OAuthDataVO(String userId, String identifierForVendor,
        String md5_oauth_token, Date deviceTokenUpdatedDatetime,
        Date deviceTokenExpirationDatetime, Date oauthUpdatedDatetime,
        Date oauthExpirationDatetime, String oauthToken, String deviceType) {
        super();
        this.userId = userId;
        this.identifierForVendor = identifierForVendor;
        this.md5_oauth_token = md5_oauth_token;
        this.deviceTokenUpdatedDatetime = deviceTokenUpdatedDatetime;
        this.deviceTokenExpirationDatetime = deviceTokenExpirationDatetime;
        this.oauthUpdatedDatetime = oauthUpdatedDatetime;
        this.oauthExpirationDatetime = oauthExpirationDatetime;
        this.oauthToken = oauthToken;
        this.deviceType = deviceType;
    }

    public Date getDeviceTokenExpirationDatetime() {
        return deviceTokenExpirationDatetime;
    }

    public Date getDeviceTokenUpdatedDatetime() {
        return deviceTokenUpdatedDatetime;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getMd5_oauth_token() {
        return md5_oauth_token;
    }

    public Date getOauthExpirationDatetime() {
        return oauthExpirationDatetime;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public Date getOauthUpdatedDatetime() {
        return oauthUpdatedDatetime;
    }

    public String getUserId() {
        return userId;
    }

    public String getIdentifierForVendor() {
        return identifierForVendor;
    }

    @Override
    public String toString() {
        return "OAuthDataVO [userId=" + userId + ", identifierForVendor=" +
        		identifierForVendor + ", md5_oauth_token=" + md5_oauth_token +
        ", deviceTokenUpdatedDatetime=" + deviceTokenUpdatedDatetime +
        ", deviceTokenExpirationDatetime=" + deviceTokenExpirationDatetime +
        ", oauthUpdatedDatetime=" + oauthUpdatedDatetime +
        ", oauthExpirationDatetime=" + oauthExpirationDatetime +
        ", oauthToken=" + oauthToken + ", deviceType=" + deviceType + "]";
    }
}
