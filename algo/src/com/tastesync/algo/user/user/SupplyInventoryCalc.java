package com.tastesync.algo.user.user;

import com.tastesync.algo.db.dao.UserUserDAO;
import com.tastesync.algo.db.dao.UserUserDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.RecorequestReplyUserVO;
import com.tastesync.algo.model.vo.RecorequestTsAssignedVO;
import com.tastesync.algo.model.vo.RecorequestUserVO;
import com.tastesync.algo.util.TSConstants;

import java.util.ArrayList;
import java.util.List;


public class SupplyInventoryCalc {
    private UserUserDAO userUserDAO = new UserUserDAOImpl();

    //-- TODO: TBD - final tier calculation
    //-- TODO: Tier 1 IF User is online right now
    //-- TODO: What happens when this process is running? Are all the tables locked? How do we account for changes that happen in tables while this process is running?

    //-- Identify flagged users
    public void processAllUserFlaggedUserList() throws TasteSyncException {
        List<String> recorequestUserFlaggedUserList = userUserDAO.getRecorequestReplyUserFlaggedUserList();

        List<String> recorequestTsAssignedFlaggedUserList = userUserDAO.getRecorequestTsAssignedFlaggedUserList();

        List<String> recorequestReplyUserFlaggedUserList = userUserDAO.getRecorequestReplyUserFlaggedUserList();

        List<String> recorequestReplyUserForSameRecorequestIdFlaggedUserList = userUserDAO.getRecorequestReplyUserForSameRecorequestIdFlaggedUserList();

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

        int numRecorequestsAssignedToday = 0;
        int numRecorequestsAssignedTodayReplied = 0;
        int numRecorequestsAssigned7Days = 0;
        int numRecorequestsAssigned7DaysReplied = 0;
        int numRecorequestsInitiatedTotal = 0;
        int numUserRecorequestsRepliedWithin10Mins = 0;

        int numRecorequestsAssignedTotal = 0;
        int numUserRecorequestsAssignedRepliedWithin10Mins = 0;

        for (String flagUserId : allUserFlaggedUserList) {
            numRecorequestsAssignedToday = userUserDAO.getNumRecorequestsAssignedToday(flagUserId);

            numRecorequestsAssignedTodayReplied = userUserDAO.getNumRecorequestsAssignedTodayReplied(flagUserId);

            numRecorequestsAssigned7Days = userUserDAO.getNumRecorequestsAssigned7Days(flagUserId);

            numRecorequestsAssigned7DaysReplied = userUserDAO.getNumRecorequestsAssigned7DaysReplied(flagUserId);

            numRecorequestsInitiatedTotal = userUserDAO.getNumRecorequestsInitiatedTotal(flagUserId);

            numRecorequestsAssignedTotal = userUserDAO.getNumRecorequestsAssignedTotal(flagUserId);

            if (numRecorequestsInitiatedTotal >= TSConstants.MAX_RECOREQUEST_CONSIDERED) {
                List<RecorequestUserVO> recorequestUserVOList = userUserDAO.getLastNRecorequestsUserRecorequestId(flagUserId);

                numUserRecorequestsRepliedWithin10Mins = 0;

                for (RecorequestUserVO recorequestUserVOElement : recorequestUserVOList) {
                    RecorequestReplyUserVO recorequestReplyUserVO = userUserDAO.recorequestsAssignedFirstReplyId(recorequestUserVOElement.getRecorequestId());

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
                List<RecorequestTsAssignedVO> recorequestTsAssignedVOList = userUserDAO.getLastNRecorequestsAssignedRecorequestId(flagUserId);

                numUserRecorequestsAssignedRepliedWithin10Mins = 0;

                for (RecorequestTsAssignedVO recorequestTsAssignedVOElement : recorequestTsAssignedVOList) {
                    RecorequestReplyUserVO recorequestReplyUserVO = userUserDAO.recorequestsAssignedFirstReplyId(recorequestTsAssignedVOElement.getRecorequestId());

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

            if ((numRecorequestsAssignedToday >= 3) ||
                    ((numRecorequestsAssignedToday -
                    numRecorequestsAssignedTodayReplied) >= 2) ||
                    ((numRecorequestsAssigned7Days -
                    numRecorequestsAssigned7DaysReplied) >= 2)) {
                userUserDAO.submitUserRecoSupplyTier(flagUserId, 4);
            } else {
                //-- TODO: Tier 1 IF User if online right now 
                if (1 == 2) {
                    userUserDAO.submitUserRecoSupplyTier(flagUserId, 1);
                } else {
                    if (((numRecorequestsInitiatedTotal >= TSConstants.MAX_RECOREQUEST_CONSIDERED) &&
                            ((numUserRecorequestsRepliedWithin10Mins / numRecorequestsInitiatedTotal) >= 0.6)) ||
                            ((numRecorequestsAssignedTotal >= TSConstants.MAX_RECOREQUEST_CONSIDERED) &&
                            ((numUserRecorequestsAssignedRepliedWithin10Mins / numRecorequestsInitiatedTotal) >= 0.6)) ||
                            (numRecorequestsAssigned7Days == 0)) {
                        userUserDAO.submitUserRecoSupplyTier(flagUserId, 2);
                    } else {
                        userUserDAO.submitUserRecoSupplyTier(flagUserId, 3);
                    }
                }
            }
        }

        for (String flaggedUserId : recorequestUserFlaggedUserList) {
            userUserDAO.submitRecorrequestUser(flaggedUserId, 1);
        }

        for (String flaggedUserId : recorequestTsAssignedFlaggedUserList) {
            userUserDAO.submitRecorrequestAssigned(flaggedUserId, 0);
        }

        for (String flaggedUserId : recorequestReplyUserFlaggedUserList) {
            userUserDAO.submitRecorrequestReplyUser(flaggedUserId, 3);
        }
    }
}
