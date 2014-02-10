package com.tastesync.fb.vo;

public class City {
    private String city;
    private String cityId;
    private String country;
    private String state;

    public City() {
        super();
    }

    public String getCity() {
        return city;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "City [city=" + city + ", cityId=" + cityId + ", country=" +
        country + ", state=" + state + "]";
    }
}
