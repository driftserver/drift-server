package com.github.driftserver.core.metamodel;

import com.github.driftserver.core.TestSubjects;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Path;

@RunWith(JUnit4.class)
public class ModelStoreTest extends TestCase {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private ModelStore modelStore;

    @Before
    public void setUp() throws Exception {
        Path baseDir = tempFolder.newFolder().toPath();
        modelStore = TestSubjects.aModelStore(new StandardCoreModule(baseDir), new DummyModule());
    }

    @Test
    public void test_write_then_read() throws ModelException{
        ModelURN urn = new ModelURN(new ModelId("a"), new ModelId("id1"));

        DummyModel1 before = TestSubjects.aDummyModel1();
        modelStore.write(before, urn);

        DummyModel1 after = modelStore.read(urn, DummyModel1.class);

        System.out.println(after);


    }


}
