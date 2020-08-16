package com.github.driftserver.core.metamodel.urn;

import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelFormat;
import com.github.driftserver.core.metamodel.id.ModelId;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemModelURNResolver implements ModelURNResolver {

    private Path baseDir;

    public FileSystemModelURNResolver(Path baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public Reader getReader(ModelURN urn, ModelFormat modelFormat) throws ModelException {
        try {
            Path path = getPath(urn, modelFormat);
            return new BufferedReader(new FileReader(path.toFile()));
        } catch (IOException e) {
            throw new ModelException(String.format("error resolving urn to filesystem reader. urn=%s, format=%s", urn, modelFormat), e);
        }
    }

    @Override
    public Writer getWriter(ModelURN urn, ModelFormat format) throws ModelException {
        try {
            Path path = getPath(urn, format);
            Files.createDirectories(path.getParent());
            return new BufferedWriter(new FileWriter(path.toFile()));
        } catch (IOException e) {
            throw new ModelException(String.format("error resolving urn to filesystem writer. urn=%s, format=%s", urn, format), e);
        }
    }

    Path getPath(ModelURN urn, ModelFormat format) {
        ModelURN parentUrn = urn.getParentURN();
        ModelId modelId = urn.getLastFragment();
        Path path = getParentPath(parentUrn).resolve(getFileName(modelId, format));
        return path;
    }

    Path getParentPath(ModelURN parentURN) {
        Path path = baseDir;
        for (ModelId pathSegment : parentURN.getFragments()) {
            path = path.resolve(pathSegment.getId());
        }
        return path;
    }

    String getFileName(ModelId modelId, ModelFormat format) {
        return modelId.getId() + "." + format.getFileExtension();
    }

}
