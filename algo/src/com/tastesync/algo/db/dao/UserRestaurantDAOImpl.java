package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.pool.TSDataSource;
import com.tastesync.algo.db.queries.UserRestaurantQueries;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantCityVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.util.CommonFunctionsUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class UserRestaurantDAOImpl extends BaseDaoImpl
    implements UserRestaurantDAO {
    @Override
    public List<RestaurantCityVO> getFlaggedRestaurantList(
        int algoIndicatorIdentifyRestaurantIdList) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_FLAGGED_SELECT_SQL);

            //int algoIndicator = 1;
            statement.setInt(1, algoIndicatorIdentifyRestaurantIdList);
            resultset = statement.executeQuery();

            List<RestaurantCityVO> restaurantCityVOList = new ArrayList<RestaurantCityVO>();
            RestaurantCityVO restaurantCityVO = null;
            String restaurantId = null;
            String cityId = null;

            while (resultset.next()) {
                cityId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.restaurant_city_id"));
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.restaurant_id"));
                restaurantCityVO = new RestaurantCityVO(cityId, restaurantId);

                restaurantCityVOList.add(restaurantCityVO);
            }

            statement.close();

            return restaurantCityVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestUserFlaggedUserList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getFlaggedCityIdList(
        int algoIndicatorIdentifyRestaurantIdList) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.FLAGGED_RESTAURANT_CITY_SELECT_SQL);

            //int algoIndicator = 1;
            statement.setInt(1, algoIndicatorIdentifyRestaurantIdList);
            resultset = statement.executeQuery();

            List<String> cityIdList = new ArrayList<String>();

            while (resultset.next()) {
                cityIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("restaurant.restaurant_city_id")));
            }

            statement.close();

            return cityIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while getFlaggedCityIdList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public int getMedianvalueForSingleCityIdList(String cityId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        int medianValueForSingleCityId = 0;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.CALCULATE_MEDIAN_USRS_NUMBER_FOR_CITY_SELECT_SQL);

            //int algoIndicator = 1;
            statement.setString(1, cityId);
            resultset = statement.executeQuery();

            if (resultset.next()) {
                medianValueForSingleCityId = CommonFunctionsUtil.getModifiedValueInteger(resultset.getString(
                            "4SQ_USERS_COUNT"));
            }

            statement.close();

            return medianValueForSingleCityId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getMedianvalueForSingleCityIdList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void processSingleRestaurantIdCalc(String restaurantId,
        int medianUsersNumberForCity) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();

            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_SELECT_SQL);

            statement.setString(1, restaurantId);
            resultset = statement.executeQuery();

            String restaurantName = null;
            String restaurantRating = null;
            String restaurantPriceId = null;
            String restaurantLat = null;
            String restaurantLon = null;
            String restaurantCityId = null;
            String restaurantHours = null;

            //only one result
            if (resultset.next()) {
                restaurantName = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.restaurant_name"));
                restaurantRating = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.factual_rating"));
                restaurantPriceId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.price_range"));
                restaurantLat = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.restaurant_lat"));
                restaurantLon = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.restaurant_lon"));
                restaurantCityId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.restaurant_city_id"));
                restaurantHours = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.restaurant_hours"));
            }

            statement.close();

            String restaurantAddress = null;
            String restaurantPhone = null;
            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_EXTENDED_INFO_SELECT_SQL);

            statement.setString(1, restaurantId);
            resultset = statement.executeQuery();

            //only one result
            if (resultset.next()) {
                restaurantAddress = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_extended_info.address"));
                restaurantPhone = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_extended_info.tel"));
            }

            statement.close();

            int numRestaurantPhotos = -1;
            int numRestaurantCheckins = -1;
            int numRestaurantTips = -1;
            int numRestaurantUsers = -1;

            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_FACTUAL_4SQVENUE_SELECT_SQL);

            statement.setString(1, restaurantId);
            resultset = statement.executeQuery();

            //only one result
            if (resultset.next()) {
                numRestaurantPhotos = CommonFunctionsUtil.getModifiedValueInteger(resultset.getString(
                            "restaurant_factual_4sqvenue.4SQ_PHOTOS_COUNT"));

                numRestaurantCheckins = CommonFunctionsUtil.getModifiedValueInteger(resultset.getString(
                            "restaurant_factual_4sqvenue.4SQ_CHECKINS_COUNT"));

                numRestaurantTips = CommonFunctionsUtil.getModifiedValueInteger(resultset.getString(
                            "restaurant_factual_4sqvenue.4SQ_TIPS_COUNT"));

                numRestaurantUsers = CommonFunctionsUtil.getModifiedValueInteger(resultset.getString(
                            "restaurant_factual_4sqvenue.4SQ_USERS_COUNT"));
            }

            statement.close();

            String restaurantMenuMobUrl = null;
            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_MENU_SELECT_SQL);

            statement.setString(1, restaurantId);
            resultset = statement.executeQuery();

            //only one result
            if (resultset.next()) {
                restaurantMenuMobUrl = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_menu.menu_mobileurl"));
            }

            statement.close();

            int tierId = 0;

            if ((numRestaurantPhotos < 3) || (restaurantAddress == null) ||
                    (restaurantLat == null) || (restaurantLon == null) ||
                    (restaurantPhone == null) || (restaurantRating == null) ||
                    (restaurantName == null) || (restaurantCityId == null)) {
                tierId = 5;
                submitRestaurantInfoPopularityTier(restaurantId, tierId);
            } else {
                if ((restaurantPriceId == null) || (restaurantHours == null) ||
                        (restaurantMenuMobUrl == null)) {
                    tierId = 4;
                    submitRestaurantInfoPopularityTier(restaurantId, tierId);
                } else {
                    if ((numRestaurantTips == 0) ||
                            (numRestaurantCheckins == 0) ||
                            (numRestaurantUsers == 0)) {
                        tierId = 3;
                        submitRestaurantInfoPopularityTier(restaurantId, tierId);
                    } else {
                        if (numRestaurantUsers < medianUsersNumberForCity) {
                            tierId = 2;
                            submitRestaurantInfoPopularityTier(restaurantId,
                                tierId);
                        } else {
                            tierId = 1;
                            submitRestaurantInfoPopularityTier(restaurantId,
                                tierId);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestUserFlaggedUserList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitRestaurantInfoPopularityTier(String restaurantId,
        int tierId) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_INFO_POPULARITY_TIER_INSERT_SQS);

            statement.setString(1, restaurantId);
            statement.setInt(2, tierId);

            statement.setInt(3, tierId);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

            if (tsDataSource != null) {
                try {
                    tsDataSource.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            throw new TasteSyncException(
                "Error while submitRestaurantInfoPopularityTier = " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitFlaggedRestaurant(String restaurantId, int algoIndicator)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_FLAGGED_UPDATE_SQL);
            statement.setInt(1, algoIndicator);
            statement.setString(2, restaurantId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

            if (tsDataSource != null) {
                try {
                    tsDataSource.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            throw new TasteSyncException(
                "Error while submitFlaggedRestaurant = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<RestaurantUserVO> getFlaggedRestaurantReplyUserList(
        int algoIndicator) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.RECOREQUEST_REPLY_USER_SELECT_SQL);

            statement.setInt(1, algoIndicator);
            resultset = statement.executeQuery();

            List<RestaurantUserVO> restaurantUserVOList = new ArrayList<RestaurantUserVO>();
            RestaurantUserVO restaurantUserVO = null;
            String restaurantId = null;
            String userId = null;

            while (resultset.next()) {
                userId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_reply_user.reply_user_id"));
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_reply.restaurant_id"));

                restaurantUserVO = new RestaurantUserVO(userId, restaurantId);

                restaurantUserVOList.add(restaurantUserVO);
            }

            statement.close();

            return restaurantUserVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getFlaggedRestaurantReplyUserList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<RestaurantUserVO> getFlaggedRestaurantTipsUserList(
        int algoIndicator) throws TasteSyncException {
    	TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_TIPS_TASTESYNC_SELECT_SQL);

            statement.setInt(1, algoIndicator);
            resultset = statement.executeQuery();

            List<RestaurantUserVO> restaurantUserVOList = new ArrayList<RestaurantUserVO>();
            RestaurantUserVO restaurantUserVO = null;
            String restaurantId = null;
            String userId = null;

            while (resultset.next()) {
                userId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_tips_tastesync.user_id"));
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_tips_tastesync.restaurant_id"));

                restaurantUserVO = new RestaurantUserVO(userId, restaurantId);

                restaurantUserVOList.add(restaurantUserVO);
            }

            statement.close();

            return restaurantUserVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getFlaggedRestaurantTipsUserList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
        
    }

    @Override
    public List<RestaurantUserVO> getFlaggedRestaurantFavUserList(
        int algoIndicator) throws TasteSyncException {
    	TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.USER_RESTAURANT_FAV_SELECT_SQL);

            statement.setInt(1, algoIndicator);
            resultset = statement.executeQuery();

            List<RestaurantUserVO> restaurantUserVOList = new ArrayList<RestaurantUserVO>();
            RestaurantUserVO restaurantUserVO = null;
            String restaurantId = null;
            String userId = null;

            while (resultset.next()) {
                userId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_restaurant_fav.user_id"));
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_restaurant_fav.restaurant_id"));

                restaurantUserVO = new RestaurantUserVO(userId, restaurantId);

                restaurantUserVOList.add(restaurantUserVO);
            }

            statement.close();

            return restaurantUserVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getFlaggedRestaurantTipsUserList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }
}
