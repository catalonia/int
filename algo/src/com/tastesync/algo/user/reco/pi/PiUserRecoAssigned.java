package com.tastesync.algo.user.reco.pi;

import com.tastesync.algo.db.dao.PiUserRecoDAO;
import com.tastesync.algo.db.dao.PiUserRecoDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.PiRecommendationsTopicMatchRateVO;
import com.tastesync.algo.model.vo.PiRestaurantRecommendationVO;
import com.tastesync.algo.util.CommonFunctionsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class PiUserRecoAssigned {
    private PiUserRecoDAO piUserRecoDAO = new PiUserRecoDAOImpl();
    private boolean printDebugExtra = false;

    public void processingPiAssignRecorequestToUsers(String recoRequestId,
        int recorequestIteration, String cityId, String nbrHoodId,
        String initiatorUserId, List<String> cuisineTier2IdList,
        List<String> cuisineTier1IdList, List<String> priceIdList,
        List<String> themeIdList, List<String> whoareyouwithIdList,
        List<String> typeOfRestaurantIdList, List<String> occasionIdList)
        throws TasteSyncException {
        //7A
        List<String> temp1PiUsersUserIdList = piUserRecoDAO.getPiUsersCategoryCityNbrhoodList(cityId,
                nbrHoodId);

        String temp1PiUsersUserId = null;
        boolean userReported = false;
        List<Integer> indexElementToBeRemovedFrmUserRecoSupplyTierVOList = new ArrayList<Integer>();

        //7B
        List<String> piUsersAlreadyAssignedIdList = piUserRecoDAO.getPiUserAlreadyAssignedToUser(initiatorUserId);

        //TODO filter list of temp1users{userId}
        for (int i = 0; i < temp1PiUsersUserIdList.size(); ++i) {
            temp1PiUsersUserId = temp1PiUsersUserIdList.get(i);

            userReported = piUserRecoDAO.isUserIdLinkedWithReportedInfo(initiatorUserId,
                    temp1PiUsersUserId);

            if (userReported) {
                if (!indexElementToBeRemovedFrmUserRecoSupplyTierVOList.contains(
                            i)) {
                    indexElementToBeRemovedFrmUserRecoSupplyTierVOList.add(i);
                }
            }

            if (!piUsersAlreadyAssignedIdList.contains(temp1PiUsersUserId)) {
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
            temp1PiUsersUserIdList.remove(index - currentRemovedCounter);
            ++currentRemovedCounter;
        }

        if (printDebugExtra) {
            String[] temp1PiUsersUserIdListResult = new String[temp1PiUsersUserIdList.size()];
            System.out.println("temp1PiUsersUserIdListResult=" +
                Arrays.toString(temp1PiUsersUserIdListResult));

            String[] indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult = new String[indexElementToBeRemovedFrmUserRecoSupplyTierVOList.size()];
            System.out.println(
                "indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult=" +
                Arrays.toString(
                    indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult));
        }

        // 7C
        String assigneduserUserId = null;

        // no users
        if ((temp1PiUsersUserIdList == null) ||
                (temp1PiUsersUserIdList.size() == 0)) {
            return;
        } else {
            if (temp1PiUsersUserIdList.size() == 1) {
                assigneduserUserId = temp1PiUsersUserIdList.get(0);
            } else {
                int matchCount = 0;

                int topicMatchCounter = 0;
                double topicMatchRate = 0;
                temp1PiUsersUserId = null;

                int numRecorequestParams = cuisineTier2IdList.size() +
                    cuisineTier1IdList.size() + priceIdList.size() +
                    themeIdList.size() + whoareyouwithIdList.size();
                double userAUserBMatchTier = 0;

                List<String> tranche1PiUsersUserId = new ArrayList<String>(temp1PiUsersUserIdList.size());
                List<String> tranche2PiUsersUserId = new ArrayList<String>(temp1PiUsersUserIdList.size());
                List<String> tranche3PiUsersUserId = new ArrayList<String>(temp1PiUsersUserIdList.size());

                for (int i = 0; i < temp1PiUsersUserIdList.size(); ++i) {
                    temp1PiUsersUserId = temp1PiUsersUserIdList.get(i);

                    matchCount = 1;
                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserCuistier2Match(temp1PiUsersUserIdList.get(
                                i), cuisineTier2IdList, matchCount);
                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserCuistier2MatchMapper(temp1PiUsersUserIdList.get(
                                i), cuisineTier1IdList, matchCount);

                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserPriceMatch(temp1PiUsersUserIdList.get(
                                i), priceIdList, matchCount);

                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserThemeMatch(temp1PiUsersUserIdList.get(
                                i), themeIdList, matchCount);

                    //TODO check mathcocunt not required?
                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserWhoareyouwithMatch(temp1PiUsersUserIdList.get(
                                i), whoareyouwithIdList);

                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserTypeofrestMatch(temp1PiUsersUserIdList.get(
                                i), typeOfRestaurantIdList, matchCount);

                    topicMatchCounter = topicMatchCounter +
                        piUserRecoDAO.getCountUserOccasionMatch(temp1PiUsersUserIdList.get(
                                i), occasionIdList, matchCount);

                    topicMatchRate = (double) topicMatchCounter / numRecorequestParams;

                    userAUserBMatchTier = piUserRecoDAO.getUserAUserBMatchTier(initiatorUserId,
                            temp1PiUsersUserIdList.get(i));

                    if ((topicMatchRate >= 0.6) &&
                            ((userAUserBMatchTier == 1) ||
                            (userAUserBMatchTier == 2))) {
                        tranche1PiUsersUserId.add(temp1PiUsersUserIdList.get(i));
                    }

                    if ((topicMatchRate >= 1) &&
                            ((userAUserBMatchTier == 1) ||
                            (userAUserBMatchTier == 2))) {
                        tranche2PiUsersUserId.add(temp1PiUsersUserIdList.get(i));
                    }

                    if (userAUserBMatchTier != 4) {
                        tranche3PiUsersUserId.add(temp1PiUsersUserIdList.get(i));
                    }
                }

                if (printDebugExtra) {
                    String[] tranche1PiUsersUserIdResult = new String[tranche1PiUsersUserId.size()];
                    System.out.println("tranche1PiUsersUserIdResult=" +
                        Arrays.toString(tranche1PiUsersUserIdResult));

                    String[] tranche2PiUsersUserIdResult = new String[tranche2PiUsersUserId.size()];
                    System.out.println("tranche2PiUsersUserIdResult=" +
                        Arrays.toString(tranche2PiUsersUserIdResult));

                    String[] tranche3PiUsersUserIdResult = new String[tranche3PiUsersUserId.size()];
                    System.out.println("tranche3PiUsersUserIdResult=" +
                        Arrays.toString(tranche3PiUsersUserIdResult));

                    tranche1PiUsersUserIdResult = null;
                    tranche2PiUsersUserIdResult = null;
                    tranche3PiUsersUserIdResult = null;
                }

                Random randomGenerator = new Random();
                int index = 0;

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
            piUserRecoDAO.submitRecorequestTsAssigned(recoRequestId,
                assigneduserUserId);

            PiRestaurantRecommendationVO piRestaurantRecommendationVO = getPiRestaurantRecommendation(initiatorUserId,
                    cuisineTier2IdList, cuisineTier1IdList, priceIdList,
                    themeIdList, whoareyouwithIdList, typeOfRestaurantIdList,
                    occasionIdList);

            // TODO: Send notification to `recorequest_ts_assigned`.`ASSIGNED_USER_ID`

            // TODO invoke web service
            piUserRecoDAO.submitRecommendationRequestAnswer(recoRequestId,
                assigneduserUserId,
                CommonFunctionsUtil.convertStringListAsArrayList(
                    piRestaurantRecommendationVO.getRestaurantId()),
                piRestaurantRecommendationVO.getReccomendationText());

            // log data
            piUserRecoDAO.submitPiRecoLog(initiatorUserId, assigneduserUserId,
                piRestaurantRecommendationVO);
        } else {
            System.out.println(
                "End of processingPiAssignRecorequestToUsers - No user is assigned!!!");
        }
    }

    private PiRestaurantRecommendationVO getPiRestaurantRecommendation(
        String initiatorUserId, List<String> cuisineTier2IdList,
        List<String> cuisineTier1IdList, List<String> priceIdList,
        List<String> themeIdList, List<String> whoareyouwithIdList,
        List<String> typeOfRestaurantIdList, List<String> occasionIdList)
        throws TasteSyncException {
        List<String> allRecommendationIdsList = piUserRecoDAO.getAllReccomendationIds();
        int topicMatchCounter = 0;
        double topicMatchRate = 0;
        int numRecorequestParams = cuisineTier2IdList.size() +
            cuisineTier1IdList.size() + priceIdList.size() +
            themeIdList.size() + whoareyouwithIdList.size();

        List<PiRecommendationsTopicMatchRateVO> piRecommendationsTopicMatchRateVOList =
            new LinkedList<PiRecommendationsTopicMatchRateVO>();

        PiRecommendationsTopicMatchRateVO piRecommendationsTopicMatchRateVO = null;

        for (String recommendationId : allRecommendationIdsList) {
            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiUserCuistier2Match(recommendationId,
                    cuisineTier2IdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserCuistier2MatchMapper(recommendationId,
                    cuisineTier1IdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserPriceMatch(recommendationId,
                    priceIdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserThemeMatch(recommendationId,
                    themeIdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserWhoareyouwithMatch(recommendationId,
                    whoareyouwithIdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserTypeofrestMatch(recommendationId,
                    typeOfRestaurantIdList);

            topicMatchCounter = topicMatchCounter +
                piUserRecoDAO.getCountPiRecoUserOccasionMatch(recommendationId,
                    occasionIdList);

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
                                 .compareTo(Double.valueOf(
                            o2.getTopicMatchRate()));
                }
            });

        List<String> excludedRecommendationIdsList = piUserRecoDAO.getExcludedRecommendationIdList(initiatorUserId);

        for (PiRecommendationsTopicMatchRateVO piRecommendationsTopicMatchRateVOElement : piRecommendationsTopicMatchRateVOList) {
            if (excludedRecommendationIdsList.contains(
                        piRecommendationsTopicMatchRateVOElement.getRecommendationId())) {
                piRecommendationsTopicMatchRateVOList.remove(piRecommendationsTopicMatchRateVOElement);
            }
        }

        PiRestaurantRecommendationVO piRestaurantRecommendationVO = null;

        //from the remaining list. pick the first one.
        if (piRecommendationsTopicMatchRateVOList.size() > 0) {
            System.out.println("piRestaurantRecommendationVO Found " +
                piRecommendationsTopicMatchRateVOList.get(0));
            piRestaurantRecommendationVO = piUserRecoDAO.getPiRecommendationIdText(piRecommendationsTopicMatchRateVOList.get(
                        0).getRecommendationId());
        } else {
            System.out.println("piRestaurantRecommendationVO Not Found");
        }

        return piRestaurantRecommendationVO;
    }
}
