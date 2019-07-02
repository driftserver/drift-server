package io.drift.core.system;

import io.drift.core.recording.ActionLogger;

import java.util.UUID;

public interface SystemDescriptionDomainService {

    SystemDescription getSystemDescription();

    UUID testConnectivity(EnvironmentKey environmentKey);

    ActionLogger getConnectivityTestResult(UUID actionId);
}
