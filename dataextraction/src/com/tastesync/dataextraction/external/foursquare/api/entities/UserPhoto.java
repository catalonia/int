package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;

public class UserPhoto implements FoursquareEntity {

	private static final long serialVersionUID = -1220926084894112015L;
	private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public String toString() {
        return "UserPhoto [prefix=" + prefix + ", suffix=" + suffix + "]";
    }
}
