package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.PiRestaurantRecommendationVO;

import java.util.List;


public interface PiUserRecoDAO extends UserRecoDAO {
    List<String> getPiUsersCategoryCityNbrhoodList(String cityId,
        String nbrHoodId) throws TasteSyncException;

    List<String> getPiUserAlreadyAssignedToUser(String userId)
        throws TasteSyncException;

    int getCountPiUserCuistier2Match(String recommendationId,
        List<String> cuisineTier2IdList) throws TasteSyncException;

    int getCountPiRecoUserCuistier2MatchMapper(String recommendationId,
        List<String> cuisineTier1IdList) throws TasteSyncException;

    int getCountPiRecoUserPriceMatch(String recommendationId,
        List<String> priceIdList) throws TasteSyncException;

    int getCountPiRecoUserThemeMatch(String recommendationId,
        List<String> themeIdList) throws TasteSyncException;

    int getCountPiRecoUserWhoareyouwithMatch(String userecommendationIdrId,
        List<String> whoareyouwithIdList) throws TasteSyncException;

    int getCountPiRecoUserTypeofrestMatch(String recommendationId,
        List<String> typeOfRestaurantIdList) throws TasteSyncException;

    int getCountPiRecoUserOccasionMatch(String recommendationId,
        List<String> occasionIdList) throws TasteSyncException;

    List<String> getAllReccomendationIds() throws TasteSyncException;

    List<String> getExcludedRecommendationIdList(String userId)
        throws TasteSyncException;

    PiRestaurantRecommendationVO getPiRecommendationIdText(
        String recommendationId) throws TasteSyncException;

    void submitPiRecoLog(String userId, String assignedPiUserId,
        PiRestaurantRecommendationVO piRestaurantRecommendationVO)
        throws TasteSyncException;
    
    void submitRecommendationRequestAnswer(String recorequestId,
            String recommenderUserId, String[] restaurantIdList, String replyText)
            throws TasteSyncException;
    
}
