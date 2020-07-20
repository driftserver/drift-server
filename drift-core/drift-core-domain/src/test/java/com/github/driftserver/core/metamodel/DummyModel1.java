package com.github.driftserver.core.metamodel;

import com.github.driftserver.core.metamodel.id.ModelId;

public class DummyModel1 implements Model {

    private String att1;
    private String att2;

    @Override
    public ModelId getId() {
        return null;
    }

    public DummyModel1() {
        super();
    }

    public DummyModel1(String att1, String att2) {
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
        return "DummyModel1{" +
                "att1='" + att1 + '\'' +
                ", att2='" + att2 + '\'' +
                '}';
    }
}
