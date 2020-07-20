package io.drift.plugin.jdbc;

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

        snapshotConfig = new SnapshotConfig(jdbcConnectionDetails.getTableNames());
        dbSnapShotBuilder = new DBSnapShotBuilder();

    }

    public void initialize() {
        DBMetaDataBuilder dbMetaDataBuilder = new DBMetaDataBuilder();
        dbMetaData = dbMetaDataBuilder.buildDBMetaData(getDataSource(), jdbcConnectionDetails.getTableNames());
    }

    public void reconnect(DBSnapShot lastDBSnapshot, DBMetaData dbMetaData) {
        this.lastDBSnapshot = lastDBSnapshot;
        this.dbMetaData = dbMetaData;
    }

    public void takeSnapshot() {
        DBSnapShot dbSnapShot = dbSnapShotBuilder.takeDBSnapShot(getDataSource(), dbMetaData, snapshotConfig);
        dbSnapShot.setSubSystem(subSystemKey.getName());

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


    public void open() {
        getDataSource();
    }

}
