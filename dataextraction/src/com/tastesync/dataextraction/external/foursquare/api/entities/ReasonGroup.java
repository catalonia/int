package com.tastesync.dataextraction.external.foursquare.api.entities;

import java.util.Arrays;

/**
 * Group of Reasons
 * 
 * @author Antti Leppä
 */
public class ReasonGroup extends Group<Reason> {

  private static final long serialVersionUID = 8674537442804669148L;

  @Override
  public Reason[] getItems() {
    return items;
  }
  
  private Reason[] items;

@Override
public String toString() {
	return "ReasonGroup [items=" + Arrays.toString(items) + "]";
}
  
}
