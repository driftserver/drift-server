package io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart;

import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.data.TableSnapShot;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor.RootDescriptor;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor.TableViewDescriptor;

import java.io.Serializable;
import java.util.List;

public class RootsViewPart extends ViewPart implements Serializable {

    private List<RootDescriptor> roots;

    private DBSnapShot dbSnapShot;

    public RootsViewPart(List<RootDescriptor> roots, DBSnapShot dbSnapShot) {
        this.roots = roots;
        this.dbSnapShot = dbSnapShot;
    }

    public List<RootDescriptor> getRoots() {
        return roots;
    }

    public void select(RootDescriptor rootDescriptor) {
        String tableName = rootDescriptor.getTableName();
        TableViewDescriptor tableDescriptor = rootDescriptor.getTableDescriptor();
        TableSnapShot tableSnapShot = dbSnapShot.getTableSnapShotFor(tableName);
        // addPart(new TableViewPart(tableSnapShot, tableDescriptor));
    }

}
