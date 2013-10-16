package com.tastesync.push.model.vo;

import java.io.Serializable;


public class NotificationsPushTextDataVO implements Serializable {
    private static final long serialVersionUID = 485822460696227076L;
    private String firstName;
    private String lastName;

    public NotificationsPushTextDataVO(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
