package com.github.driftserver.jdbc.ui.app.flux.snapshot.viewpart;

import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.TableRoot;

import java.io.Serializable;
import java.util.List;

public class RootsViewPart extends ViewPart implements Serializable {

    private List<TableRoot> roots;

    public RootsViewPart(List<TableRoot> roots) {
        this.roots = roots;
    }

    public List<TableRoot> getRoots() {
        return roots;
    }

    public void select(TableRoot rootDescriptor) {
        DBSnapshotMainViewPart mainViewPart = (DBSnapshotMainViewPart) getParent();
        mainViewPart.selectRoot(rootDescriptor);
    }

}
