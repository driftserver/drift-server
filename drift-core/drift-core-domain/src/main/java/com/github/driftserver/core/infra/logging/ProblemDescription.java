package com.github.driftserver.core.infra.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProblemDescription implements Serializable {

    private String location;
    private String action;
    private Exception exception;


    public ProblemDescription(String location, String action, Exception exception) {
        this.location = location;
        this.action = action;
        this.exception = exception;
    }

    public String getLocation() {
        return location;
    }

    public String getAction() {
        return action;
    }

    public String getProblem() {
        return exception.getMessage();
    }

    public List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        recursiveGetMessages(messages, exception.getCause());
        return messages;
    }

    private void recursiveGetMessages(List<String> messages, Throwable throwable) {
        if (throwable != null) {
            messages.add(throwable.getClass().getName() + ": " + throwable.getMessage());
            recursiveGetMessages(messages, throwable.getCause());
        }

    }

    public Exception getException() {
        return exception;
    }

    public boolean isDriftException() {
        return exception != null && exception instanceof DriftException;
    }

    public DriftException getDriftException() {
        return (DriftException) exception;
    }

    public Throwable getException(int depth) {
        return getException(exception, depth);
    }

    private Throwable getException(Exception exception, int depth) {
        if (depth==0) {
            return exception;
        } else {
            return exception.getCause();
        }
    }
}