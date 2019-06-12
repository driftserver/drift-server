package io.drift.core.recording;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProblemDescription implements Serializable {

    private String location;
    private String action;
    private String problem;
    private Exception exception;


    public ProblemDescription(String location, String action, String problem, Exception exception) {
        this.location = location;
        this.action = action;
        this.problem = problem;
        this.exception = exception;
    }

    public String getLocation() {
        return location;
    }

    public String getAction() {
        return action;
    }

    public String getProblem() {
        return problem;
    }

    public List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        recursiveGetMessages(messages, exception);
        return messages;
    }

    private void recursiveGetMessages(List<String> messages, Throwable throwable) {
        if (throwable != null) {
            messages.add(throwable.getClass().getName() + ": " + throwable.getMessage());
            recursiveGetMessages(messages, throwable.getCause());
        }

    }

}