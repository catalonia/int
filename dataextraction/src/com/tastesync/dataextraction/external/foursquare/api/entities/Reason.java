package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;

/**
 * Class representing Reason entity
 * 
 * @author Antti Leppä
 */
public class Reason implements FoursquareEntity {

  private static final long serialVersionUID = -6388056927086935959L;

  /**
   * Returns type
   * 
   * @return type
   */
  public String getType() {
    return type;
  }
  
  /**
   * Returns message
   * 
   * @return message
   */
  public String getMessage() {
    return message;
  }
  
  private String type;
  private String message;

@Override
public String toString() {
	return "Reason [type=" + type + ", message=" + message + "]";
}
  
}