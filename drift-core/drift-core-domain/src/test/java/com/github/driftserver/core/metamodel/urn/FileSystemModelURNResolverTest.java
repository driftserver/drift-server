package com.github.driftserver.core.metamodel.urn;

import com.github.driftserver.core.TestSubjects;
import com.github.driftserver.core.metamodel.DummyModel1;
import com.github.driftserver.core.metamodel.ModelFormat;
import com.github.driftserver.core.metamodel.id.ModelId;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Path;

@RunWith(JUnit4.class)
public class FileSystemModelURNResolverTest extends TestCase {

    private FileSystemModelURNResolver resolver;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        Path baseDir = tempFolder.newFolder().toPath();
        resolver = new FileSystemModelURNResolver(baseDir);
    }


    @Test
    public void test() {

        ModelURN parentUrn = TestSubjects.aParentModelURN();

        String id = "id";
        ModelId modelId = new ModelId(id);

        ModelURN modelURN = parentUrn.resolve(modelId);

        Path path = resolver.getPath(modelURN, ModelFormat.JSON, DummyModel1.class);
        System.out.println(path);
        assert(path.toString().endsWith(id + "-dummymodel1.json"));
    }

}
