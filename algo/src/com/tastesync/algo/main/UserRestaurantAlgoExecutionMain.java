package com.tastesync.algo.main;

import com.tastesync.algo.exception.TasteSyncException;
import com.tastesync.algo.user.reco.UserRecoAssigned;


public class UserRestaurantAlgoExecutionMain {
    /**
     * @param args   input arguments - first argument - reco request id
     */
    public static void main(String[] args) {
        UserRecoAssigned userRecoAssigned = new UserRecoAssigned();

        //
        String recoRequestId = null;

        if (args.length == 0) {
            System.out.println("Input parameters - recoRequestId is needed  ");

            return;
        }

        recoRequestId = args[0];

        System.out.println("Input parameters - recoRequestId=" + recoRequestId);

        int recorequestIteration = 1;

        try {
            userRecoAssigned.processAssignRecorequestToUsers(recoRequestId,
                recorequestIteration);
        } catch (TasteSyncException e) {
            e.printStackTrace();
        }
    }
}
