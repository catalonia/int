package com.tastesync.fb.model.fqlmodel;

import com.restfb.Facebook;


public class FqlUser {
    @Facebook
    public String age_range;
    @Facebook
    public String birthday_date;
    @Facebook
    public String current_location;
    @Facebook
    public String devices;
    @Facebook
    public String email;
    @Facebook
    public String first_name;
    @Facebook
    public String friend_count;
    @Facebook
    public String hometown_location;
    @Facebook
    public String is_verified;
    @Facebook
    public String last_name;
    @Facebook
    public String likes_count;
    @Facebook
    public String locale;
    @Facebook
    public String middle_name;
    @Facebook
    public String name;
    @Facebook
    public String pic_square;
    @Facebook
    public String profile_url;
    @Facebook
    public String sex;
    @Facebook
    public String subscriber_count;
    @Facebook
    public String third_party_id;
    @Facebook
    public String timezone;
    @Facebook
    public String uid;
    @Facebook
    public String username;

    @Override
    public String toString() {
        return "FqlUser [age_range=" + age_range + ", birthday_date=" +
        birthday_date + ", current_location=" + current_location +
        ", devices=" + devices + ", email=" + email + ", first_name=" +
        first_name + ", friend_count=" + friend_count + ", hometown_location=" +
        hometown_location + ", is_verified=" + is_verified + ", last_name=" +
        last_name + ", likes_count=" + likes_count + ", locale=" + locale +
        ", middle_name=" + middle_name + ", name=" + name + ", pic_square=" +
        pic_square + ", profile_url=" + profile_url + ", sex=" + sex +
        ", subscriber_count=" + subscriber_count + ", third_party_id=" +
        third_party_id + ", timezone=" + timezone + ", uid=" + uid +
        ", username=" + username + "]";
    }
}
