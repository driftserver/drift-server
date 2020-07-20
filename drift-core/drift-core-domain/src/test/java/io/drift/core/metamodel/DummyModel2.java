package io.drift.core.metamodel;

import io.drift.core.metamodel.id.ModelId;

class DummyModel2 implements Model {

    private String att1;
    private String att2;

    @Override
    public ModelId getId() {
        return null;
    }

    public DummyModel2() {
        super();
    }

    public DummyModel2(String att1, String att2) {
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

}
