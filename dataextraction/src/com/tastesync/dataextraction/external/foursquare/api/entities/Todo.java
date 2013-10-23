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
 * Class representing Todo entity
 * 
 * @see <a href="https://developer.foursquare.com/docs/responses/todo.html" target="_blank">https://developer.foursquare.com/docs/responses/todo.html</a>
 * 
 * @author Antti Leppä
 */
public class Todo implements FoursquareEntity {

  private static final long serialVersionUID = 8713217262629234118L;

  /**
   * Returns to-do's id
   * 
   * @return to-do's id
   */
  public Long getCreatedAt() {
    return createdAt;
  }
  
  /**
   * Returns seconds since epoch when this to-do was created.
   * 
   * @return seconds since epoch when this to-do was created. 
   */
  public String getId() {
    return id;
  }
  
  /**
   * Returns the tip which is to-do 
   * 
   * @return the tip which is to-do 
   */
  public CompleteTip getTip() {
    return tip;
  }
  
  private String id;
  private Long createdAt;
  private CompleteTip tip;

@Override
public String toString() {
	return "Todo [id=" + id + ", createdAt=" + createdAt + ", tip=" + tip + "]";
}
  
}
