package com.tastesync.push.model.vo;

import java.io.Serializable;


public class UserNotificationsPushVO implements Serializable {
    private static final long serialVersionUID = -821310246036608125L;
    private String userId;
    private String notificationType;
    private String linkedId;

    public UserNotificationsPushVO(String userId, String notificationType,
        String linkedId) {
        super();
        this.userId = userId;
        this.notificationType = notificationType;
        this.linkedId = linkedId;
    }

    public String getUserId() {
        return userId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public String getLinkedId() {
        return linkedId;
    }
}
