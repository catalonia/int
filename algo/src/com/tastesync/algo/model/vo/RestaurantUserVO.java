package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class RestaurantUserVO implements Serializable {
    private static final long serialVersionUID = -5598607948248769749L;
    private String userId;
    private String restaurantId;

    public RestaurantUserVO(String userId, String restaurantId) {
        super();
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

	@Override
	public String toString() {
		return "RestaurantUserVO [userId=" + userId + ", restaurantId="
				+ restaurantId + "]";
	}
    
    
}
