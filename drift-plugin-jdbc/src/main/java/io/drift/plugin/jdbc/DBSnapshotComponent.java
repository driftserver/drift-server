package io.drift.plugin.jdbc;

import io.drift.core.api.Flow;
import io.drift.plugin.jdbc.model.data.DBDelta;
import io.drift.plugin.jdbc.model.data.DBDeltaBuilder;
import io.drift.plugin.jdbc.model.data.DBSnapShot;
import io.drift.plugin.jdbc.model.data.DBSnapShotBuilder;
import io.drift.plugin.jdbc.model.data.SnapshotConfig;
import io.drift.plugin.jdbc.model.metadata.DBMetaData;
import io.drift.plugin.jdbc.model.metadata.DBMetaDataBuilder;

import java.io.IOException;
import java.io.PrintStream;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DBSnapshotComponent implements Flow {

	DBSnapShot lastDBSnapshot = null;
	private SnapshotConfig snapshotConfig;
	private DBSnapShotBuilder dbSnapShotBuilder;
	private DBMetaData dbMetaData;
	private DataSource dataSource;

	public void setup() {
		dataSource = createDataSource();

		String[] tableNames = new String[] { "OWNERS", "PETS", "VETS" };

		DBMetaDataBuilder dbMetaDataBuilder = new DBMetaDataBuilder();
		dbMetaData = dbMetaDataBuilder.buildDBMetaData(dataSource, tableNames);

		snapshotConfig = new SnapshotConfig(tableNames);
		dbSnapShotBuilder = new DBSnapShotBuilder();

	}

	public void takeSnaphot() {
		DBSnapShot dbSnapShot = dbSnapShotBuilder.takeDBSnapShot(dataSource, dbMetaData, snapshotConfig);

		DBDeltaBuilder dbDeltaBuilder = new DBDeltaBuilder(dbMetaData);
		DBDelta dbDelta = dbDeltaBuilder.createDBDelta(lastDBSnapshot, dbSnapShot);

		// writeSnapshot(dbSnapShot, System.out);
		System.out.println(dbDelta.getSummary());
		// writeDBDelta(dbDelta, System.out);

		lastDBSnapshot = dbSnapShot;
	}

	private DataSource createDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:tcp://localhost/./test");
		dataSource.setUser("user1");
		dataSource.setPassword("pwd");
		return dataSource;
	}

	private void writeDBMetaData(DBMetaData dbMetaData, PrintStream out) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(out, dbMetaData);
		out.flush();
	}

	private void writeSnapshot(DBSnapShot dbSnapShot, PrintStream out) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(out, dbSnapShot);
		out.flush();
	}

	private void writeDBDelta(DBDelta dbDelta, PrintStream out) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(out, dbDelta);
		out.flush();
		out.close();
	}

	@Override
	public String getName() {
		return "DB Snapshot	";
	}

}
