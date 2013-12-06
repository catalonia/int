package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.queries.PiUserRecoQueries;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.PiRestaurantRecommendationVO;

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


public class PiUserRecoDAOImpl extends UserRecoDAOImpl implements PiUserRecoDAO {
    @Override
    public List<String> getAllReccomendationIds(TSDataSource tsDataSource,
        Connection connection) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(PiUserRecoQueries.PI_RECOMMENDATIONS_ALL_SELECT_SQL);
            resultset = statement.executeQuery();

            List<String> allRecommendationIdsList = new LinkedList<String>();
            String reccomendationId;

            while (resultset.next()) {
                reccomendationId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "pi_recommendations.recommendation_id"));
                allRecommendationIdsList.add(reccomendationId);
            }

            statement.close();

            return allRecommendationIdsList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getAllReccomendationIds= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountPiRecoUserCuistier2MatchMapper(
        TSDataSource tsDataSource, Connection connection,
        String recommendationId, List<String> cuisineTier1IdList)
        throws TasteSyncException {
        if ((cuisineTier1IdList == null) || (cuisineTier1IdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = PiUserRecoQueries.COUNT_PI_RECO_CUISTIER_TIER2_MATCH_CUSINE_TIER_MAPPER_SELECT_SQL;
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
            statement.setString(bindposition, recommendationId);

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
                "Error while getCountPiRecoUserCuistier2MatchMapper= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountPiRecoUserOccasionMatch(TSDataSource tsDataSource,
        Connection connection, String recommendationId,
        List<String> occasionIdList) throws TasteSyncException {
        if ((occasionIdList == null) || (occasionIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = PiUserRecoQueries.COUNT_PI_RECO_OCCASION_MATCH_SELECT_SQL;
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
            statement.setString(bindposition, recommendationId);

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
                "Error while getCountPiRecoUserOccasionMatch= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountPiRecoUserPriceMatch(TSDataSource tsDataSource,
        Connection connection, String userId, List<String> priceIdList)
        throws TasteSyncException {
        if ((priceIdList == null) || (priceIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = PiUserRecoQueries.COUNT_PI_RECO_PRICE_MATCH_SELECT_SQL;
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
    public int getCountPiRecoUserThemeMatch(TSDataSource tsDataSource,
        Connection connection, String recommendationId, List<String> themeIdList)
        throws TasteSyncException {
        if ((themeIdList == null) || (themeIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = PiUserRecoQueries.COUNT_PI_USER_THEME_MATCH_SELECT_SQL;
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
            statement.setString(bindposition, recommendationId);

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
            throw new TasteSyncException(
                "Error while getCountPiRecoUserThemeMatch= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountPiRecoUserTypeofrestMatch(TSDataSource tsDataSource,
        Connection connection, String recommendationId,
        List<String> typeOfRestaurantIdList) throws TasteSyncException {
        if ((typeOfRestaurantIdList == null) ||
                (typeOfRestaurantIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = PiUserRecoQueries.COUNT_PI_RECO_TYPEOFREST_MATCH_SELECT_SQL;
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
            statement.setString(bindposition, recommendationId);

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
                "Error while getCountPiRecoUserTypeofrestMatch= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountPiRecoUserWhoareyouwithMatch(TSDataSource tsDataSource,
        Connection connection, String recommendationId,
        List<String> whoareyouwithIdList) throws TasteSyncException {
        if ((whoareyouwithIdList == null) || (whoareyouwithIdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = PiUserRecoQueries.COUNT_PI_RECO_WHOAREYOUWITH_MATCH_SELECT_SQL;
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
            statement.setString(bindposition, recommendationId);

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
                "Error while getCountPiRecoUserWhoareyouwithMatch= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public int getCountPiUserCuistier2Match(TSDataSource tsDataSource,
        Connection connection, String recommendationId,
        List<String> cuisineTier2IdList) throws TasteSyncException {
        if ((cuisineTier2IdList == null) || (cuisineTier2IdList.size() == 0)) {
            return 0;
        }

        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = PiUserRecoQueries.COUNT_PI_RECO_CUISTIER2_MATCH_SELECT_SQL;
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
            statement.setString(bindposition, recommendationId);

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
                "Error while getCountPiUserCuistier2Match= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getExcludedRecommendationIdList(
        TSDataSource tsDataSource, Connection connection, String userId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(PiUserRecoQueries.EXCLUDE_RESTAURANT_ID_FRM_PI_RECOMMENDATIONS_SELECT_SQL);
            statement.setString(1, userId);
            resultset = statement.executeQuery();

            List<String> excludedRecommendationIdsList = new LinkedList<String>();
            String reccomendationId;

            while (resultset.next()) {
                reccomendationId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "pi_recommendations.recommendation_id"));
                excludedRecommendationIdsList.add(reccomendationId);
            }

            statement.close();

            return excludedRecommendationIdsList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getExcludedRecommendationIdList= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public PiRestaurantRecommendationVO getPiRecommendationIdText(
        TSDataSource tsDataSource, Connection connection,
        String recommendationId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(PiUserRecoQueries.PI_RECOMMEDATION_ID_TEXT_SELECT_SQL);
            statement.setString(1, recommendationId);
            resultset = statement.executeQuery();

            PiRestaurantRecommendationVO piRestaurantRecommendationVO = null;
            String restaurantId;
            String recommendationText;

            if (resultset.next()) {
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "pi_recommendations.restaurant_id"));
                recommendationText = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "pi_recommendations.recommendation_text"));

                piRestaurantRecommendationVO = new PiRestaurantRecommendationVO(restaurantId,
                        recommendationText, recommendationId);
            }

            statement.close();

            return piRestaurantRecommendationVO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getPiRecommendationIdText= " + e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getPiUserAlreadyAssignedToUser(
        TSDataSource tsDataSource, Connection connection, String userId)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(PiUserRecoQueries.PI_RECO_LOG_SELECT_SQL);

            statement.setString(1, userId);
            resultset = statement.executeQuery();

            List<String> piUsersAlreadyAssignedIdList = new LinkedList<String>();
            String piUsersAlreadyAssignedId;

            while (resultset.next()) {
                piUsersAlreadyAssignedId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "pi_reco_log.pi_user_id"));
                piUsersAlreadyAssignedIdList.add(piUsersAlreadyAssignedId);
            }

            statement.close();

            return piUsersAlreadyAssignedIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getPiUserAlreadyAssignedToUser= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public List<String> getPiUsersCategoryCityNbrhoodList(
        TSDataSource tsDataSource, Connection connection, String cityId,
        String nbrHoodId) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            String sql = PiUserRecoQueries.USER_CATEGORY_CITY_SELECT_SQL;

            if (nbrHoodId != null) {
                sql = PiUserRecoQueries.USER_CATEGORY_CITY_NBRHOOD_SELECT_SQL;
            }

            statement = connection.prepareStatement(sql);
            statement.setInt(1, 5);
            statement.setInt(2, 1);

            statement.setString(3, cityId);

            if (nbrHoodId != null) {
                statement.setString(4, nbrHoodId);
            }

            resultset = statement.executeQuery();

            List<String> piUsersCategoryIdList = new LinkedList<String>();
            String piUsersCategoryId;

            while (resultset.next()) {
                piUsersCategoryId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "users_category.user_id"));
                piUsersCategoryIdList.add(piUsersCategoryId);
            }

            statement.close();

            return piUsersCategoryIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getPiUsersCategoryCityNbrhoodList= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    @Override
    public void submitPiRecoLog(TSDataSource tsDataSource,
        Connection connection, String userId, String assignedPiUserId,
        PiRestaurantRecommendationVO piRestaurantRecommendationVO)
        throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(PiUserRecoQueries.PI_RECO_LOG_INSERT_SQL);

            //generate Pi_Reco_Log_Id
            String piRecoLogId = userId +
                CommonFunctionsUtil.generateUniqueKey();
            statement.setString(1, piRecoLogId);
            statement.setString(2, assignedPiUserId);
            statement.setString(3,
                piRestaurantRecommendationVO.getRecommendationId());
            statement.setTimestamp(4,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setString(5, userId);

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException("Error while submitPiRecoLog= " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }

    //also see submitRecommendationRequestAnswer (shpould be aligned!!!)
    @Override
    public void submitRecommendationRequestAnswer(TSDataSource tsDataSource,
        Connection connection, String recorequestId, String recommenderUserId,
        String[] restaurantIdList, String replyText) throws TasteSyncException {
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            statement = connection.prepareStatement(PiUserRecoQueries.RECOREQUEST_REPLY_USER_INSERT_SQL);

            //datetime userid random number
            statement.setString(1, recorequestId);

            List<String> inputKeyStr = new ArrayList<String>();
            inputKeyStr.add(recorequestId);

            String replyId = CommonFunctionsUtil.generateUniqueKey(inputKeyStr);
            statement.setString(2, replyId);
            statement.setString(3, null);
            statement.setTimestamp(4,
                CommonFunctionsUtil.getCurrentDateTimestamp());
            statement.setString(5, replyText);
            statement.setString(6, recommenderUserId);
            statement.setString(7, recommenderUserId);
            statement.setInt(8, 4);
            statement.setInt(9, 1);

            statement.executeUpdate();

            statement.close();
            statement = connection.prepareStatement(PiUserRecoQueries.RECOREQUEST_USER_FRIEND_SELECT_SQL);
            statement.setString(1, recorequestId);
            resultset = statement.executeQuery();

            String recommendeeUserUserId = null;

            if (resultset.next()) {
                recommendeeUserUserId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_user.initiator_user_id"));
            }

            statement.close();
            statement = connection.prepareStatement(PiUserRecoQueries.RESTAURANT_REPLY_INSERT_SQL);

            for (String restaurantId : restaurantIdList) {
                statement.setTimestamp(1,
                    CommonFunctionsUtil.getCurrentDateTimestamp());

                statement.setString(2, null);

                statement.setString(3, replyId);
                statement.setString(4, restaurantId);

                statement.executeUpdate();
            }

            statement.close();
            statement = connection.prepareStatement(PiUserRecoQueries.USER_RESTAURANT_INSERT_SQL);

            for (String restaurantId : restaurantIdList) {
                //datetime userid random number
                statement.setString(1, recommendeeUserUserId);
                statement.setString(2, recommenderUserId);
                statement.setString(3, replyId);
                statement.setString(4, restaurantId);

                statement.setTimestamp(5,
                    CommonFunctionsUtil.getCurrentDateTimestamp());

                statement.executeUpdate();
            }

            statement.close();

            statement = connection.prepareStatement(PiUserRecoQueries.COUNT_REPLIES_RECOREQUEST_REPLY_USER_SELECT_SQL);

            statement.setString(1, recorequestId);

            statement.setString(2, recommenderUserId);

            resultset = statement.executeQuery();

            int rowCount;

            if (resultset.next()) {
                rowCount = resultset.getInt(1);
                statement.close();

                if (rowCount == 1) {
                    //There should be way to add points only once!! - recommenderUserId
                    statement = connection.prepareStatement(PiUserRecoQueries.USER_POINTS_UPDATE_SQL);

                    statement.setInt(1, 2);

                    statement.setString(2, recommenderUserId);

                    statement.executeUpdate();

                    statement.close();
                }
            }

            if (statement != null) {
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
                "Error while submitRecommendationRequestAnswer " +
                e.getMessage());
        } finally {
            tsDataSource.closeConnection(statement, resultset);
        }
    }
}
