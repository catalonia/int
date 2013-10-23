package com.tastesync.dataextraction.model;

import java.io.Serializable;


public class FlaggedRestaurantVO implements Serializable {
    private static final long serialVersionUID = -4382283863444720895L;
    private String restaurantId;
    private String pullEligIndOrder;
    private FactualDataVO factualDataVO;

    public FlaggedRestaurantVO(String restaurantId, String pullEligIndOrder,
        FactualDataVO factualDataVO) {
        super();
        this.restaurantId = restaurantId;
        this.pullEligIndOrder = pullEligIndOrder;
        this.factualDataVO = factualDataVO;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getPullEligIndOrder() {
        return pullEligIndOrder;
    }

    public FactualDataVO getFactualDataVO() {
        return factualDataVO;
    }
}
