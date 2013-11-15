package com.tastesync.algo.user.reco.pi;

import com.tastesync.algo.db.dao.PiUserRecoDAO;
import com.tastesync.algo.db.dao.PiUserRecoDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.PiRecommendationsTopicMatchRateVO;
import com.tastesync.algo.model.vo.PiRestaurantRecommendationVO;
import com.tastesync.algo.util.TSConstants;

import com.tastesync.common.utils.CommonFunctionsUtil;

import com.tastesync.db.pool.TSDataSource;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class PiUserRecoAssigned {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(PiUserRecoAssigned.class);
    private static final boolean printDebugExtra = false;
    private PiUserRecoDAO piUserRecoDAO = new PiUserRecoDAOImpl();

    public PiUserRecoAssigned() {
        super();
    }

    private PiRestaurantRecommendationVO getPiRestaurantRecommendation(
        TSDataSource tsDataSource, Connection connection,
        String initiatorUserId, List<String> cuisineTier2IdList,
        List<String> cuisineTier1IdList, List<String> priceIdList,
        List<String> themeIdList, List<String> whoareyouwithIdList,
        List<String> typeOfRestaurantIdList, List<String> occasionIdList)
        throws TasteSyncException {
        List<String> allRecommendationIdsList = piUserRecoDAO.getAllReccomendationIds(tsDataSource,
                connection);
        int topicMatchCounter = 0;
        double topicMatchRate;
        int numRecorequestParams = cuisineTier2IdList.size() +
            cuisineTier1IdList.size() + priceIdList.size() +
            themeIdList.size() + whoareyouwithIdList.size();

        List<PiRecommendationsTopicMatchRateVO> piRecommendationsTopicMatchRateVOList =
            new LinkedList<PiRecommendationsTopicMatchRateVO>();

        PiRecommendationsTopicMatchRateVO piRecommendationsTopicMatchRateVO = null;

        for (String recommendationId : allRecommendationIdsList) {
            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiUserCuistier2Match(tsDataSource,
                    connection, recommendationId, cuisineTier2IdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserCuistier2MatchMapper(tsDataSource,
                    connection, recommendationId, cuisineTier1IdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserPriceMatch(tsDataSource,
                    connection, recommendationId, priceIdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserThemeMatch(tsDataSource,
                    connection, recommendationId, themeIdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserWhoareyouwithMatch(tsDataSource,
                    connection, recommendationId, whoareyouwithIdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserTypeofrestMatch(tsDataSource,
                    connection, recommendationId, typeOfRestaurantIdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserOccasionMatch(tsDataSource,
                    connection, recommendationId, occasionIdList);

            topicMatchRate = (double) topicMatchCounter / numRecorequestParams;

            piRecommendationsTopicMatchRateVO = new PiRecommendationsTopicMatchRateVO(recommendationId,
                    topicMatchRate);
            piRecommendationsTopicMatchRateVOList.add(piRecommendationsTopicMatchRateVO);
        }

        Collections.sort(piRecommendationsTopicMatchRateVOList,
            new Comparator<PiRecommendationsTopicMatchRateVO>() {
                public int compare(PiRecommendationsTopicMatchRateVO o1,
                    PiRecommendationsTopicMatchRateVO o2) {
                    return Double.valueOf(o1.getTopicMatchRate())
                                 .compareTo(o2.getTopicMatchRate());
                }
            });

        List<String> excludedRecommendationIdsList = piUserRecoDAO.getExcludedRecommendationIdList(tsDataSource,
                connection, initiatorUserId);

        for (Iterator<PiRecommendationsTopicMatchRateVO> it = piRecommendationsTopicMatchRateVOList.iterator();
                it.hasNext();) {
            PiRecommendationsTopicMatchRateVO piRecommendationsTopicMatchRateVOElement =
                it.next();

            if (excludedRecommendationIdsList.contains(
                        piRecommendationsTopicMatchRateVOElement.getRecommendationId())) {
                it.remove();
            }
        }

        PiRestaurantRecommendationVO piRestaurantRecommendationVO = null;

        //from the remaining list. pick the first one.
        if (piRecommendationsTopicMatchRateVOList.size() > 0) {
            if (logger.isInfoEnabled()) {
                logger.info("piRestaurantRecommendationVO Found " +
                    piRecommendationsTopicMatchRateVOList.get(0));
            }

            piRestaurantRecommendationVO = piUserRecoDAO.getPiRecommendationIdText(tsDataSource,
                    connection,
                    piRecommendationsTopicMatchRateVOList.get(0)
                                                         .getRecommendationId());
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("piRestaurantRecommendationVO Not Found");
            }
        }

        return piRestaurantRecommendationVO;
    }

    public void processingPiAssignRecorequestToUsers(
        TSDataSource tsDataSource, Connection connection, String recoRequestId,
        int recorequestIteration, String cityId, String nbrHoodId,
        String initiatorUserId, List<String> cuisineTier2IdList,
        List<String> cuisineTier1IdList, List<String> priceIdList,
        List<String> themeIdList, List<String> whoareyouwithIdList,
        List<String> typeOfRestaurantIdList, List<String> occasionIdList)
        throws TasteSyncException {
        //7A
        List<String> temp1PiUsersUserIdList = piUserRecoDAO.getPiUsersCategoryCityNbrhoodList(tsDataSource,
                connection, cityId, nbrHoodId);

        String temp1PiUsersUserId;
        boolean userReported;
        List<Integer> indexElementToBeRemovedFrmUserRecoSupplyTierVOList = new ArrayList<Integer>();

        //7B
        List<String> piUsersAlreadyAssignedIdList = piUserRecoDAO.getPiUserAlreadyAssignedToUser(tsDataSource,
                connection, initiatorUserId);

        // filter list of temp1users{userId}
        for (int i = 0; i < temp1PiUsersUserIdList.size(); ++i) {
            temp1PiUsersUserId = temp1PiUsersUserIdList.get(i);

            userReported = piUserRecoDAO.isUserIdLinkedWithReportedInfo(tsDataSource,
                    connection, initiatorUserId, temp1PiUsersUserId);

            if (userReported) {
                if (!indexElementToBeRemovedFrmUserRecoSupplyTierVOList.contains(
                            i)) {
                    indexElementToBeRemovedFrmUserRecoSupplyTierVOList.add(i);
                }
            }

            if ((piUsersAlreadyAssignedIdList.size() != 0) &&
                    piUsersAlreadyAssignedIdList.contains(temp1PiUsersUserId)) {
                if (!indexElementToBeRemovedFrmUserRecoSupplyTierVOList.contains(
                            i)) {
                    indexElementToBeRemovedFrmUserRecoSupplyTierVOList.add(i);
                }
            }
        }

        // remove from the list based on indexElementToBeRemovedFrmUserRecoSupplyTierVOList
        int currentRemovedCounter = 0;

        for (Integer index : indexElementToBeRemovedFrmUserRecoSupplyTierVOList) {
            temp1PiUsersUserIdList.remove(index - currentRemovedCounter);
            ++currentRemovedCounter;
        }

        if (printDebugExtra) {
            String[] temp1PiUsersUserIdListResult = new String[temp1PiUsersUserIdList.size()];
            temp1PiUsersUserIdListResult = temp1PiUsersUserIdList.toArray(temp1PiUsersUserIdListResult);

            if (logger.isDebugEnabled()) {
                logger.debug("temp1PiUsersUserIdListResult=" +
                    Arrays.toString(temp1PiUsersUserIdListResult));
            }

            Integer[] indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult = new Integer[indexElementToBeRemovedFrmUserRecoSupplyTierVOList.size()];
            indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult = indexElementToBeRemovedFrmUserRecoSupplyTierVOList.toArray(indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult);

            if (logger.isDebugEnabled()) {
                logger.debug(
                    "indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult=" +
                    Arrays.toString(
                        indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult));
            }
        }

        // 7C
        String assigneduserUserId = null;

        // no users
        if ((temp1PiUsersUserIdList == null) ||
                (temp1PiUsersUserIdList.size() == 0)) {
            if (logger.isInfoEnabled()) {
                logger.info("No Pi Users list found. temp1PiUsersUserIdList=" +
                    temp1PiUsersUserIdList);
            }

            return;
        } else {
            if (temp1PiUsersUserIdList.size() == 1) {
                assigneduserUserId = temp1PiUsersUserIdList.get(0);
            } else {
                int matchCount;

                int topicMatchCounter = 0;
                double topicMatchRate;
                temp1PiUsersUserId = null;

                int numRecorequestParams = cuisineTier2IdList.size() +
                    cuisineTier1IdList.size() + priceIdList.size() +
                    themeIdList.size() + whoareyouwithIdList.size();
                double userAUserBMatchTier;

                List<String> tranche1PiUsersUserId = new ArrayList<String>(temp1PiUsersUserIdList.size());
                List<String> tranche2PiUsersUserId = new ArrayList<String>(temp1PiUsersUserIdList.size());
                List<String> tranche3PiUsersUserId = new ArrayList<String>(temp1PiUsersUserIdList.size());

                for (String aTemp1PiUsersUserIdList : temp1PiUsersUserIdList) {
                    temp1PiUsersUserId = aTemp1PiUsersUserIdList;

                    matchCount = 1;
                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserCuistier2Match(tsDataSource,
                            connection, aTemp1PiUsersUserIdList,
                            cuisineTier2IdList, matchCount);
                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserCuistier2MatchMapper(tsDataSource,
                            connection, aTemp1PiUsersUserIdList,
                            cuisineTier1IdList, matchCount);

                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserPriceMatch(tsDataSource,
                            connection, aTemp1PiUsersUserIdList, priceIdList,
                            matchCount);

                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserThemeMatch(tsDataSource,
                            connection, aTemp1PiUsersUserIdList, themeIdList,
                            matchCount);

                    //check matchCount not required?
                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserWhoareyouwithMatch(tsDataSource,
                            connection, aTemp1PiUsersUserIdList,
                            whoareyouwithIdList, matchCount);

                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserTypeofrestMatch(tsDataSource,
                            connection, aTemp1PiUsersUserIdList,
                            typeOfRestaurantIdList, matchCount);

                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserOccasionMatch(tsDataSource,
                            connection, aTemp1PiUsersUserIdList,
                            occasionIdList, matchCount);

                    topicMatchRate = (double) topicMatchCounter / numRecorequestParams;

                    userAUserBMatchTier = piUserRecoDAO.getUserAUserBMatchTier(tsDataSource,
                            connection, initiatorUserId, aTemp1PiUsersUserIdList);

                    if ((topicMatchRate >= 0.6) &&
                            ((userAUserBMatchTier == 1) ||
                            (userAUserBMatchTier == 2))) {
                        tranche1PiUsersUserId.add(aTemp1PiUsersUserIdList);
                    }

                    if ((topicMatchRate >= 1) &&
                            ((userAUserBMatchTier == 1) ||
                            (userAUserBMatchTier == 2))) {
                        tranche2PiUsersUserId.add(aTemp1PiUsersUserIdList);
                    }

                    if (userAUserBMatchTier != 4) {
                        tranche3PiUsersUserId.add(aTemp1PiUsersUserIdList);
                    }
                }

                if (printDebugExtra) {
                    String[] tranche1PiUsersUserIdResult = new String[tranche1PiUsersUserId.size()];
                    tranche1PiUsersUserIdResult = tranche1PiUsersUserId.toArray(tranche1PiUsersUserIdResult);

                    if (logger.isInfoEnabled()) {
                        logger.info("tranche1PiUsersUserIdResult=" +
                            Arrays.toString(tranche1PiUsersUserIdResult));
                    }

                    String[] tranche2PiUsersUserIdResult = new String[tranche2PiUsersUserId.size()];
                    tranche2PiUsersUserIdResult = tranche2PiUsersUserId.toArray(tranche2PiUsersUserIdResult);

                    if (logger.isInfoEnabled()) {
                        logger.info("tranche2PiUsersUserIdResult=" +
                            Arrays.toString(tranche2PiUsersUserIdResult));
                    }

                    String[] tranche3PiUsersUserIdResult = new String[tranche3PiUsersUserId.size()];
                    tranche3PiUsersUserIdResult = tranche3PiUsersUserId.toArray(tranche3PiUsersUserIdResult);

                    if (logger.isInfoEnabled()) {
                        logger.info("tranche3PiUsersUserIdResult=" +
                            Arrays.toString(tranche3PiUsersUserIdResult));
                    }

                    tranche1PiUsersUserIdResult = null;
                    tranche2PiUsersUserIdResult = null;
                    tranche3PiUsersUserIdResult = null;
                }

                Random randomGenerator = new Random();
                int index;

                if (!tranche1PiUsersUserId.isEmpty()) {
                    index = randomGenerator.nextInt(tranche1PiUsersUserId.size());
                    assigneduserUserId = tranche1PiUsersUserId.get(index);
                } else if (!tranche2PiUsersUserId.isEmpty()) {
                    index = randomGenerator.nextInt(tranche2PiUsersUserId.size());
                    assigneduserUserId = tranche2PiUsersUserId.get(index);
                } else if (!tranche3PiUsersUserId.isEmpty()) {
                    index = randomGenerator.nextInt(tranche3PiUsersUserId.size());
                    assigneduserUserId = tranche3PiUsersUserId.get(index);
                }

                tranche1PiUsersUserId = null;
                tranche2PiUsersUserId = null;
                tranche3PiUsersUserId = null;
            }
        }

        if (assigneduserUserId != null) {
            try {
                tsDataSource.begin();
                piUserRecoDAO.submitPiRecorequestTsAssigned(tsDataSource,
                    connection, recoRequestId, assigneduserUserId);
                tsDataSource.commit();

                //sleep for remaining time!!
                try {
                    long sleepTime = (long) (TSConstants.PI_ASSIGNED_START_SLEEP_TIME * ((Math.random() * 6) +
                        2));

                    if (logger.isDebugEnabled()) {
                        logger.debug(" SLEEP (in ms) for " + sleepTime);
                    }

                    Thread.currentThread();
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tsDataSource.begin();

                PiRestaurantRecommendationVO piRestaurantRecommendationVO = getPiRestaurantRecommendation(tsDataSource,
                        connection, initiatorUserId, cuisineTier2IdList,
                        cuisineTier1IdList, priceIdList, themeIdList,
                        whoareyouwithIdList, typeOfRestaurantIdList,
                        occasionIdList);

                // Send notification to `recorequest_ts_assigned`.`ASSIGNED_USER_ID`

                //  invoke underlying implementation as part of web service
                piUserRecoDAO.submitRecommendationRequestAnswer(tsDataSource,
                    connection, recoRequestId, assigneduserUserId,
                    CommonFunctionsUtil.convertStringListAsArrayList(
                        piRestaurantRecommendationVO.getRestaurantId()),
                    piRestaurantRecommendationVO.getReccomendationText());

                // log data
                piUserRecoDAO.submitPiRecoLog(tsDataSource, connection,
                    initiatorUserId, assigneduserUserId,
                    piRestaurantRecommendationVO);
                tsDataSource.commit();
            } catch (SQLException e1) {
                e1.printStackTrace();

                try {
                    tsDataSource.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }

                throw new TasteSyncException(e1.getMessage());
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("No user is assigned!!!");
            }
        }
    }
}
