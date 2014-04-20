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
            	//1: 1 to 0
                RestInfoPopularityTierCalc restInfoPopularityTierCalc = new RestInfoPopularityTierCalc();
                restInfoPopularityTierCalc.processAllFlaggedRestaurantListRestInfoPopularityTier(tsDataSource,
                    connection);

                // 2/3:
                RestUserMatchCounterCalc restUserMatchCounterCalc = new RestUserMatchCounterCalc();
                restUserMatchCounterCalc.processAllFlaggedRestaurantListRestUserMatchCounter(tsDataSource,
                    connection);

                // 4: 0 to -1
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
