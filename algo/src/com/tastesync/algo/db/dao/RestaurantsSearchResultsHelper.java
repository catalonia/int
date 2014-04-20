package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.queries.UserRestaurantQueries;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.InputRestaurantSearchVO;
import com.tastesync.algo.model.vo.RestaurantsSearchResultsVO;
import com.tastesync.algo.util.TSConstants;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.db.pool.TSDataSource;

import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RestaurantsSearchResultsHelper {
    private static final Logger logger = Logger.getLogger(RestaurantsSearchResultsHelper.class);

    //private static final String TAG_SEARCH_QUERY_PART1_SQL = "SELECT A.RESTAURANT_ID, A.user_restaurant_rank, tag_match FROM ( ";
    private static final String TAG_SEARCH_QUERY_PART1_SQL = "SELECT A.RESTAURANT_ID, A.info_popularity_tier_id, A.match_counter, tag_match FROM ( ";
    private static final String COUNT_SEARCH_QUERY_PART1_SQL = "" +
        " SELECT Count(*) FROM ( ";

    //private static final String SEARCH_QUERY_PART1_SQL = "" +
    //  " SELECT x.RESTAURANT_ID, y.user_restaurant_rank FROM ( ";
    private static final String SEARCH_QUERY_PART1_SQL = "" +
        " SELECT x.RESTAURANT_ID, x.info_popularity_tier_id, y.match_counter FROM ( ";
    private static final String SEARCH_QUERY_PART2_1_SQL = "" +
        " SELECT distinct restaurant.RESTAURANT_ID, restaurant.info_popularity_tier_id FROM restaurant  ";
    private static final String SEARCH_QUERY_PART3_COUNT_SQL = "" + " ) x ";
    private static final String SEARCH_QUERY_PART3_LEFT_OUTER_JOIN_SQL = "" +
        " ) x LEFT OUTER JOIN user_restaurant_match_counter y ON " +
        " y.user_id = ? " + " AND x.RESTAURANT_ID = y.RESTAURANT_ID ) A " +
        " LEFT OUTER JOIN ( " +
        " SELECT DISTINCT restaurant_id, 1 as tag_match FROM restaurant_tag WHERE ( ";

    //    private static final String SEARCH_QUERY_INCLUDE_LAST_ORDER_BY_JOIN_SQL = "" +
    //        " ) ) B ON A.RESTAURANT_ID = B.restaurant_id " +
    //        " ORDER BY  ISNULL(B.tag_match), ISNULL(A.user_restaurant_rank), A.user_restaurant_rank, A.restaurant_id ASC ";
    private static final String SEARCH_QUERY_INCLUDE_LAST_ORDER_BY_JOIN_SQL = "" +
        " ) ) B ON A.RESTAURANT_ID = B.restaurant_id " +
        " ORDER BY  ISNULL(B.tag_match), ISNULL(A.info_popularity_tier_id), A.info_popularity_tier_id, ISNULL(A.match_counter), A.match_counter DESC, A.restaurant_id ASC ";
    private static final String OCCASSION_TAG_PARAMS_SQL = " (tag_type='occasion' and tag_type_id in (1_REPLACE_PARAM) )";
    private static final String THEME_TAG_PARAMS_SQL = " OR (tag_type='theme' and tag_type_id in (1_REPLACE_PARAM) )";
    private static final String TYPEOFREST_TAG_PARAMS_SQL = " OR (tag_type='typeofrest' and tag_type_id in (1_REPLACE_PARAM) )";
    private static final String WHOAREYOUWITH_TAG_PARAMS_SQL = " OR (tag_type='whoareyouwith' and tag_type_id in (1_REPLACE_PARAM) )";

    //        " ORDER BY " +
    //        " ISNULL(y.user_restaurant_rank), y.user_restaurant_rank, x.restaurant_id ASC ";
    private static final String SEARCH_QUERY_PART4_SQL = "LIMIT ?, ?";
    private static final String HIDE_CHAINED_RESTAURANT = "0";
    private static final boolean printDebugExtra = false;

    public RestaurantsSearchResultsHelper() {
        super();
    }

    private ResultSet bindParametersForConsolidatedQuery(
        boolean executingCount,
        InputRestaurantSearchVO inputRestaurantSearchVO,
        PreparedStatement statement) throws SQLException {
        ResultSet resultset;
        int bindPosition = 0;

        if (inputRestaurantSearchVO.getCityId() != null) {
            ++bindPosition;
            statement.setString(bindPosition,
                inputRestaurantSearchVO.getCityId());
        }

        if ((inputRestaurantSearchVO.getNeighborhoodIdArray() != null) &&
                (inputRestaurantSearchVO.getNeighborhoodIdArray().length != 0)) {
            for (int i = 0;
                    i < inputRestaurantSearchVO.getNeighborhoodIdArray().length;
                    ++i) {
                ++bindPosition;
                statement.setString(bindPosition,
                    inputRestaurantSearchVO.getNeighborhoodIdArray()[i]);
            }
        }
        
        // recheck
        if (inputRestaurantSearchVO.getSavedFlag() != null) {
            ++bindPosition;
            statement.setString(bindPosition,
                inputRestaurantSearchVO.getUserId());
        }

        // recheck
        if (inputRestaurantSearchVO.getFavFlag() != null) {
            ++bindPosition;
            statement.setString(bindPosition,
                inputRestaurantSearchVO.getUserId());
        }

        if ((inputRestaurantSearchVO.getCuisineTier2IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier2IdArray().length != 0)) {
            for (int i = 0;
                    i < inputRestaurantSearchVO.getCuisineTier2IdArray().length;
                    ++i) {
                ++bindPosition;
                statement.setString(bindPosition,
                    inputRestaurantSearchVO.getCuisineTier2IdArray()[i]);
            }
        }

        //-- IF restaurantSearchParameters{listOfPrice{priceId}} OR recoRequestParameters{listOfPrice{priceId}} is not null 
        if ((inputRestaurantSearchVO.getPriceIdList() != null) &&
                (inputRestaurantSearchVO.getPriceIdList().length != 0)) {
            for (int i = 0;
                    i < inputRestaurantSearchVO.getPriceIdList().length; ++i) {
                ++bindPosition;
                statement.setString(bindPosition,
                    inputRestaurantSearchVO.getPriceIdList()[i]);
            }
        }

        //-- IF restaurantSearchParameters{rating} is not null 
        if ((inputRestaurantSearchVO.getRating() != null)) {
            ++bindPosition;
            statement.setDouble(bindPosition,
                new Double(inputRestaurantSearchVO.getRating()));
        }

        if (!executingCount) {
            ++bindPosition;
            statement.setString(bindPosition,
                inputRestaurantSearchVO.getUserId());
        }

        if (!executingCount) {
            String[] occasionIdArray = inputRestaurantSearchVO.getOccasionIdArray();
            int occasionIdArrayLength = 0;

            if (occasionIdArray != null) {
                occasionIdArrayLength = occasionIdArray.length;
            }

            if (occasionIdArrayLength != 0) {
                for (int i = 0; i < occasionIdArrayLength; ++i) {
                    ++bindPosition;
                    statement.setString(bindPosition, occasionIdArray[i]);
                }
            } else {
                ++bindPosition;
                statement.setString(bindPosition, null);
            }

            String[] themeIdArray = inputRestaurantSearchVO.getThemeIdArray();
            int themeIdArrayLength = 0;

            if (themeIdArray != null) {
                themeIdArrayLength = themeIdArray.length;
            }

            if (themeIdArrayLength != 0) {
                for (int i = 0; i < themeIdArrayLength; ++i) {
                    ++bindPosition;
                    statement.setString(bindPosition, themeIdArray[i]);
                }
            } else {
                ++bindPosition;
                statement.setString(bindPosition, null);
            }

            String[] typeOfRestaurantIdArray = inputRestaurantSearchVO.getTypeOfRestaurantIdArray();
            int typeOfRestaurantIdArrayLength = 0;

            if (typeOfRestaurantIdArray != null) {
                typeOfRestaurantIdArrayLength = typeOfRestaurantIdArray.length;
            }

            if (typeOfRestaurantIdArrayLength != 0) {
                for (int i = 0; i < typeOfRestaurantIdArrayLength; ++i) {
                    ++bindPosition;
                    statement.setString(bindPosition, typeOfRestaurantIdArray[i]);
                }
            } else {
                ++bindPosition;
                statement.setString(bindPosition, null);
            }

            String[] whoareyouwithIdArray = inputRestaurantSearchVO.getWhoareyouwithIdArray();
            int whoareyouwithIdArrayLength = 0;

            if (whoareyouwithIdArray != null) {
                whoareyouwithIdArrayLength = whoareyouwithIdArray.length;
            }

            if (whoareyouwithIdArrayLength != 0) {
                for (int i = 0; i < whoareyouwithIdArrayLength; ++i) {
                    ++bindPosition;
                    statement.setString(bindPosition, whoareyouwithIdArray[i]);
                }
            } else {
                ++bindPosition;
                statement.setString(bindPosition, null);
            }
        }

        if (!executingCount) {
            ++bindPosition;

            int pagintionIndex = 0;

            if (inputRestaurantSearchVO.getPaginationId() != null) {
                pagintionIndex = (Integer.valueOf(inputRestaurantSearchVO.getPaginationId()) -
                    1) * TSConstants.PAGINATION_GAP;
            }

            if (pagintionIndex < 0) {
                pagintionIndex = 0;
            }

            statement.setInt(bindPosition, pagintionIndex);
            ++bindPosition;
            statement.setInt(bindPosition, TSConstants.PAGINATION_GAP);
        }

        resultset = statement.executeQuery();

        return resultset;
    }

    private StringBuffer buildConsolidatedQuery(boolean executingCount,
        InputRestaurantSearchVO inputRestaurantSearchVO)
        throws TasteSyncException {
        // build query based on various input parameters
        StringBuffer consolidatedSearchQuery = new StringBuffer();

        if (executingCount) {
            consolidatedSearchQuery.append(COUNT_SEARCH_QUERY_PART1_SQL);
        } else {
            consolidatedSearchQuery.append(TAG_SEARCH_QUERY_PART1_SQL);

            consolidatedSearchQuery.append(SEARCH_QUERY_PART1_SQL);
        }

        consolidatedSearchQuery.append(SEARCH_QUERY_PART2_1_SQL);

        // add different tables for select part

        //-- IF restaurantSearchParameters{searchLocation{neighborhoodId}} 
        //OR recoRequestParameters{recoRequestLocation{neighborhoodId}} is not NULL
        if ((inputRestaurantSearchVO.getNeighborhoodIdArray() != null) &&
                (inputRestaurantSearchVO.getNeighborhoodIdArray().length != 0)) {
            consolidatedSearchQuery.append(", ").append("restaurant_neighbourhood ");
        }
        
        //-- IF restaurantSearchParameters{savedFlag} is not null 
        if (inputRestaurantSearchVO.getSavedFlag() != null) {
            consolidatedSearchQuery.append(",  ")
                                   .append("user_restaurant_saved ");
        }

        //-- IF restaurantSearchParameters{favFlag} is not null
        if (inputRestaurantSearchVO.getFavFlag() != null) {
            consolidatedSearchQuery.append(", ").append("user_restaurant_fav ");
        }

        //-- IF recoRequestParameters{listOfCuisTier2{cuisineTier2Id}} OR restaurantSearchParameters{listOfCuisinesTier1{cuisineTier1Id}} 
        //OR recoRequestParameters{listOfCuisTier1{cuisineTier1Id}} is not null 
        if ((inputRestaurantSearchVO.getCuisineTier2IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier2IdArray().length != 0)) {
            consolidatedSearchQuery.append(", ").append("restaurant_cuisine ");
        }

        // -- IF restaurantSearchParameters{chainFlag} = 0 (0 means "Hide" Chain Restaurants)
        if (HIDE_CHAINED_RESTAURANT.equals(
                    inputRestaurantSearchVO.getChainFlag())) {
            consolidatedSearchQuery.append(", ")
                                   .append("restaurant_extended_info ");
        }

        // add different tables for where part
        // Always present
        if ((inputRestaurantSearchVO.getCityId() == null) ||
                inputRestaurantSearchVO.getCityId().isEmpty()) {
            //JAGS TODO to be removed later
            inputRestaurantSearchVO.setCityId("11756");

            //            throw new TasteSyncException("City Id is not avaialble cityId=" +
            //                inputRestaurantSearchVO.getCityId());
        }

        consolidatedSearchQuery.append("WHERE restaurant.restaurant_delete_status <> 1 and ");

        if (inputRestaurantSearchVO.getCityId() != null) {
            //-- IF restaurantSearchParameters{searchLocation{cityId}} OR recoRequestParameters{recoRequestLocation{cityId}} is not NULL
            //-- restaurantSearchParameters{searchLocation{cityId}} OR recoRequestParameters{recoRequestLocation{cityId}}
            consolidatedSearchQuery.append("restaurant.RESTAURANT_CITY_ID=? ");
        }

        //-- IF restaurantSearchParameters{searchLocation{neighborhoodId}} OR recoRequestParameters{recoRequestLocation{neighborhoodId}} is not NULL
        //-- restaurantSearchParameters{searchLocation{neighborhoodId}} OR recoRequestParameters{recoRequestLocation{neighborhoodId}}
       if ((inputRestaurantSearchVO.getNeighborhoodIdArray() != null) &&
                (inputRestaurantSearchVO.getNeighborhoodIdArray().length != 0)) {
            consolidatedSearchQuery.append(
                "AND restaurant_neighbourhood.neighbourhood_id IN ( ");

            for (int i = 0;
                    i < inputRestaurantSearchVO.getNeighborhoodIdArray().length;
                    ++i) {
                if (i != (inputRestaurantSearchVO.getNeighborhoodIdArray().length -
                        1)) {
                    consolidatedSearchQuery.append("?,");
                } else {
                    consolidatedSearchQuery.append("?");
                }
            }

            consolidatedSearchQuery.append(" ) ");
        }

        if (((inputRestaurantSearchVO.getNeighborhoodIdArray() != null) &&
                (inputRestaurantSearchVO.getNeighborhoodIdArray().length != 0))) {
            consolidatedSearchQuery.append(
            		"AND restaurant.restaurant_id = restaurant_neighbourhood.restaurant_id ");
        }
        
        
        //-- IF restaurantSearchParameters{savedFlag} is not null 
        if (inputRestaurantSearchVO.getSavedFlag() != null) {
            consolidatedSearchQuery.append(
                "AND user_restaurant_saved.USER_ID=? ");
            consolidatedSearchQuery.append(
                "AND restaurant.restaurant_id = user_restaurant_saved.restaurant_id ");
        }

        //-- IF restaurantSearchParameters{favFlag} is not null 
        if (inputRestaurantSearchVO.getFavFlag() != null) {
            consolidatedSearchQuery.append("AND user_restaurant_fav.USER_ID=? ");
            consolidatedSearchQuery.append(
                "AND restaurant.restaurant_id = user_restaurant_fav.restaurant_id ");
        }

        //-- IF recoRequestParameters{listOfCuisTier2{cuisineTier2Id}} is not null
        if ((inputRestaurantSearchVO.getCuisineTier2IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier2IdArray().length != 0)) {
            consolidatedSearchQuery.append(
                "AND restaurant_cuisine.tier2_cuisine_id IN ( ");

            for (int i = 0;
                    i < inputRestaurantSearchVO.getCuisineTier2IdArray().length;
                    ++i) {
                if (i != (inputRestaurantSearchVO.getCuisineTier2IdArray().length -
                        1)) {
                    consolidatedSearchQuery.append("?,");
                } else {
                    consolidatedSearchQuery.append("?");
                }
            }

            consolidatedSearchQuery.append(" ) ");
        }

        if (((inputRestaurantSearchVO.getCuisineTier2IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier2IdArray().length != 0))) {
            consolidatedSearchQuery.append(
                "AND restaurant.restaurant_id = restaurant_cuisine.restaurant_id ");
        }

        //-- IF restaurantSearchParameters{listOfPrice{priceId}} OR recoRequestParameters{listOfPrice{priceId}} is not null 
        if ((inputRestaurantSearchVO.getPriceIdList() != null) &&
                (inputRestaurantSearchVO.getPriceIdList().length != 0)) {
            consolidatedSearchQuery.append("AND restaurant.PRICE_RANGE IN ( ");

            for (int i = 0;
                    i < inputRestaurantSearchVO.getPriceIdList().length; ++i) {
                if (i != (inputRestaurantSearchVO.getPriceIdList().length - 1)) {
                    consolidatedSearchQuery.append("?,");
                } else {
                    consolidatedSearchQuery.append("?");
                }
            }

            consolidatedSearchQuery.append(" ) ");
        }

        //-- IF restaurantSearchParameters{rating} is not null 
        if ((inputRestaurantSearchVO.getRating() != null)) {
            consolidatedSearchQuery.append(
                "AND restaurant.FACTUAL_RATING >= ? ");
        }

        // -- IF restaurantSearchParameters{chainFlag} = 0 (0 means "Hide" Chain Restaurants)
        if (HIDE_CHAINED_RESTAURANT.equals(
                    inputRestaurantSearchVO.getChainFlag())) {
            consolidatedSearchQuery.append(
                "AND restaurant_extended_info.chain_name IS NULL ");
            consolidatedSearchQuery.append(
                "AND restaurant.restaurant_id = restaurant_extended_info.restaurant_id ");
        }

        //Fixed part
        // Left outer join
        if (executingCount) {
            consolidatedSearchQuery.append(SEARCH_QUERY_PART3_COUNT_SQL);
        } else {
            consolidatedSearchQuery.append(SEARCH_QUERY_PART3_LEFT_OUTER_JOIN_SQL);
        }

        if (!executingCount) {
            StringBuffer buildQuestionMarksInSql = new StringBuffer();

            String occasionInSql = OCCASSION_TAG_PARAMS_SQL;
            String[] occasionIdArray = inputRestaurantSearchVO.getOccasionIdArray();
            int occasionIdArrayLength = 0;

            if (occasionIdArray != null) {
                occasionIdArrayLength = occasionIdArray.length;
            }

            if (occasionIdArrayLength != 0) {
                for (int i = 0; i < occasionIdArrayLength; ++i) {
                    buildQuestionMarksInSql.append("?");

                    if (i != (occasionIdArrayLength - 1)) {
                        buildQuestionMarksInSql.append(",");
                    }
                }
            } else {
                buildQuestionMarksInSql.append("?");
            }

            occasionInSql = StringUtils.replace(occasionInSql,
                    "1_REPLACE_PARAM", buildQuestionMarksInSql.toString());

            buildQuestionMarksInSql = new StringBuffer();

            String themeInSql = THEME_TAG_PARAMS_SQL;
            String[] themeIdArray = inputRestaurantSearchVO.getThemeIdArray();

            int themeIdArrayLength = 0;

            if (themeIdArray != null) {
                themeIdArrayLength = themeIdArray.length;
            }

            if (themeIdArrayLength != 0) {
                for (int i = 0; i < themeIdArrayLength; ++i) {
                    buildQuestionMarksInSql.append("?");

                    if (i != (themeIdArrayLength - 1)) {
                        buildQuestionMarksInSql.append(",");
                    }
                }
            } else {
                buildQuestionMarksInSql.append("?");
            }

            themeInSql = StringUtils.replace(themeInSql, "1_REPLACE_PARAM",
                    buildQuestionMarksInSql.toString());

            buildQuestionMarksInSql = new StringBuffer();

            String typeofrestInSql = TYPEOFREST_TAG_PARAMS_SQL;
            String[] typeOfRestaurantIdArray = inputRestaurantSearchVO.getTypeOfRestaurantIdArray();

            int typeOfRestaurantIdArrayLength = 0;

            if (typeOfRestaurantIdArray != null) {
                typeOfRestaurantIdArrayLength = typeOfRestaurantIdArray.length;
            }

            if (typeOfRestaurantIdArrayLength != 0) {
                for (int i = 0; i < typeOfRestaurantIdArrayLength; ++i) {
                    buildQuestionMarksInSql.append("?");

                    if (i != (typeOfRestaurantIdArrayLength - 1)) {
                        buildQuestionMarksInSql.append(",");
                    }
                }
            } else {
                buildQuestionMarksInSql.append("?");
            }

            typeofrestInSql = StringUtils.replace(typeofrestInSql,
                    "1_REPLACE_PARAM", buildQuestionMarksInSql.toString());

            buildQuestionMarksInSql = new StringBuffer();

            String whoareyouwithInSql = WHOAREYOUWITH_TAG_PARAMS_SQL;

            String[] whoareyouwithIdArray = inputRestaurantSearchVO.getWhoareyouwithIdArray();

            int whoareyouwithIdArrayLength = 0;

            if (whoareyouwithIdArray != null) {
                whoareyouwithIdArrayLength = whoareyouwithIdArray.length;
            }

            if (whoareyouwithIdArrayLength != 0) {
                for (int i = 0; i < whoareyouwithIdArrayLength; ++i) {
                    buildQuestionMarksInSql.append("?");

                    if (i != (whoareyouwithIdArrayLength - 1)) {
                        buildQuestionMarksInSql.append(",");
                    }
                }
            } else {
                buildQuestionMarksInSql.append("?");
            }

            whoareyouwithInSql = StringUtils.replace(whoareyouwithInSql,
                    "1_REPLACE_PARAM", buildQuestionMarksInSql.toString());

            consolidatedSearchQuery.append(occasionInSql);
            consolidatedSearchQuery.append(themeInSql);
            consolidatedSearchQuery.append(typeofrestInSql);
            consolidatedSearchQuery.append(whoareyouwithInSql);
        }

        if (!executingCount) {
            consolidatedSearchQuery.append(SEARCH_QUERY_INCLUDE_LAST_ORDER_BY_JOIN_SQL);
            consolidatedSearchQuery.append(SEARCH_QUERY_PART4_SQL);
        }

        if (printDebugExtra) {
            if (logger.isDebugEnabled()) {
                logger.debug("consolidatedSearchQuery=" +
                    consolidatedSearchQuery.toString());
            }
        }

        return consolidatedSearchQuery;
    }

    // return list of restaurantIds based on different parameters
    public RestaurantsSearchResultsVO showListOfRestaurantsSearchResults(
        TSDataSource tsDataSource, Connection connection, String userId,
        String restaurantId, String[] neighborhoodIdArray, String cityId,
        String stateName, String[] priceIdList, String rating,
        String savedFlag, String favFlag, String dealFlag, String chainFlag,
        String paginationId, String[] cuisineTier2IdArray,
        String[] themeIdArray, String[] whoareyouwithIdArray,
        String[] typeOfRestaurantIdArray, String[] occasionIdArray)
        throws TasteSyncException {
        if (chainFlag == null) {
            chainFlag = HIDE_CHAINED_RESTAURANT;
        }

        InputRestaurantSearchVO inputRestaurantSearchVO = new InputRestaurantSearchVO(userId,
                restaurantId, neighborhoodIdArray, cityId, stateName, priceIdList,
                rating, savedFlag, favFlag, dealFlag, chainFlag, paginationId,
                cuisineTier2IdArray, themeIdArray, whoareyouwithIdArray,
                typeOfRestaurantIdArray, occasionIdArray);

        PreparedStatement statement = null;
        ResultSet resultset = null;

        if (printDebugExtra) {
            if (logger.isDebugEnabled()) {
                logger.debug("inputRestaurantSearchVO=" +
                    inputRestaurantSearchVO);
            }
        }

        //once do count(*), second time do select using limit derived from paginationId
        try {
            //true 	if calculating count(*)
            boolean executingCount = true;

            StringBuffer consolidatedSearchQuery = buildConsolidatedQuery(executingCount,
                    inputRestaurantSearchVO);
            // query is built. now bind the parameters!
            statement = connection.prepareStatement(consolidatedSearchQuery.toString());

            resultset = bindParametersForConsolidatedQuery(executingCount,
                    inputRestaurantSearchVO, statement);

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            //reassigned
            int maxPaginationId = (rowCount / TSConstants.PAGINATION_GAP) + 1;

            //false 	if doing general select
            executingCount = false;

            consolidatedSearchQuery = buildConsolidatedQuery(executingCount,
                    inputRestaurantSearchVO);
            // query is built. now bind the parameters!
            statement = connection.prepareStatement(consolidatedSearchQuery.toString());

            resultset = bindParametersForConsolidatedQuery(executingCount,
                    inputRestaurantSearchVO, statement);

            List<String> restaurantIdList = new ArrayList<String>(TSConstants.PAGINATION_GAP);
            String restaurantIdValue = null;

            while (resultset.next()) {
                restaurantIdValue = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_id"));

                if (restaurantIdValue != null) {
                    restaurantIdList.add(restaurantIdValue);
                }
            }

            statement.close();

            return new RestaurantsSearchResultsVO(String.valueOf(
                    maxPaginationId), restaurantIdList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while showListOfRestaurantsSearchResults= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    public RestaurantsSearchResultsVO showListOfRestaurantsSearchResultsBasedOnUserCity(
        TSDataSource tsDataSource, Connection connection, String userId,
        String cityId, String paginationId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        //once do count(*), second time do select using limit derived from paginationId
        try {
            statement = connection.prepareStatement(UserRestaurantQueries.COUNT_USER_CITY_RESTAURANT_SEARCH_RESULTS_SELECT_SQL);

            statement.setString(1, cityId);
            statement.setString(2, userId);

            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            //reassigned
            int maxPaginationId = (rowCount / TSConstants.PAGINATION_GAP) + 1;

            // query is built. now bind the parameters!
            statement = connection.prepareStatement(UserRestaurantQueries.USER_CITY_RESTAURANT_SEARCH_RESULTS_SELECT_SQL);

            statement.setString(1, cityId);
            statement.setString(2, userId);

            int pagintionIndex = 0;

            if (paginationId != null) {
                pagintionIndex = (Integer.valueOf(paginationId) - 1) * TSConstants.PAGINATION_GAP;
            }

            if (pagintionIndex < 0) {
                pagintionIndex = 0;
            }

            statement.setInt(3, pagintionIndex);
            statement.setInt(4, TSConstants.PAGINATION_GAP);

            resultset = statement.executeQuery();

            List<String> restaurantIdList = new ArrayList<String>(TSConstants.PAGINATION_GAP);
            String restaurantIdValue = null;

            while (resultset.next()) {
                restaurantIdValue = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_id"));

                if (restaurantIdValue != null) {
                    restaurantIdList.add(restaurantIdValue);
                }
            }

            statement.close();

            return new RestaurantsSearchResultsVO(String.valueOf(
                    maxPaginationId), restaurantIdList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while showListOfRestaurantsSearchResults= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }
}
