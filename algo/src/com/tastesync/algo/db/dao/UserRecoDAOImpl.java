package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.queries.UserRecoQueries;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.CityNeighbourhoodVO;
import com.tastesync.algo.model.vo.UserRecoSupplyTierVO;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.db.pool.TSDataSource;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class UserRecoDAOImpl extends BaseDaoImpl implements UserRecoDAO {
    @Override
    public int getCountRecorequestReplyUser(TSDataSource tsDataSource,
        Connection connection, String recoRequestId, String assignedUserId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.COUNT_RECOREQUEST_REPLY_USER_SELECT_SQL);

            statement.setString(1, recoRequestId);
            statement.setString(2, assignedUserId);
            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getCountRecorequestReplyUser= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountUserCuistier2Match(TSDataSource tsDataSource,
        Connection connection, String userId, List<String> cuisineTier2IdList,
        double matchCount) throws TasteSyncException {
        if ((cuisineTier2IdList == null) || (cuisineTier2IdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = UserRecoQueries.COUNT_USER_CUISTIER2_MATCH_SELECT_SQL;
            StringBuffer buildQuestionMarksInSql = new StringBuffer();

            // add additonal parameters as ?
            for (int i = 0; i < cuisineTier2IdList.size(); ++i) {
                buildQuestionMarksInSql.append("?");

                if (i != (cuisineTier2IdList.size() - 1)) {
                    buildQuestionMarksInSql.append(",");
                }
            }

            sql = StringUtils.replace(sql, "1_REPLACE_PARAM",
                    buildQuestionMarksInSql.toString());

            statement = connection.prepareStatement(sql);

            int bindposition = 0;
            ++bindposition;
            statement.setString(bindposition, userId);
            ++bindposition;
            statement.setDouble(bindposition, matchCount);

            for (String cuisineTier2Id : cuisineTier2IdList) {
                ++bindposition;
                statement.setString(bindposition, cuisineTier2Id);
            }

            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getCountUserCuistier2Match= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountUserCuistier2MatchMapper(TSDataSource tsDataSource,
        Connection connection, String userId, List<String> cuisineTier1IdList,
        double matchCount) throws TasteSyncException {
        if ((cuisineTier1IdList == null) || (cuisineTier1IdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = UserRecoQueries.COUNT_USER_CUISTIER2_MATCH_CUSINE_TIER_MAPPER_SELECT_SQL;
            StringBuffer buildQuestionMarksInSql = new StringBuffer();

            // add additonal parameters as ?
            for (int i = 0; i < cuisineTier1IdList.size(); ++i) {
                buildQuestionMarksInSql.append("?");

                if (i != (cuisineTier1IdList.size() - 1)) {
                    buildQuestionMarksInSql.append(",");
                }
            }

            sql = StringUtils.replace(sql, "1_REPLACE_PARAM",
                    buildQuestionMarksInSql.toString());

            statement = connection.prepareStatement(sql);

            int bindposition = 0;
            ++bindposition;
            statement.setString(bindposition, userId);
            ++bindposition;
            statement.setDouble(bindposition, matchCount);

            for (String cuisineTier1Id : cuisineTier1IdList) {
                ++bindposition;
                statement.setString(bindposition, cuisineTier1Id);
            }

            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getCountUserCuistier2MatchMapper= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountUserOccasionMatch(TSDataSource tsDataSource,
        Connection connection, String userId, List<String> occasionIdList,
        double matchCount) throws TasteSyncException {
        if ((occasionIdList == null) || (occasionIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = UserRecoQueries.COUNT_USER_TYPEOFREST_MATCH_SELECT_SQL;
            StringBuffer buildQuestionMarksInSql = new StringBuffer();

            // add additonal parameters as ?
            for (int i = 0; i < occasionIdList.size(); ++i) {
                buildQuestionMarksInSql.append("?");

                if (i != (occasionIdList.size() - 1)) {
                    buildQuestionMarksInSql.append(",");
                }
            }

            sql = StringUtils.replace(sql, "1_REPLACE_PARAM",
                    buildQuestionMarksInSql.toString());

            statement = connection.prepareStatement(sql);

            int bindposition = 0;
            ++bindposition;
            statement.setString(bindposition, userId);
            ++bindposition;
            statement.setDouble(bindposition, matchCount);

            for (String occasionId : occasionIdList) {
                ++bindposition;
                statement.setString(bindposition, occasionId);
            }

            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getCountUserOccasionMatch= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountUserPriceMatch(TSDataSource tsDataSource,
        Connection connection, String userId, List<String> priceIdList,
        double matchCount) throws TasteSyncException {
        if ((priceIdList == null) || (priceIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = UserRecoQueries.COUNT_USER_PRICE_MATCH_SELECT_SQL;
            StringBuffer buildQuestionMarksInSql = new StringBuffer();

            // add additonal parameters as ?
            for (int i = 0; i < priceIdList.size(); ++i) {
                buildQuestionMarksInSql.append("?");

                if (i != (priceIdList.size() - 1)) {
                    buildQuestionMarksInSql.append(",");
                }
            }

            sql = StringUtils.replace(sql, "1_REPLACE_PARAM",
                    buildQuestionMarksInSql.toString());

            statement = connection.prepareStatement(sql);

            int bindposition = 0;
            ++bindposition;
            statement.setString(bindposition, userId);
            ++bindposition;
            statement.setDouble(bindposition, matchCount);

            for (String priceId : priceIdList) {
                ++bindposition;
                statement.setString(bindposition, priceId);
            }

            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while getCountUserPriceMatch= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountUserThemeMatch(TSDataSource tsDataSource,
        Connection connection, String userId, List<String> themeIdList,
        double matchCount) throws TasteSyncException {
        if ((themeIdList == null) || (themeIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = UserRecoQueries.COUNT_USER_THEME_MATCH_SELECT_SQL;
            StringBuffer buildQuestionMarksInSql = new StringBuffer();

            // add additonal parameters as ?
            for (int i = 0; i < themeIdList.size(); ++i) {
                buildQuestionMarksInSql.append("?");

                if (i != (themeIdList.size() - 1)) {
                    buildQuestionMarksInSql.append(",");
                }
            }

            sql = StringUtils.replace(sql, "1_REPLACE_PARAM",
                    buildQuestionMarksInSql.toString());

            statement = connection.prepareStatement(sql);

            int bindposition = 0;
            ++bindposition;
            statement.setString(bindposition, userId);
            ++bindposition;
            statement.setDouble(bindposition, matchCount);

            for (String themeId : themeIdList) {
                ++bindposition;
                statement.setString(bindposition, themeId);
            }

            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while getCountUserThemeMatch= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountUserTypeofrestMatch(TSDataSource tsDataSource,
        Connection connection, String userId,
        List<String> typeOfRestaurantIdList, double matchCount)
        throws TasteSyncException {
        if ((typeOfRestaurantIdList == null) ||
                (typeOfRestaurantIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = UserRecoQueries.COUNT_USER_TYPEOFREST_MATCH_SELECT_SQL;
            StringBuffer buildQuestionMarksInSql = new StringBuffer();

            // add additonal parameters as ?
            for (int i = 0; i < typeOfRestaurantIdList.size(); ++i) {
                buildQuestionMarksInSql.append("?");

                if (i != (typeOfRestaurantIdList.size() - 1)) {
                    buildQuestionMarksInSql.append(",");
                }
            }

            sql = StringUtils.replace(sql, "1_REPLACE_PARAM",
                    buildQuestionMarksInSql.toString());

            statement = connection.prepareStatement(sql);

            int bindposition = 0;
            ++bindposition;
            statement.setString(bindposition, userId);
            ++bindposition;
            statement.setDouble(bindposition, matchCount);

            for (String typeOfRestaurantId : typeOfRestaurantIdList) {
                ++bindposition;
                statement.setString(bindposition, typeOfRestaurantId);
            }

            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getCountUserTypeofrestMatch= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountUserWhoareyouwithMatch(TSDataSource tsDataSource,
        Connection connection, String userId, List<String> whoareyouwithIdList,
        double matchCount) throws TasteSyncException {
        if ((whoareyouwithIdList == null) || (whoareyouwithIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = UserRecoQueries.COUNT_USER_WHOAREYOUWITH_MATCH_SELECT_SQL;
            StringBuffer buildQuestionMarksInSql = new StringBuffer();

            // add additonal parameters as ?
            for (int i = 0; i < whoareyouwithIdList.size(); ++i) {
                buildQuestionMarksInSql.append("?");

                if (i != (whoareyouwithIdList.size() - 1)) {
                    buildQuestionMarksInSql.append(",");
                }
            }

            sql = StringUtils.replace(sql, "1_REPLACE_PARAM",
                    buildQuestionMarksInSql.toString());

            statement = connection.prepareStatement(sql);

            int bindposition = 0;
            ++bindposition;
            statement.setString(bindposition, userId);
            ++bindposition;
            statement.setDouble(bindposition, matchCount);

            for (String whoareyouwithId : whoareyouwithIdList) {
                ++bindposition;
                statement.setString(bindposition, whoareyouwithId);
            }

            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getCountUserWhoareyouwithMatch= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getDemandTierForSingleUser(TSDataSource tsDataSource,
        Connection connection, String userId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.USER_RECO_DEMAND_TIER_PRECALC_SELECT_SQL);

            statement.setString(1, userId);
            resultset = statement.executeQuery();

            String demandTier = null;

            // only one result
            if (resultset.next()) {
                demandTier = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_reco_demand_tier_precalc.demand_tier_precalc"));
            }

            statement.close();

            // wht should be the default
            if (demandTier == null) {
                throw new TasteSyncException(
                    "Not available Demand Tier (null) for the userId= " +
                    userId);
            }

            return Integer.valueOf(demandTier);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getInitiatorUserIdFromRecorequestId= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public String getInitiatorUserIdFromRecorequestId(
        TSDataSource tsDataSource, Connection connection, String recoRequestId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_USER_SELECT_SQL);

            statement.setString(1, recoRequestId);
            resultset = statement.executeQuery();

            String initiatorUserId = null;

            // only one result
            if (resultset.next()) {
                initiatorUserId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_user.initiator_user_id"));
            }

            statement.close();

            return initiatorUserId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getInitiatorUserIdFromRecorequestId= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getNumUserCityNbrhoodMatchTopicFound(TSDataSource tsDataSource,
        Connection connection, String userId, String cityId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = UserRecoQueries.COUNT_NOT_USER_TOPIC_MATCH_4_SELECT_SQL;

            //            if ((neighborhoodId != null) && !neighborhoodId.isEmpty()) {
            //                sql = UserRecoQueries.COUNT_NOT_USER_TOPIC_MATCH_4_WITH_NBRHD_SELECT_SQL;
            //            }
            statement = connection.prepareStatement(sql);

            statement.setInt(1, 1);
            statement.setString(2, cityId);
            statement.setString(3, userId);
            //
            //            if ((neighborhoodId != null) && !neighborhoodId.isEmpty()) {
            //                statement.setString(4, neighborhoodId);
            //            }
            resultset = statement.executeQuery();

            int numUserCityNbrhoodMatchTopicFound = 0;

            if (resultset.next()) {
                numUserCityNbrhoodMatchTopicFound = resultset.getInt(1);
            }

            statement.close();

            return numUserCityNbrhoodMatchTopicFound;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getNumUserCityNbrhoodMatchTopicFound= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getNumberOfSameParamRequests(TSDataSource tsDataSource,
        Connection connection, String initiatorUserId, String recoRequestId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.COUNT_MATCH_FOUND_RECOREQUEST_MERGEDPARAMETER_SELECT_SQL);

            statement.setInt(1, 2);
            statement.setString(2, initiatorUserId);
            statement.setString(3, recoRequestId);
            resultset = statement.executeQuery();

            int numSameParamRequests = 0;

            if (resultset.next()) {
                numSameParamRequests = resultset.getInt(1);
            }

            statement.close();

            return numSameParamRequests;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getNumRecorequestsAssignedToday= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public CityNeighbourhoodVO getRecorequestCityIdNbrIdList(
        TSDataSource tsDataSource, Connection connection, String recoRequestId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_LOCATION_SELECT_SQL);

            statement.setString(1, recoRequestId);
            resultset = statement.executeQuery();

            String cityId = null;
            String neighbourhoodId = null;

            //only one result
            if (resultset.next()) {
                cityId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_location.city_id"));

                neighbourhoodId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_location.neighbourhood_id"));
            }

            statement.close();

            CityNeighbourhoodVO cityNeighbourhoodVO = null;

            if (cityId != null) {
                cityNeighbourhoodVO = new CityNeighbourhoodVO(cityId,
                        neighbourhoodId);
            }

            return cityNeighbourhoodVO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestOccasionIdList= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestCuisineTier1IdList(
        TSDataSource tsDataSource, Connection connection, String recoRequestId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_RESTAURANT_CUISINE_TIER1_SELECT_SQL);

            statement.setString(1, recoRequestId);
            resultset = statement.executeQuery();

            List<String> cuisineTier1IdList = new ArrayList<String>();
            String cuisineTier1Id = null;

            while (resultset.next()) {
                cuisineTier1Id = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_cuisine_tier1.cuisine_tier1_id"));

                if (cuisineTier1Id != null) {
                    cuisineTier1IdList.add(cuisineTier1Id);
                }
            }

            statement.close();

            return cuisineTier1IdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestCuisineTier2IdList= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestCuisineTier2IdList(
        TSDataSource tsDataSource, Connection connection, String recoRequestId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_RESTAURANT_CUISINE_SELECT_SQL);

            statement.setString(1, recoRequestId);
            resultset = statement.executeQuery();

            List<String> cuisineTier2IdList = new ArrayList<String>();
            String cuisineTier2Id = null;

            while (resultset.next()) {
                cuisineTier2Id = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_cuisine_tier2.cuisine_tier2_id"));

                if (cuisineTier2Id != null) {
                    cuisineTier2IdList.add(cuisineTier2Id);
                }
            }

            statement.close();

            return cuisineTier2IdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestCuisineTier2IdList= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestOccasionIdList(
        TSDataSource tsDataSource, Connection connection, String recoRequestId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_OCCASION_SELECT_SQL);

            statement.setString(1, recoRequestId);
            resultset = statement.executeQuery();

            List<String> occasionIdList = new ArrayList<String>();
            String occasionId = null;

            while (resultset.next()) {
                occasionId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_occasion.occasion_id"));

                if (occasionId != null) {
                    occasionIdList.add(occasionId);
                }
            }

            statement.close();

            return occasionIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestOccasionIdList= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestPriceIdList(TSDataSource tsDataSource,
        Connection connection, String recoRequestId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_PRICE_SELECT_SQL);

            statement.setString(1, recoRequestId);
            resultset = statement.executeQuery();

            List<String> priceIdList = new ArrayList<String>();
            String priceId = null;

            while (resultset.next()) {
                priceId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_price.price_id"));

                if (priceId != null) {
                    priceIdList.add(priceId);
                }
            }

            statement.close();

            return priceIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestPriceIdList= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestThemeIdList(TSDataSource tsDataSource,
        Connection connection, String recoRequestId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_THEME_SELECT_SQL);

            statement.setString(1, recoRequestId);
            resultset = statement.executeQuery();

            List<String> themeIdList = new ArrayList<String>();
            String themeId = null;

            while (resultset.next()) {
                themeId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_theme.theme_id"));

                if (themeId != null) {
                    themeIdList.add(themeId);
                }
            }

            statement.close();

            return themeIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestPriceIdList= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestTypeofrestIdList(
        TSDataSource tsDataSource, Connection connection, String recoRequestId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_TYPEOFREST_SELECT_SQL);

            statement.setString(1, recoRequestId);
            resultset = statement.executeQuery();

            List<String> typeofrestIdList = new ArrayList<String>();
            String typeofrestId = null;

            while (resultset.next()) {
                typeofrestId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_typeofrest.typeofrest_id"));

                if (typeofrestId != null) {
                    typeofrestIdList.add(typeofrestId);
                }
            }

            statement.close();

            return typeofrestIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestTypeofrestIdList= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestWhoareyouwithIdList(
        TSDataSource tsDataSource, Connection connection, String recoRequestId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_WHOAREYOUWITH_SELECT_SQL);

            statement.setString(1, recoRequestId);
            resultset = statement.executeQuery();

            List<String> whoareyouwithIdList = new ArrayList<String>();
            String whoareyouwithId = null;

            while (resultset.next()) {
                whoareyouwithId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_whoareyouwith.whoareyouwith_id"));

                if (whoareyouwithId != null) {
                    whoareyouwithIdList.add(whoareyouwithId);
                }
            }

            statement.close();

            return whoareyouwithIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestWhoareyouwithIdList= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public double getUserAUserBMatchTier(TSDataSource tsDataSource,
        Connection connection, String userAId, String userBId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.USER_USER_MATCH_TIER_SELECT_SQL);

            statement.setString(1, userAId);
            statement.setString(2, userBId);

            resultset = statement.executeQuery();

            String userAUserBMatchTier = null;

            // only one result
            if (resultset.next()) {
                userAUserBMatchTier = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_user_match_tier.match_tier"));
            }

            statement.close();

            double dblUserAUserBMatchTier = 0;

            if (userAUserBMatchTier != null) {
                Double.valueOf(dblUserAUserBMatchTier);
            }

            return dblUserAUserBMatchTier;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while getUserAUserBMatchTier= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<UserRecoSupplyTierVO> getUserRecoSupplyTierVO(
        TSDataSource tsDataSource, Connection connection, String userId,
        String recorequestId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.USER_RECO_SUPPLY_TIER_SELECT_SQL);

            statement.setString(1, userId);
            statement.setString(2, recorequestId);

            resultset = statement.executeQuery();

            List<UserRecoSupplyTierVO> userRecoSupplyTierVOList = new LinkedList<UserRecoSupplyTierVO>();
            String temp1usersUserId = null;
            String temp1usersSupplyTier = null;
            int temp1usersSupplyTierValue = Integer.MIN_VALUE;
            UserRecoSupplyTierVO userRecoSupplyTierVO = null;

            while (resultset.next()) {
                temp1usersUserId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_reco_supply_tier.user_id"));

                temp1usersSupplyTier = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_reco_supply_tier.user_supply_inv_tier"));

                if (temp1usersSupplyTier != null) {
                    temp1usersSupplyTierValue = Integer.valueOf(temp1usersSupplyTier);
                } else {
                    temp1usersSupplyTierValue = Integer.MIN_VALUE;
                }

                userRecoSupplyTierVO = new UserRecoSupplyTierVO(temp1usersUserId,
                        temp1usersSupplyTierValue);
                userRecoSupplyTierVOList.add(userRecoSupplyTierVO);
            }

            statement.close();

            return userRecoSupplyTierVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getUserRecoSupplyTierVO= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public boolean isUserIdLinkedWithFbFriend(TSDataSource tsDataSource,
        Connection connection, String userId, String otherUserFbId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            // Check dynamic part
            statement = connection.prepareStatement(UserRecoQueries.COUNT_USER_FRIEND_FB_SELECT_SQL);

            statement.setString(1, userId);
            statement.setString(2, otherUserFbId);
            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            boolean userIdLinkedWithFbFriend = false;

            if (rowCount > 0) {
                userIdLinkedWithFbFriend = true;
            }

            statement.close();

            return userIdLinkedWithFbFriend;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while isUserIdLinkedWithFbFriend= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public boolean isUserIdLinkedWithReportedInfo(TSDataSource tsDataSource,
        Connection connection, String userId, String reportedUserId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(UserRecoQueries.COUNT_USER_REPORTED_INFO_REPORTED_OTHER_USERS_SELECT_SQL);

            statement.setString(1, userId);
            statement.setString(2, reportedUserId);
            resultset = statement.executeQuery();

            int rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();

            boolean userIdLinkedWithFbFriend = false;

            if (rowCount > 0) {
                userIdLinkedWithFbFriend = true;

                return true;
            }

            statement = connection.prepareStatement(UserRecoQueries.COUNT_USER_REPORTED_INFO_REPORTED_OTHER_USERS_SELECT_SQL);

            statement.setString(1, reportedUserId);
            statement.setString(2, userId);
            resultset = statement.executeQuery();

            rowCount = 0;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
            }

            statement.close();
            userIdLinkedWithFbFriend = false;

            if (rowCount > 0) {
                userIdLinkedWithFbFriend = true;

                return true;
            }

            return userIdLinkedWithFbFriend;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while isUserIdLinkedWithReportedInfo= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void submitPiRecorequestTsAssigned(TSDataSource tsDataSource,
        Connection connection, String recoRequestId, String assigneduserUserId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            tsDataSource.begin();
            statement = connection.prepareStatement(UserRecoQueries.PI_RECOREQUEST_TS_ASSIGNED_INSERT_SQL);

            statement.setTimestamp(1,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setString(2, "N");
            statement.setString(3, "N");
            statement.setString(4, assigneduserUserId);
            statement.setString(5, "Y");
            statement.setString(6, "system-assigned-other");
            statement.setString(7, recoRequestId);

            statement.executeUpdate();
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
    public void submitRecorequestTsAssigned(TSDataSource tsDataSource,
        Connection connection, String recoRequestId, String assigneduserUserId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            tsDataSource.begin();
            statement = connection.prepareStatement(UserRecoQueries.RECOREQUEST_TS_ASSIGNED_INSERT_SQL);
            statement.setTimestamp(1,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setString(2, "N");
            statement.setString(3, "N");
            statement.setString(4, assigneduserUserId);
            statement.setString(5, "Y");
            statement.setString(6, "system-assigned-other");
            statement.setInt(7, 1);
            statement.setString(8, recoRequestId);

            statement.executeUpdate();
            statement.close();
            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while submitAssignedRankUserRestaurant= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void submitUserRecoSupplyTier(TSDataSource tsDataSource,
        Connection connection, String userId, int usrSupplyInvTier,
        int userTierCalcFlag) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            tsDataSource.begin();
            statement = connection.prepareStatement(UserRecoQueries.USER_RECO_SUPPLY_TIER_INSERT_SQL);
            statement.setString(1, userId);
            statement.setInt(2, usrSupplyInvTier);
            statement.setInt(3, userTierCalcFlag);
            statement.setInt(4, userTierCalcFlag);

            statement.executeUpdate();
            statement.close();
            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while submitUserRecoSupplyTier= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }
}
