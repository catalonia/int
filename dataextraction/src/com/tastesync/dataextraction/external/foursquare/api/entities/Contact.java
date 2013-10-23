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
 * Class representing Contact entity
 * 
 * @author Antti Leppä
 */
public class Contact implements FoursquareEntity {
  
  private static final long serialVersionUID = -7810041187718129997L;
  
  /**
   * Returns phone number
   * 
   * @return phone number
   */
  public String getPhone() {
    return phone;
  }
  
  /**
   * Returns twitter id
   * 
   * @return twitter id
   */
  public String getTwitter() {
    return twitter;
  }
  
  /**
   * Returns user's email
   * 
   * @return user's email
   */
  public String getEmail() {
    return email;
  }
  
  /**
   * Returns user's facebook id
   * 
   * @return user's facebook id
   */
  public String getFacebook() {
    return facebook;
  }
  
  private String email;
  private String facebook;
  private String twitter;
  private String phone;

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
    return "Contact [email=" + email + ", facebook=" + facebook + ", twitter="
            + twitter + ", phone=" + phone + "]";
}
  
  
}
