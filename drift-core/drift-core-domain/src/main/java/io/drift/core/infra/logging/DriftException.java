package io.drift.core.infra.logging;

public class DriftException extends Exception {

    private String code;

    private String description;

    public DriftException(String code, String description, Throwable cause) {
        super(code + ": " + description, cause);
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
