package com.tastesync.dataextraction.external.foursquare.api.entities;

import java.util.Arrays;


public class Menus extends Group<MenuDetails> {
    private static final long serialVersionUID = 9175607249410336782L;
    private MenuDetails[] items;

    @Override
    public MenuDetails[] getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Menus [items=" + Arrays.toString(items) + "]";
    }
}
