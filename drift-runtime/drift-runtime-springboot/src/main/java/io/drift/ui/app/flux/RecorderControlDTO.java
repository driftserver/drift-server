package io.drift.ui.app.flux;

import java.io.Serializable;

public class RecorderControlDTO implements Serializable {

    private final boolean autosave;

    private final boolean connected;

    public RecorderControlDTO(boolean autosave, boolean connected) {
        this.autosave = autosave;
        this.connected = connected;
    }

    public boolean isAutosave() {
        return autosave;
    }

    public boolean isConnected() {
        return connected;
    }
}