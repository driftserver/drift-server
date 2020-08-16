package com.github.driftserver.core.recording;

import com.github.driftserver.core.recording.model.SystemState;

public class DummySystemState1 extends SystemState {

    private String att1;
    private String att2;

    public DummySystemState1() {
        super();
    }

    public DummySystemState1(String att1, String att2) {
        super();
        this.att1 = att1;
        this.att2 = att2;
    }

    public String getAtt1() {
        return att1;
    }

    public String getAtt2() {
        return att2;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }

    @Override
    public String toString() {
        return "DummySystemState1{" +
                "att1='" + att1 + '\'' +
                ", att2='" + att2 + '\'' +
                '}';
    }
}
