package io.drift.core.system;

public class NullDetails  implements SubSystemConnectionDetails{

    private SubSystemEnvironmentKey subSystemEnvironmentKey;

    public NullDetails(SubSystemEnvironmentKey subSystemEnvironmentKey) {
        this.subSystemEnvironmentKey = subSystemEnvironmentKey;
    }

    @Override
    public SubSystemEnvironmentKey getKey() {
        return subSystemEnvironmentKey;
    }
}
