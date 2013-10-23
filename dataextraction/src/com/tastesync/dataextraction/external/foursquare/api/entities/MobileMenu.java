package com.tastesync.dataextraction.external.foursquare.api.entities;

import com.tastesync.dataextraction.external.foursquare.api.FoursquareEntity;

public class MobileMenu implements FoursquareEntity {

	private static final long serialVersionUID = 966305108684111122L;
	private String type;
    private String url;
    private String mobileUrl;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    @Override
    public String toString() {
        return "MobileMenu [type=" + type + ", url=" + url + ", mobileUrl=" +
        mobileUrl + "]";
    }
}
