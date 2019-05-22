package io.drift.plugin.jdbc;

import io.drift.core.recording.RecordingContext;
import io.drift.core.recording.RecordingId;
import io.drift.core.recording.RecordingSessionContribution;
import io.drift.core.system.SubSystemConnectionDetails;
import io.drift.core.system.SubSystemKey;
import io.drift.jdbc.domain.data.*;
import io.drift.jdbc.domain.metadata.DBMetaDataBuilder;
import io.drift.jdbc.domain.system.JDBCConnectionDetails;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JDBCContribution implements RecordingSessionContribution {

    public static final String SUBSYSTEM_TYPE = "jdbc";

    private Map<RecordingId, List<JDBCRecordingContext>> jdbcContextsByRecordingId = new HashMap<>();

    public void start(RecordingContext recordingContext) {

        Map<SubSystemKey, SubSystemConnectionDetails> jdbcSubSystems = recordingContext.getSubSystemDetails(SUBSYSTEM_TYPE);

        List<JDBCRecordingContext> jdbcContexts = new ArrayList<>();
        jdbcContextsByRecordingId.put(recordingContext.getRecordingId(), jdbcContexts);

        jdbcSubSystems.entrySet().forEach(jdbcSubSystem -> {

            SubSystemKey subSystemKey = jdbcSubSystem.getKey();
            JDBCConnectionDetails jdbcConnectionDetails = (JDBCConnectionDetails) jdbcSubSystem.getValue();

            JDBCRecordingContext jdbcContext = new JDBCRecordingContext();
            jdbcContexts.add(jdbcContext);

            jdbcContext.subSystemKey = subSystemKey;

            jdbcContext.dataSource = createDataSource(jdbcConnectionDetails);

            String[] tableNames = jdbcConnectionDetails.getTableNames();
            DBMetaDataBuilder dbMetaDataBuilder = new DBMetaDataBuilder();
            jdbcContext.dbMetaData = dbMetaDataBuilder.buildDBMetaData(jdbcContext.dataSource, tableNames);

            jdbcContext.snapshotConfig = new SnapshotConfig(tableNames);

            jdbcContext.dbSnapShotBuilder = new DBSnapShotBuilder();

            recordingContext.getRecording().addSubSystemDescription(jdbcContext.subSystemKey.getName(), jdbcContext.dbMetaData);

        });


    }

    public void takeSnapshot(RecordingContext recordingContext) {
        List<JDBCRecordingContext> jdbcContexts = jdbcContextsByRecordingId.get(recordingContext.getRecordingId());

        jdbcContexts.forEach(jdbcContext -> {

            DBSnapShot dbSnapShot = jdbcContext.dbSnapShotBuilder.takeDBSnapShot(jdbcContext.dataSource, jdbcContext.dbMetaData, jdbcContext.snapshotConfig);

            DBDeltaBuilder dbDeltaBuilder = new DBDeltaBuilder(jdbcContext.dbMetaData);
            DBDelta dbDelta = dbDeltaBuilder.createDBDelta(jdbcContext.lastDBSnapshot, dbSnapShot);
            jdbcContext.lastDBSnapshot = dbSnapShot;

            dbDelta.setSubSystem(jdbcContext.subSystemKey.getName());
            recordingContext.getCurrentStep().getSystemInteractions().add(dbDelta);
        });

    }

    @Override
    public void finish(RecordingContext context) {
        jdbcContextsByRecordingId.remove(context.getRecordingId());
    }

    private DataSource createDataSource(JDBCConnectionDetails connectionDetails) {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(connectionDetails.getJdbcUrl());
        dataSource.setUser(connectionDetails.getUserName());
        dataSource.setPassword(connectionDetails.getPassword());

        return dataSource;

    }

}
