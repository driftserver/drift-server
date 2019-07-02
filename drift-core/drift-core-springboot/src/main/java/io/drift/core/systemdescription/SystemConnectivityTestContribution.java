package io.drift.core.systemdescription;

import io.drift.core.recording.ActionLogger;
import io.drift.core.system.EnvironmentKey;
import io.drift.core.system.SystemDescription;

public interface SystemConnectivityTestContribution {
    void testConnectivity(EnvironmentKey environmentKey, SystemDescription systemDescription, ActionLogger actionLogger);
}
