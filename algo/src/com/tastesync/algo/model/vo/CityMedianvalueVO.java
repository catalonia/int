package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class CityMedianvalueVO implements Serializable {
    private static final long serialVersionUID = 7106605551869915215L;
    String cityId;
    int medianValue;

    public CityMedianvalueVO(String cityId, int medianValue) {
        super();
        this.cityId = cityId;
        this.medianValue = medianValue;
    }

    public String getCityId() {
        return cityId;
    }

    public int getMedianValue() {
        return medianValue;
    }
}
