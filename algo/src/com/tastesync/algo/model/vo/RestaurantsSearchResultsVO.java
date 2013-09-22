package com.tastesync.algo.model.vo;

import java.io.Serializable;

import java.util.List;


public class RestaurantsSearchResultsVO implements Serializable {
    private static final long serialVersionUID = 3519688553480149246L;
    private String maxPaginationId;
    private List<String> restaurantIdList = null;

    public RestaurantsSearchResultsVO() {
        super();
    }

    public RestaurantsSearchResultsVO(String maxPaginationId,
			List<String> restaurantIdList) {
		super();
		this.maxPaginationId = maxPaginationId;
		this.restaurantIdList = restaurantIdList;
	}

	public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getMaxPaginationId() {
        return maxPaginationId;
    }

    public List<String> getRestaurantIdList() {
        return restaurantIdList;
    }
}
