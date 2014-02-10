package com.tastesync.fb.db.dao;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.db.pool.TSDataSource;

import com.tastesync.fb.db.queries.FbQueries;
import com.tastesync.fb.exception.TasteSyncException;
import com.tastesync.fb.json.FBLocation;
import com.tastesync.fb.model.fqlmodel.FqlFriend;
import com.tastesync.fb.model.fqlmodel.FqlUser;
import com.tastesync.fb.vo.City;
import com.tastesync.fb.vo.TsUser;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;


public class FbDAOImpl implements FbDAO {
    public static final String DEFAULT_CITY_ID = "1";
    public static final String DEFAULT_CITY_NAME = "Anchorage";
    public static final String DEFAULT_STATE = "AK";
    public static final String DEFAULT_COUNTRY = "us";
    private static final boolean printExtraDebug = false;

    @Override
    public void createOrUpdateUserFrmFbData(TSDataSource tsDataSource,
        Connection connection, FqlUser currentUserFB,
        String identifierForVendor, String accessToken)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            FBLocation currentLocation = null;
            String cityFB = null;
            String userCityId = null;
            String stateFB = null;
            String countryFB = null;

            if (currentUserFB.current_location != null) {
                currentLocation = mapper.readValue(currentUserFB.current_location,
                        FBLocation.class);
                stateFB = currentLocation.getState();
                countryFB = currentLocation.getCountry();
                cityFB = currentLocation.getCity();

                if ((stateFB != null) && !stateFB.isEmpty() &&
                        (cityFB != null) && !cityFB.isEmpty()) {
                    City city = getCityInforByStateAndCityName(tsDataSource,
                            connection, stateFB, cityFB);

                    if (city != null) {
                        userCityId = city.getCityId();
                    }
                }
            }

            if (userCityId == null) {
                cityFB = null;
                userCityId = null;
                stateFB = null;
                countryFB = null;

                FBLocation hometownLocation = null;

                if (currentUserFB.hometown_location != null) {
                    hometownLocation = mapper.readValue(currentUserFB.hometown_location,
                            FBLocation.class);
                    stateFB = hometownLocation.getState();
                    countryFB = hometownLocation.getCountry();
                    cityFB = hometownLocation.getCity();

                    if ((stateFB != null) && !stateFB.isEmpty() &&
                            (cityFB != null) && !cityFB.isEmpty()) {
                        City city = getCityInforByStateAndCityName(tsDataSource,
                                connection, stateFB, cityFB);

                        if (city != null) {
                            userCityId = city.getCityId();
                        }
                    }
                }
            }

            String state = (userCityId == null) ? DEFAULT_STATE : stateFB;
            String country = (userCityId == null) ? DEFAULT_COUNTRY : countryFB;

            String cityId = (userCityId == null) ? DEFAULT_CITY_ID : userCityId;

            String userID = null;
            TsUser currentUser = getUserInformationByFacebookID(tsDataSource,
                    connection, currentUserFB.uid);

            if (currentUser == null) {
                userID = cityId + "-" +
                    CommonFunctionsUtil.generateUniqueKey();
                statement = connection.prepareStatement(FbQueries.USERS_INSERT_SQL);
                statement.setString(1, currentUserFB.email);
                statement.setTimestamp(2,
                    CommonFunctionsUtil.getCurrentDateTimestamp());
                statement.setString(3, currentUserFB.first_name);
                statement.setString(4, currentUserFB.last_name);
                statement.setString(5, currentUserFB.sex);
                statement.setString(6, cityId);
                statement.setString(7, state);
                statement.setString(8, country);
                statement.setString(9, currentUserFB.uid);
                statement.setString(10, userID);
                statement.setString(11, userID);
                statement.executeUpdate();
                statement.close();
                currentUser = getUserInformationByFacebookID(tsDataSource,
                        connection, currentUserFB.uid);
            } else {
                statement = connection.prepareStatement(FbQueries.USERS_UPDATE_SQL);
                statement.setString(1, currentUserFB.email);
                statement.setString(2, currentUserFB.first_name);
                statement.setString(3, currentUserFB.last_name);
                statement.setString(4, currentUserFB.sex);
                statement.setString(5, cityId);
                statement.setString(6, state);
                statement.setString(7, country);
                statement.setString(8, currentUser.getUserId());

                statement.execute();
                statement.close();
            }

            statement = connection.prepareStatement(FbQueries.USER_FB_ACCESS_INSERT_SQL);

