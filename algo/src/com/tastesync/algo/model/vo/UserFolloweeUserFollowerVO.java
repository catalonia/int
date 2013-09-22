package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class UserFolloweeUserFollowerVO implements Serializable {
    private static final long serialVersionUID = -7496905841321798130L;
    private String userFollowee;
    private String userFollower;

    public UserFolloweeUserFollowerVO() {
        super();
    }

    public UserFolloweeUserFollowerVO(String userFollowee, String userFollower) {
        super();
        this.userFollowee = userFollowee;
        this.userFollower = userFollower;
    }

    public String getUserFollowee() {
        return userFollowee;
    }

    public String getUserFollower() {
        return userFollower;
    }

    public void setUserFollowee(String userFollowee) {
        this.userFollowee = userFollowee;
    }

    public void setUserFollower(String userFollower) {
        this.userFollower = userFollower;
    }
}
