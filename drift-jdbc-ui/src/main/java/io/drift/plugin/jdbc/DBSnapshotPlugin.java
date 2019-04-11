package io.drift.plugin.jdbc;

import io.drift.core.api.Flow;
import io.drift.core.config.DriftEngine;
import io.drift.core.ui.DriftServer;
import io.drift.core.ui.DriftServerPlugin;
import io.drift.jdbc.usecase.DBSnapshotFlow;
import io.drift.plugin.jdbc.ui.DBSnapshotFlowComponentFactory;

public class DBSnapshotPlugin extends DriftServerPlugin {

	public Flow flow = new DBSnapshotFlow(null);

	@Override
	public void register(DriftEngine engine) {
		engine.addFlow(flow);
	}

	@Override
	public void registerUI(DriftServer driftServer) {
		driftServer.addComponentFactoryForFlow(flow, new DBSnapshotFlowComponentFactory());
	}

}
