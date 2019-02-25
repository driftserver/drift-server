package io.drift.core.ui;

import io.drift.core.config.DriftEngine;
import io.drift.core.config.EnginePlugin;

abstract public class DriftServerPlugin extends EnginePlugin {

	public void register(DriftEngine engine) {
	}

	public void registerUI(DriftServer driftServer) {
	}

}
