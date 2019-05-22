package io.drift.core.system;

public class SubSystem {

    private SubSystemKey key;

    public SubSystemKey getKey() {
        return key;
    }

    public void setKey(SubSystemKey key) {
        this.key = key;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
