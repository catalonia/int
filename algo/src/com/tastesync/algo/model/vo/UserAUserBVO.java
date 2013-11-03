package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class UserAUserBVO implements Serializable {
    private static final long serialVersionUID = 4693216470379297377L;
    private String userA;
    private String userB;

    public UserAUserBVO() {
        super();
    }

    public UserAUserBVO(String userA, String userB) {
        super();
        this.userA = userA;
        this.userB = userB;
    }

    public String getUserA() {
        return userA;
    }

    public String getUserB() {
        return userB;
    }

    public void setUserA(String userA) {
        this.userA = userA;
    }

    public void setUserB(String userB) {
        this.userB = userB;
    }

    @Override
    public String toString() {
        return "UserAUserBVO [userA=" + userA + ", userB=" + userB + "]";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((userA == null) ? 0 : userA.hashCode());
        result = (prime * result) + ((userB == null) ? 0 : userB.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        UserAUserBVO other = (UserAUserBVO) obj;
        // add additional condition if swap is same then also equals.. that is A, B is same as B,A
        if (userA.equals(other.userB) && userB.equals(other.userA)) {
            return true;
        }
        
        if (userA == null) {
            if (other.userA != null) {
                return false;
            }
        } else if (!userA.equals(other.userA)) {
            return false;
        }

        if (userB == null) {
            if (other.userB != null) {
                return false;
            }
        } else if (!userB.equals(other.userB)) {
            return false;
        }

 

        return true;
    }
}
