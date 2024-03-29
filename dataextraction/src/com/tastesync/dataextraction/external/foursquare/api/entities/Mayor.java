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

/**
 * Class representing Mayor entity
 * 
 * @author Antti Leppä
 */
public class Mayor extends Count {

  private static final long serialVersionUID = -6838261267509832567L;

  /**
   * Returns user
   * 
   * @return user
   */
  public CompactUser getUser() {
    return user;
  }
  
  private CompactUser user;

@Override
public String toString() {
	return "Mayor [user=" + user + "]";
}
  
}
