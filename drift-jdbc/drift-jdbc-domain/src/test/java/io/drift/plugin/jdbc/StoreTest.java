package io.drift.plugin.jdbc;

import io.drift.core.store.ModelStore;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.storage.InMemoryModelStorage;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.metadata.DBMetaData;
import junit.framework.TestCase;

public class StoreTest extends TestCase {

	private ModelStore createStore() {
		ModelStore store = new ModelStore();
		store.getSerializationManager().registerSerializer(new JsonModelSerializer());
		store.getModelStorageManager().registerStorage(new InMemoryModelStorage());
		return store;
	}

	public void test() throws ModelStoreException {
		ModelStore store = createStore();

		MockDBMetaDataBuilder dbMetaDataBuilder = new MockDBMetaDataBuilder();
		MockDBDeltaBuilder dbDeltaBuilder = new MockDBDeltaBuilder();

		DBMetaData dbMetaData = dbMetaDataBuilder.createDbMetaData();
		DBSnapShot dbSnapShot = dbDeltaBuilder.createDBSnapshot(dbMetaData);
		DBDelta dbDelta = dbDeltaBuilder.createDbDelta(dbMetaData);

		StoragePath id1 = new StoragePath(new StorageId("recordings"), new StorageId("1"), new StorageId("1"));
		StoragePath id2 = new StoragePath(new StorageId("recordings"), new StorageId("1"), new StorageId("2"));
		StoragePath id3 = new StoragePath(new StorageId("recordings"), new StorageId("1"), new StorageId("3"));

		store.save(dbSnapShot, id1);
		store.save(dbDelta, id2);

		DBSnapShot dbSnapShot2 = store.load(id1, DBSnapShot.class);
		DBDelta dbDelta2 = store.load(id2, DBDelta.class);

	}

}
