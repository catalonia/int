package com.tastesync.algo.model.vo;

import java.io.Serializable;

import java.util.List;


public class RestaurantNeighbourhoodVO implements Serializable {
    private static final long serialVersionUID = 8571260399337690991L;
    private String restaurantId;
    private List<String> neighbourhoodIdList;

    public RestaurantNeighbourhoodVO() {
        super();
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public List<String> getNeighbourhoodIdList() {
        return neighbourhoodIdList;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setNeighbourhoodIdList(List<String> neighbourhoodIdList) {
        this.neighbourhoodIdList = neighbourhoodIdList;
    }
}
