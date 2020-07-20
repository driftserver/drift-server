package com.github.driftserver.core.metamodel.urn;

import com.github.driftserver.core.metamodel.id.ModelId;
import junit.framework.TestCase;

import java.nio.file.Paths;

public class FileSystemModelURNResolverTest extends TestCase {

    public static final String BASE_DIR = "some/dir";
    private FileSystemModelURNResolver resolver;

    protected void setUp() {
        resolver = new FileSystemModelURNResolver(Paths.get(BASE_DIR));
    }


    public void test() {

        String path1 = "A";
        String path2 = "A";
        String id = "id";

        ModelURN urn = new ModelURN(new ModelId(path1), new ModelId(path2));
        ModelId modelId = new ModelId(id);
    }

}
