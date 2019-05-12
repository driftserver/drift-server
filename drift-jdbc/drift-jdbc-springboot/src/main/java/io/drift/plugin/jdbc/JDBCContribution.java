package io.drift.plugin.jdbc;

import io.drift.core.recording.RecordingContext;
import io.drift.core.recording.RecordingSessionContribution;
import io.drift.jdbc.domain.data.*;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.metadata.DBMetaDataBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JDBCContribution implements RecordingSessionContribution {

    private DataSource dataSource;

    private DBMetaData dbMetaData;
    private DBSnapShotBuilder dbSnapShotBuilder;
    private DBSnapShot lastDBSnapshot = null;
    private SnapshotConfig snapshotConfig;
    private String subSystemName;


    public void start(RecordingContext context) {
        dataSource = createDataSource();

        String[] tableNames = new String[] { "OWNERS", "PETS", "VETS" };

        DBMetaDataBuilder dbMetaDataBuilder = new DBMetaDataBuilder();
        dbMetaData = dbMetaDataBuilder.buildDBMetaData(dataSource, tableNames);

        snapshotConfig = new SnapshotConfig(tableNames);
        dbSnapShotBuilder = new DBSnapShotBuilder();

        context.getRecording().addSubSystemDescription(subSystemName, dbMetaData);
    }

    public void takeSnapshot(RecordingContext context) {
        DBSnapShot dbSnapShot = dbSnapShotBuilder.takeDBSnapShot(dataSource, dbMetaData, snapshotConfig);

        DBDeltaBuilder dbDeltaBuilder = new DBDeltaBuilder(dbMetaData);
        DBDelta dbDelta = dbDeltaBuilder.createDBDelta(lastDBSnapshot, dbSnapShot);
        lastDBSnapshot = dbSnapShot;

        dbDelta.setSubSystem(subSystemName);
        context.getCurrentStep().getSystemInteractions().add(dbDelta);

    }

    @Override
    public void finish(RecordingContext context) {

    }


    private DataSource createDataSource() {

        subSystemName = "db";

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:tcp://localhost/./test");
        dataSource.setUser("user1");
        dataSource.setPassword("pwd");

        return dataSource;

    }


}