            statement.setString(1, accessToken);
            statement.setTimestamp(2,
                CommonFunctionsUtil.getCurrentDateTimestamp());

            String fbAccessTokenId = CommonFunctionsUtil.generateUniqueKey();
            statement.setString(3, fbAccessTokenId);
            statement.setString(4, "0");
            statement.setTimestamp(5,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setString(6, currentUser.getUserFbId());
            statement.setString(7, currentUser.getUserId());
            statement.setString(8, identifierForVendor);

            statement.setTimestamp(9,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setString(10, identifierForVendor);

            statement.executeUpdate();
            statement.close();

            statement = connection.prepareStatement(FbQueries.USER_ONLINE_UPDATE_SQL);
            statement.setString(1, String.valueOf("y"));
            statement.setTimestamp(2,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setString(3, currentUser.getUserId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException("Error while createUserFrmFbData " +
                e.getMessage());
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new TasteSyncException("createUserFrmFbData " +
                e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new TasteSyncException("createUserFrmFbData " +
                e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new TasteSyncException("createUserFrmFbData " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getAllDeviceTokensForSingleUser(
        TSDataSource tsDataSource, Connection connection, String userId)
        throws com.tastesync.fb.exception.TasteSyncException {
        // TODO Auto-generated method stub
        return null;
    }

    public City getCityInforByStateAndCityName(TSDataSource tsDataSource,
        Connection connection, String state, String city_name)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(FbQueries.CITY_STATE_SELECT_SQL);
            statement.setString(1, state);
            statement.setString(2, city_name);

            resultset = statement.executeQuery();

            City city = null;

            if (resultset.next()) {
                city = new City();
                city.setCityId(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("CITIES.CITY_ID")));
                city.setCountry(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("CITIES.COUNTRY")));
                city.setState(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("CITIES.STATE")));
                city.setCity(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("CITIES.CITY")));
            }

            statement.close();

            return city;
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while getCityInforByStateAndCityName " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public String getExistingUserIdBasedOnFBId(TSDataSource tsDataSource,
        Connection connection, String userFbId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(FbQueries.TS_USER_ID_FROM_USERS_SELECT_SQL);
            statement.setString(1, userFbId);
            resultset = statement.executeQuery();

            String existingUserId = null;

            while (resultset.next()) {
                existingUserId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "USERS.USER_ID"));
            }

            statement.close();

            return existingUserId;
        } catch (SQLException e) {
            e.printStackTrace();

            throw new TasteSyncException(
                "Error while getExistingUserIdBasedOnFBId " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public TsUser getUserInformationByFacebookID(TSDataSource tsDataSource,
        Connection connection, String userFbId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(FbQueries.USER_FBID_SELECT_SQL);
            statement.setString(1, userFbId);
            statement.setString(2, String.valueOf("e"));
            statement.setString(3, String.valueOf("p"));

            resultset = statement.executeQuery();

            TsUser user = null;

            if (resultset.next()) {
                user = new TsUser();
                user.setUserId(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.USER_ID")));
                user.setTsUserId(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.TS_USER_ID")));
                user.setTsUserEmail(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.TS_USER_EMAIL")));
                user.setTsFirstName(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.TS_FIRST_NAME")));
                user.setTsLastName(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.TS_LAST_NAME")));
                user.setMaxInvites(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.MAX_INVITES")));
                user.setUserCreatedInitialDatetime(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "users.USER_CREATED_INITIAL_DATETIME")));
                user.setUserPoints(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.USER_POINTS")));
                user.setTwitterUsrUrl(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.TWITTER_USR_URL")));
                user.setUserDisabledFlag(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.USER_DISABLED_FLAG")));
                user.setUserActivationKey(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.USER_ACTIVATION_KEY")));
                user.setUserGender(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.USER_GENDER")));
                user.setUserCityId(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.USER_CITY_ID")));
                user.setUserState(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.USER_STATE")));
                user.setIsOnline(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.IS_ONLINE")));
                user.setUserCountry(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.USER_COUNTRY")));
                user.setAbout(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.ABOUT")));
                user.setCurrentStatus(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.CURRENT_STATUS")));
                user.setUserFbId(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("users.USER_FB_ID")));
            }

            statement.close();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while getCityInforByStateAndCityName " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void submitFriendsData(TSDataSource tsDataSource,
        Connection connection, List<FqlFriend> friends, String userFbId,
        String existingUserId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            Timestamp currentDateTimestamp = null;
            String status = "ACTIVE";

            tsDataSource.begin();

            // check user already exist or not from email id
            ArrayList<String> existingFriends = new ArrayList<String>();

            for (FqlFriend fqlFriend : friends) {
                if (printExtraDebug) {
                    System.out.println("fqlFriend=" + fqlFriend);
                }

                existingFriends.add(fqlFriend.uid);
                currentDateTimestamp = CommonFunctionsUtil.getCurrentDateTimestamp();

                if (printExtraDebug) {
                    System.out.println(fqlFriend.current_location);
                }

                FBLocation currentLocation = null;

                if (fqlFriend.current_location != null) {
                    currentLocation = mapper.readValue(fqlFriend.current_location,
                            FBLocation.class);
                    statement = connection.prepareStatement(FbQueries.FB_LOCATION_DATA_INSERT_SQL);
                    statement.setString(1, currentLocation.getCity());
                    statement.setString(2, currentLocation.getCountry());
                    statement.setDouble(3, currentLocation.getLatitude());
                    statement.setString(4, currentLocation.getId());
                    statement.setString(5, currentLocation.getName());
                    statement.setDouble(6, currentLocation.getLongitude());
                    statement.setString(7, currentLocation.getState());
                    statement.setString(8, currentLocation.getZip());
                    statement.executeUpdate();
                    statement.close();
                }

                if (printExtraDebug) {
                    System.out.println(fqlFriend.hometown_location);
                }

                FBLocation hometownLocation = null;

                if (fqlFriend.hometown_location != null) {
                    hometownLocation = mapper.readValue(fqlFriend.hometown_location,
                            FBLocation.class);

                    statement = connection.prepareStatement(FbQueries.FB_LOCATION_DATA_INSERT_SQL);
                    statement.setString(1, hometownLocation.getCity());
                    statement.setString(2, hometownLocation.getCountry());
                    statement.setDouble(3, hometownLocation.getLatitude());
                    statement.setString(4, hometownLocation.getId());
                    statement.setString(5, hometownLocation.getName());
                    statement.setDouble(6, hometownLocation.getLongitude());
                    statement.setString(7, hometownLocation.getState());
                    statement.setString(8, hometownLocation.getZip());
                    statement.executeUpdate();
                    statement.close();
                }

                statement = connection.prepareStatement(FbQueries.FACEBOOK_FRIEND_DATA_INSERT_UPDATE_SQL);
                statement.setString(1, fqlFriend.birthday_date);
                statement.setString(2,
                    (currentLocation == null) ? null : currentLocation.getId());
                statement.setString(3, fqlFriend.devices);
                currentDateTimestamp = CommonFunctionsUtil.getCurrentDateTimestamp();
                statement.setTimestamp(4, currentDateTimestamp);
                statement.setString(5, fqlFriend.first_name);
                statement.setString(6, fqlFriend.friend_count);
                statement.setString(7, fqlFriend.sex);
                statement.setString(8,
                    (hometownLocation == null) ? null : hometownLocation.getId());
                statement.setString(9, fqlFriend.last_name);
                statement.setString(10, fqlFriend.locale);
                statement.setString(11, fqlFriend.middle_name);
                statement.setString(12, fqlFriend.name);
                statement.setString(13, fqlFriend.pic_square);
                statement.setString(14, fqlFriend.subscriber_count);
                statement.setString(15, fqlFriend.third_party_id);
                statement.setString(16, fqlFriend.uid);
                statement.setString(17, fqlFriend.username);
                statement.setString(18, fqlFriend.is_verified);

                statement.setString(19, fqlFriend.birthday_date);
                statement.setString(20,
                    (currentLocation == null) ? null : currentLocation.getId());
                statement.setString(21, fqlFriend.devices);
                statement.setTimestamp(22, currentDateTimestamp);
                statement.setString(23, fqlFriend.first_name);
                statement.setString(24, fqlFriend.friend_count);
                statement.setString(25, fqlFriend.sex);
                statement.setString(26,
                    (hometownLocation == null) ? null : hometownLocation.getId());
                statement.setString(27, fqlFriend.last_name);
                statement.setString(28, fqlFriend.locale);
                statement.setString(29, fqlFriend.middle_name);
                statement.setString(30, fqlFriend.name);
                statement.setString(31, fqlFriend.pic_square);
                statement.setString(32, fqlFriend.subscriber_count);
                statement.setString(33, fqlFriend.third_party_id);
                statement.setString(34, fqlFriend.username);
                statement.setString(35, fqlFriend.is_verified);
                statement.executeUpdate();
                statement.close();

                statement = connection.prepareStatement(FbQueries.USER_FRIEND_FB_INSERT_UPDATE_SQL);
                statement.setTimestamp(1, currentDateTimestamp);
                statement.setString(2, userFbId);
                statement.setString(3, fqlFriend.uid);
                statement.setString(4, "ACTIVE");
                statement.setString(5, existingUserId);

                statement.setTimestamp(6, currentDateTimestamp);
                statement.setString(7, "ACTIVE");

                statement.executeUpdate();
                statement.close();
            }

            //find existing friends which are not in FqlFriend 
            statement = connection.prepareStatement(FbQueries.USER_FRIEND_FB_FROM_USER_FRIEND_FB_SELECT_SQL);
            statement.setString(1, existingUserId);
            resultset = statement.executeQuery();

            ArrayList<String> deletedFriends = new ArrayList<String>();
            String existingFriend = null;

            while (resultset.next()) {
                existingFriend = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "USER_FRIEND_FB.USER_FRIEND_FB"));

                if (!existingFriends.contains(existingFriend)) {
                    deletedFriends.add(existingFriend);
                }
            }

            statement.close();

            statement = connection.prepareStatement(FbQueries.USER_FRIEND_FB_STATUS_UPDATE_SQL);

            for (String deletedFBId : deletedFriends) {
                statement.setString(1, "DELETED");
                statement.setString(2, deletedFBId);
                statement.executeUpdate();
            }

            statement.close();
            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException("Error while submitFriendsData " +
                e.getMessage());
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while submitFriendsData " +
                e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while submitFriendsData " +
                e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while submitFriendsData " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void submitTrustedFriends(TSDataSource tsDataSource,
        Connection connection, List<TsUser> friendsListUsingTasteSync,
        String userID) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            if ((friendsListUsingTasteSync != null) &&
                    !friendsListUsingTasteSync.isEmpty()) {
                statement = connection.prepareStatement(FbQueries.USER_FRIEND_TASTESYNC_TRUST_INSERT_UPDATE_SQL);

                for (TsUser tsUser : friendsListUsingTasteSync) {
                    statement.setString(1, tsUser.getUserId());
                    statement.setString(2, userID);
                    statement.execute();
                }

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException("Error while submitTrustedFriends " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void submitUsersData(TSDataSource tsDataSource,
        Connection connection, List<FqlUser> users) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            tsDataSource.begin();

            Timestamp currentDateTimestamp = null;
            ObjectMapper mapper = new ObjectMapper();

            for (FqlUser fqlUser : users) {
                if (printExtraDebug) {
                    System.out.println("fqlUser=" + fqlUser);
                }

                //TODO add homezone_location, current location data
                currentDateTimestamp = CommonFunctionsUtil.getCurrentDateTimestamp();
                statement = connection.prepareStatement(FbQueries.COUNT_FACEBOOK_USER_DATA_SELECT_SQL);
                statement.setString(1, fqlUser.uid);
                resultset = statement.executeQuery();

                int rowCount = 0;

                if (resultset.next()) {
                    rowCount = resultset.getInt(1);
                }

                statement.close();

                if (printExtraDebug) {
                    System.out.println(fqlUser.current_location);
                }

                FBLocation currentLocation = null;

                if (fqlUser.current_location != null) {
                    currentLocation = mapper.readValue(fqlUser.current_location,
                            FBLocation.class);
                    statement = connection.prepareStatement(FbQueries.FB_LOCATION_DATA_INSERT_SQL);
                    statement.setString(1, currentLocation.getCity());
                    statement.setString(2, currentLocation.getCountry());
                    statement.setDouble(3, currentLocation.getLatitude());
                    statement.setString(4, currentLocation.getId());
                    statement.setString(5, currentLocation.getName());
                    statement.setDouble(6, currentLocation.getLongitude());
                    statement.setString(7, currentLocation.getState());
                    statement.setString(8, currentLocation.getZip());
                    statement.executeUpdate();
                    statement.close();
                }

                if (printExtraDebug) {
                    System.out.println(fqlUser.hometown_location);
                }

                FBLocation hometownLocation = null;

                if (fqlUser.hometown_location != null) {
                    hometownLocation = mapper.readValue(fqlUser.hometown_location,
                            FBLocation.class);

                    statement = connection.prepareStatement(FbQueries.FB_LOCATION_DATA_INSERT_SQL);
                    statement.setString(1, hometownLocation.getCity());
                    statement.setString(2, hometownLocation.getCountry());
                    statement.setDouble(3, hometownLocation.getLatitude());
                    statement.setString(4, hometownLocation.getId());
                    statement.setString(5, hometownLocation.getName());
                    statement.setDouble(6, hometownLocation.getLongitude());
                    statement.setString(7, hometownLocation.getState());
                    statement.setString(8, hometownLocation.getZip());
                    statement.executeUpdate();
                    statement.close();
                }

                // insert
                if (rowCount == 0) {
                    statement = connection.prepareStatement(FbQueries.FACEBOOK_USER_DATA_INSERT_SQL);
                    statement.setString(1, fqlUser.age_range);
                    statement.setString(2, fqlUser.birthday_date);
                    statement.setTimestamp(3, currentDateTimestamp);
                    statement.setString(4,
                        (currentLocation == null) ? null : currentLocation.getId());
                    statement.setString(5, fqlUser.devices);
                    statement.setString(6, fqlUser.email);
                    statement.setString(7, fqlUser.first_name);
                    statement.setString(8, fqlUser.friend_count);
                    statement.setString(9, fqlUser.sex);
                    statement.setString(10,
                        (hometownLocation == null) ? null
                                                   : hometownLocation.getName());
                    statement.setString(11,
                        (hometownLocation == null) ? null
                                                   : hometownLocation.getId());
                    statement.setString(12, fqlUser.last_name);
                    statement.setString(13, fqlUser.likes_count);
                    statement.setString(14,
                        "http://www.facebook.com/" + fqlUser.username);
                    statement.setString(15, fqlUser.locale);
                    statement.setString(16,
                        (currentLocation == null) ? null
                                                  : currentLocation.getName());
                    statement.setString(17, fqlUser.middle_name);
                    statement.setString(18, fqlUser.name);
                    statement.setString(19, fqlUser.pic_square);
                    statement.setString(20, fqlUser.third_party_id);
                    statement.setString(21, fqlUser.timezone);
                    statement.setTimestamp(22, currentDateTimestamp);
                    statement.setString(23, fqlUser.uid);
                    statement.setString(24, fqlUser.username);
                    statement.setString(25, fqlUser.is_verified);
                } else {
                    //update
                    statement = connection.prepareStatement(FbQueries.FACEBOOK_USER_DATA_UPDATE_SQL);

                    statement.setString(1, fqlUser.age_range);
                    statement.setString(2, fqlUser.birthday_date);
                    statement.setString(3,
                        (currentLocation == null) ? null : currentLocation.getId());
                    statement.setString(4, fqlUser.devices);
                    statement.setString(5, fqlUser.email);
                    statement.setString(6, fqlUser.first_name);
                    statement.setString(7, fqlUser.friend_count);
                    statement.setString(8, fqlUser.sex);
                    statement.setString(9,
                        (hometownLocation == null) ? null
                                                   : hometownLocation.getName());
                    statement.setString(10,
                        (hometownLocation == null) ? null
                                                   : hometownLocation.getId());
                    statement.setString(11, fqlUser.last_name);
                    statement.setString(12, fqlUser.likes_count);
                    statement.setString(13,
                        "http://www.facebook.com/" + fqlUser.username);
                    statement.setString(14, fqlUser.locale);
                    statement.setString(15,
                        (currentLocation == null) ? null
                                                  : currentLocation.getName());
                    statement.setString(16, fqlUser.middle_name);
                    statement.setString(17, fqlUser.name);
                    statement.setString(18, fqlUser.pic_square);
                    statement.setString(19, fqlUser.third_party_id);
                    statement.setString(20, fqlUser.timezone);
                    statement.setTimestamp(21, currentDateTimestamp);
                    statement.setString(22, fqlUser.username);
                    statement.setString(23, fqlUser.is_verified);
                    statement.setString(24, fqlUser.uid);
                }

                statement.executeUpdate();
                statement.close();
            }

            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException("Error while submitUsersData " +
                e.getMessage());
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while submitUsersData " +
                e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while submitUsersData " +
                e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while submitUsersData " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }
}
