package com.tastesync.algo.main;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.user.reco.UserRecoAssigned;
import com.tastesync.db.pool.TSDataSource;


public class TriggerAlgo1ExecutionMain {
    /**
     * @param args   input arguments - first argument - reco request id
     */
    public static void main(String[] args) {
        UserRecoAssigned userRecoAssigned = new UserRecoAssigned();

        if (args.length == 0) {
            System.out.println("Input parameters - recoRequestId is needed  ");

            return;
        }

        String recoRequestId = args[0];

        System.out.println("Input parameters - recoRequestId=" + recoRequestId);

        int recorequestIteration = 1;
        try {
        	
        	TSDataSource tsDataSource = TSDataSource.getInstance();
        	
            userRecoAssigned.processAssignRecorequestToUsers(recoRequestId,
                recorequestIteration);
        } catch (TasteSyncException e) {
            e.printStackTrace();
        }
    }
}
