package com.tastesync.algo.db.dao;

import com.tastesync.algo.db.pool.TSDataSource;
import com.tastesync.algo.db.queries.UserUserQueries;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RecorequestReplyUserVO;
import com.tastesync.algo.model.vo.RecorequestTsAssignedVO;
import com.tastesync.algo.model.vo.RecorequestUserVO;
import com.tastesync.algo.util.CommonFunctionsUtil;
import com.tastesync.algo.util.TSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class UserUserDAOImpl extends BaseDaoImpl implements UserUserDAO {
    @Override
    public List<String> getRecorequestUserFlaggedUserList()
        throws TasteSyncException {
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
    public List<String> getRecorequestTsAssignedFlaggedUserList()
        throws TasteSyncException {
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
    public List<String> getRecorequestReplyUserFlaggedUserList()
        throws TasteSyncException {
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
    public List<String> getRecorequestReplyUserForSameRecorequestIdFlaggedUserList()
        throws TasteSyncException {
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
    public int getNumRecorequestsAssigned7Days(String flaggedUserId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.COUNT_RECOREQUEST_TS_ASSIGNED_DATETIME_SELECT_SQL);

            statement.setString(1, flaggedUserId);

            //java.sql.Date today = CommonFunctionsUtil.getCurrentDate();
            // Get today as a Calendar  
            Calendar today = Calendar.getInstance();
            // Subtract 7 day  
            today.add(Calendar.DATE, -7);

            // Make an SQL Date out of that  
            java.sql.Date requiredDate = new java.sql.Date(today.getTimeInMillis());

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
    public int getNumRecorequestsAssigned7DaysReplied(String flaggedUserId)
        throws TasteSyncException {
        TSDataSource tsDataSource = TSDataSource.getInstance();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            connection = tsDataSource.getConnection();
            statement = connection.prepareStatement(UserUserQueries.COUNT_RECOREQUEST_TS_ASSIGNED_REPLY_USER_SELECT_SQL);

            statement.setString(1, flaggedUserId);

            //java.sql.Date today = CommonFunctionsUtil.getCurrentDate();
            // Get today as a Calendar  
            Calendar today = Calendar.getInstance();
            // Subtract 7 day  
            today.add(Calendar.DATE, -7);

            // Make an SQL Date out of that  
            java.sql.Date requiredDate = new java.sql.Date(today.getTimeInMillis());

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
}
