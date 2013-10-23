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

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;

/**
 * Class representing Stats entity
 * 
 * @author Antti Leppä
 */
public class Stats implements FoursquareEntity {

  private static final long serialVersionUID = 1191621361079941540L;

  /**
   * Returns checkins count
   * 
   * @return checkins count
   */
  public Integer getCheckinsCount() {
    return checkinsCount;
  }
  
  /**
   * Returns users count
   * 
   * @return users count
   */
  public Integer getUsersCount() {
    return usersCount;
  }
  
  private Integer checkinsCount;
  private Integer usersCount;

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
    return "Stats [checkinsCount=" + checkinsCount + ", usersCount="
            + usersCount + "]";
}
  
}
