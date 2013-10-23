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
 * Class representing Warning entity
 *
 * @author Antti Leppä
 */
public class Warning implements FoursquareEntity {
  
  private static final long serialVersionUID = 7167332454422034014L;

  /**
   * Returns warning text
   * 
   * @return warning text
   */
  public String getText() {
    return text;
  }
  
  private String text;

@Override
public String toString() {
	return "Warning [text=" + text + "]";
}
  
}
