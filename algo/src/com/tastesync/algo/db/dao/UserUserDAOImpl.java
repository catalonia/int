package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.pool.TSDataSource;
import com.tastesync.algo.db.queries.UserUserQueries;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RecorequestReplyUserVO;
import com.tastesync.algo.model.vo.RecorequestTsAssignedVO;
import com.tastesync.algo.model.vo.RecorequestUserVO;
import com.tastesync.algo.model.vo.RestaurantUserVO;
import com.tastesync.algo.util.CommonFunctionsUtil;
import com.tastesync.algo.util.TSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserUserDAOImpl extends BaseDaoImpl implements UserUserDAO {
    @Override
    public List<String> getRecorequestUserFlaggedUserList(
        int algoIndicatorIdentifyUseridListOne) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_USER_ALGO_SELECT_SQL);

            int algoIndicator = 2;
            statement.setInt(1, algoIndicator);
            resultset = statement.executeQuery();

            List<String> recorequestUserFlaggedUserList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestUserFlaggedUserList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_user.initiator_user_id")));
            }

            statement.close();

            return recorequestUserFlaggedUserList;
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
    public List<String> getRecorequestTsAssignedFlaggedUserList(
        int algoIndicatorIdentifyUseridListSecond) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_TS_ASSIGNED_ALGO_SELECT_SQL);

            int algoIndicator = 1;
            statement.setInt(1, algoIndicator);
            resultset = statement.executeQuery();

            List<String> recorequestTsAssignedFlaggedUserList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestTsAssignedFlaggedUserList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_ts_assigned.assigned_user_id")));
            }

            statement.close();

            return recorequestTsAssignedFlaggedUserList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestTsAssignedFlaggedUserList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestReplyUserFlaggedUserList(
        int algoIndicatorIdentifyUseridListThird) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_REPLY_USER_ALGO_SELECT_SQL);

            int algoIndicator = 4;
            statement.setInt(1, algoIndicator);
            resultset = statement.executeQuery();

            List<String> recorequestReplyUserFlaggedUserList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestReplyUserFlaggedUserList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_reply_user.reply_user_id")));
            }

            statement.close();

            return recorequestReplyUserFlaggedUserList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestReplyUserFlaggedUserList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestReplyUserForSameRecorequestIdFlaggedUserList(
        int algoIndicatorIdentifyUseridListFourth) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_REPLY_USER_USER_ALGO_SELECT_SQL);

            int algoIndicator = 4;
            statement.setInt(1, algoIndicator);
            resultset = statement.executeQuery();

            List<String> recorequestReplyUserForSameRecorequestIdFlaggedUserList =
                new ArrayList<String>();

            while (resultset.next()) {
                recorequestReplyUserForSameRecorequestIdFlaggedUserList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_user.initiator_user_id")));
            }

            statement.close();

            return recorequestReplyUserForSameRecorequestIdFlaggedUserList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRecorequestReplyUserForSameRecorequestIdFlaggedUserList= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public int getNumRecorequestsAssignedToday(String flaggedUserId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.COUNT_RECOREQUEST_TS_ASSIGNED_DATETIME_SELECT_SQL);

            statement.setString(1, flaggedUserId);
            statement.setDate(2, CommonFunctionsUtil.getCurrentDate());
            resultset = statement.executeQuery();

            int numRecorequestsAssignedToday = 0;

            if (resultset.next()) {
                numRecorequestsAssignedToday = resultset.getInt(1);
            }

            statement.close();

            return numRecorequestsAssignedToday;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getNumRecorequestsAssignedToday= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public int getNumRecorequestsAssignedTodayReplied(String flaggedUserId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.COUNT_RECOREQUEST_TS_ASSIGNED_REPLY_USER_SELECT_SQL);

            statement.setString(1, flaggedUserId);
            statement.setDate(2, CommonFunctionsUtil.getCurrentDate());
            resultset = statement.executeQuery();

            int numRecorequestsAssignedTodayReplied = 0;

            if (resultset.next()) {
                numRecorequestsAssignedTodayReplied = resultset.getInt(1);
            }

            statement.close();

            return numRecorequestsAssignedTodayReplied;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getNumRecorequestsAssignedTodayReplied= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public int getNumRecorequestsAssignedNDays(String flaggedUserId, int nDays)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.COUNT_RECOREQUEST_TS_ASSIGNED_DATETIME_SELECT_SQL);

            statement.setString(1, flaggedUserId);

            java.sql.Date requiredDate = CommonFunctionsUtil.getDateNDaysBeforeCurrentDate(nDays);

            statement.setDate(2, requiredDate);
            resultset = statement.executeQuery();

            int numRecorequestsAssigned7Days = 0;

            if (resultset.next()) {
                numRecorequestsAssigned7Days = resultset.getInt(1);
            }

            statement.close();

            return numRecorequestsAssigned7Days;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getNumRecorequestsAssigned7Days= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public int getNumRecorequestsAssignedNDaysReplied(String flaggedUserId,
        int nDays) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.COUNT_RECOREQUEST_TS_ASSIGNED_REPLY_USER_SELECT_SQL);

            statement.setString(1, flaggedUserId);

            // Make an SQL Date out of that  
            java.sql.Date requiredDate = CommonFunctionsUtil.getDateNDaysBeforeCurrentDate(nDays);

            statement.setDate(2, requiredDate);
            resultset = statement.executeQuery();

            int numRecorequestsAssigned7DaysReplied = 0;

            if (resultset.next()) {
                numRecorequestsAssigned7DaysReplied = resultset.getInt(1);
            }

            statement.close();

            return numRecorequestsAssigned7DaysReplied;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getNumRecorequestsAssigned7DaysReplied= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public int getNumRecorequestsInitiatedTotal(String flaggedUserId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.COUNT_RECOREQUEST_USER_SELECT_SQL);

            statement.setString(1, flaggedUserId);
            resultset = statement.executeQuery();

            int numRecorequestsInitiatedTotal = 0;

            if (resultset.next()) {
                numRecorequestsInitiatedTotal = resultset.getInt(1);
            }

            statement.close();

            return numRecorequestsInitiatedTotal;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getNumRecorequestsAssigned7DaysReplied= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<RecorequestUserVO> getLastNRecorequestsUserRecorequestId(
        String flaggedUserId) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_TS_ASSIGNED_SELECT_SQL);

            statement.setString(1, flaggedUserId);
            statement.setInt(2, TSConstants.MAX_RECOREQUEST_CONSIDERED);
            resultset = statement.executeQuery();

            List<RecorequestUserVO> recorequestUserVOList = new ArrayList<RecorequestUserVO>();
            RecorequestUserVO recorequestUserVO = null;
            String recorequestsInitiatedRecorequestId = null;
            Date recorequestsInitiatedRecorequestSentDatetime = null;

            while (resultset.next()) {
                recorequestsInitiatedRecorequestId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_ts_assigned.recorequest_id"));
                recorequestsInitiatedRecorequestSentDatetime = resultset.getDate(
                        "recorequest_ts_assigned.assigned_datetime");

                recorequestUserVO = new RecorequestUserVO(recorequestsInitiatedRecorequestId,
                        recorequestsInitiatedRecorequestSentDatetime);

                recorequestUserVOList.add(recorequestUserVO);
            }

            statement.close();

            return recorequestUserVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getLastNRecorequestsAssignedRecorequestId= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<RecorequestTsAssignedVO> getLastNRecorequestsAssignedRecorequestId(
        String flaggedUserId) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.COUNT_RECOREQUEST_USER_LIMIT_SELECT_SQL);

            statement.setString(1, flaggedUserId);
            statement.setInt(2, TSConstants.MAX_RECOREQUEST_CONSIDERED);
            resultset = statement.executeQuery();

            List<RecorequestTsAssignedVO> recorequestTsAssignedVOList = new ArrayList<RecorequestTsAssignedVO>();
            RecorequestTsAssignedVO recorequestTsAssignedVO = null;
            String recorequestsInitiatedRecorequestId = null;
            Date recorequestsInitiatedRecorequestSentDatetime = null;

            while (resultset.next()) {
                recorequestsInitiatedRecorequestId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_user.recorequest_id"));
                recorequestsInitiatedRecorequestSentDatetime = resultset.getDate(
                        "recorequest_user.recorequest_sent_datetime");

                recorequestTsAssignedVO = new RecorequestTsAssignedVO(recorequestsInitiatedRecorequestId,
                        recorequestsInitiatedRecorequestSentDatetime);

                recorequestTsAssignedVOList.add(recorequestTsAssignedVO);
            }

            statement.close();

            return recorequestTsAssignedVOList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getLastNRecorequestsAssignedRecorequestId= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public RecorequestReplyUserVO recorequestsAssignedFirstReplyId(
        String recorequestId) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_REPLY_USER_SELECT_SQL);

            statement.setString(1, recorequestId);
            statement.setInt(2, 1);
            resultset = statement.executeQuery();

            RecorequestReplyUserVO recorequestReplyUserVO = null;
            String recorequestsInitiatedFirstReplyId = null;
            Date recorequestsInitiatedFirstReplyDatetime = null;

            if (resultset.next()) {
                recorequestsInitiatedFirstReplyId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_reply_user.reply_id"));
                recorequestsInitiatedFirstReplyDatetime = resultset.getDate(
                        "recorequest_reply_user.reply_send_datetime");

                recorequestReplyUserVO = new RecorequestReplyUserVO(recorequestsInitiatedFirstReplyId,
                        recorequestsInitiatedFirstReplyDatetime);
            }

            statement.close();

            return recorequestReplyUserVO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while recorequestsAssignedFirstReplyId= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public int getNumRecorequestsAssignedTotal(String flaggedUserId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.COUNT_RECOREQUEST_TS_ASSIGNED_SELECT_SQL);

            statement.setString(1, flaggedUserId);
            resultset = statement.executeQuery();

            int numRecorequestsAssignedTotal = 0;

            if (resultset.next()) {
                numRecorequestsAssignedTotal = resultset.getInt(1);
            }

            statement.close();

            return numRecorequestsAssignedTotal;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getNumRecorequestsAssigned7DaysReplied= " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitUserRecoSupplyTier(String flaggedUserId,
        int userSupplyInvTier) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.USER_RECO_SUPPLY_TIER_INSERT_SQL);
            statement.setString(1, flaggedUserId);
            statement.setInt(2, userSupplyInvTier);
            statement.setInt(3, 0);
            statement.setInt(4, userSupplyInvTier);
            statement.setInt(5, 0);

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

            throw new TasteSyncException("Error while creating reco request " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitRecorrequestUser(String flaggedUserId, int algoInd)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_USER_UPDATE_SQL);
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);
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
                "Error while submitRecorrequestUser = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitRecorrequestAssigned(String flaggedUserId, int algoInd)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_TS_ASSIGNED_UPDATE_SQL);
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);
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
                "Error while submitRecorrequestAssigned = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitRecorrequestReplyUser(String flaggedUserId, int algoInd)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_REPLY_USER_UPDATE_SQL);
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);
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
                "Error while submitRecorrequestReplyUser = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecoRequestsLastNDays(String flaggedUserId, int nDays)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_REPLY_USER_UPDATE_SQL);
            statement.setString(1, flaggedUserId);

            // Make an SQL Date out of that  
            java.sql.Date requiredDate = CommonFunctionsUtil.getDateNDaysBeforeCurrentDate(nDays);

            statement.setDate(2, requiredDate);

            resultset = statement.executeQuery();

            List<String> recorequestUserRecorequestIdList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestUserRecorequestIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("recorequest_user.recorequest_id")));
            }

            statement.close();

            return recorequestUserRecorequestIdList;
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
                "Error while getRecoRequestsLastNDays = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestCuisineTier1(String recorequestId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_CUISINE_TIER1_SELECT_SQL);
            statement.setString(1, recorequestId);
            resultset = statement.executeQuery();

            List<String> recorequestCuisineTier1List = new ArrayList<String>();

            while (resultset.next()) {
                recorequestCuisineTier1List.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_cuisine_tier1.cuisine_tier1_id")));
            }

            statement.close();

            return recorequestCuisineTier1List;
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
                "Error while getRecorequestCuisineTier1 = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestCuisineTier2(String recorequestId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_CUISINE_TIER2_SELECT_SQL);
            statement.setString(1, recorequestId);
            resultset = statement.executeQuery();

            List<String> recorequestCuisineTier2List = new ArrayList<String>();

            while (resultset.next()) {
                recorequestCuisineTier2List.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_cuisine_tier2.cuisine_tier2_id")));
            }

            statement.close();

            return recorequestCuisineTier2List;
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
                "Error while getRecorequestCuisineTier2 = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestOccasion(String recorequestId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_OCCASION_SELECT_SQL);
            statement.setString(1, recorequestId);
            resultset = statement.executeQuery();

            List<String> recorequestOccassionIdList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestOccassionIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("recorequest_occasion.occasion_id")));
            }

            statement.close();

            return recorequestOccassionIdList;
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
                "Error while getRecorequestOccasion = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestPrice(String recorequestId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_PRICE_SELECT_SQL);
            statement.setString(1, recorequestId);
            resultset = statement.executeQuery();

            List<String> recorequestPriceIdList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestPriceIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("recorequest_price.price_id")));
            }

            statement.close();

            return recorequestPriceIdList;
        } catch (SQLException e) {
            e.printStackTrace();

            if (tsDataSource != null) {
                try {
                    tsDataSource.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            throw new TasteSyncException("Error while getRecorequestPrice = " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestTheme(String recorequestId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_THEME_SELECT_SQL);
            statement.setString(1, recorequestId);
            resultset = statement.executeQuery();

            List<String> recorequestThemeIdList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestThemeIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("recorequest_theme.theme_id")));
            }

            statement.close();

            return recorequestThemeIdList;
        } catch (SQLException e) {
            e.printStackTrace();

            if (tsDataSource != null) {
                try {
                    tsDataSource.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            throw new TasteSyncException("Error while getRecorequestTheme = " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestTypeofRest(String recorequestId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_TYPEOFREST_SELECT_SQL);
            statement.setString(1, recorequestId);
            resultset = statement.executeQuery();

            List<String> recorequestTypeofrestIdList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestTypeofrestIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_typeofrest.typeofrest_id")));
            }

            statement.close();

            return recorequestTypeofrestIdList;
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
                "Error while getRecorequestTypeofRest = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestWhoarewithyou(String recorequestId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_WHOAREYOUWITH_SELECT_SQL);
            statement.setString(1, recorequestId);
            resultset = statement.executeQuery();

            List<String> recorequestWhoareyouwithIdList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestWhoareyouwithIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_whoareyouwith.whoareyouwith_id")));
            }

            statement.close();

            return recorequestWhoareyouwithIdList;
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
                "Error while getRecorequestWhoarewithyou = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecorequestLocation(String recorequestId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_LOCATION_SELECT_SQL);
            statement.setString(1, recorequestId);
            resultset = statement.executeQuery();

            //one neighbourhood id for a city id . cud be null
            List<String> recorequestCityIdNeighbourIdList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestCityIdNeighbourIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString("recorequest_location.city_id")));
                recorequestCityIdNeighbourIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_location.neighbourhood_id")));
            }

            statement.close();

            return recorequestCityIdNeighbourIdList;
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
                "Error while getRecorequestLocation = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecoRequestsReplyAnsweredLastNDays(
        String flaggedUserId, int nDays) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_REPLY_ANSWERED_NDAYS_SELECT_SQL);
            statement.setString(1, flaggedUserId);

            java.sql.Date requiredDate = CommonFunctionsUtil.getDateNDaysBeforeCurrentDate(nDays);

            statement.setDate(2, requiredDate);

            resultset = statement.executeQuery();

            //one neighbourhood id for a city id . cud be null
            List<String> recorequestReplyAnsweredRecorequestIdList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestReplyAnsweredRecorequestIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_reply_user.recorequest_id")));
            }

            statement.close();

            return recorequestReplyAnsweredRecorequestIdList;
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
                "Error while getRecoRequestsReplyAnsweredLastNDays = " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<String> getRecoRequestsReplyUserAnsweredLastNDays(
        String flaggedUserId, int nDays) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_REPLY_USER_ANSWERED_NDAYS_SELECT_SQL);
            statement.setString(1, flaggedUserId);

            java.sql.Date requiredDate = CommonFunctionsUtil.getDateNDaysBeforeCurrentDate(nDays);

            statement.setDate(2, requiredDate);

            resultset = statement.executeQuery();

            //one neighbourhood id for a city id . cud be null
            List<String> recorequestReplyUserAnsweredRecorequestIdList = new ArrayList<String>();

            while (resultset.next()) {
                recorequestReplyUserAnsweredRecorequestIdList.add(CommonFunctionsUtil.getModifiedValueString(
                        resultset.getString(
                            "recorequest_reply_user.recorequest_id")));
            }

            statement.close();

            return recorequestReplyUserAnsweredRecorequestIdList;
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
                "Error while getRecoRequestsReplyUserAnsweredLastNDays = " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitUserRecoDemandTierPrecalc(String flaggedUserId,
        int demandTierFlag) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.USER_RECO_DEMAND_INSERT_SQL);

            int calcFlag = 0;
            statement.setInt(1, calcFlag);
            statement.setInt(2, demandTierFlag);
            statement.setString(3, flaggedUserId);
            statement.setInt(4, calcFlag);
            statement.setInt(5, demandTierFlag);

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
                "Error while getRecoRequestsReplyUserAnsweredLastNDays = " +
                e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<RestaurantUserVO> getRecorequestReplyUserRestaurant(
        int algoIndicatorIdentifyUseridListThree) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RECOREQUEST_REPLY_USER_RESTAURANT_REPLY_SELECT_SQL);
            statement.setInt(1, algoIndicatorIdentifyUseridListThree);
            resultset = statement.executeQuery();

            List<RestaurantUserVO> recorequestReplyUserRestaurantUsersList = new ArrayList<RestaurantUserVO>();
            RestaurantUserVO restaurantUserVO = null;
            String userId = null;
            String restaurantId = null;

            while (resultset.next()) {
                userId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "recorequest_reply_user.reply_user_id"));
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_reply.restaurant_id"));

                restaurantUserVO = new RestaurantUserVO(userId, restaurantId);
                recorequestReplyUserRestaurantUsersList.add(restaurantUserVO);
            }

            statement.close();

            return recorequestReplyUserRestaurantUsersList;
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
                "Error while getRecorequestLocation = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<RestaurantUserVO> getRestaurantTipsTastesync(
        int algoIndicatorIdentifyUseridListFour) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RESTAURANT_TIPS_TASTESNC_SELECT_SQL);
            statement.setInt(1, algoIndicatorIdentifyUseridListFour);
            resultset = statement.executeQuery();

            List<RestaurantUserVO> restaurantTipsTastesyncUsersList = new ArrayList<RestaurantUserVO>();
            RestaurantUserVO restaurantUserVO = null;
            String userId = null;
            String restaurantId = null;

            while (resultset.next()) {
                userId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_tips_tastesync.user_id"));
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_tips_tastesync.restaurant_id"));

                restaurantUserVO = new RestaurantUserVO(userId, restaurantId);
                restaurantTipsTastesyncUsersList.add(restaurantUserVO);
            }

            statement.close();

            return restaurantTipsTastesyncUsersList;
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
                "Error while getRecorequestLocation = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public List<RestaurantUserVO> getUserRestaurantFav(
        int algoIndicatorIdentifyUseridListFive) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.USER_RSTAURANT_FAV_SELECT_SQL);
            statement.setInt(1, algoIndicatorIdentifyUseridListFive);
            resultset = statement.executeQuery();

            List<RestaurantUserVO> restaurantFavUsersList = new ArrayList<RestaurantUserVO>();
            RestaurantUserVO restaurantUserVO = null;
            String userId = null;
            String restaurantId = null;

            while (resultset.next()) {
                userId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_restaurant_fav.user_id"));
                restaurantId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "user_restaurant_fav.restaurant_id"));

                restaurantUserVO = new RestaurantUserVO(userId, restaurantId);
                restaurantFavUsersList.add(restaurantUserVO);
            }

            statement.close();

            return restaurantFavUsersList;
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
                "Error while getRecorequestLocation = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public String getRestaurantInfoChained(String restaurantId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.RESTAURANT_EXTENDED_INFO_CHAINED_SELECT_SQL);

            statement.setString(1, restaurantId);
            resultset = statement.executeQuery();

            String chainFlag = "0";

            if (resultset.next()) {
                String chainId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_extended_info.chain_id"));

                if (chainId != null) {
                    chainFlag = "1";
                }
            }

            statement.close();

            return chainFlag;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRestaurantInfoChained= " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitUserCityNbrHoodAndCusineTier2Match(
        RestaurantUserVO flaggedRestaurantUserVO) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        PreparedStatement statementInner = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.RESTAURANT_NEIGHBOURHOOD_CITY_SELECT_SQL);

            statement.setString(1, flaggedRestaurantUserVO.getRestaurantId());
            resultset = statement.executeQuery();

            String restaurantCityId = null;
            String restaurantNbrhoodId = null;

            while (resultset.next()) {
                restaurantCityId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.restaurant_city_id"));

                restaurantNbrhoodId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_neighbourhood.neighbourhood_id"));

                statementInner = connection.prepareStatement(UserUserQueries.USER_CITY_NBRHOOD_INSERT_SQL);
                statementInner.setString(1, restaurantCityId);
                statementInner.setInt(2, 1);
                statementInner.setString(3, restaurantNbrhoodId);
                statementInner.setString(4, flaggedRestaurantUserVO.getUserId());

                statementInner.close();
            }

            statement.close();

            statement = connection.prepareStatement(UserUserQueries.RESTAURANT_PRICERANGE_SELECT_SQL);
            statement.setString(1, flaggedRestaurantUserVO.getRestaurantId());
            resultset = statement.executeQuery();

            String priceId = null;

            if (resultset.next()) {
                priceId = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant.restaurant_city_id"));

                statementInner = connection.prepareStatement(UserUserQueries.USER_PRICE_INSERT_SQL);
                statementInner.setInt(1, 1);
                statementInner.setString(2, priceId);
                statementInner.setString(3, flaggedRestaurantUserVO.getUserId());

                statementInner.close();
            }

            statement.close();

            statement = connection.prepareStatement(UserUserQueries.RESTAURANT_CUISINE_SELECT_SQL);
            statement.setString(1, flaggedRestaurantUserVO.getRestaurantId());
            resultset = statement.executeQuery();

            String cuisTier2Id = null;

            while (resultset.next()) {
                cuisTier2Id = CommonFunctionsUtil.getModifiedValueString(resultset.getString(
                            "restaurant_cuisine.tier2_cuisine_id"));

                statementInner = connection.prepareStatement(UserUserQueries.USER_CUISTIER2_INSERT_SQL);
                statementInner.setInt(2, 1);
                statementInner.setString(1, cuisTier2Id);
                statementInner.setString(3, flaggedRestaurantUserVO.getUserId());

                statementInner.close();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TasteSyncException(
                "Error while getRestaurantInfoChained= " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitRestaurantTipsTastesync(String flaggedUserId,
        String restaurantId, int algoInd) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.RESTAURANT_TIPS_TASTESYNC_UPDATE_SQL);
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);
            statement.setString(3, restaurantId);
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
                "Error while submitRecorrequestUser = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }

    @Override
    public void submitRestaurantFav(String flaggedUserId, String restaurantId,
        int algoInd) throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            tsDataSource.begin();

            statement = connection.prepareStatement(UserUserQueries.USER_RESTAURANT_FAV_RESTAURANT_UPDATE_SQL);
            statement.setInt(1, algoInd);
            statement.setString(2, flaggedUserId);
            statement.setString(3, restaurantId);
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
                "Error while submitRecorrequestUser = " + e.getMessage());
        } finally {
            tsDataSource.close();
            tsDataSource.closeConnection(connection, statement, resultset);
        }
    }
}
