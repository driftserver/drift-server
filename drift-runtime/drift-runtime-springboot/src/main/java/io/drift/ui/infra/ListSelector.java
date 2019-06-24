package io.drift.ui.infra;

import java.io.Serializable;

public class ListSelector<PARENT_SELECTOR_CLASS extends Selector > extends Selector<PARENT_SELECTOR_CLASS> implements Serializable {

    private Integer idx;

    private int minIdx;

    private int maxIdx;

    protected ListSelector() {}

    public ListSelector(PARENT_SELECTOR_CLASS parentSelector) {
        super(parentSelector);
    }

    public int getSelection() {
        return idx;
    }

    public boolean isSelected() {
        return idx != null;
    }

    public void decrease() {
        if (idx != null && idx > 0)
            idx--;
    }

    public void increase() {
        if (idx < maxIdx)
            idx++;
    }

    public void select(Integer idx) {
        this.idx = idx;
    }

    public void emptySelection() {
        idx = null;
    }

    public void setMaxIdx(int maxIdx) {
        this.maxIdx = maxIdx;
    }

    public int getMaxIdx() {
        return maxIdx;
    }
}
