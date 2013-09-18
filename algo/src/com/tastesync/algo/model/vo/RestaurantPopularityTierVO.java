package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class RestaurantPopularityTierVO implements Serializable {
    private static final long serialVersionUID = 4886210352528952164L;
    String restaurantId;
    String popularityTierId;
    String userId;
    String numUserRestaurantMatchCount;
    int rankNumber;

    public RestaurantPopularityTierVO(String restaurantId,
        String popularityTierId, String userId,
        String numUserRestaurantMatchCount, int rankNumber) {
        super();
        this.restaurantId = restaurantId;
        this.popularityTierId = popularityTierId;
        this.userId = userId;
        this.numUserRestaurantMatchCount = numUserRestaurantMatchCount;
        this.rankNumber = rankNumber;
    }

    public RestaurantPopularityTierVO(String restaurantId,
        String popularityTierId) {
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

    public String getNumUserRestaurantMatchCount() {
        return numUserRestaurantMatchCount;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setPopularityTierId(String popularityTierId) {
        this.popularityTierId = popularityTierId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNumUserRestaurantMatchCount(
        String numUserRestaurantMatchCount) {
        this.numUserRestaurantMatchCount = numUserRestaurantMatchCount;
    }

    public String getPopularityTierId() {
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
