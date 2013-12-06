package com.tastesync.algo.user.user;

import com.tastesync.algo.db.dao.UserUserDAO;
import com.tastesync.algo.db.dao.UserUserDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RecorequestReplyUserVO;
import com.tastesync.algo.model.vo.RecorequestTsAssignedVO;
import com.tastesync.algo.model.vo.RecorequestUserVO;
import com.tastesync.algo.util.TSConstants;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


// algo 1 - calc 1 - supply inventory
public class SupplyInventoryCalc {
    private UserUserDAO userUserDAO = new UserUserDAOImpl();

    //-- TODO: TBD - final tier calculation
    //-- TODO: Tier 1 IF User is online right now
    //-- TODO: What happens when this process is running? Are all the tables locked? How do we account for changes that happen in tables while this process is running?
    public SupplyInventoryCalc() {
        super();
    }

    //-- Identify flagged users
    public void processAllUserFlaggedUserListSupplyInventory(
        TSDataSource tsDataSource, Connection connection)
        throws TasteSyncException, SQLException {
        int algoIndicatorIdentifyUseridList = 2;
        List<String> recorequestUserFlaggedUserList = userUserDAO.getRecorequestUserFlaggedUserList(tsDataSource,
                connection, algoIndicatorIdentifyUseridList);
        algoIndicatorIdentifyUseridList = 1;

        List<String> recorequestTsAssignedFlaggedUserList = userUserDAO.getRecorequestTsAssignedFlaggedUserList(tsDataSource,
                connection, algoIndicatorIdentifyUseridList);
        algoIndicatorIdentifyUseridList = 4;

        List<String> recorequestReplyUserFlaggedUserList = userUserDAO.getRecorequestReplyUserFlaggedUserList(tsDataSource,
                connection, algoIndicatorIdentifyUseridList);
        algoIndicatorIdentifyUseridList = 4;

        List<String> recorequestReplyUserForSameRecorequestIdFlaggedUserList = userUserDAO.getRecorequestReplyUserForSameRecorequestIdFlaggedUserList(tsDataSource,
                connection, algoIndicatorIdentifyUseridList);

        //Combine all flagged userId lists into one list.
        List<String> allUserFlaggedUserList = new ArrayList<String>();

        for (String recorequestUserFlaggedUserListElement : recorequestUserFlaggedUserList) {
            if (!allUserFlaggedUserList.contains(
                        recorequestUserFlaggedUserListElement)) {
                allUserFlaggedUserList.add(recorequestUserFlaggedUserListElement);
            }
        }

        for (String recorequestTsAssignedFlaggedUserListElement : recorequestTsAssignedFlaggedUserList) {
            if (!allUserFlaggedUserList.contains(
                        recorequestTsAssignedFlaggedUserListElement)) {
                allUserFlaggedUserList.add(recorequestTsAssignedFlaggedUserListElement);
            }
        }

        for (String recorequestReplyUserFlaggedUserListElement : recorequestReplyUserFlaggedUserList) {
            if (!allUserFlaggedUserList.contains(
                        recorequestReplyUserFlaggedUserListElement)) {
                allUserFlaggedUserList.add(recorequestReplyUserFlaggedUserListElement);
            }
        }

        for (String recorequestReplyUserForSameRecorequestIdFlaggedUserListElement : recorequestReplyUserForSameRecorequestIdFlaggedUserList) {
            if (!allUserFlaggedUserList.contains(
                        recorequestReplyUserForSameRecorequestIdFlaggedUserListElement)) {
                allUserFlaggedUserList.add(recorequestReplyUserForSameRecorequestIdFlaggedUserListElement);
            }
        }

        double numRecorequestsAssignedToday = 0;
        double numRecorequestsAssignedTodayReplied = 0;
        double numRecorequestsAssigned7Days = 0;
        double numRecorequestsAssigned7DaysReplied = 0;
        double numRecorequestsInitiatedTotal = 0;
        double numUserRecorequestsRepliedWithin10Mins = 0;

        double numRecorequestsAssignedTotal = 0;
        double numUserRecorequestsAssignedRepliedWithin10Mins = 0;

        for (String flagUserId : allUserFlaggedUserList) {
            numRecorequestsAssignedToday = userUserDAO.getNumRecorequestsAssignedToday(tsDataSource,
                    connection, flagUserId);

            numRecorequestsAssignedTodayReplied = userUserDAO.getNumRecorequestsAssignedTodayReplied(tsDataSource,
                    connection, flagUserId);

            numRecorequestsAssigned7Days = userUserDAO.getNumRecorequestsAssignedNDays(tsDataSource,
                    connection, flagUserId, -7);

            numRecorequestsAssigned7DaysReplied = userUserDAO.getNumRecorequestsAssignedNDaysReplied(tsDataSource,
                    connection, flagUserId, -7);

            numRecorequestsInitiatedTotal = userUserDAO.getNumRecorequestsInitiatedTotal(tsDataSource,
                    connection, flagUserId);

            numRecorequestsAssignedTotal = userUserDAO.getNumRecorequestsAssignedTotal(tsDataSource,
                    connection, flagUserId);

            if (numRecorequestsInitiatedTotal >= TSConstants.MAX_RECOREQUEST_CONSIDERED) {
                List<RecorequestUserVO> recorequestUserVOList = userUserDAO.getLastNRecorequestsUserRecorequestId(tsDataSource,
                        connection, flagUserId);

                numUserRecorequestsRepliedWithin10Mins = 0;

                for (RecorequestUserVO recorequestUserVOElement : recorequestUserVOList) {
                    RecorequestReplyUserVO recorequestReplyUserVO = userUserDAO.recorequestsAssignedFirstReplyId(tsDataSource,
                            connection,
                            recorequestUserVOElement.getRecorequestId());

                    if (recorequestReplyUserVO == null) {
                        continue;
                    }

                    // increment if the reply is withing 10 minutes of assignment!!
                    //Time in milli seoncds - 10 minutes = 
                    long milliSecondsBetween = (recorequestReplyUserVO.getReplyDatetime()
                                                                      .getTime() -
                        recorequestUserVOElement.getRecorequestSentDatetime()
                                                .getTime());

                    if (milliSecondsBetween <= 600000) {
                        // increment - condition 10 minutues difference
                        ++numUserRecorequestsRepliedWithin10Mins;
                    }
                }
            }

            if (numRecorequestsAssignedTotal >= TSConstants.MAX_RECOREQUEST_CONSIDERED) {
                List<RecorequestTsAssignedVO> recorequestTsAssignedVOList = userUserDAO.getLastNRecorequestsAssignedRecorequestId(tsDataSource,
                        connection, flagUserId);

                numUserRecorequestsAssignedRepliedWithin10Mins = 0;

                for (RecorequestTsAssignedVO recorequestTsAssignedVOElement : recorequestTsAssignedVOList) {
                    RecorequestReplyUserVO recorequestReplyUserVO = userUserDAO.recorequestsAssignedFirstReplyId(tsDataSource,
                            connection,
                            recorequestTsAssignedVOElement.getRecorequestId());

                    // increment if the reply is withing 10 minutes of assignment!!
                    //Time in milli seoncds - 10 minutes = 
                    long milliSecondsBetween = (recorequestReplyUserVO.getReplyDatetime()
                                                                      .getTime() -
                        recorequestTsAssignedVOElement.getRecorequestAssignedDatetime()
                                                      .getTime());

                    if (milliSecondsBetween <= 600000) {
                        // increment - condition 10 minutues difference
                        ++numUserRecorequestsAssignedRepliedWithin10Mins;
                    }
                }
            }

            tsDataSource.begin();

            if ((numRecorequestsAssignedToday >= 3) ||
                    ((numRecorequestsAssignedToday -
                    numRecorequestsAssignedTodayReplied) >= 2) ||
                    ((numRecorequestsAssigned7Days -
                    numRecorequestsAssigned7DaysReplied) >= 2)) {
                userUserDAO.submitUserRecoSupplyTier(tsDataSource, connection,
                    flagUserId, 4);
            } else {
                //-- TODO: Tier 1 IF User if online right now 
                boolean isUserOnline = userUserDAO.isUserOnlin(tsDataSource,
                        connection, flagUserId);

                if (isUserOnline) {
                    userUserDAO.submitUserRecoSupplyTier(tsDataSource,
                        connection, flagUserId, 1);
                } else {
                    if (((numRecorequestsInitiatedTotal >= TSConstants.MAX_RECOREQUEST_CONSIDERED) &&
                            ((numUserRecorequestsRepliedWithin10Mins / numRecorequestsInitiatedTotal) >= 0.6)) ||
                            ((numRecorequestsAssignedTotal >= TSConstants.MAX_RECOREQUEST_CONSIDERED) &&
                            ((numUserRecorequestsAssignedRepliedWithin10Mins / numRecorequestsInitiatedTotal) >= 0.6)) ||
                            (numRecorequestsAssigned7Days == 0)) {
                        userUserDAO.submitUserRecoSupplyTier(tsDataSource,
                            connection, flagUserId, 2);
                    } else {
                        userUserDAO.submitUserRecoSupplyTier(tsDataSource,
                            connection, flagUserId, 3);
                    }
                }
            }

            tsDataSource.commit();
        }

        tsDataSource.begin();

        for (String flaggedUserId : recorequestUserFlaggedUserList) {
            userUserDAO.submitRecorrequestUser(tsDataSource, connection,
                flaggedUserId, 1);
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (String flaggedUserId : recorequestTsAssignedFlaggedUserList) {
            userUserDAO.submitRecorrequestAssigned(tsDataSource, connection,
                flaggedUserId, 0);
        }

        tsDataSource.commit();
        tsDataSource.begin();

        for (String flaggedUserId : recorequestReplyUserFlaggedUserList) {
            userUserDAO.submitRecorrequestReplyUserAlgo1(tsDataSource,
                connection, flaggedUserId, 3);
        }

        tsDataSource.commit();
    }
}
