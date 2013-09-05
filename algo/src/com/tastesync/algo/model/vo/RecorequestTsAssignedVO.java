package com.tastesync.algo.model.vo;

import java.io.Serializable;

import java.util.Date;


public class RecorequestTsAssignedVO implements Serializable {
    private static final long serialVersionUID = -8784106599266634967L;
    private String recorequestId;
    private Date recorequestAssignedDatetime;

    public RecorequestTsAssignedVO(String recorequestId,
        Date recorequestAssignedDatetime) {
        super();
        this.recorequestId = recorequestId;
        this.recorequestAssignedDatetime = recorequestAssignedDatetime;
    }

    public String getRecorequestId() {
        return recorequestId;
    }

    public Date getRecorequestAssignedDatetime() {
        return recorequestAssignedDatetime;
    }
}
