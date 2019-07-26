package io.drift.plugin.jdbc.ui.app.flux.snapshot.graphmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBSnapshotGraph implements Serializable {

    private List<TableRoot> roots = new ArrayList<>();

    private List<ForeignKeyEdge> edges = new ArrayList<>();

    private Map<String, TableNode> nodes = new HashMap<>();

    public void addRoot(TableRoot root) {
        roots.add(root);
    }

    public void addNode(TableNode node) {
        nodes.put(node.getNodeName(), node);
    }

    public void addEdge(ForeignKeyEdge edge) {
        edges.add(edge);
    }

    public void connect() {
        for (TableRoot root : roots) {
            root.setToNode(nodes.get(root.getToNodeName()));
        }
        for(ForeignKeyEdge edge: edges) {
            edge.setFromNode(nodes.get(edge.getFromNodeName()));
            edge.setToNode(nodes.get(edge.getToNodeName()));
            edge.getFromNode().addEdge(edge);
        }
    }

    public List<TableRoot> getRoots() {
        return roots;
    }

}
