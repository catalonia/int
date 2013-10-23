package com.tastesync.dataextraction.external.foursquare.api.entities;

import java.util.Arrays;

/**
 * Group of VenueHistories
 * 
 * @author Antti Leppä
 */
public class VenueHistoryGroup extends Group<VenueHistory> {

  private static final long serialVersionUID = 7288520717618562618L;

  @Override
  public VenueHistory[] getItems() {
    return items;
  }
  
  private VenueHistory[] items;

@Override
public String toString() {
	return "VenueHistoryGroup [items=" + Arrays.toString(items) + "]";
}
  
}
