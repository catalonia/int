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
 * Group of TipGroups
 * 
 * @author Antti Leppä
 */
public class Tips extends Count {
  
  private static final long serialVersionUID = 6598277929123957554L;

  /**
   * Returns tip groups
   * 
   * @return tip groups
   */
  public TipGroup[] getGroups() {
    return groups;
  }
  
  private TipGroup[] groups;

@Override
public String toString() {
	return "Tips [groups=" + Arrays.toString(groups) + "]";
}
  
}
