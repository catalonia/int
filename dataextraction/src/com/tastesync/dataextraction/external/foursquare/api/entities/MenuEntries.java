package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;

import java.util.Arrays;


public class MenuEntries extends Group<Sections> implements FoursquareEntity {
    private static final long serialVersionUID = 7745035021637880794L;
    private Sections[] items;

    @Override
    public Sections[] getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "MenuEntries [items=" + Arrays.toString(items) + "]";
    }
}
