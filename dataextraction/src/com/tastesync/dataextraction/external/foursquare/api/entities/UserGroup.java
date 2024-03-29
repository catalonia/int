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
 * Group of Users
 * 
 * @author Antti Leppä
 */
public class UserGroup extends Group<CompactUser> {

  private static final long serialVersionUID = 3181702805520905399L;
  
  @Override
  public CompactUser[] getItems() {
    return items;
  }

  private CompactUser[] items;

@Override
public String toString() {
	return "UserGroup [items=" + Arrays.toString(items) + "]";
}
  
}
