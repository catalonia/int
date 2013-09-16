package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.pool.TSDataSource;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.InputRestaurantSearchVO;
import com.tastesync.algo.util.CommonFunctionsUtil;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RestaurantsSearchResultsHelper {
    private static final Logger logger = Logger.getLogger(RestaurantsSearchResultsHelper.class);
    private static final String SEARCH_QUERY_PART1_SQL = "" +
        " SELECT x.RESTAURANT_ID," + " y.user_restaurant_rank," + " FROM ( ";
    private static final String SEARCH_QUERY_PART2_1_SQL = "" +
        " SELECT distinct restaurant.RESTAURANT_ID" + " FROM restaurant ";
    private static final String SEARCH_QUERY_PART3_LEFT_OUTER_JOIN_SQL = "" +
        " ) x LEFT OUTER JOIN (" +
        "SELECT user_restaurant_match_counter.restaurant_id, " +
        "       user_restaurant_match_counter.user_restaurant_rank " +
        "FROM   user_restaurant_match_counter " +
        "WHERE  user_restaurant_match_counter.user_id = ? " + ") y " + "ON " +
        " x.RESTAURANT_ID = y.RESTAURANT_ID" + "ORDER BY " +
        " ISNULL(y.user_restaurant_rank), y.user_restaurant_rank ASC";
    private static final String HIDE_CHAINED_RESTAURANT = "0";
    private static final boolean printDebugExtra = true;

    public RestaurantsSearchResultsHelper() {
        super();
    }

    // return list of restaurantIds based on different parameters
    public List<String> showListOfRestaurantsSearchResults(String userId,
        String restaurantId, String neighborhoodId, String cityId,
        String stateName, String[] cuisineTier1IdArray, String[] priceIdList,
        String rating, String savedFlag, String favFlag, String dealFlag,
        String chainFlag, String paginationId, String[] cuisineTier2IdArray,
        String[] themeIdArray, String[] whoareyouwithIdArray,
        String[] typeOfRestaurantIdArray, String[] occasionIdArray)
        throws TasteSyncException {
        InputRestaurantSearchVO inputRestaurantSearchVO = new InputRestaurantSearchVO(userId,
                restaurantId, neighborhoodId, cityId, stateName,
                cuisineTier1IdArray, priceIdList, rating, savedFlag, favFlag,
                dealFlag, chainFlag, paginationId, cuisineTier2IdArray,
                themeIdArray, whoareyouwithIdArray, typeOfRestaurantIdArray,
                occasionIdArray);

        if (printDebugExtra) {
            if (logger.isDebugEnabled()) {
                logger.debug("inputRestaurantSearchVO=" +
                    inputRestaurantSearchVO);
            }

            System.out.println("inputRestaurantSearchVO=" +
                inputRestaurantSearchVO);
        }

        // build query based on various input parameters
        StringBuffer consolidatedSearchQuery = new StringBuffer();
        consolidatedSearchQuery.append(SEARCH_QUERY_PART1_SQL);
        consolidatedSearchQuery.append(SEARCH_QUERY_PART2_1_SQL);

        // add different tables for select part

        //-- IF restaurantSearchParameters{searchLocation{neighborhoodId}} 
        //OR recoRequestParameters{recoRequestLocation{neighborhoodId}} is not NULL
        if (inputRestaurantSearchVO.getNeighborhoodId() != null) {
            consolidatedSearchQuery.append(", ")
                                   .append("restaurant_neighbourhood");
        }

        //-- IF restaurantSearchParameters{savedFlag} is not null 
        if (inputRestaurantSearchVO.getSavedFlag() != null) {
            consolidatedSearchQuery.append(",  ").append("user_restaurant_saved");
        }

        //-- IF restaurantSearchParameters{favFlag} is not null
        if (inputRestaurantSearchVO.getFavFlag() != null) {
            consolidatedSearchQuery.append(", ").append("user_restaurant_fav");
        }

        //-- IF recoRequestParameters{listOfCuisTier2{cuisineTier2Id}} OR restaurantSearchParameters{listOfCuisinesTier1{cuisineTier1Id}} 
        //OR recoRequestParameters{listOfCuisTier1{cuisineTier1Id}} is not null 
        if (((inputRestaurantSearchVO.getCuisineTier1IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier1IdArray().length != 0)) ||
                ((inputRestaurantSearchVO.getCuisineTier2IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier2IdArray().length != 0))) {
            consolidatedSearchQuery.append(", ").append("restaurant_cuisine");
        }

        //-- IF restaurantSearchParameters{listOfCuisinesTier1{cuisineTier1Id}} 
        //OR recoRequestParameters{listOfCuisTier1{cuisineTier1Id}} is not null 
        if ((inputRestaurantSearchVO.getCuisineTier1IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier1IdArray().length != 0)) {
            consolidatedSearchQuery.append(", ").append("cuisine_tier_mapper");
        }

        // -- IF restaurantSearchParameters{chainFlag} = 0 (0 means "Hide" Chain Restaurants)
        if (HIDE_CHAINED_RESTAURANT.equals(
                    inputRestaurantSearchVO.getChainFlag())) {
            consolidatedSearchQuery.append(", ")
                                   .append("restaurant_extended_info");
        }

        // add different tables for where part
        // Always present
        if ((inputRestaurantSearchVO.getCityId() == null) ||
                inputRestaurantSearchVO.getCityId().isEmpty()) {
            throw new TasteSyncException("City Id is not avaialble cityId=" +
                inputRestaurantSearchVO.getCityId());
        }

        consolidatedSearchQuery.append("WHERE ");

        if (inputRestaurantSearchVO.getCityId() != null) {
            //-- IF restaurantSearchParameters{searchLocation{cityId}} OR recoRequestParameters{recoRequestLocation{cityId}} is not NULL
            //-- restaurantSearchParameters{searchLocation{cityId}} OR recoRequestParameters{recoRequestLocation{cityId}}
            consolidatedSearchQuery.append("restaurant.RESTAURANT_CITY_ID=? ");
        }

        //-- IF restaurantSearchParameters{searchLocation{neighborhoodId}} OR recoRequestParameters{recoRequestLocation{neighborhoodId}} is not NULL
        //-- restaurantSearchParameters{searchLocation{neighborhoodId}} OR recoRequestParameters{recoRequestLocation{neighborhoodId}}
        if (inputRestaurantSearchVO.getNeighborhoodId() != null) {
            consolidatedSearchQuery.append(
                "AND restaurant_neighbourhood.neighbourhood_id=? ");
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
                "AND restaurant_cuisine.tier2_cuisine_id=? ");
        }

        //-- IF restaurantSearchParameters{listOfCuisinesTier1{cuisineTier1Id}} OR recoRequestParameters{listOfCuisTier1{cuisineTier1Id}} is not null 
        if ((inputRestaurantSearchVO.getCuisineTier1IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier1IdArray().length != 0)) {
            consolidatedSearchQuery.append(
                "AND cuisine_tier_mapper.tier1_cuisine_id IN (?) ");
            consolidatedSearchQuery.append(
                "AND restaurant_cuisine.tier2_cuisine_id = cuisine_tier_mapper.tier2_cuisine_id ");
        }

        if (((inputRestaurantSearchVO.getCuisineTier1IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier1IdArray().length != 0)) ||
                ((inputRestaurantSearchVO.getCuisineTier2IdArray() != null) &&
                (inputRestaurantSearchVO.getCuisineTier2IdArray().length != 0))) {
            consolidatedSearchQuery.append(
                "AND restaurant.restaurant_id = restaurant_cuisine.restaurant_id ");
        }

        //-- IF restaurantSearchParameters{listOfPrice{priceId}} OR recoRequestParameters{listOfPrice{priceId}} is not null 
        if ((inputRestaurantSearchVO.getPriceIdList() != null) &&
                (inputRestaurantSearchVO.getPriceIdList().length != 0)) {
            consolidatedSearchQuery.append("AND restaurant.PRICE_RANGE IN (?) ");
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
        consolidatedSearchQuery.append(SEARCH_QUERY_PART3_LEFT_OUTER_JOIN_SQL);

        if (printDebugExtra) {
            if (logger.isDebugEnabled()) {
                logger.debug("consolidatedSearchQuery=" +
                    consolidatedSearchQuery.toString());
            }

            System.out.println("consolidatedSearchQuery=" +
                consolidatedSearchQuery.toString());
        }

        // query is built. now bind the parameters!
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(consolidatedSearchQuery.toString());

            int bindPosition = 0;

            if (inputRestaurantSearchVO.getCityId() != null) {
                ++bindPosition;
                statement.setString(bindPosition,
                    inputRestaurantSearchVO.getCityId());
            }

            if (inputRestaurantSearchVO.getNeighborhoodId() != null) {
                ++bindPosition;
                statement.setString(bindPosition,
                    inputRestaurantSearchVO.getNeighborhoodId());
            }

            //TODO recheck
            if (inputRestaurantSearchVO.getSavedFlag() != null) {
                ++bindPosition;
                statement.setString(bindPosition,
                    inputRestaurantSearchVO.getUserId());
            }

            //TODO recheck
            if (inputRestaurantSearchVO.getFavFlag() != null) {
                ++bindPosition;
                statement.setString(bindPosition,
                    inputRestaurantSearchVO.getUserId());
            }

            //TODO recheck
            if ((inputRestaurantSearchVO.getCuisineTier2IdArray() != null) &&
                    (inputRestaurantSearchVO.getCuisineTier2IdArray().length != 0)) {
                consolidatedSearchQuery.append(
                    "AND restaurant_cuisine.tier2_cuisine_id=? ");
            }

            //-- IF restaurantSearchParameters{listOfCuisinesTier1{cuisineTier1Id}} OR recoRequestParameters{listOfCuisTier1{cuisineTier1Id}} is not null 
            if ((inputRestaurantSearchVO.getCuisineTier1IdArray() != null) &&
                    (inputRestaurantSearchVO.getCuisineTier1IdArray().length != 0)) {
                StringBuffer cuisineTier1IdParameters = new StringBuffer();

                for (int i = 0;
                        i < inputRestaurantSearchVO.getCuisineTier1IdArray().length;
                        ++i) {
                    cuisineTier1IdParameters.append(inputRestaurantSearchVO.getCuisineTier1IdArray()[i]);

                    if (i != (inputRestaurantSearchVO.getCuisineTier1IdArray().length -
                            1)) {
                        cuisineTier1IdParameters.append(",");
                    }
                }

                ++bindPosition;
                statement.setString(bindPosition,
                    cuisineTier1IdParameters.toString());
            }

            //-- IF restaurantSearchParameters{listOfPrice{priceId}} OR recoRequestParameters{listOfPrice{priceId}} is not null 
            if ((inputRestaurantSearchVO.getPriceIdList() != null) &&
                    (inputRestaurantSearchVO.getPriceIdList().length != 0)) {
                StringBuffer priceIdParameters = new StringBuffer();

                for (int i = 0;
                        i < inputRestaurantSearchVO.getPriceIdList().length;
                        ++i) {
                    priceIdParameters.append(inputRestaurantSearchVO.getPriceIdList()[i]);

                    if (i != (inputRestaurantSearchVO.getPriceIdList().length -
                            1)) {
                        priceIdParameters.append(",");
                    }
                }

                ++bindPosition;
                statement.setString(bindPosition, priceIdParameters.toString());
            }

            //-- IF restaurantSearchParameters{rating} is not null 
            if ((inputRestaurantSearchVO.getRating() != null)) {
                ++bindPosition;
                statement.setDouble(bindPosition, new Double(rating));
            }

            // -- IF restaurantSearchParameters{chainFlag} = 0 (0 means "Hide" Chain Restaurants)
            if (HIDE_CHAINED_RESTAURANT.equals(
                        inputRestaurantSearchVO.getChainFlag())) {
                ++bindPosition;
                statement.setDouble(bindPosition, new Double(rating));
            }

            //Fixed part
            // Left outer join
            consolidatedSearchQuery.append(SEARCH_QUERY_PART3_LEFT_OUTER_JOIN_SQL);

            ++bindPosition;
            statement.setString(bindPosition, userId);

            resultset = statement.executeQuery();

            List<String> restaurantIdList = new ArrayList<String>(50);
            String restaurantIdValue = null;

            while (resultset.next()) {
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_id"));

                restaurantIdList.add(restaurantIdValue);
            }

            statement.close();

            return restaurantIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while showListOfRestaurantsSearchResults= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }
}
