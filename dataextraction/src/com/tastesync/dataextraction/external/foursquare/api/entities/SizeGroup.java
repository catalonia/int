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
 * Group of Sizes
 * 
 * @author Antti Leppä
 */
public class SizeGroup extends Group<Size> {

  private static final long serialVersionUID = 8028153409437582383L;

  @Override
  public Size[] getItems() {
    return items;
  }
  
  private Size[] items;

@Override
public String toString() {
	return "SizeGroup [items=" + Arrays.toString(items) + "]";
}
  
}
