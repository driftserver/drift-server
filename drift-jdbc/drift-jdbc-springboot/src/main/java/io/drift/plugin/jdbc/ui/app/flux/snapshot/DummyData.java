package io.drift.plugin.jdbc.ui.app.flux.snapshot;

import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.data.Row;
import io.drift.jdbc.domain.data.TableSnapShot;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor.FKViewDescriptor;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor.RootDescriptor;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor.SnapshotViewDescriptor;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor.TableViewDescriptor;

public class DummyData {

    private DBSnapShot dbSnapShot;
    private SnapshotViewDescriptor dbDescriptor;

    public DummyData() {
        initDBSnapshot();
        initDescriptor();
    }

    private void initDescriptor() {
        dbDescriptor = new SnapshotViewDescriptor();
        {
            RootDescriptor allOwners = new RootDescriptor("owners", "OWNER");
            dbDescriptor.addRoot(allOwners);
        }
        {
            TableViewDescriptor ownerTable = new TableViewDescriptor("OWNER");
            ownerTable.addFk(new FKViewDescriptor("pets", "PET"));
            dbDescriptor.addTable(ownerTable);
        }
        {
            TableViewDescriptor petsTable = new TableViewDescriptor("PET");
            dbDescriptor.addTable(petsTable);
        }
        dbDescriptor.connect();
    }

    private void initDBSnapshot() {
        dbSnapShot = new DBSnapShot();
        {
            TableSnapShot ownersData = new TableSnapShot("OWNER");
            {
                Row owner1 = new Row();
                owner1.addValue("id", "1");
                owner1.addValue("name", "Alice");
                ownersData.addRow(owner1);
            }
            {
                Row owner2 = new Row();
                owner2.addValue("id", "2");
                owner2.addValue("name", "Bob");
                ownersData.addRow(owner2);
            }
            dbSnapShot.add(ownersData);
        }
        {
            TableSnapShot petsData = new TableSnapShot("PET");
            {
                Row pet1 = new Row();
                pet1.addValue("id", "1");
                pet1.addValue("name", "Leo");
                pet1.addValue("ownerId", "1");
                petsData.addRow(pet1);
            }
            {
                Row pet2 = new Row();
                pet2.addValue("id", "2");
                pet2.addValue("name", "Blub");
                pet2.addValue("ownerId", "1");
                petsData.addRow(pet2);
            }
            {
                Row pet3 = new Row();
                pet3.addValue("id", "3");
                pet3.addValue("name", "Bobby");
                pet3.addValue("ownerId", "2");
                petsData.addRow(pet3);
            }
            dbSnapShot.add(petsData);
        }
    }

    public SnapshotViewDescriptor getDbDescriptor() {
        return dbDescriptor;
    }

    public DBSnapShot getDbSnapShot() {
        return dbSnapShot;
    }

}
