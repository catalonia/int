package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;

public class Provider implements FoursquareEntity{

	private static final long serialVersionUID = 8641196716517413307L;

	private String name;
	private String attributionImage;
	private String attributionLink;
	private String attributionText;
	public String getName() {
		return name;
	}
	public String getAttributionImage() {
		return attributionImage;
	}
	public String getAttributionLink() {
		return attributionLink;
	}
	public String getAttributionText() {
		return attributionText;
	}
	@Override
	public String toString() {
		return "Provider [name=" + name + ", attributionImage="
				+ attributionImage + ", attributionLink=" + attributionLink
				+ ", attributionText=" + attributionText + "]";
	}
	
}
