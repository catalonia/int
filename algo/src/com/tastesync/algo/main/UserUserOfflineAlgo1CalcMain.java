package com.tastesync.algo.main;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.user.user.DemandPriorityCalc;
import com.tastesync.algo.user.user.SupplyInventoryCalc;
import com.tastesync.algo.user.user.UserTopicCalc;
import com.tastesync.algo.user.user.UserUserCalc;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class UserUserOfflineAlgo1CalcMain {
    public static void main(String[] args) {
        TSDataSource tsDataSource = TSDataSource.getInstance();
        Connection connection = null;

        try {
            //Get DB connection object
            connection = tsDataSource.getConnection();

            while (true) {
                SupplyInventoryCalc supplyInventoryCalc = new SupplyInventoryCalc();
                supplyInventoryCalc.processAllUserFlaggedUserListSupplyInventory(tsDataSource,
                    connection);

                DemandPriorityCalc demandPriorityCalc = new DemandPriorityCalc();
                demandPriorityCalc.processAllUserFlaggedUserListDemandPriority(tsDataSource,
                    connection);

                UserTopicCalc userTopicCalc = new UserTopicCalc();
                userTopicCalc.processAllUserFlaggedUserListUserTopic(tsDataSource,
                    connection);

                UserUserCalc userUserCalc = new UserUserCalc();
                userUserCalc.processAllUserFlaggedUserListUserUser(tsDataSource,
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
