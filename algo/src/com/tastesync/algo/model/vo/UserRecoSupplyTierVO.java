package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class UserRecoSupplyTierVO implements Serializable {
    private static final long serialVersionUID = 8259244574715808016L;
    private String userId;
    private int userSupplyInvTier;

    public UserRecoSupplyTierVO(String userId, int userSupplyInvTier) {
        super();
        this.userId = userId;
        this.userSupplyInvTier = userSupplyInvTier;
    }

    public String getUserId() {
        return userId;
    }

    public int getUserSupplyInvTier() {
        return userSupplyInvTier;
    }

	@Override
	public String toString() {
		return "UserRecoSupplyTierVO [userId=" + userId
				+ ", userSupplyInvTier=" + userSupplyInvTier + "]";
	}
    
}
