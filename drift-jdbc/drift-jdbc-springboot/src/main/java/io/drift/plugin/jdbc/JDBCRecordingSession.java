package io.drift.plugin.jdbc;

import io.drift.core.recording.SubSystemState;
import io.drift.core.system.SubSystemKey;
import io.drift.jdbc.domain.data.*;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.metadata.DBMetaDataBuilder;
import io.drift.jdbc.domain.system.JDBCConnectionDetails;

import javax.sql.DataSource;

public class JDBCRecordingSession {

    private final JDBCConnectionDetails jdbcConnectionDetails;
    private final JDBCConnectionManager connectionManager;
    private final SubSystemKey subSystemKey;

    private DBDelta dbDelta;
    private DBMetaData dbMetaData;
    private DBSnapShotBuilder dbSnapShotBuilder;
    private DBSnapShot lastDBSnapshot;
    private SnapshotConfig snapshotConfig;

    public JDBCRecordingSession(JDBCConnectionDetails jdbcConnectionDetails, JDBCConnectionManager connectionManager, SubSystemKey subSystemKey) {
        this.jdbcConnectionDetails = jdbcConnectionDetails;
        this.connectionManager = connectionManager;
        this.subSystemKey = subSystemKey;
        initDBMetaData();
        initSnapshotBuilder();
    }

    public JDBCRecordingSession(JDBCConnectionDetails jdbcConnectionDetails, JDBCConnectionManager connectionManager, SubSystemKey subSystemKey, DBMetaData dbMetaData, DBSnapShot lastDBSnapshot) {
        this.jdbcConnectionDetails = jdbcConnectionDetails;
        this.connectionManager = connectionManager;
        this.subSystemKey = subSystemKey;
        this.dbMetaData = dbMetaData;
        initSnapshotBuilder();
        this.lastDBSnapshot = lastDBSnapshot;
    }

    private void initDBMetaData() {
        String[] tableNames = jdbcConnectionDetails.getTableNames();
        DBMetaDataBuilder dbMetaDataBuilder = new DBMetaDataBuilder();
        dbMetaData = dbMetaDataBuilder.buildDBMetaData(getDataSource(), tableNames);
    }

    private void initSnapshotBuilder() {
        String[] tableNames = jdbcConnectionDetails.getTableNames();
        snapshotConfig = new SnapshotConfig(tableNames);
        dbSnapShotBuilder = new DBSnapShotBuilder();
    }

    public void takeSnapshot() {
        DBSnapShot dbSnapShot = dbSnapShotBuilder.takeDBSnapShot(getDataSource(), dbMetaData, snapshotConfig);

        if (lastDBSnapshot != null) {
            DBDeltaBuilder dbDeltaBuilder = new DBDeltaBuilder(dbMetaData);
            dbDelta = dbDeltaBuilder.createDBDelta(lastDBSnapshot, dbSnapShot);
            dbDelta.setSubSystem(subSystemKey.getName());
        }

        lastDBSnapshot = dbSnapShot;
    }

    private DataSource getDataSource() {
        return connectionManager.getDataSource(jdbcConnectionDetails);
    }

    public DBMetaData getDbMetaData() {
        return dbMetaData;
    }

    public DBSnapShot getLastSnapshot() {
        return lastDBSnapshot;
    }

    public DBDelta getLastDelta() {
        return dbDelta;
    }

    public void close() {
    }

    public SubSystemKey getSubSystemKey() {
        return subSystemKey;
    }


}
