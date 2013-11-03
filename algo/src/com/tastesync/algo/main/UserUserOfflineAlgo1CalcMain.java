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
        TSDataSource tsDataSource = null;
        Connection connection = null;

        try {
            while (true) {
                //Get DB connection object
                tsDataSource = TSDataSource.getInstance();

                connection = tsDataSource.getConnection();

                SupplyInventoryCalc supplyInventoryCalc = new SupplyInventoryCalc();
                supplyInventoryCalc.processAllUserFlaggedUserListSupplyInventory();

                DemandPriorityCalc demandPriorityCalc = new DemandPriorityCalc();
                demandPriorityCalc.processAllUserFlaggedUserListDemandPriority();

                UserTopicCalc userTopicCalc = new UserTopicCalc();
                userTopicCalc.processAllUserFlaggedUserListUserTopic();

                UserUserCalc userUserCalc = new UserUserCalc();
                userUserCalc.processAllUserFlaggedUserListUserUser();
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
            if (tsDataSource != null) {
                tsDataSource.close();
                tsDataSource.closeConnection(connection, null, null);
            }
        }
    }
}
