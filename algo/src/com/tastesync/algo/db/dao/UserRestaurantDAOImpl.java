package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.queries.UserRestaurantQueries;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantCityVO;
import com.tastesync.algo.model.vo.RestaurantPopularityTierVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.model.vo.RestaurantsSearchResultsVO;
import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedList;
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
            tsDataSource.closeConnection(null, statement, resultset);
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
            tsDataSource.closeConnection(null, statement, resultset);
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
            statement.setString(2, cityId);
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
            tsDataSource.closeConnection(null, statement, resultset);
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
            tsDataSource.closeConnection(null, statement, resultset);
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

            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_INFO_POPULARITY_TIER_INSERT_SQL);

            statement.setString(1, restaurantId);
            statement.setInt(2, tierId);

            statement.setInt(3, tierId);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while submitRestaurantInfoPopularityTier = " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
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

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while submitFlaggedRestaurant = " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
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
            tsDataSource.closeConnection(null, statement, resultset);
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
            tsDataSource.closeConnection(null, statement, resultset);
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
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public LinkedList<RestaurantPopularityTierVO> getConsolidatedFlaggedRestaurantForSingleUser(
        RestaurantUserVO flaggedRestaurantUserVO) throws TasteSyncException {
        //For each userId, multiple restaurant ids are associated!
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        PreparedStatement statementInner = null;
        ResultSet resultsetInner = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_NEIGHBOURHOOD_CITY_SELECT_SQL);

            statement.setString(1, flaggedRestaurantUserVO.getRestaurantId());
            statement.setString(2, flaggedRestaurantUserVO.getRestaurantId());

            resultset = statement.executeQuery();

            String restaurantNbrhoodId = null;

            LinkedList<String> restaurantIdList = new LinkedList<String>();
            LinkedList<RestaurantPopularityTierVO> restaurantPopularityTierVOList =
                new LinkedList<RestaurantPopularityTierVO>();

            while (resultset.next()) {
                restaurantNbrhoodId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_neighbourhood.neighbourhood_id"));

                statementInner = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_FROM_NGBRHOOD_SELECT_SQL);
                statementInner.setString(1, restaurantNbrhoodId);
                resultsetInner = statementInner.executeQuery();

                String restaurantIdFromNgbrhoodId = null;
                String popularityTierId = null;

                while (resultsetInner.next()) {
                    restaurantIdFromNgbrhoodId = CommonFunctionsUtil.getModifiedValueString(resultsetInner.getString(
                                "restaurant_neighbourhood.restaurant_id"));
                    popularityTierId = CommonFunctionsUtil.getModifiedValueString(resultsetInner.getString(
                                "restaurant_info_popularity_tier.tier_id"));

                    if (!restaurantIdList.contains(restaurantIdFromNgbrhoodId)) {
                        restaurantIdList.add(restaurantIdFromNgbrhoodId);
                        restaurantPopularityTierVOList.add(new RestaurantPopularityTierVO(
                                restaurantIdFromNgbrhoodId, popularityTierId));
                    }
                }

                statementInner.close();
            }

            statement.close();

            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_PRICERANGE_SELECT_SQL);
            statement.setString(1, flaggedRestaurantUserVO.getRestaurantId());
            statement.setString(2, flaggedRestaurantUserVO.getRestaurantId());
            resultset = statement.executeQuery();

            String priceId = null;

            //one results
            if (resultset.next()) {
                priceId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.price_range"));

                statementInner = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_FROM_PRICERANGE_SELECT_SQL);
                statementInner.setString(1, priceId);
                resultsetInner = statementInner.executeQuery();

                String restaurantIdFromPricerangeId = null;
                String popularityTierId = null;

                while (resultsetInner.next()) {
                    restaurantIdFromPricerangeId = CommonFunctionsUtil.getModifiedValueString(resultsetInner.getString(
                                "restaurant.restaurant_id"));
                    popularityTierId = CommonFunctionsUtil.getModifiedValueString(resultsetInner.getString(
                                "restaurant_info_popularity_tier.tier_id"));

                    if (!restaurantIdList.contains(restaurantIdFromPricerangeId)) {
                        restaurantIdList.add(restaurantIdFromPricerangeId);
                        restaurantPopularityTierVOList.add(new RestaurantPopularityTierVO(
                                restaurantIdFromPricerangeId, popularityTierId));
                    }
                }

                statementInner.close();
            }

            statement.close();

            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_CUISINE_SELECT_SQL);
            statement.setString(1, flaggedRestaurantUserVO.getRestaurantId());
            resultset = statement.executeQuery();

            String cuisTier2Id = null;

            while (resultset.next()) {
                cuisTier2Id = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_cuisine.tier2_cuisine_id"));

                statementInner = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_FROM_CUISINE_SELECT_SQL);
                statementInner.setString(1, cuisTier2Id);
                resultsetInner = statementInner.executeQuery();

                String restaurantIdFromPricerangeId = null;
                String popularityTierId = null;

                while (resultsetInner.next()) {
                    restaurantIdFromPricerangeId = CommonFunctionsUtil.getModifiedValueString(resultsetInner.getString(
                                "restaurant_cuisine.restaurant_id"));
                    popularityTierId = CommonFunctionsUtil.getModifiedValueString(resultsetInner.getString(
                                "restaurant_info_popularity_tier.tier_id"));

                    if (!restaurantIdList.contains(restaurantIdFromPricerangeId)) {
                        restaurantIdList.add(restaurantIdFromPricerangeId);
                        restaurantPopularityTierVOList.add(new RestaurantPopularityTierVO(
                                restaurantIdFromPricerangeId, popularityTierId));
                    }
                }

                statementInner.close();
            }

            statement.close();
            restaurantIdList = null;

            return restaurantPopularityTierVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getConsolidatedFlaggedRestaurantForSingleUser= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public int getRestUserMatchCounter(String flaggedUserId,
        RestaurantPopularityTierVO flaggedRestaurantPopularityTierVO)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.COUNT_USER_CITY_RESTAURANT_NHBR_SELECT_SQL);

            statement.setString(1,
                flaggedRestaurantPopularityTierVO.getRestaurantId());
            statement.setString(2, flaggedUserId);

            resultset = statement.executeQuery();

            int numNbrhoodMatch = 0;

            if (resultset.next()) {
                numNbrhoodMatch = resultset.getInt(1);
            }

            statement.close();

            statement = connection.prepareStatement(UserRestaurantQueries.COUNT_USER_CUISINETIER2_RESTAURANT_CUISINE_SELECT_SQL);

            statement.setString(1,
                flaggedRestaurantPopularityTierVO.getRestaurantId());

            statement.setString(2, flaggedUserId);

            resultset = statement.executeQuery();

            int numCuis2Match = 0;

            if (resultset.next()) {
                numCuis2Match = resultset.getInt(1);
            }

            statement.close();

            statement = connection.prepareStatement(UserRestaurantQueries.COUNT_USER_PRICE_RESTAURANT_SELECT_SQL);

            statement.setString(1,
                flaggedRestaurantPopularityTierVO.getRestaurantId());

            statement.setString(2, flaggedUserId);

            resultset = statement.executeQuery();

            int numPriceMatch = 0;

            if (resultset.next()) {
                numPriceMatch = resultset.getInt(1);
            }

            statement.close();

            statement = connection.prepareStatement(UserRestaurantQueries.COUNT_USER_FOLLOW_DATA_USER_RESTAURANT_FAV_SELECT_SQL);

            statement.setString(1,
                flaggedRestaurantPopularityTierVO.getRestaurantId());

            statement.setString(2, flaggedUserId);

            resultset = statement.executeQuery();

            int numFavFollowMatch = 0;

            if (resultset.next()) {
                numFavFollowMatch = resultset.getInt(1);
            }

            statement.close();

            statement = connection.prepareStatement(UserRestaurantQueries.COUNT_USER_FOLLOW_DATA_USER_RESTAURANT_RECO_SELECT_SQL);

            statement.setString(1,
                flaggedRestaurantPopularityTierVO.getRestaurantId());

            statement.setString(2, flaggedUserId);

            resultset = statement.executeQuery();

            int numRecoFollowMatch = 0;

            if (resultset.next()) {
                numRecoFollowMatch = resultset.getInt(1);
            }

            statement.close();

            statement = connection.prepareStatement(UserRestaurantQueries.COUNT_USER_FRIEND_USER_RESTAURANT_FAV_SELECT_SQL);

            statement.setString(1,
                flaggedRestaurantPopularityTierVO.getRestaurantId());

            statement.setString(2, flaggedUserId);

            resultset = statement.executeQuery();

            int numFavTrustedMatch = 0;

            if (resultset.next()) {
                numFavTrustedMatch = resultset.getInt(1);
            }

            statement.close();

            statement = connection.prepareStatement(UserRestaurantQueries.COUNT_USER_FRIEND_RESTAURANT_RECO_SELECT_SQL);

            statement.setString(1,
                flaggedRestaurantPopularityTierVO.getRestaurantId());

            statement.setString(2, flaggedUserId);

            resultset = statement.executeQuery();

            int numRecoTrustedMatch = 0;

            if (resultset.next()) {
                numRecoTrustedMatch = resultset.getInt(1);
            }

            statement.close();

            return (numNbrhoodMatch + numCuis2Match +
                    numPriceMatch + numFavFollowMatch + numRecoFollowMatch +
                    numFavTrustedMatch + numRecoTrustedMatch);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while processRestUserMatchCounter= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public RestaurantsSearchResultsVO showListOfRestaurantsSearchResults(String userId,
        String restaurantId, String neighborhoodId, String cityId,
        String stateName, String[] cuisineTier1IdArray, String[] priceIdList,
        String rating, String savedFlag, String favFlag, String dealFlag,
        String chainFlag, String paginationId, String[] cuisineTier2IdArray,
        String[] themeIdArray, String[] whoareyouwithIdArray,
        String[] typeOfRestaurantIdArray, String[] occasionIdArray)
        throws TasteSyncException {
        RestaurantsSearchResultsHelper restaurantsSearchResultsHelper = new RestaurantsSearchResultsHelper();

        return restaurantsSearchResultsHelper.showListOfRestaurantsSearchResults(userId,
            restaurantId, neighborhoodId, cityId, stateName,
            cuisineTier1IdArray, priceIdList, rating, savedFlag, favFlag,
            dealFlag, chainFlag, paginationId, cuisineTier2IdArray,
            themeIdArray, whoareyouwithIdArray, typeOfRestaurantIdArray,
            occasionIdArray);
    }

    @Override
    public void submitAssignedRankUserRestaurantForWhole(
        List<RestaurantPopularityTierVO> restaurantPopularityTierVOList)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            for (RestaurantPopularityTierVO restaurantPopularityTierVO : restaurantPopularityTierVOList) {
                statement = connection.prepareStatement(UserRestaurantQueries.USER_RESTAURANT_MATCH_COUNTER_INSERT_SQL);

                statement.setInt(1, 0);
                statement.setInt(2,
                    Integer.valueOf(
                        restaurantPopularityTierVO.getNumUserRestaurantMatchCount()));
                statement.setString(3,
                    restaurantPopularityTierVO.getRestaurantId());
                statement.setString(4, restaurantPopularityTierVO.getUserId());
                statement.setInt(5, restaurantPopularityTierVO.getRankNumber());

                statement.setInt(6, 0);
                statement.setInt(7,
                    Integer.valueOf(
                        restaurantPopularityTierVO.getNumUserRestaurantMatchCount()));
                statement.setInt(8, restaurantPopularityTierVO.getRankNumber());

                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while submitAssignedRankUserRestaurant= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    

    @Override
    public int getUserMatchCounter(String userId, String restaurantId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.USER_MATCH_COUNTER_SELECT_SQL);
            statement.setString(1, userId);
            statement.setString(2, restaurantId);
            resultset = statement.executeQuery();

            int userMatchCounter = 0;
            String strUserMatchCounter = null;

            //only one result
            if (resultset.next()) {
                strUserMatchCounter = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_restaurant_match_counter.match_counter"));
            }

            if (strUserMatchCounter != null && !strUserMatchCounter.isEmpty()) {
                userMatchCounter = Integer.valueOf(strUserMatchCounter);
            } else {
                userMatchCounter = 0;
                System.out.println("userMatchCounter is NULL. set to 0");
            }

            statement.close();

            return userMatchCounter;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while getUserMatchCounter= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public int getRestaurantInfoTierId(String userId, String restaurantId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_INFO_POPULARITY_TIER_SELECT_SQL);
            statement.setString(1, restaurantId);
            resultset = statement.executeQuery();

            int pupularityTierId = 0;
            String strPupularityTierId = null;

            //only one result
            if (resultset.next()) {
                strPupularityTierId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_info_popularity_tier.tier_id"));
            }

            if (strPupularityTierId != null) {
                pupularityTierId = Integer.valueOf(strPupularityTierId);
            } else {
                pupularityTierId = 0;
                System.out.println("pupularityTierId is NULL. set to 0");
            }

            statement.close();

            return pupularityTierId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRestaurantInfoTierId= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }

    @Override
    public void submitAssignedRankUserRestaurant(
        List<RestaurantPopularityTierVO> restaurantPopularityTierVOList)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            for (RestaurantPopularityTierVO restaurantPopularityTierVO : restaurantPopularityTierVOList) {
                statement = connection.prepareStatement(UserRestaurantQueries.RANK_USER_RESTAURANT_MATCH_COUNTER_INSERT_SQL);

                statement.setString(1,
                    restaurantPopularityTierVO.getRestaurantId());
                statement.setString(2, restaurantPopularityTierVO.getUserId());
                statement.setInt(3, restaurantPopularityTierVO.getRankNumber());

                statement.setInt(4, restaurantPopularityTierVO.getRankNumber());

                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(
                "Error while submitAssignedRankUserRestaurant= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(null, statement, resultset);
        }
    }
}
