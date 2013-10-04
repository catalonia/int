package com.tastesync.algo.main;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.user.restaurant.RestInfoPopularityTierCalc;
import com.tastesync.algo.user.restaurant.RestUserMatchCounterCalc;
import com.tastesync.algo.user.restaurant.UserRestRankOrderCalc;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class UserRestaurantOfflineAlgo2CalcMain {
    public static void main(String[] args) {
        TSDataSource tsDataSource = null;
        Connection connection = null;

        try {
            //Get DB connection object
            tsDataSource = TSDataSource.getInstance();

            connection = tsDataSource.getConnection();

            tsDataSource.begin();

            //Run algo calc
            RestInfoPopularityTierCalc restInfoPopularityTierCalc = new RestInfoPopularityTierCalc();
            restInfoPopularityTierCalc.processAllFlaggedRestaurantListRestInfoPopularityTier();
            tsDataSource.commit();
            tsDataSource.begin();

            RestUserMatchCounterCalc restUserMatchCounterCalc = new RestUserMatchCounterCalc();
            restUserMatchCounterCalc.processAllFlaggedRestaurantListRestUserMatchCounter();
            tsDataSource.commit();
            tsDataSource.begin();

            UserRestRankOrderCalc userRestRankOrderCalc = new UserRestRankOrderCalc();
            userRestRankOrderCalc.updateUserRestRankOrderCalc();
            tsDataSource.commit();
        } catch (TasteSyncException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                tsDataSource.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (tsDataSource != null) {
                tsDataSource.close();
                tsDataSource.closeConnection(connection, null, null);
            }
        }
    }
}
