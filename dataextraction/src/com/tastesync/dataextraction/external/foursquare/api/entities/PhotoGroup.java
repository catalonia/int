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
 * Group of Photos
 * 
 * @author Antti Leppä
 */
public class PhotoGroup extends Group<Photo> {

  private static final long serialVersionUID = -7698755278771196812L;

  @Override
  public Photo[] getItems() {
    return items;
  }
  
  private Photo[] items;

@Override
public String toString() {
	return "PhotoGroup [items=" + Arrays.toString(items) + "]";
}
  
}
