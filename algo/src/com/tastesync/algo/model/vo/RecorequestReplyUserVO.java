package com.tastesync.algo.model.vo;

import java.io.Serializable;

import java.util.Date;


public class RecorequestReplyUserVO implements Serializable {
    private static final long serialVersionUID = -7938620329590667254L;
    private String replyId;
    private Date replyDatetime;

    public RecorequestReplyUserVO(String replyId, Date replyDatetime) {
        super();
        this.replyId = replyId;
        this.replyDatetime = replyDatetime;
    }

    public String getReplyId() {
        return replyId;
    }

    public Date getReplyDatetime() {
        return replyDatetime;
    }
}
