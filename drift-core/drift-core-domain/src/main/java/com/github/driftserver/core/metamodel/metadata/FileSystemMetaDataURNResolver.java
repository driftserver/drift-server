package com.github.driftserver.core.metamodel.metadata;

import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileSystemMetaDataURNResolver {

    private Path baseDir;

    public FileSystemMetaDataURNResolver(Path baseDir) {
        this.baseDir = baseDir;
    }

    public Writer getWriter(ModelURN parentURN) throws ModelException {
        try {
            Path path = getMetaDataPath(parentURN);
            Files.createDirectories(path.getParent());
            return new BufferedWriter(new FileWriter(path.toFile()));
        } catch (IOException e) {
            throw new ModelException(String.format("error resolving parent urn to filesystem writer. urn=%s", parentURN), e);
        }
    }

    public Optional<Reader> getReader(ModelURN parentURN) throws ModelException  {
        try {
            Path path = getMetaDataPath(parentURN);
            return Optional.of(new BufferedReader(new FileReader(path.toFile())));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    private Path getMetaDataPath(ModelURN parentURN) {
        Path path = baseDir;
        for (ModelId pathSegment : parentURN.getFragments()) {
            path = path.resolve(pathSegment.getId());
        }
        return path.resolve("metadata.json");
    }


}
