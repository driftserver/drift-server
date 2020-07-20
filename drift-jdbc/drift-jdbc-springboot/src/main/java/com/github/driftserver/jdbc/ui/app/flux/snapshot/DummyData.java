package com.github.driftserver.jdbc.ui.app.flux.snapshot;

import com.github.driftserver.jdbc.domain.data.DBSnapShot;
import com.github.driftserver.jdbc.domain.data.Row;
import com.github.driftserver.jdbc.domain.data.TableSnapShot;
import com.github.driftserver.jdbc.domain.metadata.ColumnMetaData;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.DBSnapshotGraph;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.ForeignKeyEdge;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.TableNode;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.TableRoot;

public class DummyData {

    private DBSnapShot dbSnapShot;
    private DBSnapshotGraph dbDescriptor;

    public DummyData() {
        initDBSnapshot();
        initDescriptor();
    }

    private void initDescriptor() {
        dbDescriptor = new DBSnapshotGraph();
        {
            TableRoot allOwners = new TableRoot("owners", "OWNER");
            dbDescriptor.addRoot(allOwners);
        }
        {
            TableNode ownerTable = new TableNode("OWNER");
            dbDescriptor.addNode(ownerTable);
            {
                ColumnMetaData idCol = new ColumnMetaData("id", "String", 0);
                ownerTable.getColumns().add(idCol);
            }
            {
                ColumnMetaData nameCol = new ColumnMetaData("name", "String", 0);
                ownerTable.getColumns().add(nameCol);
            }
        }
        {

            TableNode petsTable = new TableNode("PET");
            dbDescriptor.addNode(petsTable);
            {
                ColumnMetaData idCol = new ColumnMetaData("id", "String", 0);
                petsTable.getColumns().add(idCol);
            }
            {
                ColumnMetaData nameCol = new ColumnMetaData("name", "String", 0);
                petsTable.getColumns().add(nameCol);
            }
        }
        {
            ForeignKeyEdge ownersPets  = new ForeignKeyEdge("OWNER", "PET", "pets", "ownerId");
            dbDescriptor.addEdge(ownersPets);
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

    public DBSnapshotGraph getDbDescriptor() {
        return dbDescriptor;
    }

    public DBSnapShot getDbSnapShot() {
        return dbSnapShot;
    }

}
