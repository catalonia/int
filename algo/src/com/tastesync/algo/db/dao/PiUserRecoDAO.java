package com.tastesync.algo.db.dao;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.model.vo.PiRestaurantRecommendationVO;
import com.tastesync.algo.model.vo.PiUsersAlreadyAssignedDataVO;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;

import java.util.List;


public interface PiUserRecoDAO extends UserRecoDAO {
    List<String> getAllReccomendationIds(TSDataSource tsDataSource,
        Connection connection, String cityId) throws TasteSyncException;

    int getCountPiRecoUserCuistier2MatchMapper(TSDataSource tsDataSource,
        Connection connection, String recommendationId,
        List<String> cuisineTier1IdList) throws TasteSyncException;

    int getCountPiRecoUserOccasionMatch(TSDataSource tsDataSource,
        Connection connection, String recommendationId,
        List<String> occasionIdList) throws TasteSyncException;

    int getCountPiRecoUserPriceMatch(TSDataSource tsDataSource,
        Connection connection, String recommendationId, List<String> priceIdList)
        throws TasteSyncException;

    int getCountPiRecoUserThemeMatch(TSDataSource tsDataSource,
        Connection connection, String recommendationId, List<String> themeIdList)
        throws TasteSyncException;

    int getCountPiRecoUserTypeofrestMatch(TSDataSource tsDataSource,
        Connection connection, String recommendationId,
        List<String> typeOfRestaurantIdList) throws TasteSyncException;

    int getCountPiRecoUserWhoareyouwithMatch(TSDataSource tsDataSource,
        Connection connection, String userecommendationIdrId,
        List<String> whoareyouwithIdList) throws TasteSyncException;

    int getCountPiUserCuistier2Match(TSDataSource tsDataSource,
        Connection connection, String recommendationId,
        List<String> cuisineTier2IdList) throws TasteSyncException;

    int getCountPiUserNeighbourhoodIdMatch(TSDataSource tsDataSource,
        Connection connection, String recommendationId, String neighbourhoodId)
        throws TasteSyncException;

    List<String> getExcludedRecommendationIdList(TSDataSource tsDataSource,
        Connection connection, String userId) throws TasteSyncException;

    PiRestaurantRecommendationVO getPiRecommendationIdText(
        TSDataSource tsDataSource, Connection connection,
        String recommendationId) throws TasteSyncException;

    List<String> getPiUserAlreadyAssignedToUser(TSDataSource tsDataSource,
        Connection connection, String userId) throws TasteSyncException;

    List<PiUsersAlreadyAssignedDataVO> getPiUserAlreadyAssignedToUserData(TSDataSource tsDataSource,
            Connection connection, String userId) throws TasteSyncException;

    List<String> getPiUsersCategoryCityNbrhoodList(TSDataSource tsDataSource,
        Connection connection, String cityId)
        throws TasteSyncException;

    void submitPiRecoLog(TSDataSource tsDataSource, Connection connection,
        String userId, String assignedPiUserId,
        PiRestaurantRecommendationVO piRestaurantRecommendationVO)
        throws TasteSyncException;

    void submitRecommendationRequestAnswer(TSDataSource tsDataSource,
        Connection connection, String recorequestId, String recommenderUserId,
        String[] restaurantIdList, String replyText) throws TasteSyncException;
}
