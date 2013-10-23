/*
 * FoursquareAPI - Foursquare API for Java
 * Copyright (C) 2008 - 2011 Antti Leppä / Foyt
 * http://www.foyt.fi
 * 
 * License: 
 * 
 * Licensed under GNU Lesser General Public License Version 3 or later (the "LGPL")
 * http://www.gnu.org/licenses/lgpl.html
 */

package com.tastesync.dataextraction.external.foursquare.api.entities;

import java.util.Arrays;

/**
 * Group of Venues
 * 
 * @author Antti Leppä
 */
public class VenueGroup extends Group<CompactVenue> {

  private static final long serialVersionUID = -996401659508844800L;

  @Override
  public CompactVenue[] getItems() {
    return items;
  }
  
  private CompactVenue[] items;

@Override
public String toString() {
	return "VenueGroup [items=" + Arrays.toString(items) + "]";
}
  
}
