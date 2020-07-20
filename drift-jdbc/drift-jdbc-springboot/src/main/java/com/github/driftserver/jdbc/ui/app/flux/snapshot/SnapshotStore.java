package com.github.driftserver.jdbc.ui.app.flux.snapshot;

import com.github.driftserver.jdbc.domain.data.DBSnapShot;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.DBSnapshotGraph;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.viewpart.DBSnapshotMainViewPart;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.viewpart.RootsViewPart;
import org.springframework.stereotype.Component;

@Component
public class SnapshotStore {

    private DBSnapshotMainViewPart mainViewPart;
    private final RootsViewPart rootsViewPart;

    public SnapshotStore() {

        DummyData dummyData = new DummyData();
        DBSnapShot dbSnapShot = dummyData.getDbSnapShot();
        DBSnapshotGraph viewDescriptor = dummyData.getDbDescriptor();

        mainViewPart = new DBSnapshotMainViewPart(dbSnapShot);

        rootsViewPart = new RootsViewPart(viewDescriptor.getRoots());
        mainViewPart.add(rootsViewPart);

        /*
        {
            TableViewPart owners = new TableViewPart();
            owners.setName("OWNER");

            owners.addEdge(new OneToManyRelationViewPart("pets"));
            mainViewPart.add(owners);
        }
        */
    }

    public DBSnapshotMainViewPart getMainViewPart() {
        return mainViewPart;
    }


    /*
    public void selectEdge(OneToManyRelationViewPart svRelation) {
        if (svRelation.getName().equals("pets")) {
            if (svRelation.isActive()) {
                mainViewPart.getTables().remove(mainViewPart.getTables().size()-1);
                svRelation.setActive(false);
            } else {
                TableViewPart pets = new TableViewPart();
                pets.setName("PET");
                pets.addEdge(new OneToManyRelationViewPart("visits"));
                mainViewPart.add(pets);
                svRelation.setActive(true);
            }
        } else if(svRelation.getName().equals("visits")) {
            if (svRelation.isActive()) {
                mainViewPart.getTables().remove(mainViewPart.getTables().size()-1);
                svRelation.setActive(false);
            } else {
                TableViewPart visits = new TableViewPart();
                visits.setName("VISITS");
                mainViewPart.add(visits);
                svRelation.setActive(true);
            }
        }
    }
    */
}
