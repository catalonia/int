package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;


public class Menu implements FoursquareEntity {
    private static final long serialVersionUID = 8330530197523459648L;
    private Provider provider;
    private Menus menus;

    public Provider getProvider() {
        return provider;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Menus getMenus() {
        return menus;
    }

    @Override
    public String toString() {
        return "Menu [provider=" + provider + ", menus=" + menus + "]";
    }
}
