package com.tastesync.dataextraction.model;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Comparator;


public class FactualDataVO implements Serializable {
    private static final long serialVersionUID = -3434740829658753789L;
    private String factualId;
    private String restuarantName;
    private BigDecimal latitude;
    private BigDecimal longtitude;
    boolean latLonAvailable = false;
    private String phoneNumber;

    public FactualDataVO() {
        super();
    }

    public FactualDataVO(String factualId, String restuarantName,
        BigDecimal latitude, BigDecimal longtitude, String phoneNumber) {
        super();
        this.factualId = factualId;
        this.restuarantName = restuarantName;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.latLonAvailable = (((latitude != null) &&
            (latitude.compareTo(new BigDecimal(0)) == 0)) &&
            ((longtitude != null) &&
            (longtitude.compareTo(new BigDecimal(0)) == 0))) ? true : false;
        this.phoneNumber = phoneNumber;
    }

    public String getFactualId() {
        return factualId;
    }

    public void setFactualId(String factualId) {
        this.factualId = factualId;
    }

    public String getRestuarantName() {
        return restuarantName;
    }

    public void setRestuarantName(String restuarantName) {
        this.restuarantName = restuarantName;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(BigDecimal longtitude) {
        this.longtitude = longtitude;
    }

    public boolean isLatLonAvailable() {
        return latLonAvailable;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "FactualData [factualId=" + factualId + ", restuarantName=" +
        restuarantName + ", latitude=" + latitude + ", longtitude=" +
        longtitude + ", latLonAvailable=" + latLonAvailable + ", phoneNumber=" +
        phoneNumber + "]";
    }

    public class FactualDataComparator implements Comparator<FactualDataVO> {
        @Override
        public int compare(FactualDataVO o1, FactualDataVO o2) {
            return o1.factualId.compareTo(o2.factualId);
        }
    }
}
