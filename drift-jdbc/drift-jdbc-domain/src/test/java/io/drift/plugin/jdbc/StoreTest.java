package io.drift.plugin.jdbc;

import io.drift.core.infra.DriftCoreJacksonModule;
import io.drift.core.store.ModelStore;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.serialization.YamlModelSerializer;
import io.drift.core.store.storage.FileSystemModelStorage;
import io.drift.core.store.storage.ModelStorageException;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import io.drift.core.system.SystemDescription;
import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.infra.DriftJDBCJacksonModule;
import junit.framework.TestCase;
import org.junit.Assert;

public class StoreTest extends TestCase {

/*
	private ModelStore createStore() {
		ModelStore store = new ModelStore();
		JsonModelSerializer modelSerializer = new JsonModelSerializer();
		modelSerializer.registerModule(new DriftJDBCJacksonModule());
		store.getSerializationManager().registerSerializer(modelSerializer);
		store.getModelStorageManager().registerStorage(new FileSystemModelStorage());
		return store;
	}

	public void test_system_interaction() throws ModelStoreException {
		ModelStore store = createStore();

		MockDBMetaDataBuilder mockDBMetaDataBuilder = new MockDBMetaDataBuilder();
		MockDBDeltaBuilder mockDBDeltaBuilder = new MockDBDeltaBuilder();

		DBMetaData dbMetaData = mockDBMetaDataBuilder.createDbMetaData();
		DBSnapShot dbSnapShot = mockDBDeltaBuilder.createDBSnapshot(dbMetaData);
		DBDelta dbDelta = mockDBDeltaBuilder.createDbDelta(dbMetaData);

		StoragePath id1 = new StoragePath(new StorageId("recordings"), new StorageId("1"), new StorageId("1"));
		StoragePath id2 = new StoragePath(new StorageId("recordings"), new StorageId("1"), new StorageId("2"));
		StoragePath id3 = new StoragePath(new StorageId("recordings"), new StorageId("1"), new StorageId("3"));

		store.save(dbSnapShot, id1);
		store.save(dbDelta, id2);

		DBSnapShot dbSnapShot2 = store.load(id1, DBSnapShot.class);
		DBDelta dbDelta2 = store.load(id2, DBDelta.class);

	}

	public void test_system_description() throws ModelStoreException {
		ModelStore store = createStore();

		MockDBSystemDescriptionBuilder mockDBSystemDescriptionBuilder = new MockDBSystemDescriptionBuilder();
		SystemDescription dbSystemDescription = mockDBSystemDescriptionBuilder.createDummy();

		StoragePath id1 = new StoragePath(new StorageId("systemdescription"), new StorageId("v1"));

		store.save(dbSystemDescription, id1);
		SystemDescription dbSystemDescription2 = store.load(id1, SystemDescription.class);


	}
*/

    public void test_system_description_yaml_serialization() throws ModelStoreException {
        YamlModelSerializer yamlModelSerializer = createYamlSerializer();
        SystemDescription dbSystemDescription = generateMockSystemDescription();

        String content = yamlModelSerializer.from(dbSystemDescription);
        System.out.println(content);

        SystemDescription dbSystemDescription2 = (SystemDescription) yamlModelSerializer.loadModel(content, SystemDescription.class);
        String content2 = yamlModelSerializer.from(dbSystemDescription2);

        Assert.assertEquals(content, content2);

    }

    private SystemDescription generateMockSystemDescription() {
        MockDBSystemDescriptionBuilder mockDBSystemDescriptionBuilder = new MockDBSystemDescriptionBuilder();
        return mockDBSystemDescriptionBuilder.createDummy();
    }

    private YamlModelSerializer createYamlSerializer() {
        YamlModelSerializer yamlModelSerializer = new YamlModelSerializer();
        yamlModelSerializer.registerModule(new DriftCoreJacksonModule());
        yamlModelSerializer.registerModule(new DriftJDBCJacksonModule());
        return yamlModelSerializer;
    }

    public void test_parse_system_description() throws ModelStoreException {

        YamlModelSerializer yamlModelSerializer = createYamlSerializer();

        String content = "---\n" +
                "connectionDetails:\n" +
                "  PETSDB::LOCAL: !<JDBCConnectionDetails>\n" +
                "    userName: user1\n" +
                "    password: pwd\n" +
                "    jdbcUrl: jdbc:h2:tcp://localhost/./test\n" +
                "    tableNames:\n" +
                "    - OWNERS\n" +
                "    - PETS\n" +
                "    - VETS\n" +
                "  PRODUCTSDB::LOCAL: !<JDBCConnectionDetails>\n" +
                "    userName: user1\n" +
                "    password: pwd\n" +
                "    jdbcUrl: jdbc:h2:tcp://localhost/./test2\n" +
                "    tableNames:\n" +
                "    - CUSTOMER\n" +
                "    - PRODUCT\n" +
                "    - SUPPLIER\n" +
                "environments:\n" +
                "- key: LOCAL\n" +
                "  name: Local\n" +
                "- key: DEV\n" +
                "  name: Develop\n" +
                "subSystems:\n" +
                "- key: PETSDB\n" +
                "  type: jdbc\n" +
                "  name: Pets Database\n" +
                "- key: PRODUCTSDB\n" +
                "  type: jdbc\n" +
                "  name: Product Catalog\n"
                ;

        SystemDescription dbSystemDescription2 = (SystemDescription) yamlModelSerializer.loadModel(content, SystemDescription.class);

        String content2 = yamlModelSerializer.from(dbSystemDescription2);

        Assert.assertEquals(content, content2);

    }


}