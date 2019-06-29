package io.drift.core.recording;

import java.util.ArrayList;
import java.util.List;

public class ActionLogger {

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

}
