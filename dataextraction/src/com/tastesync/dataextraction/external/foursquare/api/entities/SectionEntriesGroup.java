package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;

import java.util.Arrays;


public class SectionEntriesGroup extends Group<SectionEntries>
    implements FoursquareEntity {
    private static final long serialVersionUID = -2169846910395277417L;
    private SectionEntries[] items;

    @Override
    public SectionEntries[] getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "SectionEntriesGroup [items=" + Arrays.toString(items) + "]";
    }
}
