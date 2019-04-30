package io.drift.jdbc.usecase;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.drift.core.api.Flow;
import io.drift.core.store.ModelStoreException;
import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.data.DBDeltaBuilder;
import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.data.DBSnapShotBuilder;
import io.drift.jdbc.domain.data.SnapshotConfig;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.metadata.DBMetaDataBuilder;
import io.drift.jdbc.domain.session.DataCaptureSession;
import io.drift.jdbc.domain.session.SessionId;

public class DBSnapshotFlow implements Flow {

	private DataSource dataSource;

	private DBMetaData dbMetaData;
	private DBSnapShotBuilder dbSnapShotBuilder;
	private DBSnapShot lastDBSnapshot = null;
	Logger logger = LoggerFactory.getLogger(DBSnapshotFlow.class);
	private SnapshotConfig snapshotConfig;
	private SnapshotWorkspace workspace;

	public DBSnapshotFlow(SnapshotWorkspace workspace) {
		this.workspace = workspace;
	}

	public void connect() throws ModelStoreException {
		logger.info("connecting");
		dataSource = createDataSource();
		String[] tableNames = new String[] { "OWNERS", "PETS", "VETS" };

		DBMetaDataBuilder dbMetaDataBuilder = new DBMetaDataBuilder();
		dbMetaData = dbMetaDataBuilder.buildDBMetaData(dataSource, tableNames);

		workspace.storeDBMetadata(dbMetaData);

		snapshotConfig = new SnapshotConfig(tableNames);
		dbSnapShotBuilder = new DBSnapShotBuilder();
	}

	private DataSource createDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:tcp://localhost/./test");
		dataSource.setUser("user1");
		dataSource.setPassword("pwd");
		return dataSource;
	}

	public void destroy() {
		logger.info("shutting down");
	}

	@Override
	public String getName() {
		return "DB Snapshot	";
	}

	public void init() {
		logger.info("init");
	}

	public DataCaptureSession startSession() {
		return workspace.createSession();
	}

	public void takeSnaphot(SessionId sessionId) throws ModelStoreException {
		DBSnapShot dbSnapShot = dbSnapShotBuilder.takeDBSnapShot(dataSource, dbMetaData, snapshotConfig);

		DBDeltaBuilder dbDeltaBuilder = new DBDeltaBuilder(dbMetaData);
		DBDelta dbDelta = dbDeltaBuilder.createDBDelta(lastDBSnapshot, dbSnapShot);

		// writeSnapshot(dbSnapShot, System.out);
		System.out.println(dbDelta.getSummary());
		// writeDBDelta(dbDelta, System.out);

		workspace.storeDBDelta(sessionId, dbDelta);

		lastDBSnapshot = dbSnapShot;
	}

}
