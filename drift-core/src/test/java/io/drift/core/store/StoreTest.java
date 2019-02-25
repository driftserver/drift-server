package io.drift.core.store;

import io.drift.core.api.Model;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.storage.InMemoryModelStorage;
import io.drift.core.store.storage.StorageId;
import junit.framework.TestCase;

import org.junit.Assert;

public class StoreTest extends TestCase {

	public void testSaveThenLoad() throws ModelStoreException {
		ModelStore store = createStore();

		StorageId storageId = new StorageId("a", "b");

		String att1 = "att1";
		String att2 = "att2";
		Model model = new MyModel(att1, att2);
		store.save(model, storageId);

		MyModel model2 = store.load(storageId, MyModel.class);

		Assert.assertEquals(MyModel.class, model2.getClass());
		MyModel myModel2 = (MyModel) model2;
		Assert.assertEquals(att1, myModel2.getAtt1());
		Assert.assertEquals(att2, myModel2.getAtt2());

	}

	public void testSaveThenGetMetaData() throws ModelStoreException {
		ModelStore store = createStore();

		StorageId storageId = new StorageId("a", "b");

		String att1 = "att1";
		String att2 = "att2";
		Model model = new MyModel(att1, att2);
		store.save(model, storageId);

		MetaModel metaModel = store.getMetaModel(storageId);
		Assert.assertNotNull(metaModel);
		Assert.assertEquals(MyModel.class.toString(), metaModel.getModelType());
	}

	private ModelStore createStore() {
		ModelStore store = new ModelStore();

		store.getSerializationManager().registerSerializer(new JsonModelSerializer());
		store.getModelStorageManager().registerStorage(new InMemoryModelStorage());
		store.getMetaDataManager().registerMetaDataCreator(MyModel.class, new MyModelMetaModelCreator());
		return store;
	}

}
