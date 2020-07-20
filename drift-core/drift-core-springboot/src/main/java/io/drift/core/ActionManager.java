package io.drift.core;

import io.drift.core.infra.logging.ActionLogger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ActionManager {

    private Map<UUID, ActionLogger> actions = new HashMap<>();

    public void register(ActionLogger actionLogger) {
        actions.put(actionLogger.getId(), actionLogger);
    }

    public ActionLogger get(UUID id) {
        return actions.get(id);
    }


}
