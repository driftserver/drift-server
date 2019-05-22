package io.drift.core.system;

public class Environment {

    private EnvironmentKey key;

    public EnvironmentKey getKey() {
        return key;
    }

    public void setKey(EnvironmentKey key) {
        this.key = key;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
