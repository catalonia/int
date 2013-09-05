package com.tastesync.algo.model.vo;

import java.io.Serializable;

import java.util.Date;


public class RecorequestUserVO implements Serializable {
    private static final long serialVersionUID = -338159441152241723L;
    private String recorequestId;
    private Date recorequestSentDatetime;

    public RecorequestUserVO(String recorequestId, Date recorequestSentDatetime) {
        super();
        this.recorequestId = recorequestId;
        this.recorequestSentDatetime = recorequestSentDatetime;
    }

    public String getRecorequestId() {
        return recorequestId;
    }

    public Date getRecorequestSentDatetime() {
        return recorequestSentDatetime;
    }
}
