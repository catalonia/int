package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.queries.UserRestaurantQueries;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RestaurantCityVO;
import com.tastesync.algo.model.vo.RestaurantPopularityTierVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.model.vo.RestaurantsSearchResultsVO;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.db.pool.TSDataSource;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class UserRestaurantDAOImpl extends BaseDaoImpl
    implements UserRestaurantDAO {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(UserRestaurantDAOImpl.class);

    @Override
    public LinkedList<RestaurantPopularityTierVO> getConsolidatedFlaggedRestaurantForSingleUser(
        TSDataSource tsDataSource, Connection connection,
        RestaurantUserVO flaggedRestaurantUserVO) throws TasteSyncException {
        //For each userId, multiple restaurant ids are associated!
        PreparedStatement statement = null;
        ResultSet resultset = null;

        PreparedStatement statementInner = null;
        ResultSet resultsetInner = null;

        try {
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

                    if ((popularityTierId == null) ||
                            popularityTierId.isEmpty()) {
                        popularityTierId = "5"; // worst
                    }

                    if (!restaurantIdList.contains(restaurantIdFromNgbrhoodId)) {
                        restaurantIdList.add(restaurantIdFromNgbrhoodId);
                        restaurantPopularityTierVOList.add(new RestaurantPopularityTierVO(
                                restaurantIdFromNgbrhoodId,
                                Integer.valueOf(popularityTierId)));
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

                    if ((popularityTierId == null) ||
                            popularityTierId.isEmpty()) {
                        popularityTierId = "5"; // worst
                    }

                    if (!restaurantIdList.contains(restaurantIdFromPricerangeId)) {
                        restaurantIdList.add(restaurantIdFromPricerangeId);
                        restaurantPopularityTierVOList.add(new RestaurantPopularityTierVO(
                                restaurantIdFromPricerangeId,
                                Integer.valueOf(popularityTierId)));
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

                    if ((popularityTierId == null) ||
                            popularityTierId.isEmpty()) {
                        popularityTierId = "5"; // worst
                    }

                    if (!restaurantIdList.contains(restaurantIdFromPricerangeId)) {
                        restaurantIdList.add(restaurantIdFromPricerangeId);
                        restaurantPopularityTierVOList.add(new RestaurantPopularityTierVO(
                                restaurantIdFromPricerangeId,
                                Integer.valueOf(popularityTierId)));
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
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public ArrayList<RestaurantPopularityTierVO> getExistingConsolidatedFlaggedRestaurantForSingleUser(
        TSDataSource tsDataSource, Connection connection, String userId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRestaurantQueries.EXISTING_USER_RESTAURANT_MATCH_COUNTER_SELECT_SQL);
            statement.setString(1, userId);
            resultset = statement.executeQuery();

            ArrayList<RestaurantPopularityTierVO> existingRestaurantPopularityTierVOList =
                new ArrayList<RestaurantPopularityTierVO>();

            String restaurantId;
            String popularityTierId;
            String numUserRestaurantMatchCount;
            int rankNumber;
            String strRankNumber;

            while (resultset.next()) {
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "USER_RESTAURANT_MATCH_COUNTER.RESTAURANT_ID"));
                popularityTierId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "RESTAURANT_INFO_POPULARITY_TIER.TIER_ID"));

                if ((popularityTierId == null) || popularityTierId.isEmpty()) {
                    numUserRestaurantMatchCount = "5";
                }

                numUserRestaurantMatchCount = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "USER_RESTAURANT_MATCH_COUNTER.MATCH_COUNTER"));

                if ((numUserRestaurantMatchCount == null) ||
                        numUserRestaurantMatchCount.isEmpty()) {
                    numUserRestaurantMatchCount = "0";
                }

                strRankNumber = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "USER_RESTAURANT_MATCH_COUNTER.USER_RESTAURANT_RANK"));
                rankNumber = (strRankNumber != null)
                    ? Integer.valueOf(strRankNumber) : 0;
                existingRestaurantPopularityTierVOList.add(new RestaurantPopularityTierVO(
                        restaurantId, Integer.valueOf(popularityTierId),
                        userId, Integer.valueOf(numUserRestaurantMatchCount),
                        rankNumber));
            }

            statement.close();

            return existingRestaurantPopularityTierVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getExistingConsolidatedFlaggedRestaurantForSingleUser= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getFlaggedCityIdList(TSDataSource tsDataSource,
        Connection connection, int algoIndicatorIdentifyRestaurantIdList)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<RestaurantUserVO> getFlaggedRestaurantFavUserList(
        TSDataSource tsDataSource, Connection connection, int algoIndicator)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<RestaurantCityVO> getFlaggedRestaurantList(
        TSDataSource tsDataSource, Connection connection,
        int algoIndicatorIdentifyRestaurantIdList) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<RestaurantUserVO> getFlaggedRestaurantReplyUserList(
        TSDataSource tsDataSource, Connection connection, int algoIndicator)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<RestaurantUserVO> getFlaggedRestaurantTipsUserList(
        TSDataSource tsDataSource, Connection connection, int algoIndicator)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getMedianvalueForSingleCityIdList(TSDataSource tsDataSource,
        Connection connection, String cityId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        int medianValueForSingleCityId = 0;

        try {
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
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getRestUserMatchCounter(TSDataSource tsDataSource,
        Connection connection, String flaggedUserId,
        RestaurantPopularityTierVO flaggedRestaurantPopularityTierVO)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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

            return (numNbrhoodMatch + numCuis2Match + numPriceMatch +
            numFavFollowMatch + numRecoFollowMatch + numFavTrustedMatch +
            numRecoTrustedMatch);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while processRestUserMatchCounter= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getRestaurantInfoTierId(TSDataSource tsDataSource,
        Connection connection, String userId, String restaurantId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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

                if (logger.isInfoEnabled()) {
                    logger.info("pupularityTierId is NULL. set to 0");
                }
            }

            statement.close();

            return pupularityTierId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRestaurantInfoTierId= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getUserMatchCounter(TSDataSource tsDataSource,
        Connection connection, String userId, String restaurantId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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

            if ((strUserMatchCounter != null) &&
                    !strUserMatchCounter.isEmpty()) {
                userMatchCounter = Integer.valueOf(strUserMatchCounter);
            } else {
                userMatchCounter = 0;

                if (logger.isInfoEnabled()) {
                    logger.info("userMatchCounter is NULL. set to 0");
                }
            }

            statement.close();

            return userMatchCounter;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while getUserMatchCounter= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void processSingleRestaurantIdCalc(TSDataSource tsDataSource,
        Connection connection, String restaurantId, int medianUsersNumberForCity)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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
            tsDataSource.begin();

            if ((numRestaurantPhotos < 3) || (restaurantAddress == null) ||
                    (restaurantLat == null) || (restaurantLon == null) ||
                    (restaurantPhone == null) || (restaurantRating == null) ||
                    (restaurantName == null) || (restaurantCityId == null)) {
                tierId = 5;
                submitRestaurantInfoPopularityTier(tsDataSource, connection,
                    restaurantId, tierId);
            } else {
                if ((restaurantPriceId == null) || (restaurantHours == null) ||
                        (restaurantMenuMobUrl == null)) {
                    tierId = 4;
                    submitRestaurantInfoPopularityTier(tsDataSource,
                        connection, restaurantId, tierId);
                } else {
                    if ((numRestaurantTips == 0) ||
                            (numRestaurantCheckins == 0) ||
                            (numRestaurantUsers == 0)) {
                        tierId = 3;
                        submitRestaurantInfoPopularityTier(tsDataSource,
                            connection, restaurantId, tierId);
                    } else {
                        if (numRestaurantUsers < medianUsersNumberForCity) {
                            tierId = 2;
                            submitRestaurantInfoPopularityTier(tsDataSource,
                                connection, restaurantId, tierId);
                        } else {
                            tierId = 1;
                            submitRestaurantInfoPopularityTier(tsDataSource,
                                connection, restaurantId, tierId);
                        }
                    }
                }
            }

            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestUserFlaggedUserList= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public RestaurantsSearchResultsVO showListOfRestaurantsSearchResults(
        TSDataSource tsDataSource, Connection connection, String userId,
        String restaurantId, String[] neighborhoodIdArray, String cityId,
        String stateName, String[] priceIdList, String rating,
        String savedFlag, String favFlag, String dealFlag, String chainFlag,
        String paginationId, String[] cuisineTier2IdArray,
        String[] themeIdArray, String[] whoareyouwithIdArray,
        String[] typeOfRestaurantIdArray, String[] occasionIdArray)
        throws TasteSyncException {
        RestaurantsSearchResultsHelper restaurantsSearchResultsHelper = new RestaurantsSearchResultsHelper();

        return restaurantsSearchResultsHelper.showListOfRestaurantsSearchResults(tsDataSource,
            connection, userId, restaurantId, neighborhoodIdArray, cityId,
            stateName, priceIdList, rating, savedFlag, favFlag, dealFlag,
            chainFlag, paginationId, cuisineTier2IdArray, themeIdArray,
            whoareyouwithIdArray, typeOfRestaurantIdArray, occasionIdArray);
    }

    @Override
    public RestaurantsSearchResultsVO showListOfRestaurantsSearchResultsBasedOnUserCity(
        TSDataSource tsDataSource, Connection connection, String userId,
        String cityId, String paginationId) throws TasteSyncException {
        RestaurantsSearchResultsHelper restaurantsSearchResultsHelper = new RestaurantsSearchResultsHelper();

        return restaurantsSearchResultsHelper.showListOfRestaurantsSearchResultsBasedOnUserCity(tsDataSource,
            connection, userId, cityId, paginationId);
    }

    @Override
    public void submitAssignedRankUserRestaurant(TSDataSource tsDataSource,
        Connection connection,
        List<RestaurantPopularityTierVO> restaurantPopularityTierVOList)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            if (1 == 1) {
                return;
            }

            // do for only 1000+
            int i = 0;

            for (RestaurantPopularityTierVO restaurantPopularityTierVO : restaurantPopularityTierVOList) {
                tsDataSource.begin();
                statement = connection.prepareStatement(UserRestaurantQueries.RANK_USER_RESTAURANT_MATCH_COUNTER_INSERT_SQL);

                statement.setString(1,
                    restaurantPopularityTierVO.getRestaurantId());
                statement.setString(2, restaurantPopularityTierVO.getUserId());
                statement.setInt(3, restaurantPopularityTierVO.getRankNumber());

                statement.setInt(4, restaurantPopularityTierVO.getRankNumber());

                statement.executeUpdate();
                statement.close();
                ++i;

                //                if (i == 1000) {
                //                    break;
                //                }
                tsDataSource.commit();
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
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void submitAssignedRankUserRestaurantForWhole(
        TSDataSource tsDataSource, Connection connection,
        List<RestaurantPopularityTierVO> restaurantPopularityTierVOList,
        String userId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            // do for only 1000+
            int i = 0;
            tsDataSource.begin();
            // first delete, then insert
            statement = connection.prepareStatement(UserRestaurantQueries.USER_RESTAURANT_MATCH_COUNTER_DELETE_SQL);
            statement.setString(1, userId);
            statement.executeUpdate();
            statement.close();
            //          tsDataSource.commit();
            //          tsDataSource.begin();
            statement = connection.prepareStatement(UserRestaurantQueries.USER_RESTAURANT_MATCH_COUNTER_INSERT_SQL);

            for (RestaurantPopularityTierVO restaurantPopularityTierVO : restaurantPopularityTierVOList) {
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
                ++i;

                if (i == 1000) {
                    break;
                }
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

            throw new TasteSyncException(
                "Error while submitAssignedRankUserRestaurant= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void submitFlaggedRestaurant(TSDataSource tsDataSource,
        Connection connection, String restaurantId, int algoIndicator)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            tsDataSource.begin();
            statement = connection.prepareStatement(UserRestaurantQueries.RESTAURANT_FLAGGED_UPDATE_SQL);
            statement.setInt(1, algoIndicator);
            statement.setString(2, restaurantId);
            statement.executeUpdate();
            statement.close();
            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while submitFlaggedRestaurant = " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void submitRestaurantInfoPopularityTier(TSDataSource tsDataSource,
        Connection connection, String restaurantId, int tierId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
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
            tsDataSource.closeConnection(statement, resultset);
        }
    }
}
