package com.tastesync.dataextraction.db.dao;

import com.tastesync.dataextraction.exception.TasteSyncException;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;

import java.util.List;


public interface FactualCrosswalkDAO {
    void addInvalidCrossWalkData(TSDataSource tsDataSource,
        Connection connection, String restaurantId, String factualId)
        throws TasteSyncException;

    void addOpenTableFactualCrossWalkData(TSDataSource tsDataSource,
        Connection connection, String restaurantId, String factualId,
        String reservationSource, String reservationSourceId,
        String reservationUrl) throws TasteSyncException;

    List<String> getFactualIds(TSDataSource tsDataSource, Connection connection)
        throws TasteSyncException;
}
