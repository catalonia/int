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
        TSDataSource tsDataSource = TSDataSource.getInstance();
        Connection connection = null;

        try {
            //Get DB connection object
            connection = tsDataSource.getConnection();

            while (true) {
                //Run algo calc
                RestInfoPopularityTierCalc restInfoPopularityTierCalc = new RestInfoPopularityTierCalc();
                restInfoPopularityTierCalc.processAllFlaggedRestaurantListRestInfoPopularityTier(tsDataSource,
                    connection);

                RestUserMatchCounterCalc restUserMatchCounterCalc = new RestUserMatchCounterCalc();
                restUserMatchCounterCalc.processAllFlaggedRestaurantListRestUserMatchCounter(tsDataSource,
                    connection);

                UserRestRankOrderCalc userRestRankOrderCalc = new UserRestRankOrderCalc();
                userRestRankOrderCalc.updateUserRestRankOrderCalc(tsDataSource,
                    connection);
            }
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
        	tsDataSource.closeConnection(connection);
        }
    }
}
