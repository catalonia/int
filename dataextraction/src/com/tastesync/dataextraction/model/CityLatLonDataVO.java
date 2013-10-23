package com.tastesync.dataextraction.model;

import java.io.Serializable;

import java.math.BigDecimal;


public class CityLatLonDataVO implements Serializable {
    private static final long serialVersionUID = -406984627583417833L;
    private int identifierID;
    private String state;
    private String place;
    private String anpsadpi;
    private String ab;
    private BigDecimal iNTPTLAT;
    private BigDecimal iNTPTLNG;
    private String identifier;

    public CityLatLonDataVO(int identifierID, String state, String place,
        String anpsadpi, String ab, BigDecimal iNTPTLAT, BigDecimal iNTPTLNG) {
        super();
        this.identifierID = identifierID;
        this.state = state;
        this.place = place;
        this.anpsadpi = anpsadpi;
        this.ab = ab;
        this.iNTPTLAT = iNTPTLAT;
        this.iNTPTLNG = iNTPTLNG;
        this.identifier = state + place + anpsadpi + ab + iNTPTLAT + iNTPTLNG;
    }

    public CityLatLonDataVO(int identifierID, String state, String place,
        BigDecimal iNTPTLAT, BigDecimal iNTPTLNG) {
        this(identifierID, state, place, "", "", iNTPTLAT, iNTPTLNG);
    }

    public int getIdentifierID() {
        return identifierID;
    }

    public String getState() {
        return state;
    }

    public String getPlace() {
        return place;
    }

    public String getAnpsadpi() {
        return anpsadpi;
    }

    public String getAb() {
        return ab;
    }

    public BigDecimal getiNTPTLAT() {
        return iNTPTLAT;
    }

    public BigDecimal getiNTPTLNG() {
        return iNTPTLNG;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "CityLatLonData [identifierID=" + identifierID + ", state=" +
        state + ", place=" + place + ", anpsadpi=" + anpsadpi + ", ab=" + ab +
        ", iNTPTLAT=" + iNTPTLAT + ", iNTPTLNG=" + iNTPTLNG + ", identifier=" +
        identifier + "]";
    }
}
