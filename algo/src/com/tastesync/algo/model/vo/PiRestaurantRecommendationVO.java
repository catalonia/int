package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class PiRestaurantRecommendationVO implements Serializable {
	private static final long serialVersionUID = -4672894980544202140L;
	private String restaurantId;
    private String reccomendationText;
    private String recommendationId;

    public PiRestaurantRecommendationVO(String restaurantId,
        String reccomendationText, String recommendationId) {
        super();
        this.restaurantId = restaurantId;
        this.reccomendationText = reccomendationText;
        this.recommendationId = recommendationId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getReccomendationText() {
        return reccomendationText;
    }

    public String getRecommendationId() {
        return recommendationId;
    }
}
