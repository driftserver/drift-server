package io.drift.core.recording;

import java.util.ArrayList;
import java.util.List;

public class ActionResult {

    private List<ProblemDescription> problemDescriptions = new ArrayList<>();

    public void addProblem(ProblemDescription problemDescription) {
        problemDescriptions.add(problemDescription);
    }

    public List<ProblemDescription> getProblemDescriptions() {
        return problemDescriptions;
    }

}
