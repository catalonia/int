package com.tastesync.algo.user.user;

import com.tastesync.algo.db.dao.UserUserDAO;
import com.tastesync.algo.db.dao.UserUserDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


// algo one - calc two
public class DemandPriorityCalc {
    private UserUserDAO userUserDAO = new UserUserDAOImpl();

    public DemandPriorityCalc() {
        super();
    }

    public void processAllUserFlaggedUserListDemandPriority(
        TSDataSource tsDataSource, Connection connection)
        throws TasteSyncException {
        int algoIndicatorIdentifyUseridList = 1;
        List<String> recorequestUserFlaggedUserList = userUserDAO.getRecorequestUserFlaggedUserList(tsDataSource,
                connection, algoIndicatorIdentifyUseridList);
        algoIndicatorIdentifyUseridList = 3;

        List<String> recorequestReplyUserFlaggedUserList = userUserDAO.getRecorequestReplyUserFlaggedUserList(tsDataSource,
                connection, algoIndicatorIdentifyUseridList);
        algoIndicatorIdentifyUseridList = 3;

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

        //eightyPercentilePoints TODO
        double eightyPercentile = 0.80;
        int eightyPercentilePoints = userUserDAO.getNPercentilePoints(tsDataSource,
                connection, eightyPercentile);

        try {
            //
            for (String flaggedUserId : allUserFlaggedUserList) {
                //                List<String> recoreqslast2daysRecorequestIdList = userUserDAO.getRecoRequestsLastNDays(tsDataSource,
                //                        connection, flaggedUserId, -2);
                //                for (String recoreqslast2daysRecorequestId : recoreqslast2daysRecorequestIdList) {
                //                    List<String> cuisTier1IdList = userUserDAO.getRecorequestCuisineTier1(recoreqslast2daysRecorequestId);
                //    
                //                    List<String> cuisTier2IdList = userUserDAO.getRecorequestCuisineTier2(recoreqslast2daysRecorequestId);
                //    
                //                    List<String> occasionIdList = userUserDAO.getRecorequestOccasion(recoreqslast2daysRecorequestId);
                //                    List<String> priceIdList = userUserDAO.getRecorequestPrice(recoreqslast2daysRecorequestId);
                //                    List<String> themeIdList = userUserDAO.getRecorequestTheme(recoreqslast2daysRecorequestId);
                //                    List<String> typeofrestIdList = userUserDAO.getRecorequestTypeofRest(recoreqslast2daysRecorequestId);
                //                    List<String> whoareyouwithIdList = userUserDAO.getRecorequestWhoarewithyou(recoreqslast2daysRecorequestId);
                //                    List<String> recorequestCityIdNeighbourIdList = userUserDAO.getRecorequestLocation(tsDataSource,
                //                            connection, recoreqslast2daysRecorequestId);
                //                    List<String> recorequestCityIdList = new ArrayList<String>();
                //                    List<String> recorequestNeighborhoodIdList = new ArrayList<String>();
                //
                //                    for (int i = 0;
                //                            i < recorequestCityIdNeighbourIdList.size();
                //                            i = i + 2) {
                //                        recorequestCityIdList.add(recorequestCityIdNeighbourIdList.get(
                //                                i));
                //                        recorequestNeighborhoodIdList.add(recorequestCityIdNeighbourIdList.get(i +
                //                                1));
                //                    }
                //                }
                List<String> recorequestReplyAnsweredRecorequestIdTodayList = userUserDAO.getRecoRequestsReplyAnsweredLastNDays(tsDataSource,
                        connection, flaggedUserId, 0);
                int numRecoreqsAnsToday = recorequestReplyAnsweredRecorequestIdTodayList.size();

                List<String> recorequestReplyAnsweredRecorequestId2DaysList = userUserDAO.getRecoRequestsReplyAnsweredLastNDays(tsDataSource,
                        connection, flaggedUserId, -2);

                int numRecoreqsAnsLast2Days = recorequestReplyAnsweredRecorequestId2DaysList.size();

                List<String> recorequestReplyUserAnsweredRecorequestIdTodayList = userUserDAO.getRecoRequestsReplyUserAnsweredLastNDays(tsDataSource,
                        connection, flaggedUserId, 0);

                int numRecoreqsUserAnsToday = recorequestReplyUserAnsweredRecorequestIdTodayList.size();

                List<String> recorequestReplyUserAnsweredRecorequestId2DaysList = userUserDAO.getRecoRequestsReplyUserAnsweredLastNDays(tsDataSource,
                        connection, flaggedUserId, -2);
                int numRecoreqsUserAnsLast2Days = recorequestReplyUserAnsweredRecorequestId2DaysList.size();

                // user points
                int userPoints = userUserDAO.getUserPoints(tsDataSource,
                        connection, flaggedUserId);

                //-- Tier 3 logic
                if ((numRecoreqsAnsToday >= 2) ||
                        (numRecoreqsAnsLast2Days >= 3)) {
                    userUserDAO.submitUserRecoDemandTierPrecalc(tsDataSource,
                        connection, flaggedUserId, 3);
                } else {
                    //-- Tier 1 logic
                    //-- TODO: User has 50+ points AND is in the top 20th percentile of users based on points
                    if ((numRecoreqsUserAnsLast2Days >= 3) ||
                            (numRecoreqsUserAnsToday >= 2) ||
                            ((userPoints >= 50) &&
                            (userPoints >= eightyPercentilePoints))) {
                        userUserDAO.submitUserRecoDemandTierPrecalc(tsDataSource,
                            connection, flaggedUserId, 1);
                    } else {
                        userUserDAO.submitUserRecoDemandTierPrecalc(tsDataSource,
                            connection, flaggedUserId, 2);
                    }
                }

            }

            tsDataSource.begin();

            for (String flaggedUserId : recorequestUserFlaggedUserList) {
                userUserDAO.submitRecorrequestUser(tsDataSource, connection,
                    flaggedUserId, 0);
            }

            tsDataSource.commit();
            tsDataSource.begin();

            for (String flaggedUserId : recorequestReplyUserFlaggedUserList) {
                userUserDAO.submitRecorrequestUser(tsDataSource, connection,
                    flaggedUserId, 2);
            }

            tsDataSource.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new TasteSyncException(e.getMessage());
        }
    }
}
