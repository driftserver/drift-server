package com.github.driftserver.jdbc;

import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelFormat;
import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.StandardCoreModule;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;
import com.github.driftserver.core.system.SystemDescription;
import com.github.driftserver.jdbc.domain.data.DBDelta;
import com.github.driftserver.jdbc.domain.data.DBSnapShot;
import com.github.driftserver.jdbc.domain.metadata.DBMetaData;
import com.github.driftserver.jdbc.infra.DriftJdbcModule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Path;

@RunWith(JUnit4.class)
public class StoreTest  {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private ModelStore modelStore;

    @Before
	public void setUp() throws IOException {

 		Path baseDir = tempFolder.newFolder().toPath();

        modelStore = ModelStore.builder()
            .withModules(new StandardCoreModule(baseDir), new DriftJdbcModule())
			.withModel(DBSnapShot.class, ModelFormat.JSON)
			.withModel(DBDelta.class, ModelFormat.JSON)
            .build();
	}

	@Test
	public void test_system_interaction() throws ModelException {

		MockDBMetaDataBuilder mockDBMetaDataBuilder = new MockDBMetaDataBuilder();
		MockDBDeltaBuilder mockDBDeltaBuilder = new MockDBDeltaBuilder();

		DBMetaData dbMetaData = mockDBMetaDataBuilder.createDbMetaData();
		DBSnapShot dbSnapShot = mockDBDeltaBuilder.createDBSnapshot(dbMetaData);
		DBDelta dbDelta = mockDBDeltaBuilder.createDbDelta(dbMetaData);

		ModelURN id1 = new ModelURN(new ModelId("recordings"), new ModelId("1"), new ModelId("1"));
		ModelURN id2 = new ModelURN(new ModelId("recordings"), new ModelId("1"), new ModelId("2"));
		ModelURN id3 = new ModelURN(new ModelId("recordings"), new ModelId("1"), new ModelId("3"));

		modelStore.write(dbSnapShot, id1);
        modelStore.write(dbDelta, id2);

		DBSnapShot dbSnapShot2 = modelStore.read(id1, DBSnapShot.class);
		DBDelta dbDelta2 = modelStore.read(id2, DBDelta.class);

	}

    @Test
	public void test_system_description() throws ModelException {

		MockDBSystemDescriptionBuilder mockDBSystemDescriptionBuilder = new MockDBSystemDescriptionBuilder();
		SystemDescription dbSystemDescription = mockDBSystemDescriptionBuilder.createDummy();

		ModelURN id1 = new ModelURN(new ModelId("systemdescription"), new ModelId("v1"));

        modelStore.write(dbSystemDescription, id1);
		SystemDescription dbSystemDescription2 = modelStore.read(id1, SystemDescription.class);

	}



}