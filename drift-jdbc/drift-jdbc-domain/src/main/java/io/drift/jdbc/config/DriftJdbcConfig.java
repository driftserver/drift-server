package io.drift.jdbc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.drift.core.config.DriftCoreConfig;
import io.drift.core.store.IDGenerator;
import io.drift.core.store.ModelStore;
import io.drift.jdbc.usecase.ConnectAction;
import io.drift.jdbc.usecase.DBSnapshotFlow;
import io.drift.jdbc.usecase.SnapshotWorkspace;
import io.drift.jdbc.usecase.TakeSnapshotAction;

@Configuration
@Import(DriftCoreConfig.class)
public class DriftJdbcConfig {

	@Autowired
	IDGenerator idGenerator;

	@Autowired
	ModelStore modelStore;

	@Bean
	public ConnectAction connectAction() {
		return new ConnectAction(dbSnapshotFlow());
	}

	@Bean(initMethod = "init", destroyMethod = "destroy")
	public DBSnapshotFlow dbSnapshotFlow() {
		return new DBSnapshotFlow(new SnapshotWorkspace(modelStore, idGenerator));
	}

	@Bean
	public TakeSnapshotAction takeSnapshotAction() {
		return null; // new TakeSnapshotAction(dbSnapshotFlow());
	}

}
