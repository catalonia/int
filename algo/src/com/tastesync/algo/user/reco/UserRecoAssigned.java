package com.tastesync.algo.user.reco;

import com.tastesync.algo.db.dao.UserRecoDAO;
import com.tastesync.algo.db.dao.UserRecoDAOImpl;
import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.CityNeighbourhoodVO;
import com.tastesync.algo.model.vo.UserRecoSupplyTierVO;
import com.tastesync.algo.user.reco.pi.PiUserRecoAssigned;
import com.tastesync.algo.util.TSConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class UserRecoAssigned {
    private static final String NOT_USER_TOPIC_MATCH_4 = "not_user_topic_match_4";
    private UserRecoDAO userRecoDAO = new UserRecoDAOImpl();
    private boolean printDebugExtra = true;

    public UserRecoAssigned() {
        super();
    }

    public void processAssignRecorequestToUsers(String recoRequestId,
        int recorequestIteration) throws TasteSyncException {
        String initiatorUserId = userRecoDAO.getInitiatorUserIdFromRecorequestId(recoRequestId);

        if (initiatorUserId == null) {
            throw new TasteSyncException("For recoRequestId=" + recoRequestId +
                " Unknown userId= " + initiatorUserId);
        }

        List<String> cuisineTier2IdList = userRecoDAO.getRecorequestCuisineTier2IdList(recoRequestId);

        List<String> cuisineTier1IdList = userRecoDAO.getRecorequestCuisineTier1IdList(recoRequestId);

        List<String> priceIdList = userRecoDAO.getRecorequestPriceIdList(recoRequestId);

        List<String> themeIdList = userRecoDAO.getRecorequestThemeIdList(recoRequestId);

        List<String> whoareyouwithIdList = userRecoDAO.getRecorequestWhoareyouwithIdList(recoRequestId);

        List<String> typeOfRestaurantIdList = userRecoDAO.getRecorequestTypeofrestIdList(recoRequestId);

        List<String> occasionIdList = userRecoDAO.getRecorequestOccasionIdList(recoRequestId);

        //single city, single neighboourhoodid
        CityNeighbourhoodVO cityNeighbourhoodVO = userRecoDAO.getRecorequestCityIdNbrIdList(recoRequestId);

        String cityId = null;

        if ((cityNeighbourhoodVO != null) &&
                (cityNeighbourhoodVO.getCityId() != null) &&
                !cityNeighbourhoodVO.getCityId().isEmpty()) {
            cityId = cityNeighbourhoodVO.getCityId();
        }

        String neighborhoodId = null;

        if ((cityNeighbourhoodVO != null) &&
                (cityNeighbourhoodVO.getNeighbourhoodId() != null) &&
                !cityNeighbourhoodVO.getNeighbourhoodId().isEmpty()) {
            cityId = cityNeighbourhoodVO.getNeighbourhoodId();
        }

        int numRecorequestParams = cuisineTier2IdList.size() +
            cuisineTier1IdList.size() + priceIdList.size() +
            themeIdList.size() + whoareyouwithIdList.size();

        //sort lists
        if (cuisineTier1IdList != null) {
            Collections.sort(cuisineTier1IdList);
        }

        if (cuisineTier2IdList != null) {
            Collections.sort(cuisineTier2IdList);
        }

        if (priceIdList != null) {
            Collections.sort(priceIdList);
        }

        if (themeIdList != null) {
            Collections.sort(themeIdList);
        }

        if (whoareyouwithIdList != null) {
            Collections.sort(whoareyouwithIdList);
        }

        if (typeOfRestaurantIdList != null) {
            Collections.sort(typeOfRestaurantIdList);
        }

        if (occasionIdList != null) {
            Collections.sort(occasionIdList);
        }

        //dependency. build the string as it is done in web services
        //        String mergedText = getMergedtext(cuisineTier2IdList,
        //                cuisineTier1IdList, priceIdList, themeIdList,
        //                whoareyouwithIdList, typeOfRestaurantIdList, occasionIdList,
        //                cityId, neighborhoodId);
        int numSameParamRequests = userRecoDAO.getNumberOfSameParamRequests(initiatorUserId,
                recoRequestId);
        PiUserRecoAssigned piUserRecoAssigned = new PiUserRecoAssigned();

        // -- this implies Demand Tier 4
        if (numSameParamRequests > 1) {
            piUserRecoAssigned.processingPiAssignRecorequestToUsers(recoRequestId,
                recorequestIteration, cityId, neighborhoodId, initiatorUserId,
                cuisineTier2IdList, cuisineTier1IdList, priceIdList,
                themeIdList, whoareyouwithIdList, typeOfRestaurantIdList,
                occasionIdList);
        } else {
            int demandTier = userRecoDAO.getDemandTierForSingleUser(initiatorUserId);

            if ((demandTier == 1) || (demandTier == 2)) {
                //4A
                List<UserRecoSupplyTierVO> userRecoSupplyTierVOList = userRecoDAO.getUserRecoSupplyTierVO(initiatorUserId,
                        recoRequestId);
                String fbUserId = userRecoDAO.getFBUserId(initiatorUserId);

                //4B
                UserRecoSupplyTierVO userRecoSupplyTierVO = null;
                List<String> temp1usersTemp1Field = new ArrayList<String>(userRecoSupplyTierVOList.size());
                int numUserCityNbrhoodMatchTopicFound = 0;

                for (UserRecoSupplyTierVO anUserRecoSupplyTierVOList : userRecoSupplyTierVOList) {
                    userRecoSupplyTierVO = anUserRecoSupplyTierVOList;
                    numUserCityNbrhoodMatchTopicFound = userRecoDAO.getNumUserCityNbrhoodMatchTopicFound(userRecoSupplyTierVO.getUserId(),
                            cityId, neighborhoodId);

                    if (numUserCityNbrhoodMatchTopicFound > 0) {
                        temp1usersTemp1Field.add(NOT_USER_TOPIC_MATCH_4);
                    } else {
                        temp1usersTemp1Field.add(null);
                    }
                }

                //
                List<Integer> indexElementToBeRemovedFrmUserRecoSupplyTierVOList =
                    new ArrayList<Integer>();

                boolean userIdLinkedWithFbFriend = false;
                boolean userReported = false;

                //TODO filter list of temp1users{userId}
                for (int i = 0; i < userRecoSupplyTierVOList.size(); ++i) {
                    userRecoSupplyTierVO = userRecoSupplyTierVOList.get(i);
                    userIdLinkedWithFbFriend = userRecoDAO.isUserIdLinkedWithFbFriend(userRecoSupplyTierVO.getUserId(),
                            fbUserId);

                    if (userIdLinkedWithFbFriend) {
                        if (!indexElementToBeRemovedFrmUserRecoSupplyTierVOList.contains(
                                    i)) {
                            indexElementToBeRemovedFrmUserRecoSupplyTierVOList.add(i);
                        }
                    }

                    userReported = userRecoDAO.isUserIdLinkedWithReportedInfo(initiatorUserId,
                            userRecoSupplyTierVO.getUserId());

                    if (userReported) {
                        if (!indexElementToBeRemovedFrmUserRecoSupplyTierVOList.contains(
                                    i)) {
                            indexElementToBeRemovedFrmUserRecoSupplyTierVOList.add(i);
                        }
                    }
                }

                // remove from the list based on indexElementToBeRemovedFrmUserRecoSupplyTierVOList
                int currentRemovedCounter = 0;

                for (Integer index : indexElementToBeRemovedFrmUserRecoSupplyTierVOList) {
                    userRecoSupplyTierVOList.remove(index -
                        currentRemovedCounter);
                    temp1usersTemp1Field.remove(index - currentRemovedCounter);
                    ++currentRemovedCounter;
                }

                if (printDebugExtra) {
                    String[] temp1usersTemp1FieldResult = new String[temp1usersTemp1Field.size()];
                    temp1usersTemp1FieldResult = temp1usersTemp1Field.toArray(temp1usersTemp1FieldResult);

                    System.out.println("temp1usersTemp1FieldResult=" +
                        Arrays.toString(temp1usersTemp1FieldResult));

                    Integer[] indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult =
                        new Integer[indexElementToBeRemovedFrmUserRecoSupplyTierVOList.size()];
                    indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult = indexElementToBeRemovedFrmUserRecoSupplyTierVOList.toArray(indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult);
                    System.out.println(
                        "indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult=" +
                        Arrays.toString(
                            indexElementToBeRemovedFrmUserRecoSupplyTierVOListResult));
                }

                // check parameters are same as part of sanity check
                if (userRecoSupplyTierVOList.size() != temp1usersTemp1Field.size()) {
                    throw new TasteSyncException(
                        "size for userRecoSupplyTierVOList=" +
                        userRecoSupplyTierVOList.size() +
                        " temp1usersTemp1Field=" + temp1usersTemp1Field.size() +
                        " does not match.");
                }

                //4C
                int temp1usersTemp1FieldValue = 0;
                int temp1usersTemp1FieldValueFirstMatchIndex = 0;
                boolean firstTempFieldmatchFound = false;

                for (int i = 0; i < temp1usersTemp1Field.size(); ++i) {
                    if (temp1usersTemp1Field.get(i) != null) {
                        ++temp1usersTemp1FieldValue;

                        if (!firstTempFieldmatchFound) {
                            temp1usersTemp1FieldValueFirstMatchIndex = i;
                            firstTempFieldmatchFound = true;
                        }
                    }
                }

                //4C(i).
                String assigneduserUserId = null;
                boolean piassignedDone = false;

                if (temp1usersTemp1FieldValue == 0) {
                    piUserRecoAssigned.processingPiAssignRecorequestToUsers(recoRequestId,
                        recorequestIteration, cityId, neighborhoodId,
                        initiatorUserId, cuisineTier2IdList,
                        cuisineTier1IdList, priceIdList, themeIdList,
                        whoareyouwithIdList, typeOfRestaurantIdList,
                        occasionIdList);
                    piassignedDone = true;
                } else if (temp1usersTemp1FieldValue == 1) {
                    //single user
                    assigneduserUserId = userRecoSupplyTierVOList.get(temp1usersTemp1FieldValueFirstMatchIndex)
                                                                 .getUserId();
                } else {
                    // choose from multiple users
                    int matchCount = 0;
                    int topicMatchCounter = 0;
                    double topicMatchRate = 0;
                    double userAUserBMatchTier = 0;
                    List<String> tranche1usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());
                    List<String> tranche2usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());
                    List<String> tranche3usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());
                    List<String> tranche4usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());

                    List<String> tranche5usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());

                    List<String> tranche6usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());

                    List<String> tranche7usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());

                    for (int i = 0; i < userRecoSupplyTierVOList.size(); ++i) {
                        if (NOT_USER_TOPIC_MATCH_4.equals(
                                    temp1usersTemp1Field.get(i))) {
                            userRecoSupplyTierVO = userRecoSupplyTierVOList.get(i);
                            matchCount = 1;
                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserCuistier2Match(userRecoSupplyTierVO.getUserId(),
                                    cuisineTier2IdList, matchCount);
                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserCuistier2MatchMapper(userRecoSupplyTierVO.getUserId(),
                                    cuisineTier1IdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserPriceMatch(userRecoSupplyTierVO.getUserId(),
                                    priceIdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserThemeMatch(userRecoSupplyTierVO.getUserId(),
                                    themeIdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserWhoareyouwithMatch(userRecoSupplyTierVO.getUserId(),
                                    whoareyouwithIdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserTypeofrestMatch(userRecoSupplyTierVO.getUserId(),
                                    typeOfRestaurantIdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserOccasionMatch(userRecoSupplyTierVO.getUserId(),
                                    occasionIdList, matchCount);

                            topicMatchRate = (double) topicMatchCounter / numRecorequestParams;

                            userAUserBMatchTier = userRecoDAO.getUserAUserBMatchTier(initiatorUserId,
                                    userRecoSupplyTierVO.getUserId());

                            if ((userRecoSupplyTierVO.getUserSupplyInvTier() == 1) &&
                                    (topicMatchRate >= 0.6) &&
                                    (userAUserBMatchTier == 1)) {
                                tranche1usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }

                            if ((userRecoSupplyTierVO.getUserSupplyInvTier() == 1) &&
                                    (userAUserBMatchTier == 1)) {
                                tranche2usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }

                            if (userRecoSupplyTierVO.getUserSupplyInvTier() == 1) {
                                tranche3usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }

                            if ((userRecoSupplyTierVO.getUserSupplyInvTier() == 2) &&
                                    (topicMatchRate >= 0.6) &&
                                    (userAUserBMatchTier >= 1)) {
                                tranche4usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }

                            if (userRecoSupplyTierVO.getUserSupplyInvTier() == 2) {
                                tranche5usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }

                            if ((userRecoSupplyTierVO.getUserSupplyInvTier() == 3) &&
                                    ((topicMatchRate >= 0.6) ||
                                    (topicMatchCounter >= 1)) &&
                                    ((userAUserBMatchTier == 1) ||
                                    (userAUserBMatchTier == 2))) {
                                tranche6usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }

                            if (userRecoSupplyTierVO.getUserSupplyInvTier() == 3) {
                                tranche7usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }
                        }
                    }

                    // tranch user list available
                    //randomly select anyone
                    if (printDebugExtra) {
                        String[] tranche1usersUserIdResult = new String[tranche1usersUserId.size()];
                        tranche1usersUserIdResult = tranche1usersUserId.toArray(tranche1usersUserIdResult);
                        System.out.println("tranche1usersUserIdResult=" +
                            Arrays.toString(tranche1usersUserIdResult));

                        String[] tranche2usersUserIdResult = new String[tranche2usersUserId.size()];
                        tranche2usersUserIdResult = tranche2usersUserId.toArray(tranche2usersUserIdResult);
                        System.out.println("tranche2usersUserIdResult=" +
                            Arrays.toString(tranche2usersUserIdResult));

                        String[] tranche3usersUserIdResult = new String[tranche3usersUserId.size()];
                        tranche3usersUserIdResult = tranche3usersUserId.toArray(tranche3usersUserIdResult);
                        System.out.println("tranche3usersUserIdResult=" +
                            Arrays.toString(tranche3usersUserIdResult));

                        String[] tranche4usersUserIdResult = new String[tranche4usersUserId.size()];
                        tranche4usersUserIdResult = tranche4usersUserId.toArray(tranche4usersUserIdResult);
                        System.out.println("tranche4usersUserIdResult=" +
                            Arrays.toString(tranche4usersUserIdResult));

                        String[] tranche5usersUserIdResult = new String[tranche5usersUserId.size()];
                        tranche5usersUserIdResult = tranche5usersUserId.toArray(tranche5usersUserIdResult);
                        System.out.println("tranche5usersUserIdResult=" +
                            Arrays.toString(tranche5usersUserIdResult));

                        String[] tranche6usersUserIdResult = new String[tranche6usersUserId.size()];
                        tranche6usersUserIdResult = tranche6usersUserId.toArray(tranche6usersUserIdResult);
                        System.out.println("tranche6usersUserIdResult=" +
                            Arrays.toString(tranche6usersUserIdResult));

                        String[] tranche7usersUserIdResult = new String[tranche7usersUserId.size()];
                        tranche7usersUserIdResult = tranche7usersUserId.toArray(tranche7usersUserIdResult);
                        System.out.println("tranche7usersUserIdResult=" +
                            Arrays.toString(tranche7usersUserIdResult));

                        tranche1usersUserIdResult = null;
                        tranche2usersUserIdResult = null;
                        tranche3usersUserIdResult = null;
                        tranche4usersUserIdResult = null;
                        tranche5usersUserIdResult = null;
                        tranche6usersUserIdResult = null;
                        tranche7usersUserIdResult = null;
                    }

                    Random randomGenerator = new Random();
                    int index = 0;

                    if (!tranche1usersUserId.isEmpty()) {
                        index = randomGenerator.nextInt(tranche1usersUserId.size());
                        assigneduserUserId = tranche1usersUserId.get(index);
                    } else if (!tranche2usersUserId.isEmpty()) {
                        index = randomGenerator.nextInt(tranche2usersUserId.size());
                        assigneduserUserId = tranche2usersUserId.get(index);
                    } else if (!tranche3usersUserId.isEmpty()) {
                        index = randomGenerator.nextInt(tranche3usersUserId.size());
                        assigneduserUserId = tranche3usersUserId.get(index);
                    } else if (!tranche4usersUserId.isEmpty()) {
                        index = randomGenerator.nextInt(tranche4usersUserId.size());
                        assigneduserUserId = tranche4usersUserId.get(index);
                    } else if (!tranche5usersUserId.isEmpty()) {
                        index = randomGenerator.nextInt(tranche5usersUserId.size());
                        assigneduserUserId = tranche5usersUserId.get(index);
                    } else if (!tranche6usersUserId.isEmpty()) {
                        index = randomGenerator.nextInt(tranche6usersUserId.size());
                        assigneduserUserId = tranche6usersUserId.get(index);
                    } else if (!tranche7usersUserId.isEmpty()) {
                        index = randomGenerator.nextInt(tranche7usersUserId.size());
                        assigneduserUserId = tranche7usersUserId.get(index);
                    }

                    tranche1usersUserId = null;
                    tranche2usersUserId = null;
                    tranche3usersUserId = null;
                    tranche4usersUserId = null;
                    tranche5usersUserId = null;
                    tranche6usersUserId = null;

                    tranche7usersUserId = null;
                }

                System.out.println("assigneduserUserId=" + assigneduserUserId);

                if (assigneduserUserId != null) {
                    userRecoDAO.submitRecorequestTsAssigned(recoRequestId,
                        assigneduserUserId);
                    userRecoDAO.submitUserRecoSupplyTier(assigneduserUserId, 0,
                        1);

                    //sleep for remaining time!!
                    try {
                        System.out.println("SLEEP (in ms) for " +
                            TSConstants.SLEEP_TIME_BETWEEN_TWO_REQUEST_ITERATION_IN_MILLISECONDS);
                        Thread.currentThread();
                        Thread.sleep(TSConstants.SLEEP_TIME_BETWEEN_TWO_REQUEST_ITERATION_IN_MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int numReplyFromAssignedUser = userRecoDAO.getCountRecorequestReplyUser(recoRequestId,
                            assigneduserUserId);

                    if (demandTier == 1) {
                        if ((numReplyFromAssignedUser == 0) &&
                                (recorequestIteration == 1)) {
                            recorequestIteration = 2;
                            processAssignRecorequestToUsers(recoRequestId,
                                recorequestIteration);
                        } else if ((numReplyFromAssignedUser == 0) &&
                                (recorequestIteration == 2)) {
                            piUserRecoAssigned.processingPiAssignRecorequestToUsers(recoRequestId,
                                recorequestIteration, cityId, neighborhoodId,
                                initiatorUserId, cuisineTier2IdList,
                                cuisineTier1IdList, priceIdList, themeIdList,
                                whoareyouwithIdList, typeOfRestaurantIdList,
                                occasionIdList);
                            piassignedDone = true;
                        }
                    } else if (demandTier == 2) {
                        if (numReplyFromAssignedUser == 0) {
                            piUserRecoAssigned.processingPiAssignRecorequestToUsers(recoRequestId,
                                recorequestIteration, cityId, neighborhoodId,
                                initiatorUserId, cuisineTier2IdList,
                                cuisineTier1IdList, priceIdList, themeIdList,
                                whoareyouwithIdList, typeOfRestaurantIdList,
                                occasionIdList);
                            piassignedDone = true;
                        }
                    }
                }

                //TODO: Send notification to `recorequest_ts_assigned`.`ASSIGNED_USER_ID`
                if ((assigneduserUserId == null) && !piassignedDone) {
                    piUserRecoAssigned.processingPiAssignRecorequestToUsers(recoRequestId,
                        recorequestIteration, cityId, neighborhoodId,
                        initiatorUserId, cuisineTier2IdList,
                        cuisineTier1IdList, priceIdList, themeIdList,
                        whoareyouwithIdList, typeOfRestaurantIdList,
                        occasionIdList);
                }
            } else {
                //sleep for remaining time!!
                try {
                    System.out.println("SLEEP (in ms) for " +
                        TSConstants.DEMAND_TIER3_START_SLEEP_TIME);
                    Thread.currentThread();
                    Thread.sleep(TSConstants.DEMAND_TIER3_START_SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // GO to STEP 5. -- this implies Demand Tier 3 (Low)
                //            	TIME DELAY by 5 minutes i.e. when this recorequest is submitted, start countdown for 5 mins. 
                //            	At end of countdown, execute below logic:
                //            	- Execute 4A, 4B and core of 4C (i.e. NOT including 4C(i))
                //            	- Within 4C(i), Execute till end of line: 	Pick one tranche3users{userId} at RANDOM -- assigneduser{userId} 
                //            												GO to Step 6.
                //            	- After that, Execute: ELSE {GO to PROJECT PI LOGIC}
                //            	-- This means that we are time-delaying Demand Tier 3 and then assigning to user ONLY if there is a Supply Tier 1 user avl. Else PROJECT PI
                //4A
                List<UserRecoSupplyTierVO> userRecoSupplyTierVOList = userRecoDAO.getUserRecoSupplyTierVO(initiatorUserId,
                        recoRequestId);
                String fbUserId = userRecoDAO.getFBUserId(initiatorUserId);

                //4B
                UserRecoSupplyTierVO userRecoSupplyTierVO = null;
                List<String> temp1usersTemp1Field = new ArrayList<String>(userRecoSupplyTierVOList.size());
                int numUserCityNbrhoodMatchTopicFound = 0;

                for (UserRecoSupplyTierVO anUserRecoSupplyTierVOList : userRecoSupplyTierVOList) {
                    userRecoSupplyTierVO = anUserRecoSupplyTierVOList;
                    numUserCityNbrhoodMatchTopicFound = userRecoDAO.getNumUserCityNbrhoodMatchTopicFound(initiatorUserId,
                            cityId, neighborhoodId);

                    if (numUserCityNbrhoodMatchTopicFound > 0) {
                        temp1usersTemp1Field.add(NOT_USER_TOPIC_MATCH_4);
                    } else {
                        temp1usersTemp1Field.add(null);
                    }
                }

                //
                List<Integer> indexElementToBeRemovedFrmUserRecoSupplyTierVOList =
                    new ArrayList<Integer>();

                boolean userIdLinkedWithFbFriend = false;
                boolean userReported = false;

                //TODO filter list of temp1users{userId}
                for (int i = 0; i < userRecoSupplyTierVOList.size(); ++i) {
                    userRecoSupplyTierVO = userRecoSupplyTierVOList.get(i);
                    userIdLinkedWithFbFriend = userRecoDAO.isUserIdLinkedWithFbFriend(userRecoSupplyTierVO.getUserId(),
                            fbUserId);

                    if (userIdLinkedWithFbFriend) {
                        if (!indexElementToBeRemovedFrmUserRecoSupplyTierVOList.contains(
                                    i)) {
                            indexElementToBeRemovedFrmUserRecoSupplyTierVOList.add(i);
                        }
                    }

                    userReported = userRecoDAO.isUserIdLinkedWithReportedInfo(initiatorUserId,
                            userRecoSupplyTierVO.getUserId());

                    if (userReported) {
                        if (!indexElementToBeRemovedFrmUserRecoSupplyTierVOList.contains(
                                    i)) {
                            indexElementToBeRemovedFrmUserRecoSupplyTierVOList.add(i);
                        }
                    }
                }

                // remove from the list based on indexElementToBeRemovedFrmUserRecoSupplyTierVOList
                int currentRemovedCounter = 0;

                for (Integer index : indexElementToBeRemovedFrmUserRecoSupplyTierVOList) {
                    userRecoSupplyTierVOList.remove(index -
                        currentRemovedCounter);
                    temp1usersTemp1Field.remove(index - currentRemovedCounter);
                    ++currentRemovedCounter;
                }

                // check parameters are same as part of sanity check
                if (userRecoSupplyTierVOList.size() != temp1usersTemp1Field.size()) {
                    throw new TasteSyncException(
                        "size for userRecoSupplyTierVOList=" +
                        userRecoSupplyTierVOList.size() +
                        " temp1usersTemp1Field=" + temp1usersTemp1Field.size() +
                        " does not match.");
                }

                //4C
                int temp1usersTemp1FieldValue = 0;
                int temp1usersTemp1FieldValueFirstMatchIndex = 0;
                boolean firstTempFieldmatchFound = false;

                for (int i = 0; i < temp1usersTemp1Field.size(); ++i) {
                    if (temp1usersTemp1Field.get(i) != null) {
                        ++temp1usersTemp1FieldValue;

                        if (!firstTempFieldmatchFound) {
                            temp1usersTemp1FieldValueFirstMatchIndex = i;
                            firstTempFieldmatchFound = true;
                        }
                    }
                }

                //4C(i).
                String assigneduserUserId = null;
                boolean piassignedDone = false;

                if (temp1usersTemp1FieldValue == 0) {
                    piUserRecoAssigned.processingPiAssignRecorequestToUsers(recoRequestId,
                        recorequestIteration, cityId, neighborhoodId,
                        initiatorUserId, cuisineTier2IdList,
                        cuisineTier1IdList, priceIdList, themeIdList,
                        whoareyouwithIdList, typeOfRestaurantIdList,
                        occasionIdList);
                    piassignedDone = true;
                } else if (temp1usersTemp1FieldValue == 1) {
                    //single user
                    assigneduserUserId = userRecoSupplyTierVOList.get(temp1usersTemp1FieldValueFirstMatchIndex)
                                                                 .getUserId();
                } else {
                    // choose from multiple users
                    double matchCount = 0;
                    int topicMatchCounter = 0;
                    double topicMatchRate = 0;
                    double userAUserBMatchTier = 0;
                    List<String> tranche1usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());
                    List<String> tranche2usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());
                    List<String> tranche3usersUserId = new ArrayList<String>(userRecoSupplyTierVOList.size());

                    for (int i = 0; i < userRecoSupplyTierVOList.size(); ++i) {
                        if (NOT_USER_TOPIC_MATCH_4.equals(
                                    temp1usersTemp1Field.get(i))) {
                            userRecoSupplyTierVO = userRecoSupplyTierVOList.get(i);
                            matchCount = 1;
                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserCuistier2Match(userRecoSupplyTierVO.getUserId(),
                                    cuisineTier2IdList, matchCount);
                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserCuistier2MatchMapper(userRecoSupplyTierVO.getUserId(),
                                    cuisineTier1IdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserPriceMatch(userRecoSupplyTierVO.getUserId(),
                                    priceIdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserThemeMatch(userRecoSupplyTierVO.getUserId(),
                                    themeIdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserWhoareyouwithMatch(userRecoSupplyTierVO.getUserId(),
                                    whoareyouwithIdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserTypeofrestMatch(userRecoSupplyTierVO.getUserId(),
                                    typeOfRestaurantIdList, matchCount);

                            topicMatchCounter = topicMatchCounter +
                                userRecoDAO.getCountUserOccasionMatch(userRecoSupplyTierVO.getUserId(),
                                    occasionIdList, matchCount);

                            topicMatchRate = (double) topicMatchCounter / numRecorequestParams;

                            userAUserBMatchTier = userRecoDAO.getUserAUserBMatchTier(initiatorUserId,
                                    userRecoSupplyTierVO.getUserId());

                            if ((userRecoSupplyTierVO.getUserSupplyInvTier() == 1) &&
                                    (topicMatchRate >= 0.6) &&
                                    (userAUserBMatchTier == 1)) {
                                tranche1usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }

                            if ((userRecoSupplyTierVO.getUserSupplyInvTier() == 1) &&
                                    (userAUserBMatchTier == 1)) {
                                tranche2usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }

                            if (userRecoSupplyTierVO.getUserSupplyInvTier() == 1) {
                                tranche3usersUserId.add(userRecoSupplyTierVO.getUserId());
                            }
                        }
                    }

                    // tranch user list available
                    //randomly select anyone TODO
                    if (!tranche1usersUserId.isEmpty()) {
                        assigneduserUserId = tranche1usersUserId.get(0);
                    } else if (!tranche2usersUserId.isEmpty()) {
                        assigneduserUserId = tranche2usersUserId.get(0);
                    } else if (!tranche3usersUserId.isEmpty()) {
                        assigneduserUserId = tranche3usersUserId.get(0);
                    }

                    tranche1usersUserId = null;
                    tranche2usersUserId = null;
                    tranche3usersUserId = null;
                }

                if (assigneduserUserId != null) {
                    userRecoDAO.submitRecorequestTsAssigned(initiatorUserId,
                        assigneduserUserId);
                    userRecoDAO.submitUserRecoSupplyTier(initiatorUserId, 0, 1);

                    //sleep for remaining time!!
                    try {
                        System.out.println("SLEEP (in ms) for " +
                            TSConstants.SLEEP_TIME_BETWEEN_TWO_REQUEST_ITERATION_IN_MILLISECONDS);
                        Thread.currentThread();
                        Thread.sleep(TSConstants.SLEEP_TIME_BETWEEN_TWO_REQUEST_ITERATION_IN_MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int numReplyFromAssignedUser = userRecoDAO.getCountRecorequestReplyUser(recoRequestId,
                            assigneduserUserId);

                    if (numReplyFromAssignedUser == 0) {
                        piUserRecoAssigned.processingPiAssignRecorequestToUsers(recoRequestId,
                            recorequestIteration, cityId, neighborhoodId,
                            initiatorUserId, cuisineTier2IdList,
                            cuisineTier1IdList, priceIdList, themeIdList,
                            whoareyouwithIdList, typeOfRestaurantIdList,
                            occasionIdList);
                        piassignedDone = true;
                    }
                }

                //TODO: Send notification to `recorequest_ts_assigned`.`ASSIGNED_USER_ID`
                if ((assigneduserUserId == null) && !piassignedDone) {
                    piUserRecoAssigned.processingPiAssignRecorequestToUsers(recoRequestId,
                        recorequestIteration, cityId, neighborhoodId,
                        initiatorUserId, cuisineTier2IdList,
                        cuisineTier1IdList, priceIdList, themeIdList,
                        whoareyouwithIdList, typeOfRestaurantIdList,
                        occasionIdList);
                }
            }
        }
    }

    private String getMergedtext(List<String> cuisineTier2IdList,
        List<String> cuisineTier1IdList, List<String> priceIdList,
        List<String> themeIdList, List<String> whoareyouwithIdList,
        List<String> typeOfRestaurantIdList, List<String> occasionIdList,
        String cityId, String neighborhoodId) {
        StringBuffer mergedTextBuffer = new StringBuffer();

        for (String cuisineId : cuisineTier1IdList) {
            mergedTextBuffer.append("cuisine tier1 s:");
            mergedTextBuffer.append(cuisineId).append("");
        }

        for (String cuisineId : cuisineTier2IdList) {
            mergedTextBuffer.append("cuisine tier2 s:");
            mergedTextBuffer.append(cuisineId).append("");
        }

        for (String priceId : priceIdList) {
            mergedTextBuffer.append("priceId s:");
            mergedTextBuffer.append(priceId).append("");
        }

        for (String themeId : themeIdList) {
            mergedTextBuffer.append("themeId s:");
            mergedTextBuffer.append(themeId).append("");
        }

        for (String whoareyouwithId : whoareyouwithIdList) {
            mergedTextBuffer.append("whoareyouwithId s:");
            mergedTextBuffer.append(whoareyouwithId).append("");
        }

        for (String typeOfRestaurantId : typeOfRestaurantIdList) {
            mergedTextBuffer.append("typeOfRestaurantId s:");
            mergedTextBuffer.append(typeOfRestaurantId).append("");
        }

        for (String occasionId : occasionIdList) {
            mergedTextBuffer.append("occasionId s:");
            mergedTextBuffer.append(occasionId).append("");
        }

        if (cityId != null) {
            mergedTextBuffer.append("cityId s:");
            mergedTextBuffer.append(cityId).append("");

            if (neighborhoodId != null) {
                mergedTextBuffer.append("neighborhoodId s:");
                mergedTextBuffer.append(neighborhoodId).append("");
            }
        }

        return mergedTextBuffer.toString();
    }
}
