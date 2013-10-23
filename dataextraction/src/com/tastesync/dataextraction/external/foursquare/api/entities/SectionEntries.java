package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;


public class SectionEntries implements FoursquareEntity {
    private static final long serialVersionUID = -7865944533920641269L;
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SectionEntries [name=" + name + "]";
    }
}
