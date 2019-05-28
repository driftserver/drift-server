package io.drift.core.store;

import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.storage.FileSystemModelStorage;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import junit.framework.TestCase;
import org.junit.Assert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class StoreTest extends TestCase {

	private Path modelStorageDir;

	protected void setUp() throws Exception {
		modelStorageDir = Files.createTempDirectory("model");
		System.out.println("modelStorageDir: " + modelStorageDir);
	}

	private ModelStore createStore() {
		ModelStore store = new ModelStore();

		store.getSerializationManager().registerSerializer(new JsonModelSerializer());
		store.getModelStorageManager().registerStorage(new FileSystemModelStorage(modelStorageDir));
		return store;
	}

	public void testSaveThenGetMetaData() throws ModelStoreException {
		ModelStore store = createStore();

		StorageId storageId = new StorageId(UUID.randomUUID().toString());
		StoragePath parentPath = new StoragePath(new StorageId("a"), new StorageId("b"));

		String att1 = "att1";
		String att2 = "att2";
		MyModel model = new MyModel(att1, att2);
		MetaData metaData = MetaData.builder()
				.withPath(parentPath)
				.withStorageId(storageId)
				.build();
		store.save(model, metaData);

		MetaData metaData1 = store.getMetaData(storageId);
		Assert.assertNotNull(metaData1);
		Assert.assertEquals(metaData1.getStorageId(), metaData.getStorageId());
	}

	public void testSaveThenLoad() throws ModelStoreException {
		ModelStore store = createStore();

		StorageId storageId = new StorageId("id");
		StoragePath parentPath = new StoragePath(new StorageId("a"), new StorageId("b"));

		String att1 = "att1";
		String att2 = "att2";
		MyModel model = new MyModel(att1, att2);
		MetaData metaData = MetaData.builder()
				.withPath(parentPath)
				.withStorageId(storageId)
				.build();
		store.save(model, metaData);

		MyModel model2 = store.load(storageId, MyModel.class);

		Assert.assertEquals(MyModel.class, model2.getClass());
		MyModel myModel2 = model2;
		Assert.assertEquals(att1, myModel2.getAtt1());
		Assert.assertEquals(att2, myModel2.getAtt2());

	}

}
