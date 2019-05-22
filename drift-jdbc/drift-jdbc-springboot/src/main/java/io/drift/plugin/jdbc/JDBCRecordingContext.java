package io.drift.plugin.jdbc;

import io.drift.core.system.SubSystemKey;
import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.data.DBSnapShotBuilder;
import io.drift.jdbc.domain.data.SnapshotConfig;
import io.drift.jdbc.domain.metadata.DBMetaData;

import javax.sql.DataSource;

public class JDBCRecordingContext {
    DataSource dataSource;
    DBMetaData dbMetaData;
    DBSnapShotBuilder dbSnapShotBuilder;
    DBSnapShot lastDBSnapshot = null;
    SnapshotConfig snapshotConfig;
    SubSystemKey subSystemKey;
}
