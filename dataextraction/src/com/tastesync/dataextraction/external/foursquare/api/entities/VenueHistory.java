package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;

/**
 * Class representing VenueHistory entity
 * 
 * @author Antti Leppä
 */
public class VenueHistory implements FoursquareEntity {

  private static final long serialVersionUID = 427279593882572350L;

  /**
   * Returns number of times user has been in this venue
   * 
   * @return number of times user has been in this venue
   */
  public Integer getBeenHere() {
    return beenHere;
  }
  
  /**
   * Returns venue
   * 
   * @return venue
   */
  public CompactVenue getVenue() {
    return venue;
  }
  
  private Integer beenHere;
  private CompactVenue venue;

@Override
public String toString() {
	return "VenueHistory [beenHere=" + beenHere + ", venue=" + venue + "]";
}
  
}
