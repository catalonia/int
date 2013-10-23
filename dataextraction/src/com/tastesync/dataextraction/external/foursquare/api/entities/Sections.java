package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;


public class Sections implements FoursquareEntity {
    private static final long serialVersionUID = 7786675112606908777L;
    private String sectionId;
    private String name;
    private SectionEntriesGroup entries;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getName() {
        return name;
    }

    public SectionEntriesGroup getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Sections [sectionId=" + sectionId + ", name=" + name +
        ", entries=" + entries + "]";
    }
}
