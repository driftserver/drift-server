package io.drift.ui.app.flux;

import java.io.Serializable;

public class RecorderControlDTO implements Serializable {

    private final boolean autosave;

    public RecorderControlDTO(boolean autosave) {
        this.autosave = autosave;
    }

    public boolean isAutosave() {
        return autosave;
    }

}
