package com.tastesync.algo.model.vo;

import java.io.Serializable;

import java.util.Arrays;


public class InputRestaurantSearchVO implements Serializable {
    private static final long serialVersionUID = -2575368611727415318L;
    private String userId;
    private String restaurantId;
    private String neighborhoodId;
    private String cityId;
    private String stateName;
    private String[] cuisineTier1IdArray;
    private String[] priceIdList;
    private String rating;
    private String savedFlag;
    private String favFlag;
    private String dealFlag;
    private String chainFlag;
    private String paginationId;
    private String[] cuisineTier2IdArray;
    private String[] themeIdArray;
    private String[] whoareyouwithIdArray;
    private String[] typeOfRestaurantIdArray;
    private String[] occasionIdArray;

    public InputRestaurantSearchVO(String userId, String restaurantId,
        String neighborhoodId, String cityId, String stateName,
        String[] cuisineTier1IdArray, String[] priceIdList, String rating,
        String savedFlag, String favFlag, String dealFlag, String chainFlag,
        String paginationId, String[] cuisineTier2IdArray,
        String[] themeIdArray, String[] whoareyouwithIdArray,
        String[] typeOfRestaurantIdArray, String[] occasionIdArray) {
        super();
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.neighborhoodId = neighborhoodId;
        this.cityId = cityId;
        this.stateName = stateName;
        this.cuisineTier1IdArray = cuisineTier1IdArray;
        this.priceIdList = priceIdList;
        this.rating = rating;
        this.savedFlag = savedFlag;
        this.favFlag = favFlag;
        this.dealFlag = dealFlag;
        this.chainFlag = chainFlag;
        this.paginationId = paginationId;
        this.cuisineTier2IdArray = cuisineTier2IdArray;
        this.themeIdArray = themeIdArray;
        this.whoareyouwithIdArray = whoareyouwithIdArray;
        this.typeOfRestaurantIdArray = typeOfRestaurantIdArray;
        this.occasionIdArray = occasionIdArray;
    }

    public String getUserId() {
        return userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getNeighborhoodId() {
        return neighborhoodId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getStateName() {
        return stateName;
    }

    public String[] getCuisineTier1IdArray() {
        return cuisineTier1IdArray;
    }

    public String[] getPriceIdList() {
        return priceIdList;
    }

    public String getRating() {
        return rating;
    }

    public String getSavedFlag() {
        return savedFlag;
    }

    public String getFavFlag() {
        return favFlag;
    }

    public String getDealFlag() {
        return dealFlag;
    }

    public String getChainFlag() {
        return chainFlag;
    }

    public String getPaginationId() {
        return paginationId;
    }

    public String[] getCuisineTier2IdArray() {
        return cuisineTier2IdArray;
    }

    public String[] getThemeIdArray() {
        return themeIdArray;
    }

    public String[] getWhoareyouwithIdArray() {
        return whoareyouwithIdArray;
    }

    public String[] getTypeOfRestaurantIdArray() {
        return typeOfRestaurantIdArray;
    }

    public String[] getOccasionIdArray() {
        return occasionIdArray;
    }

    public void setChainFlag(String chainFlag) {
        this.chainFlag = chainFlag;
    }

    @Override
    public String toString() {
        return "InputRestaurantSearchVO [userId=" + userId + ", restaurantId=" +
        restaurantId + ", neighborhoodId=" + neighborhoodId + ", cityId=" +
        cityId + ", stateName=" + stateName + ", cuisineTier1IdArray=" +
        Arrays.toString(cuisineTier1IdArray) + ", priceIdList=" +
        Arrays.toString(priceIdList) + ", rating=" + rating + ", savedFlag=" +
        savedFlag + ", favFlag=" + favFlag + ", dealFlag=" + dealFlag +
        ", chainFlag=" + chainFlag + ", paginationId=" + paginationId +
        ", cuisineTier2IdArray=" + Arrays.toString(cuisineTier2IdArray) +
        ", themeIdArray=" + Arrays.toString(themeIdArray) +
        ", whoareyouwithIdArray=" + Arrays.toString(whoareyouwithIdArray) +
        ", typeOfRestaurantIdArray=" +
        Arrays.toString(typeOfRestaurantIdArray) + ", occasionIdArray=" +
        Arrays.toString(occasionIdArray) + "]";
    }
}
