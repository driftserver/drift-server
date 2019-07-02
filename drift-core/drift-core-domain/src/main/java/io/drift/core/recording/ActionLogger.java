package io.drift.core.recording;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActionLogger {

    private UUID id = UUID.randomUUID();
    private boolean async;
    private List<ProblemDescription> problemDescriptions = new ArrayList<>();

    public ActionLogger(boolean async) {
        this.async = async;
    }

    public void addProblem(ProblemDescription problemDescription) {
        problemDescriptions.add(problemDescription);
    }

    public boolean isAsync() {
        return async;
    }

    public List<ProblemDescription> getProblemDescriptions() {
        return problemDescriptions;
    }

    public boolean hasProblems() {
        return problemDescriptions.size() > 0;
    }

    public UUID getId() {
        return id;
    }
}
