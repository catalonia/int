package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class RestaurantPopularityTierVO implements Serializable {
    private static final long serialVersionUID = 4886210352528952164L;
    private String restaurantId;
    private int popularityTierId;
    private String userId;
    private int numUserRestaurantMatchCount;
    private int rankNumber;

    public RestaurantPopularityTierVO(String restaurantId,
        int popularityTierId, String userId,
        int numUserRestaurantMatchCount, int rankNumber) {
        super();
        this.restaurantId = restaurantId;
        this.popularityTierId = popularityTierId;
        this.userId = userId;
        this.numUserRestaurantMatchCount = numUserRestaurantMatchCount;
        this.rankNumber = rankNumber;
    }

    public RestaurantPopularityTierVO(String restaurantId,
        int popularityTierId) {
        super();
        this.restaurantId = restaurantId;
        this.popularityTierId = popularityTierId;
    }

    public int getRankNumber() {
        return rankNumber;
    }

    public void setRankNumber(int rankNumber) {
        this.rankNumber = rankNumber;
    }

    public String getUserId() {
        return userId;
    }

    public int getNumUserRestaurantMatchCount() {
        return numUserRestaurantMatchCount;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setPopularityTierId(int popularityTierId) {
        this.popularityTierId = popularityTierId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNumUserRestaurantMatchCount(
        int numUserRestaurantMatchCount) {
        this.numUserRestaurantMatchCount = numUserRestaurantMatchCount;
    }

    public int getPopularityTierId() {
        return popularityTierId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

	@Override
	public String toString() {
		return "RestaurantPopularityTierVO [restaurantId=" + restaurantId
				+ ", popularityTierId=" + popularityTierId + ", userId="
				+ userId + ", numUserRestaurantMatchCount="
				+ numUserRestaurantMatchCount + ", rankNumber=" + rankNumber
				+ "]";
	}
    
}
