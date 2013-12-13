package com.tastesync.fb.process;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

import com.restfb.types.User;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.fb.db.dao.FbDAO;
import com.tastesync.fb.db.dao.FbDAOImpl;
import com.tastesync.fb.exception.TasteSyncException;
import com.tastesync.fb.model.fqlmodel.FqlFriend;
import com.tastesync.fb.model.fqlmodel.FqlUser;

import java.util.List;


public class TsFacebookRestFb {
    public static String USER_QUERY = "SELECT uid, name, first_name, middle_name, last_name, email, locale, current_location, pic_square, sex, hometown_location, birthday_date, age_range, devices, friend_count, is_verified, likes_count, profile_url, subscriber_count, third_party_id, timezone, username " +
        "FROM user WHERE uid=me()";
    public static String FRIENDS_USER_QUERY = "SELECT uid, name, first_name, middle_name, last_name, locale, current_location, pic_square,sex, hometown_location, birthday_date, devices, friend_count, is_verified, subscriber_count, third_party_id, username " +
        //"FROM user WHERE uid = me() OR uid IN (SELECT uid2 FROM friend WHERE uid1 = me())";
        " FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me())";
    private FbDAO fbDAO = new FbDAOImpl();

    public TsFacebookRestFb() {
        super();
    }

    public void processUserAndFriendDataBasedonFbSingleAccessToken(
        TSDataSource tsDataSource, java.sql.Connection connection,
        String accessToken) throws TasteSyncException {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

        List<FqlUser> users = facebookClient.executeFqlQuery(USER_QUERY,
                FqlUser.class);
        System.out.println("Users: " + users);

        if ((users == null) || users.isEmpty()) {
            System.out.println(
                "No User data found using Facebook Query data using accessToken=" +
                accessToken);

            return;
        }

        String userFbId = users.get(0).uid;

        fbDAO.submitUsersData(tsDataSource, connection, users);

        List<FqlFriend> friends = facebookClient.executeFqlQuery(FRIENDS_USER_QUERY,
                FqlFriend.class);

        if ((friends != null) && !friends.isEmpty()) {
            // get existing user Id
            String existingUserId = fbDAO.getExistingUserIdBasedOnFBId(tsDataSource,
                    connection, userFbId);
            System.out.println("existingUserId=" + existingUserId);
            fbDAO.submitFriendsData(tsDataSource, connection, friends,
                userFbId, existingUserId);
        }
    }

    private void testProcessTsFacebookRestFb(String MY_ACCESS_TOKEN) {
        FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);
        User user = facebookClient.fetchObject("665136379", User.class);
        System.out.println(user);

        Connection<User> myFriends = facebookClient.fetchConnection("me/friends",
                User.class);
        //        Connection<Post> myFeed = facebookClient.fetchConnection("me/feed",
        //                Post.class);
        System.out.println(myFriends);
        System.out.println("Count of my friends: " +
            myFriends.getData().size());

        //        System.out.println("First item in my feed: " + myFeed.getData().get(0));
        List<FqlUser> users = facebookClient.executeFqlQuery(USER_QUERY,
                FqlUser.class);

        System.out.println("Users: " + users);

        List<FqlFriend> friend = facebookClient.executeFqlQuery(FRIENDS_USER_QUERY,
                FqlFriend.class);

        for (FqlFriend fqlFriend : friend) {
            System.out.println("fqlFriend=" + fqlFriend);
        }
    }
}
