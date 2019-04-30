package io.drift.jdbc.usecase;

import io.drift.core.store.IDGenerator;
import io.drift.core.store.ModelStore;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.data.DBDeltaId;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.session.DataCaptureSession;
import io.drift.jdbc.domain.session.SessionId;

public class SnapshotWorkspace {

	public static final StorageId STORAGE_ID_METADATA = new StorageId("metadata");
	public static final StorageId STORAGE_ID_SNAPSHOTS = new StorageId("snapshots");
	public static final StorageId STORAGE_ID_WORKSPACE = new StorageId("workspace");

	private IDGenerator idGenerator;

	private ModelStore modelStore;

	public SnapshotWorkspace(ModelStore modelStore, IDGenerator idGenerator) {
		this.modelStore = modelStore;
		this.idGenerator = idGenerator;
	}

	public DataCaptureSession createSession() {
		return new DataCaptureSession(new SessionId(idGenerator.createId()));
	}

	public DBDelta loadDBDelta(SessionId sessionId, DBDeltaId dbDeltaId) throws ModelStoreException {
		return modelStore.load(resolveDbDeltaStoragePath(sessionId, dbDeltaId), DBDelta.class);
	}

	public DBMetaData loadDBMetadata() throws ModelStoreException {
		return modelStore.load(resolveDBMetaDataStoragPath(), DBMetaData.class);
	}

	private StoragePath resolveDbDeltaStoragePath(SessionId sessionId, DBDeltaId dbDeltaId) {
		return resolveSessionPath(sessionId).resolve(dbDeltaId);
	}

	private StoragePath resolveDBMetaDataStoragPath() {
		return resolveWorkspacesPath().resolve(STORAGE_ID_METADATA);
	}

	private StoragePath resolveSessionPath(SessionId sessionId) {
		return resolveWorkspacesPath().resolve(sessionId);
	}

	private StoragePath resolveWorkspacesPath() {
		return StoragePath.of(STORAGE_ID_WORKSPACE);
	}

	public void storeDBDelta(SessionId sessionId, DBDelta dbDelta) throws ModelStoreException {
		modelStore.save(dbDelta, resolveDbDeltaStoragePath(sessionId, dbDelta.getId()));
	}

	public void storeDBMetadata(DBMetaData dbMetaData) throws ModelStoreException {
		modelStore.save(dbMetaData, resolveDBMetaDataStoragPath());
	}

	public void storeSession(DataCaptureSession session) throws ModelStoreException {
		modelStore.save(session, resolveSessionPath(session.getId()));
	}

}
