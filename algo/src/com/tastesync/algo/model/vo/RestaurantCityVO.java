package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class RestaurantCityVO implements Serializable {
    private static final long serialVersionUID = -4990373645902947387L;
    private String cityId;
    private String restaurantId;

    public RestaurantCityVO(String cityId, String restaurantId) {
        super();
        this.cityId = cityId;
        this.restaurantId = restaurantId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }
}
