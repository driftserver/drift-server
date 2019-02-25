import io.drift.core.ui.DriftServer;
import io.drift.plugin.jdbc.DBSnapshotPlugin;

DriftServer server = new DriftServer();
DBSnapshotPlugin dbSnapshotPlugin = new DBSnapshotPlugin();

server.engine.registerPlugin(dbSnapshotPlugin);
server.registerPlugin(dbSnapshotPlugin);


return server;