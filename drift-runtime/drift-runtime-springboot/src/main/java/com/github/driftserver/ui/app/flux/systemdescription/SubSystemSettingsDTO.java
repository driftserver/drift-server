package com.github.driftserver.ui.app.flux.systemdescription;

import com.github.driftserver.core.system.SubSystemConnectionDetails;
import com.github.driftserver.core.system.SubSystemKey;

import java.io.Serializable;

public class SubSystemSettingsDTO implements Serializable {

    private SubSystemKey subSystemKey;

    private SubSystemConnectionDetails connectionDetails;

    public SubSystemSettingsDTO(SubSystemKey subSystemKey, SubSystemConnectionDetails connectionDetails) {
        this.subSystemKey = subSystemKey;
        this.connectionDetails = connectionDetails;
    }

    public SubSystemKey getSubSystemKey() {
        return subSystemKey;
    }

    public SubSystemConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }

}
