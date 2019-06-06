package io.drift.core.store;

import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.serialization.YamlModelSerializer;
import io.drift.core.store.storage.FileSystemModelStorage;
import io.drift.core.store.storage.LuceneMetaDataStorage;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import io.drift.core.system.SystemDescription;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static io.drift.core.store.serialization.JsonModelSerializer.JSON_FORMAT;
import static io.drift.core.store.serialization.YamlModelSerializer.YAML_FORMAT;

public class StoreTest extends TestCase {

	private Path storeBaseDir;
	private Path luceneIndexDir;
	private Path fileStorageDir;

	private ModelStore store;

	protected void setUp() throws Exception {
		storeBaseDir = Files.createTempDirectory("store");

		luceneIndexDir = storeBaseDir.resolve("lucene");
		fileStorageDir = storeBaseDir.resolve("files");

		Files.createDirectories(luceneIndexDir);
		Files.createDirectories(fileStorageDir);

		System.out.println("storageBaseDir: " + storeBaseDir);

		store = createStore();
	}

	protected void tearDown() throws Exception {

		store.shutDown();

		// TimeUnit.SECONDS.sleep(5);


		System.out.println("deleting: " + storeBaseDir);
		// Files.delete(storeBaseDir);
		recursiveDelete(storeBaseDir);
		return;
	}

	private ModelStore createStore() {

		JsonModelSerializer jsonModelSerializer = new JsonModelSerializer();
		YamlModelSerializer yamlModelSerializer = new YamlModelSerializer();

		return new ModelStore()

				.withSerializer(jsonModelSerializer)
				.withSerializer(yamlModelSerializer)

				.withDefaultFormat(JSON_FORMAT)
				.withFormatForClass(SystemDescription.class, YAML_FORMAT)

				.withModelStorage(new FileSystemModelStorage(fileStorageDir))

				.withMetaDataStorage(new LuceneMetaDataStorage(luceneIndexDir));

	}

	public void testSaveThenGetMetaData() throws ModelStoreException {

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

	private void recursiveDelete(Path baseDir) throws IOException {
		Files.walkFileTree(baseDir, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				// System.out.println("deleting dir " + dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

}
