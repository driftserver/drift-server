package io.drift.plugin.jdbc;

import io.drift.core.store.ModelStore;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.storage.InMemoryModelStorage;
import io.drift.core.store.storage.StorageId;
import io.drift.plugin.jdbc.model.data.DBDelta;
import io.drift.plugin.jdbc.model.data.DBSnapShot;
import io.drift.plugin.jdbc.model.metadata.DBMetaData;
import junit.framework.TestCase;

public class StoreTest extends TestCase {

	public void test() throws ModelStoreException {
		ModelStore store = createStore();

		MockDBMetaDataBuilder dbMetaDataBuilder = new MockDBMetaDataBuilder();
		MockDBDeltaBuilder dbDeltaBuilder = new MockDBDeltaBuilder();

		DBMetaData dbMetaData = dbMetaDataBuilder.createDbMetaData();
		DBSnapShot dbSnapShot = dbDeltaBuilder.createDBSnapshot(dbMetaData);
		DBDelta dbDelta = dbDeltaBuilder.createDbDelta(dbMetaData);

		StorageId id1 = new StorageId("sessions", "1", "1");
		StorageId id2 = new StorageId("sessions", "1", "2");
		StorageId id3 = new StorageId("sessions", "1", "3");

		store.save(dbSnapShot, id1);
		store.save(dbDelta, id2);

		DBSnapShot dbSnapShot2 = store.load(id1, DBSnapShot.class);
		DBDelta dbDelta2 = store.load(id2, DBDelta.class);

	}

	private ModelStore createStore() {
		ModelStore store = new ModelStore();
		store.getSerializationManager().registerSerializer(new JsonModelSerializer());
		store.getModelStorageManager().registerStorage(new InMemoryModelStorage());
		return store;
	}

}
