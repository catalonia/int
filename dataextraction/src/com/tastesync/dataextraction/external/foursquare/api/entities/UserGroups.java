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
 * Group of UserGroups
 * 
 * @author Antti Leppä
 */
public class UserGroups extends Count {

  private static final long serialVersionUID = -7444569523527922004L;

  /**
   * Returns user groups
   * 
   * @return user groups
   */
  public UserGroup[] getGroups() {
    return groups;
  }
  
  private UserGroup[] groups;

@Override
public String toString() {
	return "UserGroups [groups=" + Arrays.toString(groups) + "]";
}
  
}
