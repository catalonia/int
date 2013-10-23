package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;


public class MenuDetails implements FoursquareEntity {
    /**
         *
         */
    private static final long serialVersionUID = 6092279086447345772L;
    private String menuId;
    private String name;
    private String description;
    private MenuEntries entries;

    public MenuEntries getEntries() {
        return entries;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "MenuDetails [menuId=" + menuId + ", name=" + name +
        ", description=" + description + ", entries=" + entries + "]";
    }
}
