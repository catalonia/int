package com.tastesync.algo.user.restaurant;

import com.tastesync.algo.model.vo.RestaurantPopularityTierVO;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class RankRestaurantsSingleUserCalcHelper {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(RankRestaurantsSingleUserCalcHelper.class);

    public RankRestaurantsSingleUserCalcHelper() {
        super();
    }

    public List<RestaurantPopularityTierVO> personalisedRestaurantsResultsForSingleUser(
        LinkedList<RestaurantPopularityTierVO> restaurantPopularityTierVOList) {
        // divide into 10 buckets...
        List<RestaurantPopularityTierVO> numTier1Match0 = new LinkedList<RestaurantPopularityTierVO>();
        List<RestaurantPopularityTierVO> numTier1Match1 = new LinkedList<RestaurantPopularityTierVO>();
        List<RestaurantPopularityTierVO> numTier2Match0 = new LinkedList<RestaurantPopularityTierVO>();
        List<RestaurantPopularityTierVO> numTier2Match1 = new LinkedList<RestaurantPopularityTierVO>();
        List<RestaurantPopularityTierVO> numTier3Match0 = new LinkedList<RestaurantPopularityTierVO>();
        List<RestaurantPopularityTierVO> numTier3Match1 = new LinkedList<RestaurantPopularityTierVO>();
        List<RestaurantPopularityTierVO> numTier4Match0 = new LinkedList<RestaurantPopularityTierVO>();
        List<RestaurantPopularityTierVO> numTier4Match1 = new LinkedList<RestaurantPopularityTierVO>();
        List<RestaurantPopularityTierVO> numTier5Match0 = new LinkedList<RestaurantPopularityTierVO>();
        List<RestaurantPopularityTierVO> numTier5Match1 = new LinkedList<RestaurantPopularityTierVO>();

        for (RestaurantPopularityTierVO restaurantPopularityTierVO : restaurantPopularityTierVOList) {
            int restaurantTierId = (restaurantPopularityTierVO.getPopularityTierId() != null)
                ? Integer.valueOf(restaurantPopularityTierVO.getPopularityTierId())
                : (Integer.MIN_VALUE);
            int userMatchCounter = (restaurantPopularityTierVO.getNumUserRestaurantMatchCount() != null)
                ? Integer.valueOf(restaurantPopularityTierVO.getNumUserRestaurantMatchCount())
                : (Integer.MIN_VALUE);

            if (logger.isInfoEnabled()) {
                logger.info("restaurantTierId=" + restaurantTierId +
                    " userMatchCounter=" + userMatchCounter + "\nt" +
                    restaurantPopularityTierVO);
            }

            if ((restaurantTierId == 1) && (userMatchCounter == 0)) {
                numTier1Match0.add(restaurantPopularityTierVO);
            } else if ((restaurantTierId == 1) && (userMatchCounter > 0)) {
                numTier1Match1.add(restaurantPopularityTierVO);
            } else if ((restaurantTierId == 2) && (userMatchCounter == 0)) {
                numTier2Match0.add(restaurantPopularityTierVO);
            } else if ((restaurantTierId == 2) && (userMatchCounter > 0)) {
                numTier2Match1.add(restaurantPopularityTierVO);
            } else if ((restaurantTierId == 3) && (userMatchCounter == 0)) {
                numTier3Match0.add(restaurantPopularityTierVO);
            } else if ((restaurantTierId == 3) && (userMatchCounter > 0)) {
                numTier3Match1.add(restaurantPopularityTierVO);
            } else if ((restaurantTierId == 4) && (userMatchCounter == 0)) {
                numTier4Match0.add(restaurantPopularityTierVO);
            } else if ((restaurantTierId == 4) && (userMatchCounter > 0)) {
                numTier4Match1.add(restaurantPopularityTierVO);
            } else if ((restaurantTierId == 5) && (userMatchCounter == 0)) {
                numTier5Match0.add(restaurantPopularityTierVO);
            } else if ((restaurantTierId == 5) && (userMatchCounter > 0)) {
                numTier5Match1.add(restaurantPopularityTierVO);
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info(
                        "personalisedRestaurantsResultsForSingleUser(LinkedList<RestaurantPopularityTierVO>) - Not a valid group. Unknown combination for restaurantTierId=" +
                        restaurantTierId + " userMatchCounter=" +
                        userMatchCounter);
                }
            }
        }

        //free memory 
        restaurantPopularityTierVOList = null;

        //rank them
        //int lowerRankNumber = 0;
        //int upperRankNumber = numTier1Match0.size();
        int currentRankNumber = 0;

        int restaurantsRankMatch0ListExpectedSize = numTier1Match0.size() +
            numTier2Match0.size() + numTier3Match0.size() +
            numTier4Match0.size() + numTier5Match0.size();

        int restaurantsRankMatch1ListExpectedSize = numTier1Match1.size() +
            numTier2Match1.size() + numTier3Match1.size() +
            numTier4Match1.size() + numTier5Match1.size();

        List<RestaurantPopularityTierVO> restaurantsRankMatch0List = new ArrayList<RestaurantPopularityTierVO>(restaurantsRankMatch0ListExpectedSize);

        List<RestaurantPopularityTierVO> restaurantsRankMatch1List = new ArrayList<RestaurantPopularityTierVO>(restaurantsRankMatch1ListExpectedSize);

        //shuffle
        Collections.shuffle(numTier1Match0);

        for (RestaurantPopularityTierVO aNumTier1Match0 : numTier1Match0) {
            ++currentRankNumber;
            // set rank number
            aNumTier1Match0.setRankNumber(currentRankNumber);
            restaurantsRankMatch0List.add(aNumTier1Match0);
        }

        //shuffle
        Collections.shuffle(numTier2Match1);

        for (RestaurantPopularityTierVO aNumTier2Match0 : numTier2Match0) {
            ++currentRankNumber;
            // set rank number
            aNumTier2Match0.setRankNumber(currentRankNumber);
            restaurantsRankMatch0List.add(aNumTier2Match0);
        }

        //shuffle
        Collections.shuffle(numTier3Match0);

        for (RestaurantPopularityTierVO aNumTier3Match0 : numTier3Match0) {
            ++currentRankNumber;
            // set rank number
            aNumTier3Match0.setRankNumber(currentRankNumber);
            restaurantsRankMatch0List.add(aNumTier3Match0);
        }

        //shuffle
        Collections.shuffle(numTier4Match0);

        for (RestaurantPopularityTierVO aNumTier4Match0 : numTier4Match0) {
            ++currentRankNumber;
            // set rank number
            aNumTier4Match0.setRankNumber(currentRankNumber);
            restaurantsRankMatch0List.add(aNumTier4Match0);
        }

        //shuffle
        Collections.shuffle(numTier5Match0);

        for (RestaurantPopularityTierVO aNumTier5Match0 : numTier5Match0) {
            ++currentRankNumber;

            // set rank number
            aNumTier5Match0.setRankNumber(currentRankNumber);
            restaurantsRankMatch0List.add(aNumTier5Match0);
        }

        currentRankNumber = 0;
        //shuffle
        Collections.shuffle(numTier1Match1);

        for (int i = 0; i < numTier1Match1.size(); ++i) {
            ++currentRankNumber;
            // set rank number
            numTier1Match0.get(i).setRankNumber(currentRankNumber);
            restaurantsRankMatch1List.add(numTier1Match1.get(i));
        }

        //shuffle
        Collections.shuffle(numTier2Match1);

        for (RestaurantPopularityTierVO aNumTier2Match1 : numTier2Match1) {
            ++currentRankNumber;

            // set rank number
            aNumTier2Match1.setRankNumber(currentRankNumber);
            restaurantsRankMatch1List.add(aNumTier2Match1);
        }

        //shuffle
        Collections.shuffle(numTier3Match1);

        for (RestaurantPopularityTierVO aNumTier3Match1 : numTier3Match1) {
            ++currentRankNumber;

            // set rank number
            aNumTier3Match1.setRankNumber(currentRankNumber);
            restaurantsRankMatch1List.add(aNumTier3Match1);
        }

        //shuffle
        Collections.shuffle(numTier4Match1);

        for (RestaurantPopularityTierVO aNumTier4Match1 : numTier4Match1) {
            ++currentRankNumber;

            // set rank number
            aNumTier4Match1.setRankNumber(currentRankNumber);
            restaurantsRankMatch1List.add(aNumTier4Match1);
        }

        //shuffle
        Collections.shuffle(numTier5Match1);

        for (RestaurantPopularityTierVO aNumTier5Match1 : numTier5Match1) {
            ++currentRankNumber;

            // set rank number
            aNumTier5Match1.setRankNumber(currentRankNumber);
            restaurantsRankMatch1List.add(aNumTier5Match1);
        }

        // free memory
        numTier1Match0 = null;
        numTier1Match1 = null;
        numTier2Match0 = null;
        numTier2Match1 = null;
        numTier3Match0 = null;
        numTier3Match1 = null;
        numTier4Match0 = null;
        numTier4Match1 = null;
        numTier4Match0 = null;
        numTier4Match1 = null;

        //take 4 from restaurantsRankMatch0List and 6 restaurantsRankMatch1List, reset the ranking
        //70 130
        int restaurantsRankMatch0ListSize = restaurantsRankMatch0List.size();
        int restaurantsRankMatch1ListSize = restaurantsRankMatch1List.size();

        List<RestaurantPopularityTierVO> list1ofrestaurants = new ArrayList<RestaurantPopularityTierVO>(restaurantsRankMatch0ListExpectedSize +
                restaurantsRankMatch1ListExpectedSize);

        int finalRankNumber = 0;
        RestaurantPopularityTierVO finalRestaurantPopularityTierVO = null;

        int INCREMENT0 = 4;
        int INCREMENT1 = 6;
        int lower0StartIndex = 0;
        int upper0Endindex = lower0StartIndex + INCREMENT0;

        int lower1StartIndex = 0;
        int upper1Endindex = lower1StartIndex + INCREMENT1;

        //less than INCREMENT0 (4)
        if ((restaurantsRankMatch0ListSize != 0) &&
                (upper0Endindex > restaurantsRankMatch0ListSize)) {
            upper0Endindex = restaurantsRankMatch0ListSize;
        }

        //less than INCREMENT1 (6)
        if ((restaurantsRankMatch1ListSize != 0) &&
                (upper1Endindex > restaurantsRankMatch1ListSize)) {
            upper1Endindex = restaurantsRankMatch1ListSize;
        }

        while ((upper0Endindex <= restaurantsRankMatch0ListSize) ||
                (upper1Endindex <= restaurantsRankMatch1ListSize)) {
            if (logger.isDebugEnabled()) {
                logger.debug("upper0Endindex=" + upper0Endindex +
                    " restaurantsRankMatch0ListSize=" +
                    restaurantsRankMatch0ListSize + " upper1Endindex=" +
                    upper1Endindex + " restaurantsRankMatch1ListSize=" +
                    restaurantsRankMatch1ListSize);
            }

            if (upper0Endindex <= restaurantsRankMatch0ListSize) {
                for (int currentRestaurantsRankMatch0Index = lower0StartIndex;
                        currentRestaurantsRankMatch0Index < upper0Endindex;
                        ++currentRestaurantsRankMatch0Index) {
                    ++finalRankNumber;
                    finalRestaurantPopularityTierVO = restaurantsRankMatch0List.get(currentRestaurantsRankMatch0Index);
                    finalRestaurantPopularityTierVO.setRankNumber(finalRankNumber);
                    list1ofrestaurants.add(finalRestaurantPopularityTierVO);
                }

                lower0StartIndex = upper0Endindex;
                upper0Endindex = lower0StartIndex + INCREMENT0;
            }

            if (upper1Endindex <= restaurantsRankMatch1ListSize) {
                for (int currentRestaurantsRankMatch1Index = lower1StartIndex;
                        currentRestaurantsRankMatch1Index < upper1Endindex;
                        ++currentRestaurantsRankMatch1Index) {
                    ++finalRankNumber;
                    finalRestaurantPopularityTierVO = restaurantsRankMatch1List.get(currentRestaurantsRankMatch1Index);
                    finalRestaurantPopularityTierVO.setRankNumber(finalRankNumber);
                    list1ofrestaurants.add(finalRestaurantPopularityTierVO);
                }

                lower1StartIndex = upper1Endindex;
                upper1Endindex = lower1StartIndex + INCREMENT1;
            }
        }

        return list1ofrestaurants;
    }
}
