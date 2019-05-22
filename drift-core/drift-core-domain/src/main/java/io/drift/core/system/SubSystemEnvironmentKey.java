package io.drift.core.system;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class SubSystemEnvironmentKey {

    private EnvironmentKey environmentKey;

    private SubSystemKey subSystemKey;

    public SubSystemEnvironmentKey(SubSystemKey subSystemKey, EnvironmentKey environmentKey) {
        this.environmentKey = environmentKey;
        this.subSystemKey = subSystemKey;
    }

    public EnvironmentKey getEnvironmentKey() {
        return environmentKey;
    }

    public SubSystemKey getSubSystemKey() {
        return subSystemKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubSystemEnvironmentKey that = (SubSystemEnvironmentKey) o;
        return Objects.equals(environmentKey, that.environmentKey) &&
                Objects.equals(subSystemKey, that.subSystemKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(environmentKey, subSystemKey);
    }

    public String toString() {
        return subSystemKey.getName() + "::" + environmentKey.getName();
    }
}
