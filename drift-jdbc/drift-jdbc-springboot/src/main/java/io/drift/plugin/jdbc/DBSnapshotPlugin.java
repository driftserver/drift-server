package io.drift.plugin.jdbc;

import io.drift.core.api.Flow;
import io.drift.core.config.DriftEngine;
import io.drift.core.springboot.DriftServer;
import io.drift.core.springboot.DriftServerPlugin;
import io.drift.jdbc.usecase.DBSnapshotFlow;
import io.drift.plugin.jdbc.ui.DBSnapshotFlowComponentFactory;
import org.springframework.stereotype.Component;

@Component
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
