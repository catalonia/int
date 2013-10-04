package com.tastesync.algo.model.vo;

import java.io.Serializable;

import java.util.List;


public class CityNeighbourhoodVO implements Serializable {
    private static final long serialVersionUID = -8532547896675729703L;
    private String cityId;
    private String neighbourhoodId;
    private List<String> neighbourhoodIdList;

    public CityNeighbourhoodVO(String cityId, String neighbourhoodId) {
        super();
        this.cityId = cityId;
        this.neighbourhoodId = neighbourhoodId;
    }

    public CityNeighbourhoodVO(String cityId, List<String> neighbourhoodIdList) {
        super();
        this.cityId = cityId;
        this.neighbourhoodIdList = neighbourhoodIdList;
    }

    public String getCityId() {
        return cityId;
    }

    public String getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public List<String> getNeighbourhoodIdList() {
        return neighbourhoodIdList;
    }
}
