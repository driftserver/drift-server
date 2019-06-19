package io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart;

import io.drift.jdbc.domain.data.TableSnapShot;
import io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor.TableViewDescriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableViewPart extends ViewPart implements Serializable {

    private List<OneToManyRelationViewPart> relations = new ArrayList<>();

    private TableSnapShot snapShot;
    private TableViewDescriptor descriptor;


    public TableViewPart() {
    }

    public TableViewPart(TableSnapShot snapShot, TableViewDescriptor descriptor) {
        this.snapShot = snapShot;
        this.descriptor = descriptor;
        this.relations = descriptor.getForeignKeys().stream()
                .map(fk -> new OneToManyRelationViewPart(fk.getName()))
                .collect(Collectors.toList());
    }

    public String getName() {
        return snapShot.getTable();
    }

    public List<OneToManyRelationViewPart> getRelations() {
        return relations;
    }

    public void addRelation(OneToManyRelationViewPart relation) {
        relations.add(relation);
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void selectRelation(String name) {
        RootsViewPart rootsViewPart = (RootsViewPart)getParent();
        OneToManyRelationViewPart svRelation = relations.stream().filter(rel -> rel.getName().equals(name)).findFirst().get();
    }
}
