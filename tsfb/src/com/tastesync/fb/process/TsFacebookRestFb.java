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
import com.tastesync.fb.vo.TsUser;

import java.util.ArrayList;
import java.util.List;


public class TsFacebookRestFb {
    public static String USER_QUERY = "SELECT uid, name, first_name, middle_name, last_name, email, locale, current_location, pic_square, sex, hometown_location, birthday_date, age_range, devices, friend_count, is_verified, likes_count, profile_url, subscriber_count, third_party_id, timezone, username " +
        "FROM user WHERE uid=me()";
    public static String FRIENDS_USER_QUERY = "SELECT uid, name, first_name, middle_name, last_name, locale, current_location, pic_square,sex, hometown_location, birthday_date, devices, friend_count, is_verified, subscriber_count, third_party_id, username " +
        //"FROM user WHERE uid = me() OR uid IN (SELECT uid2 FROM friend WHERE uid1 = me())";
        " FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me())";
    private static boolean printExtraDebug = false;
    private FbDAO fbDAO = new FbDAOImpl();

    public TsFacebookRestFb() {
        super();
    }

    public TsUser getTsUserAfterProcessingUserAndFriendDataBasedonFbSingleAccessToken(
        TSDataSource tsDataSource, java.sql.Connection connection,
        String accessToken, String identifierForVendor)
        throws TasteSyncException {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

        List<FqlUser> users = facebookClient.executeFqlQuery(USER_QUERY,
                FqlUser.class);

        if (printExtraDebug) {
            System.out.println("Users: " + users);
        }

        if ((users == null) || users.isEmpty()) {
            throw new TasteSyncException(
                "No User data found using Facebook Query data using accessToken=" +
                accessToken);
        }

        FqlUser currentUserFB = users.get(0);
        String userFbId = currentUserFB.uid;

        fbDAO.submitUsersData(tsDataSource, connection, users);

        TsUser currentTsUser = null;

        // create users if not existing
        fbDAO.createOrUpdateUserFrmFbData(tsDataSource, connection,
            currentUserFB, identifierForVendor, accessToken);
        currentTsUser = fbDAO.getUserInformationByFacebookID(tsDataSource,
                connection, currentUserFB.uid);

        List<FqlFriend> friends = facebookClient.executeFqlQuery(FRIENDS_USER_QUERY,
                FqlFriend.class);

        if ((friends != null) && !friends.isEmpty()) {
            // get existing user Id
            String existingUserId = fbDAO.getExistingUserIdBasedOnFBId(tsDataSource,
                    connection, userFbId);

            if (printExtraDebug) {
                System.out.println("existingUserId=" + existingUserId);
            }

            fbDAO.submitFriendsData(tsDataSource, connection, friends,
                userFbId, existingUserId);

            List<TsUser> friendsListUsingTasteSync = new ArrayList<TsUser>();

            if ((friends != null) && !friends.isEmpty()) {
                for (FqlFriend fqlFriend : friends) {
                    if (printExtraDebug) {
                        System.out.println("fqlFriend=" + fqlFriend);
                    }

                    TsUser tsUser = fbDAO.getUserInformationByFacebookID(tsDataSource,
                            connection, fqlFriend.uid);

                    if (tsUser != null) {
                        friendsListUsingTasteSync.add(tsUser);
                    }
                }
            }

            fbDAO.submitTrustedFriends(tsDataSource, connection,
                friendsListUsingTasteSync, currentTsUser.getUserId());
        }

        return currentTsUser;
    }

    private void processUserAndFriendDataBasedonFbSingleAccessToken(
        TSDataSource tsDataSource, java.sql.Connection connection,
        String accessToken) throws TasteSyncException {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

        List<FqlUser> users = facebookClient.executeFqlQuery(USER_QUERY,
                FqlUser.class);

        if (printExtraDebug) {
            System.out.println("Users: " + users);
        }

        if ((users == null) || users.isEmpty()) {
            throw new TasteSyncException(
                "No User data found using Facebook Query data using accessToken=" +
                accessToken);
        }

        FqlUser currentUserFB = users.get(0);
        String userFbId = currentUserFB.uid;

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

        if (printExtraDebug) {
            System.out.println(user);
        }

        Connection<User> myFriends = facebookClient.fetchConnection("me/friends",
                User.class);

        //        Connection<Post> myFeed = facebookClient.fetchConnection("me/feed",
        //                Post.class);
        if (printExtraDebug) {
            System.out.println(myFriends);
            System.out.println("Count of my friends: " +
                myFriends.getData().size());
        }

        //        System.out.println("First item in my feed: " + myFeed.getData().get(0));
        List<FqlUser> users = facebookClient.executeFqlQuery(USER_QUERY,
                FqlUser.class);

        if (printExtraDebug) {
            System.out.println("Users: " + users);
        }

        List<FqlFriend> friend = facebookClient.executeFqlQuery(FRIENDS_USER_QUERY,
                FqlFriend.class);

        if (printExtraDebug) {
            for (FqlFriend fqlFriend : friend) {
                System.out.println("fqlFriend=" + fqlFriend);
            }
        }
    }
}
