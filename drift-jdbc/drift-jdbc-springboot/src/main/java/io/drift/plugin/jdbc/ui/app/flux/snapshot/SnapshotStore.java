package io.drift.plugin.jdbc.ui.app.flux.snapshot;

import io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor.SnapshotViewDescriptor;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart.*;
import org.springframework.stereotype.Component;

@Component
public class SnapshotStore {

    private DBSnapshotMainViewPart svModel;
    private final RootsViewPart rootsViewPart;

    public SnapshotStore() {

        DummyData dummyData = new DummyData();
        SnapshotViewDescriptor viewDescriptor = dummyData.getDbDescriptor();

        svModel = new DBSnapshotMainViewPart();

        rootsViewPart = new RootsViewPart(viewDescriptor.getRoots(), dummyData.getDbSnapShot());

        {
            TableViewPart owners = new TableViewPart();
            owners.setName("OWNER");

            owners.addRelation(new OneToManyRelationViewPart("pets"));
            svModel.add(owners);
        }
    }

    public DBSnapshotMainViewPart getViewModel() {
        return svModel;
    }

    public ViewPart getViewPart() {
        return rootsViewPart;
    }

    public void selectRelation(OneToManyRelationViewPart svRelation) {
        if (svRelation.getName().equals("pets")) {
            if (svRelation.isActive()) {
                svModel.getTables().remove(svModel.getTables().size()-1);
                svRelation.setActive(false);
            } else {
                TableViewPart pets = new TableViewPart();
                pets.setName("PET");
                pets.addRelation(new OneToManyRelationViewPart("visits"));
                svModel.add(pets);
                svRelation.setActive(true);
            }
        } else if(svRelation.getName().equals("visits")) {
            if (svRelation.isActive()) {
                svModel.getTables().remove(svModel.getTables().size()-1);
                svRelation.setActive(false);
            } else {
                TableViewPart visits = new TableViewPart();
                visits.setName("VISITS");
                svModel.add(visits);
                svRelation.setActive(true);
            }
        }
    }
}
