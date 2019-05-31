package io.drift.ui.infra;

public class Selector<PARENT_SELECTOR_CLASS extends Selector > {

    private PARENT_SELECTOR_CLASS parentSelector;

    public Selector(PARENT_SELECTOR_CLASS parentSelector) {
        this.parentSelector = parentSelector;
    }

    public PARENT_SELECTOR_CLASS getParentselector() { return parentSelector; }

}
