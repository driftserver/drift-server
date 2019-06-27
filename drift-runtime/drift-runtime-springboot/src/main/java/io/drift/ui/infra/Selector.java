package io.drift.ui.infra;

public class Selector<PARENT_SELECTOR_CLASS extends Selector > {

    public static final String KEY_LEFT = "37";
    public static final String KEY_UP = "38";
    public static final String KEY_RIGHT = "39";
    public static final String KEY_DOWN = "40";

    private boolean focus;

    private PARENT_SELECTOR_CLASS parentSelector;

    public Selector(PARENT_SELECTOR_CLASS parentSelector) {
        this.parentSelector = parentSelector;
    }

    public PARENT_SELECTOR_CLASS getParentselector() { return parentSelector; }

    protected Selector() {}

    public boolean hasFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
}
