package com.tastesync.dataextraction.external.foursquare.api.entities;

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

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;


/**
 * Class representing User entity
 *
 * @see <a href="https://developer.foursquare.com/docs/responses/user.html" target="_blank">https://developer.foursquare.com/docs/responses/user.html</a>
 *
 * @author Antti Leppä
 */
public class TipCompactUser implements FoursquareEntity {
    
	private static final long serialVersionUID = 3081093708489447056L;
	private String id;
    private String firstName;
    private String lastName;
    private String homeCity;
    private UserPhoto photo;
    private String gender;
    private String relationship;
    private String type;

    public String getType() {
		return type;
	}

	/**
     * Returns user's id
     *
     * @return user's id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns user's first name
     *
     * @return user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns user's last name
     *
     * @return user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns user's home city
     *
     * @return user's home city
     */
    public String getHomeCity() {
        return homeCity;
    }

    /**
     * Returns URL of a profile picture for this user.
     *
     * @return URL of a profile picture for this user.
     */
    public UserPhoto getPhoto() {
        return photo;
    }

    /**
     * Returns male or female
     *
     * @return male or female
     */
    public String getGender() {
        return gender;
    }

    /**
     * The relationship of the acting user to this user. One of self, friend, pendingMe, pendingThem or followingThem
     *
     * @return relationship of the acting user to this user
     */
    public String getRelationship() {
        return relationship;
    }

	@Override
	public String toString() {
		return "TipCompactUser [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", homeCity=" + homeCity
				+ ", photo=" + photo + ", gender=" + gender + ", relationship="
				+ relationship + ", type=" + type + "]";
	}


}
